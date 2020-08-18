package xyz.huanju.accounting.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import xyz.huanju.accounting.dao.SubAccountDAO;
import xyz.huanju.accounting.domain.OpeningBalance;
import xyz.huanju.accounting.domain.SubAccount;
import xyz.huanju.accounting.mapper.SubAccountMapper;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author HuanJu
 * @date 2020/8/17 1:33
 */
@Component("subAccountDAO")
@Slf4j
public class SubAccountDAOImpl implements SubAccountDAO {

    private SubAccountMapper subAccountMapper;

    @Resource
    public void setSubAccountMapper(SubAccountMapper subAccountMapper) {
        this.subAccountMapper = subAccountMapper;
    }

    @Override
    public OpeningBalance getOpeningBalance(String date, Integer subjectId) {

        return subAccountMapper.getOpeningBalance(date, subjectId);
    }

    @Override
    public int save(SubAccount obj) {
        return subAccountMapper.save(obj);
    }

    @Override
    public int update(SubAccount obj) {
        return subAccountMapper.update(obj);
    }

    @Override
    public int delete(Integer key) {
        return subAccountMapper.delete(key);
    }

    @Override
    public SubAccount find(Integer key) {
        return subAccountMapper.find(key);
    }

    @Override
    public SubAccount findByName(String name) {
        return subAccountMapper.findByName(name);
    }

    @Override
    public List<SubAccount> list(Map<String, Object> map) {
        return subAccountMapper.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return subAccountMapper.count(map);
    }
}
