package xyz.huanju.accounting.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import xyz.huanju.accounting.dao.LedgerAccountDAO;
import xyz.huanju.accounting.domain.LedgerAccount;
import xyz.huanju.accounting.domain.OpeningBalance;
import xyz.huanju.accounting.mapper.LedgerAccountMapper;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author HuanJu
 * @date 2020/8/17 1:19
 */
@Component("ledgerAccountDAO")
@Slf4j
public class LedgerAccountDAOImpl implements LedgerAccountDAO {


    private LedgerAccountMapper ledgerAccountMapper;

    @Resource
    public void setLedgerAccountMapper(LedgerAccountMapper ledgerAccountMapper) {
        this.ledgerAccountMapper = ledgerAccountMapper;
    }

    @Override
    public LedgerAccount findBySubjectAndDate(Integer subjectId, Date date) {
        return ledgerAccountMapper.findBySubjectAndDate(subjectId, date);
    }

    @Override
    public OpeningBalance getOpeningBalance(String date, Integer subjectId) {
        return ledgerAccountMapper.getOpeningBalance(date, subjectId);
    }

    @Override
    public int save(LedgerAccount obj) {
        return ledgerAccountMapper.save(obj);
    }

    @Override
    public int update(LedgerAccount obj) {
        return ledgerAccountMapper.update(obj);
    }

    @Override
    public int delete(Integer key) {
        return ledgerAccountMapper.delete(key);
    }

    @Override
    public LedgerAccount find(Integer key) {
        return ledgerAccountMapper.find(key);
    }

    @Override
    public LedgerAccount findByName(String name) {
        return ledgerAccountMapper.findByName(name);
    }

    @Override
    public List<LedgerAccount> list(Map<String, Object> map) {
        return ledgerAccountMapper.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return ledgerAccountMapper.count(map);
    }
}
