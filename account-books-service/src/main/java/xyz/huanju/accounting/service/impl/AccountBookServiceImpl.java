package xyz.huanju.accounting.service.impl;

import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import xyz.huanju.accounting.dao.BankAccountDAO;
import xyz.huanju.accounting.dao.CashAccountDAO;
import xyz.huanju.accounting.dao.LedgerAccountDAO;
import xyz.huanju.accounting.dao.SubAccountDAO;
import xyz.huanju.accounting.domain.*;
import xyz.huanju.accounting.domain.mq.ProofMsg;
import xyz.huanju.accounting.exception.BadCreateException;
import xyz.huanju.accounting.exception.NotFoundException;
import xyz.huanju.accounting.service.AccountBookService;
import xyz.huanju.accounting.service.ProofService;
import xyz.huanju.accounting.service.SubjectService;
import xyz.huanju.accounting.service.UserService;
import xyz.huanju.accounting.utils.AccountBookUtils;
import xyz.huanju.accounting.utils.DateUtils;
import xyz.huanju.accounting.utils.JsonUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author HuanJu
 * @date 2020/8/18 0:02
 */
@Service("accountBookService ")
@Slf4j
public class AccountBookServiceImpl implements AccountBookService {

    private BankAccountDAO bankAccountDAO;

    private CashAccountDAO cashAccountDAO;

    private LedgerAccountDAO ledgerAccountDAO;

    private SubAccountDAO subAccountDAO;

    private SubjectService subjectService;

    private ProofService proofService;

    private UserService userService;

