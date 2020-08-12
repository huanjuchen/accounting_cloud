package xyz.huanju.accounting.service;


import xyz.huanju.accounting.domain.Subject;

/**
 * @author HuanJu
 * @date 2020/8/11 20:24
 */
public interface SubjectService {

    /**
     * 根据ID获得科目
     *
     * @param id 科目Id
     * @return subject
     */
    Subject getSubjectById(Integer id);


    /**
     * 根据科目代码查找科目
     *
     * @param code 代码
     * @return subject
     */
    Subject getSubjectByCode(String code);
}
