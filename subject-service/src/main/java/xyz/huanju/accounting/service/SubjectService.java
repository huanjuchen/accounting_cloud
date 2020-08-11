package xyz.huanju.accounting.service;

import xyz.huanju.accounting.domain.Subject;

import java.util.List;
import java.util.Map;

/**
 * @author HuanJu
 * @date 2020/8/10 1:15
 */
public interface SubjectService {

    /**
     * 添加科目
     *
     * @param subject subject
     * @return subject
     */
    Subject save(Subject subject);

    /**
     * 删除科目
     *
     * @param id subject Id
     */
    void delete(Integer id);

    /**
     *
     * 更新科目
     *
     * @param subject subject
     */
    void update(Subject subject);

    /**
     * 查找科目
     *
     * @param id id
     * @return subject
     */
    Subject find(Integer id);


    /**
     * 根据科目代码查找
     *
     * @param code 科目代码
     * @return subject
     */
    Subject findByCode(String code);

    /**
     *
     * 根据条件查找科目
     *
     * @param map map
     * @return subject
     */
    List<Subject> list(Map<String, Object> map);


    /**
     * 查找满足条件的科目数量
     *
     * @param map map
     * @return count
     */
    Integer count(Map<String, Object> map);


    /**
     * 禁用科目
     *
     * @param id id
     */
    void lock(Integer id);

    /**
     * 启用科目
     *
     * @param id id
     */

    void unlock(Integer id);

}