    @Autowired
    public void setSubjectService(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Autowired
    public void setBankAccountDAO(BankAccountDAO bankAccountDAO) {
        this.bankAccountDAO = bankAccountDAO;
    }

    @Autowired
    public void setCashAccountDAO(CashAccountDAO cashAccountDAO) {
        this.cashAccountDAO = cashAccountDAO;
    }

    @Autowired
    public void setLedgerAccountDAO(LedgerAccountDAO ledgerAccountDAO) {
        this.ledgerAccountDAO = ledgerAccountDAO;
    }

    @Autowired
    public void setSubAccountDAO(SubAccountDAO subAccountDAO) {
        this.subAccountDAO = subAccountDAO;
    }

    @Autowired
    public void setProofService(ProofService proofService) {
        this.proofService = proofService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public List<BankAccount> getBankAccount(String startDate, String endDate, Integer page) {
        Map<String, Object> map = paramHandle(null, startDate, endDate, page == null ? 0 : page);
        List<BankAccount> list = bankAccountDAO.list(map);
        OpeningBalance openingBalance = bankAccountDAO.getOpeningBalance(startDate);
        BigDecimal debitMoney;
        BigDecimal creditMoney;

        BigDecimal temp = null;
        BankAccount ba = null;
        //期初余额处理
        if (openingBalance != null) {
            debitMoney = openingBalance.getDebitBalance();
            creditMoney = openingBalance.getCreditBalance();
            temp = AccountBookUtils.computeMoney(1, null, debitMoney, creditMoney);
            ba = new BankAccount();
            ba.setAbstraction("期初余额").setMoney(temp).setDate(DateUtils.getDate(startDate));
        }

        List<BankAccount> resultList = new ArrayList<>(list.size() + 1);
        if (ba != null) {
            resultList.add(ba);
        }

        Subject subject = null;
        for (BankAccount item : list) {
            debitMoney = item.getDebitMoney();
            creditMoney = item.getCreditMoney();
            subject = subjectService.getSubjectById(item.getSubjectId());
            item.setSubject(subject);
            temp = AccountBookUtils.computeMoney(1, temp, debitMoney, creditMoney);
            item.setMoney(temp);
            resultList.add(item);
        }
        return resultList;
    }

    @Override
    public List<CashAccount> getCashAccount(String startDate, String endDate, Integer page) {
        Map<String, Object> map = paramHandle(null, startDate, endDate, page == null ? 0 : page);
        List<CashAccount> list = cashAccountDAO.list(map);
        OpeningBalance openingBalance = cashAccountDAO.getOpeningBalance(startDate);
        BigDecimal debitMoney;
        BigDecimal creditMoney;

        BigDecimal temp = null;
        CashAccount ca = null;
        //期初余额处理
        if (openingBalance != null) {
            debitMoney = openingBalance.getDebitBalance();
            creditMoney = openingBalance.getCreditBalance();
            temp = AccountBookUtils.computeMoney(1, null, debitMoney, creditMoney);
            ca = new CashAccount();
            ca.setAbstraction("期初余额").setMoney(temp).setDate(DateUtils.getDate(startDate));
        }

        List<CashAccount> resultList = new ArrayList<>(list.size() + 1);
        if (ca != null) {
            resultList.add(ca);
        }

        Subject subject = null;
        for (CashAccount item : list) {
            debitMoney = item.getDebitMoney();
            creditMoney = item.getCreditMoney();
            temp = AccountBookUtils.computeMoney(1, temp, debitMoney, creditMoney);
            subject = subjectService.getSubjectById(item.getSubjectId());
            item.setMoney(temp).setSubject(subject);
            resultList.add(item);
        }
        return resultList;
    }

    @Override
    public List<SubAccount> getSubAccount(Integer subjectId, String startDate, String endDate, Integer page) {
        Subject subject = subjectService.getSubjectById(subjectId);
        if (subject == null) {
            return null;
        }
        int category = subject.getCategory();
        Map<String, Object> map = paramHandle(subjectId, startDate, endDate, page == null ? 0 : page);
        List<SubAccount> list = subAccountDAO.list(map);

        OpeningBalance ob = subAccountDAO.getOpeningBalance(startDate, subjectId);
        BigDecimal dm;
        BigDecimal cm;
        /*
        账簿处理
         */
        BigDecimal temp = null;
        int mark;
        SubAccount tempObj = null;
        List<SubAccount> resultList = new ArrayList<>(list.size() + 1);
        //上期金额
        if (ob != null) {
            tempObj = new SubAccount();
            dm = ob.getDebitBalance();
            cm = ob.getCreditBalance();
            temp = AccountBookUtils.computeMoney(category, null, dm, cm);
            mark = AccountBookUtils.getMark(category, dm, cm);
            tempObj.setAbstraction("期初金额").setMoney(temp)
                    .setDate(DateUtils.getDate(startDate))
                    .setMark(mark);
            resultList.add(tempObj);
        }

        Subject ts = null;
        for (SubAccount item : list) {
            dm = item.getDebitMoney();
            cm = item.getCreditMoney();
            temp = AccountBookUtils.computeMoney(category, temp, dm, cm);
            mark = AccountBookUtils.getMark(category, dm, cm);
            ts = subjectService.getSubjectById(item.getSubjectId());
            item.setSubject(ts).setMark(mark).setMoney(temp);
            resultList.add(item);
        }
        return resultList;
    }

    @Override
    public List<LedgerAccount> getLedgerAccount(Integer subjectId, String startDate, String endDate) {
        Subject subject = subjectService.getSubjectById(subjectId);
        if (subject == null) {
            return null;
        }
        int category = subject.getCategory();
        Map<String, Object> map = paramHandle(subjectId, startDate, endDate, null);
        List<LedgerAccount> list = ledgerAccountDAO.list(map);

        OpeningBalance ob = ledgerAccountDAO.getOpeningBalance(startDate, subjectId);
        BigDecimal dm;
        BigDecimal cm;
        /*
        账簿处理
         */
        BigDecimal temp = null;
        int mark;
        LedgerAccount tempObj;
        List<LedgerAccount> resultList = new ArrayList<>(list.size() + 1);
        //上期金额
        if (ob != null) {
            tempObj = new LedgerAccount();
            dm = ob.getDebitBalance();
            cm = ob.getCreditBalance();
            temp = AccountBookUtils.computeMoney(category, null, dm, cm);
            mark = AccountBookUtils.getMark(category, dm, cm);
            tempObj.setAbstraction("期初金额").setMoney(temp)
                    .setDate(DateUtils.getDate(startDate))
                    .setMark(mark);
            resultList.add(tempObj);
        }

        Subject ts;
        for (LedgerAccount item : list) {
            dm = item.getDebitMoney();
            cm = item.getCreditMoney();
            temp = AccountBookUtils.computeMoney(category, temp, dm, cm);
            mark = AccountBookUtils.getMark(category, dm, cm);
            ts = subjectService.getSubjectById(item.getSubjectId());
            item.setSubject(ts).setMark(mark).setMoney(temp);
            resultList.add(item);
        }
        return resultList;
    }

    private final static char DEBIT = 'D';
    private final static char CREDIT = 'C';

    @Override
    @GlobalTransactional
    @Transactional(rollbackFor = RuntimeException.class,propagation = Propagation.REQUIRED)
    public void accountBookHandle(ProofMsg proofMsg) {
        Proof proof = proofService.getProof(proofMsg.getProofId());
        if (proof == null) {
            log.error("凭证号为{}的凭证不存在", proofMsg.getProofId());
            throw new NotFoundException(404, "凭证不存在");
        }
        List<ProofItem> items = proof.getItems();

        //借方总账科目
        Subject dls;
        //贷方总账科目
        Subject cls;

        for (ProofItem item : items) {
            dls = item.getDebitLedgerSubject();
            cls = item.getCreditLedgerSubject();
                        /*
            明细账
             */
            subAccountHandle(item, proof.getDate(), proof.getId());
            /*
            总账
             */
            ledgerAccountHandle(item, proof.getDate());
            /*
            填写日记账
             */
            //现金
            if (dls != null && Objects.equals(dls.getCode(), "1001")) {
                //借
                cashAccountHandle(item, DEBIT, proof.getDate(), proof.getId());
            } else if (cls != null &&
                    Objects.equals(cls.getCode(), "1001")) {
                //贷
                cashAccountHandle(item, CREDIT, proof.getDate(), proof.getId());
            }
            //银行
            if (dls != null && Objects.equals(dls.getCode(), "1002")) {
                bankAccountHandle(item, DEBIT, proof.getDate(), proof.getId());
            } else if (cls != null && Objects.equals(cls.getCode(), "1002")) {
                bankAccountHandle(item, CREDIT, proof.getDate(), proof.getId());
            }


            ProofItem itemNew = new ProofItem();
            itemNew.setId(item.getId());
            itemNew.setCharge(true);
            proofService.updateItem(itemNew);
        }
        Proof proofNew=new Proof();
        proofNew.setId(proof.getId());
        proofNew.setVerify(1);
        proofNew.setVerifyUserId(proofMsg.getVerifiedUserId());
        proofNew.setDate(proofMsg.getDate());
        proofService.update(proofNew);
    }

    /**
     * 现金日记账处理
     */
    private void cashAccountHandle(ProofItem item, char t, Date date, Integer proofId) {
        CashAccount cashAccount = new CashAccount();
        if (t == DEBIT) {
            cashAccount.setDate(date)
                    .setProofId(proofId)
                    .setAbstraction(item.getAbstraction())
                    .setSubjectId(item.getCreditLedgerSubject().getId())
                    .setDebitMoney(item.getMoney());
        } else {
            cashAccount.setDate(date)
                    .setProofId(proofId)
                    .setAbstraction(item.getAbstraction())
                    .setSubjectId(item.getDebitLedgerSubject().getId())
                    .setCreditMoney(item.getMoney());
        }
        int rows = cashAccountDAO.save(cashAccount);
        if (rows != 1) {
            throw new BadCreateException(500, "系统错误，处理失败");
        }
    }


    /**
     * 银行日记账处理
     */
    private void bankAccountHandle(ProofItem item, char t, Date date, Integer proofId) {
        log.info(JsonUtils.toJson(item));
        BankAccount bankAccount = new BankAccount();
        if (t == DEBIT) {
            bankAccount.setDate(date)
                    .setProofId(proofId)
                    .setAbstraction(item.getAbstraction())
                    .setSubjectId(item.getCreditLedgerSubject().getId())
                    .setDebitMoney(item.getMoney());
        } else {
            bankAccount.setDate(date)
                    .setProofId(proofId)
                    .setAbstraction(item.getAbstraction())
                    .setSubjectId(item.getDebitLedgerSubject().getId())
                    .setCreditMoney(item.getMoney());
        }
        int rows = bankAccountDAO.save(bankAccount);
        if (rows != 1) {
            throw new BadCreateException(500, "系统错误，处理失败");
        }
    }

    @Override
    public Integer getBankAccountCount(String startDate, String endDate) {
        Map<String, Object> map = paramHandle(null, startDate, endDate, null);
        return bankAccountDAO.count(map);
    }

    /**
     * 明细分类账处理
     */
    private void subAccountHandle(ProofItem item, Date date, Integer proofId) {
        if (item.getDebitSubSubject() != null) {
            SubAccount subAccount = new SubAccount();
            subAccount.setDate(date)
                    .setProofId(proofId)
                    .setAbstraction(item.getAbstraction())
                    .setSubjectId(item.getDebitSubSubject().getId())
                    .setDebitMoney(item.getMoney());
            int rows = subAccountDAO.save(subAccount);
            if (rows != 1) {
                throw new BadCreateException(500, "系统错误，处理失败");
            }
        }
        if (item.getCreditSubSubject() != null) {
            SubAccount subAccount = new SubAccount();
            subAccount.setDate(date)
                    .setProofId(proofId)
                    .setAbstraction(item.getAbstraction())
                    .setSubjectId(item.getCreditSubSubject().getId())
                    .setCreditMoney(item.getMoney());
            int rows = subAccountDAO.save(subAccount);
            if (rows != 1) {
                throw new BadCreateException(500, "系统错误，处理失败");
            }
        }
    }

    /**
     * 总账处理
     */
    private void ledgerAccountHandle(ProofItem item, Date date) {
        Subject dls = item.getDebitLedgerSubject();
        Subject cls = item.getCreditLedgerSubject();
        if (dls != null) {
            Integer dlsId = dls.getId();
            LedgerAccount la = ledgerAccountDAO.findBySubjectAndDate(dlsId, date);
            int row;
            if (la == null) {
                la = new LedgerAccount();
                la.setAbstraction("本日合计")
                        .setDate(date)
                        .setSubjectId(dlsId)
                        .setDebitMoney(item.getMoney());
                row = ledgerAccountDAO.save(la);
            } else {
                BigDecimal money = la.getDebitMoney();
                if (money == null) {
                    money = new BigDecimal("0.00");
                }
                LedgerAccount nla = new LedgerAccount();
                nla.setId(la.getId()).setDebitMoney(money.add(item.getMoney()));
                row = ledgerAccountDAO.update(nla);
            }
            if (row != 1) {
                throw new BadCreateException(500, "系统错误，处理失败");
            }
        }

        if (cls != null) {
            Integer clsId = cls.getId();
            LedgerAccount la = ledgerAccountDAO.findBySubjectAndDate(clsId, date);
            int row;
            if (la == null) {
                la = new LedgerAccount();
                la.setAbstraction("本日合计")
                        .setDate(date).setSubjectId(clsId).setCreditMoney(item.getMoney());
                row = ledgerAccountDAO.save(la);
            } else {
                BigDecimal money = la.getCreditMoney();
                if (money == null) {
                    money = new BigDecimal("0.00");
                }
                LedgerAccount nla = new LedgerAccount();
                nla.setId(la.getId()).setCreditMoney(money.add(item.getMoney()));
                row = ledgerAccountDAO.update(nla);
            }
            if (row != 1) {
                throw new BadCreateException(500, "系统错误，处理失败");
            }
        }
    }

    @Override
    public Integer getCashAccountCount(String startDate, String endDate) {
        Map<String, Object> map = paramHandle(null, startDate, endDate, null);
        return cashAccountDAO.count(map);
    }

    @Override
    public Integer getLedgerAccountCount(Integer subjectId, String startDate, String endDate) {
        Map<String, Object> map = paramHandle(subjectId, startDate, endDate, null);
        return ledgerAccountDAO.count(map);
    }

    @Override
    public Integer getSubAccountCount(Integer subjectId, String startDate, String endDate) {
        Map<String, Object> map = paramHandle(subjectId, startDate, endDate, null);
        return subAccountDAO.count(map);
    }


    private Map<String, Object> paramHandle(Integer subjectId, String startDate, String endDate, Integer page) {
        Map<String, Object> map = new HashMap<>(1 << 3);
        if (startDate == null || startDate.length() == 0) {
            String[] se = DateUtils.monthStartEnd(new Date());
            startDate = se[0];
            endDate = se[1];
        }
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        if (subjectId != null) {
            map.put("subjectId", subjectId);
        }
        if (page != null) {
            map.put("offset", page > 0 ? ((page - 1) * 10) : 0);
            map.put("count", 10);
        }
        return map;
    }
}
