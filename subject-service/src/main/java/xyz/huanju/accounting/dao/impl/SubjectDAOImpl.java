package xyz.huanju.accounting.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
@CacheConfig(cacheNames = "subjectCache",cacheManager = "cacheManager")
public class SubjectDAOImpl implements SubjectDAO {

    private SubjectMapper subjectMapper;

    @Autowired
    private CacheManager cacheManager;


    @Resource
    public void setSubjectMapper(SubjectMapper subjectMapper) {
        this.subjectMapper = subjectMapper;
    }


    @Override
    public Subject findByCode(String code) {
        return subjectMapper.findByCode(code);
    }

    @Override
    public int save(Subject obj) {
        return subjectMapper.save(obj);
    }

    @Override
    @CacheEvict(key = "#obj.id")
    public int update(Subject obj) {
        return subjectMapper.update(obj);
    }

    @Override
    @CacheEvict(key = "#key")
    public int delete(Integer key) {
        return subjectMapper.delete(key);
    }


    @Override
    @Cacheable(key = "#key",condition = "#key>0", unless = "#result==null")
    public Subject find(Integer key) {
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
