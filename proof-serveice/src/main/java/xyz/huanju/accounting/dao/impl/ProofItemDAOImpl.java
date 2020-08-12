package xyz.huanju.accounting.dao.impl;

import org.springframework.stereotype.Component;
import xyz.huanju.accounting.dao.ProofItemDAO;
import xyz.huanju.accounting.domain.ProofItem;
import xyz.huanju.accounting.mapper.ProofItemMapper;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author HuanJu
 * @date 2020/8/11 19:53
 */
@Component
public class ProofItemDAOImpl implements ProofItemDAO {

    @Resource
    private ProofItemMapper itemMapper;

    @Override
    public List<ProofItem> items(Integer proofId) {
        return itemMapper.items(proofId);
    }

    @Override
    public List<ProofItem> listBySubject(Integer subjectId) {
        return itemMapper.listBySubject(subjectId);
    }

    @Override
    public int save(ProofItem obj) {
        return itemMapper.save(obj);
    }

    @Override
    public int update(ProofItem obj) {
        return itemMapper.update(obj);
    }

    @Override
    public int delete(Object key) {
        return itemMapper.delete(key);
    }

    @Override
    public ProofItem find(Object key) {
        return itemMapper.find(key);
    }

    @Override
    public ProofItem findByName(String name) {
        return itemMapper.findByName(name);
    }

    @Override
    public List<ProofItem> list(Map<String, Object> map) {
        return itemMapper.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return itemMapper.count(map);
    }
}
