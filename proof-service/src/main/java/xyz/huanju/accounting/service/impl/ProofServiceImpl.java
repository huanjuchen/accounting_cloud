package xyz.huanju.accounting.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import xyz.huanju.accounting.dao.ProofDAO;
import xyz.huanju.accounting.dao.ProofItemDAO;
import xyz.huanju.accounting.domain.Proof;
import xyz.huanju.accounting.domain.ProofItem;
import xyz.huanju.accounting.domain.Subject;
import xyz.huanju.accounting.domain.User;
import xyz.huanju.accounting.domain.mq.ProofMsg;
import xyz.huanju.accounting.domain.response.ResultCode;
import xyz.huanju.accounting.exception.AccountingException;
import xyz.huanju.accounting.exception.BadCreateException;
import xyz.huanju.accounting.exception.BadUpdateException;
import xyz.huanju.accounting.exception.NotFoundException;
import xyz.huanju.accounting.service.ProofMqService;
import xyz.huanju.accounting.service.ProofService;
import xyz.huanju.accounting.service.SubjectService;
import xyz.huanju.accounting.service.UserService;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author HuanJu
 * @date 2020/8/11 19:58
 */
@Service
@Slf4j
@Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.SUPPORTS, readOnly = true)
public class ProofServiceImpl implements ProofService {

    @Resource
    private ProofDAO proofDAO;

    @Resource
    private ProofItemDAO proofItemDAO;

    @Resource
    private UserService userService;

    @Resource
    private SubjectService subjectService;

    @Resource
    private ProofMqService proofMqService;


    private static final int CLS = 1;

    private static final int DLS = 2;

    private static final int CSS = 3;

    private static final int DSS = 4;

    private static final Map<Integer, String> MSG_MAP = new HashMap<>(1 >> 3);

    static {
        MSG_MAP.put(CLS, "贷方总账科目");
        MSG_MAP.put(DLS, "借方总账科目");
        MSG_MAP.put(CSS, "贷方明细账科目");
        MSG_MAP.put(DSS, "借方明细账科目");

    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRED)
    public void save(Proof proof, String tokenId) {
        //获取创建人
        User user = userService.getUserByToken(tokenId);
        if (user == null) {
            throw new BadCreateException(ResultCode.INTERNAL_SERVER_ERROR, "创建失败，系统出现异常");
        }
        //设置凭证缺失的初始数据
        proof.setRecorderId(user.getId()).setVerify(0).
                setVerifyTime(null).setTrash(0);

        //保存到数据库
        int rows = proofDAO.save(proof);
        if (rows != 1) {
            log.error("操作数据出现了故障");
            throw new AccountingException(500, "系统出现了异常，请稍后重试");
        }

        Subject clSubject;
        Subject dlSubject;
        Subject csSubject;
        Subject dsSubject;

        for (ProofItem proofItem : proof.getItems()) {
            proofItem.setProofId(proof.getId());
            clSubject = subjectService.getSubjectById(proofItem.getCreditLedgerSubjectId());
            dlSubject = subjectService.getSubjectById(proofItem.getDebitLedgerSubjectId());
            csSubject = subjectService.getSubjectById(proofItem.getCreditSubSubjectId());
            dsSubject = subjectService.getSubjectById(proofItem.getDebitSubSubjectId());
            /*
            检查贷方总账科目CLS
             */
            checkLedSubject(clSubject, CLS);
            /*
            检查借方总账科目DLS
             */
            checkLedSubject(dlSubject, DLS);
            /*
            检查贷方明细账科目CSS
             */
            checkSubSubject(csSubject, clSubject, CSS, CLS);
            /*
            检查借方明细账科目
             */
            checkSubSubject(dsSubject, dlSubject, DSS, DLS);
            //调用DAO层保存到数据库
            int resultRows = proofItemDAO.save(proofItem);
            if (resultRows != 1) {
                log.error("操作数据出现了故障");
                throw new AccountingException(500, "系统出现了异常，请稍后重试");
            }
        }
    }

    @Override
    public Proof find(Integer id) {
        Proof proof = proofDAO.find(id);
        if (proof == null) {
            return null;
        }
        proofHandle(proof);
        return proof;
    }

    private void proofHandle(Proof proof) {
        List<ProofItem> proofItems = proofItemDAO.items(proof.getId());
        for (ProofItem proofItem : proofItems) {
            Integer clsId = proofItem.getCreditLedgerSubjectId();
            Integer dlsId = proofItem.getDebitLedgerSubjectId();
            Integer cssId = proofItem.getCreditSubSubjectId();
            Integer dssId = proofItem.getDebitSubSubjectId();
            Subject cls = subjectService.getSubjectById(clsId);
            Subject dls = subjectService.getSubjectById(dlsId);
            Subject css = subjectService.getSubjectById(cssId);
            Subject dss = subjectService.getSubjectById(dssId);
            proofItem.setCreditLedgerSubject(cls);
            proofItem.setDebitLedgerSubject(dls);
            proofItem.setCreditSubSubject(css);
            proofItem.setDebitSubSubject(dss);
        }
        proof.setItems(proofItems);
    }

