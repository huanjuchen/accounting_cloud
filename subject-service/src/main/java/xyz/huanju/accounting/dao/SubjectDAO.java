package xyz.huanju.accounting.dao;

import xyz.huanju.accounting.domain.Subject;

/**
 * @author HuanJu
 * @date 2020/8/10 1:02
 */
public interface SubjectDAO extends BaseDAO<Subject> {

    /**
     * 根据科目代码查找科目
     *
     * @param code 科目代码
     * @return 科目
     */
    Subject findByCode(String code);

}
