package xyz.huanju.accounting.mapper;

import java.util.List;
import java.util.Map;

/**
 * @author HuanJu
 * @date 2020/8/9 12:16
 */
public interface BaseMapper<T> {

    /**
     * 存储实体
     *
     * @param obj 实体
     * @return 受影响的行数
     */
    int save(T obj);

    /**
     * 更新实体
     *
     * @param obj 实体
     * @return 受影响的行数
     */
    int update(T obj);

    /**
     * 删除实体
     *
     * @param key key
     * @return 受影响的行数
     */
    int delete(Object key);

    /**
     * 根据key查找实体
     *
     * @param key key
     * @return 实体
     */
    T find(Object key);

    /**
     * 根据name查找实体
     *
     * @param name name
     * @return 实体
     */
    T findByName(String name);


    /**
     * 查询多个
     *
     * @param map 条件
     * @return 实体list
     */
    List<T> list(Map<String, Object> map);


    /**
     * 查询记录的条数
     *
     * @param map 条件
     * @return 根据条件查询的总行数
     */
    int count(Map<String, Object> map);

}
