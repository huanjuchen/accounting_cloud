package xyz.huanju.accounting.service.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import xyz.huanju.accounting.dao.SubjectDAO;
import xyz.huanju.accounting.domain.Subject;
import xyz.huanju.accounting.exception.AlreadyExistsException;
import xyz.huanju.accounting.exception.BadCreateException;
import xyz.huanju.accounting.exception.BadUpdateException;
import xyz.huanju.accounting.exception.NotFoundException;
import xyz.huanju.accounting.service.SubjectService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author HuanJu
 * @date 2020/8/10 1:51
 */
@Service("subjectService")
@Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.SUPPORTS, readOnly = true)
@Slf4j
public class SubjectServiceImpl implements SubjectService {
    
    @Resource
    private SubjectDAO subjectDAO;
    
    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRED)
    public Subject save(Subject subject) {
        log.debug(JSON.toJSONString(subject));
        try {
            Integer.valueOf(subject.getCode());
        } catch (NumberFormatException e) {
            throw new BadCreateException(400, "科目代码只能为纯数字");
        }
        if (subjectDAO.findByCode(subject.getCode()) != null) {
            throw new AlreadyExistsException(400, "科目代码已被使用");
        }
        if (subjectDAO.findByName(subject.getName()) != null) {
            throw new AlreadyExistsException(400, "科目名已存在");
        }
        subject.setValid(true);
        if (subject.getParentId() == null) {
            subject.setParentId(0);
        }
        subjectDAO.save(subject);
        return subjectDAO.find(subject.getId());
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public void update(Subject subject) {

    }

    @Override
    public Subject find(Integer id) {
        return subjectDAO.find(id);
    }

    @Override
    public Subject findByCode(String code) {
        return subjectDAO.findByCode(code);
    }

    @Override
    public List<Subject> list(Map<String, Object> map) {
        return subjectDAO.list(map);
    }

    @Override
    public Integer count(Map<String, Object> map) {
        return subjectDAO.count(map);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRED)
    public void lock(Integer id) {
        log.debug("禁用Id为 " + id + " 的科目");
        Subject subject = subjectDAO.find(id);
        if (subject == null) {
            throw new NotFoundException(400, "未找到指定科目");
        }
        subject = new Subject();
        subject.setId(id);
        subject.setValid(false);
        int rows = subjectDAO.update(subject);
        if (rows != 1) {
            throw new BadUpdateException(500, "禁用失败");
        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRED)
    public void unlock(Integer id) {
        log.debug("启用用Id为 " + id + " 的科目");
        Subject subject = subjectDAO.find(id);
        if (subject == null) {
            throw new NotFoundException(400, "未找到指定科目");
        }
        subject = new Subject();
        subject.setId(id);
        subject.setValid(true);
        int rows = subjectDAO.update(subject);
        if (rows != 1) {
            throw new BadUpdateException(500, "启用失败");
        }
    }
}
