package xyz.huanju.accounting.mapper;

import xyz.huanju.accounting.domain.Subject;

/**
 * @author HuanJu
 * @date 2020/8/10 0:29
 */
public interface SubjectMapper extends BaseMapper<Integer, Subject> {

    /**
     * 根据科目代码查找科目
     *
     * @param code 科目代码
     * @return 科目
     */
    Subject findByCode(String code);

}
