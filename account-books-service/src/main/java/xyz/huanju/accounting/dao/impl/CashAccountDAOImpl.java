package xyz.huanju.accounting.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import xyz.huanju.accounting.dao.CashAccountDAO;
import xyz.huanju.accounting.domain.CashAccount;
import xyz.huanju.accounting.domain.OpeningBalance;
import xyz.huanju.accounting.mapper.CashAccountMapper;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author HuanJu
 * @date 2020/8/17 1:02
 */
@Component("cashAccountDAO")
@Slf4j
public class CashAccountDAOImpl implements CashAccountDAO {

    private CashAccountMapper cashAccountMapper;

    @Resource
    public void setCashAccountMapper(CashAccountMapper cashAccountMapper) {
        this.cashAccountMapper = cashAccountMapper;
    }

    @Override
    public OpeningBalance getOpeningBalance(String date) {
        return cashAccountMapper.getOpeningBalance(date);
    }

    @Override
    public int save(CashAccount obj) {
        return cashAccountMapper.save(obj);
    }

    @Override
    public int update(CashAccount obj) {
        return cashAccountMapper.update(obj);
    }

    @Override
    public int delete(Integer key) {
        return cashAccountMapper.delete(key);
    }

    @Override
    public CashAccount find(Integer key) {
        return cashAccountMapper.find(key);
    }

    @Override
    public CashAccount findByName(String name) {
        return cashAccountMapper.findByName(name);
    }

    @Override
    public List<CashAccount> list(Map<String, Object> map) {
        return cashAccountMapper.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return cashAccountMapper.count(map);
    }
}
