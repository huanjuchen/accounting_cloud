package xyz.huanju.accounting.dao.impl;

import org.springframework.stereotype.Component;
import xyz.huanju.accounting.dao.ProofDAO;
import xyz.huanju.accounting.domain.Proof;
import xyz.huanju.accounting.mapper.ProofMapper;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author HuanJu
 * @date 2020/8/11 19:51
 */
@Component
public class ProofDAOImpl implements ProofDAO {

    @Resource
    private ProofMapper proofMapper;

    @Override
    public int save(Proof obj) {
        return proofMapper.save(obj);
    }

    @Override
    public int update(Proof obj) {
        return proofMapper.update(obj);
    }

    @Override
    public int delete(Object key) {
        return proofMapper.delete(key);
    }

    @Override
    public Proof find(Object key) {
        return proofMapper.find(key);
    }

    @Override
    public Proof findByName(String name) {
        return proofMapper.findByName(name);
    }

    @Override
    public List<Proof> list(Map<String, Object> map) {
        return proofMapper.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return proofMapper.count(map);
    }
}
