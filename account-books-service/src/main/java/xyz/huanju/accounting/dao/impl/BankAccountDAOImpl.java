package xyz.huanju.accounting.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.huanju.accounting.dao.BankAccountDAO;
import xyz.huanju.accounting.domain.BankAccount;
import xyz.huanju.accounting.domain.OpeningBalance;
import xyz.huanju.accounting.mapper.BankAccountMapper;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author HuanJu
 * @date 2020/8/17 0:46
 */
@Component("bankAccountDAO")
@Slf4j
public class BankAccountDAOImpl implements BankAccountDAO {

    private BankAccountMapper bankAccountMapper;

    @Resource
    public void setBankAccountMapper(BankAccountMapper bankAccountMapper) {
        this.bankAccountMapper = bankAccountMapper;
    }

    @Override
    public OpeningBalance getOpeningBalance(String date) {
        return bankAccountMapper.getOpeningBalance(date);
    }

    @Override
    public int save(BankAccount obj) {
        return bankAccountMapper.save(obj);
    }

    @Override
    public int update(BankAccount obj) {
        return bankAccountMapper.update(obj);
    }

    @Override
    public int delete(Integer key) {
        return bankAccountMapper.delete(key);
    }

    @Override
    public BankAccount find(Integer key) {
        return bankAccountMapper.find(key);
    }

    @Override
    public BankAccount findByName(String name) {
        return bankAccountMapper.findByName(name);
    }

    @Override
    public List<BankAccount> list(Map<String, Object> map) {
        return bankAccountMapper.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return bankAccountMapper.count(map);
    }
}