    @Override
    public List<Proof> list(Map<String, Object> map) {
        List<Proof> proofs = proofDAO.list(map);
        for (Proof proof : proofs) {
            proofHandle(proof);
        }
        return proofs;
    }

    @Override
    public Integer count(Map<String, Object> map) {
        return proofDAO.count(map);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRED)
    public void verify(Integer proofId, Boolean result, String tokenId) {
        Proof proof = proofDAO.find(proofId);
        if (proof == null) {
            throw new NotFoundException(400, "未找到该凭证");
        }
        log.debug("token_id: {}", tokenId);
        User user = userService.getUserByToken(tokenId);
        if (result) {
            proof.setVerify(2);
        } else {
            proof.setVerify(-1);
        }
        int rows = proofDAO.update(proof);
        if (rows != 1) {
            log.error("操作数据出现了故障");
            throw new BadUpdateException(400, "更新失败");
        }

        if (result) {
            log.info("{}号凭证通过审核，处理人编号：{}", proofId, user.getId());
            log.info("把结果发送到消息队列");
            ProofMsg proofMsg = new ProofMsg();
            proofMsg.setProofId(proofId).setVerifiedUserId(user.getId()).setDate(new Date());
            proofMqService.sendMsg(proofMsg);
        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRED)
    public void trashProof(Integer proofId, String tokenId) {
        User user = userService.getUserByToken(tokenId);
        Proof proof=proofDAO.find(proofId);
        if (proof.getTrash() != 0) {
            throw new BadUpdateException(400, "不能重复冲账");
        }
        Integer recorderId = proof.getRecorderId();
        if (!recorderId.equals(user.getId())) {
            throw new BadUpdateException(400, "只能对自己创建的凭证冲账");
        }
        Proof temp = new Proof();
        temp.setId(proof.getId());
        temp.setTrash(1);
        if (proofDAO.update(temp) != 1) {
            log.error("操作数据出现了故障");
            throw new BadCreateException(500, "服务器错误");
        }
        Proof trashProof = new Proof();
        trashProof.setDate(proof.getDate())
                .setInvoiceCount(proof.getInvoiceCount())
                .setManager(proof.getManager())
                .setCollection(proof.getCollection())
                .setRecorderId(proof.getRecorderId())
                .setCashier(proof.getCashier())
                .setPayer(proof.getPayer())
                .setVerifyUserId(null)
                .setVerifyTime(null)
                .setVerify(0)
                .setTrash(1);
        if (proofDAO.save(trashProof) != 1) {
            log.error("操作数据出现了故障");
            throw new BadCreateException(500, "服务器错误");
        }
        List<ProofItem> items = proof.getItems();
        for (ProofItem item : items) {
            ProofItem trashItem = new ProofItem();
            trashItem.setAbstraction(item.getAbstraction() + "错误冲账")
                    //反向
                    .setDebitSubSubjectId(item.getCreditSubSubjectId())
                    .setCreditSubSubjectId(item.getDebitSubSubjectId())
                    .setDebitLedgerSubjectId(item.getCreditLedgerSubjectId())
                    .setCreditLedgerSubjectId(item.getDebitLedgerSubjectId())
                    .setProofId(trashProof.getId())
                    .setCharge(false);
            trashItem.setMoney(item.getMoney());
            if (proofItemDAO.save(trashItem) != 1) {
                log.error("操作数据出现了故障");
                throw new BadCreateException(500, "服务器错误");
            }
        }
        log.info("{}号凭证进行了冲账，处理人：{}",proofId,user.getId());
    }

    @Override
    public void update(Proof proof) {
        proofDAO.update(proof);
    }

    @Override
    public void updateItem(ProofItem proofItem) {
        proofItemDAO.update(proofItem);
    }


    /**
     * 验证总账科目
     */
    private void checkLedSubject(Subject subject, int subjectType) {
        String msg = MSG_MAP.get(subjectType);
        if (subject == null) {
            throw new BadCreateException(400, msg + "输入有误");
        }

        if (Objects.equals(subject.getValid(), Boolean.FALSE)) {
            throw new BadCreateException(400, "科目" + subject.getName() + "已被禁用");
        }
    }

    /**
     * 验证明细账科目
     */
    private void checkSubSubject(Subject subject, Subject parent, int subjectType, int parentType) {
        checkLedSubject(subject, subjectType);
        String msg = MSG_MAP.get(subjectType);
        String parentMsg = MSG_MAP.get(parentType);
        if (subject.getParent() == null) {
            throw new BadCreateException(400, "科目" + subject.getName() + "不是明细账科目");
        }
        if (!Objects.equals(subject.getParent().getId(), parent.getId())) {
            throw new BadCreateException(400, msg + "不是" + parentMsg + "的明细账科目");
        }
    }

}
