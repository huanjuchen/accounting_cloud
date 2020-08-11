package xyz.huanju.accounting.dao.impl;

import org.springframework.stereotype.Component;
import xyz.huanju.accounting.dao.SubjectDAO;
import xyz.huanju.accounting.domain.Subject;
import xyz.huanju.accounting.mapper.SubjectMapper;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author HuanJu
 * @date 2020/8/10 1:58
 */
@Component("subjectDAO")
public class SubjectDAOImpl implements SubjectDAO {

    @Resource
    private SubjectMapper subjectMapper;

    @Override
    public Subject findByCode(String code) {
        return subjectMapper.findByCode(code);
    }

    @Override
    public int save(Subject obj) {
        return subjectMapper.save(obj);
    }

    @Override
    public int update(Subject obj) {
        return subjectMapper.update(obj);
    }

    @Override
    public int delete(Object key) {
        return subjectMapper.delete(key);
    }

    @Override
    public Subject find(Object key) {
        return subjectMapper.find(key);
    }

    @Override
    public Subject findByName(String name) {
        return subjectMapper.findByName(name);
    }

    @Override
    public List<Subject> list(Map<String, Object> map) {
        return subjectMapper.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return subjectMapper.count(map);
    }
}
