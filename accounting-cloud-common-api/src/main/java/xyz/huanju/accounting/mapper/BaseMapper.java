package xyz.huanju.accounting.mapper;

import java.util.List;
import java.util.Map;

/**
 * @author HuanJu
 * @date 2020/8/9 12:16
 */
public interface BaseMapper<K,V> {

    /**
     * 存储实体
     *
     * @param obj 实体
     * @return 受影响的行数
     */
    int save(V obj);

    /**
     * 更新实体
     *
     * @param obj 实体
     * @return 受影响的行数
     */
    int update(V obj);

    /**
     * 删除实体
     *
     * @param key key
     * @return 受影响的行数
     */
    int delete(K key);

    /**
     * 根据key查找实体
     *
     * @param key key
     * @return 实体
     */
    V find(K key);

    /**
     * 根据name查找实体
     *
     * @param name name
     * @return 实体
     */
    V findByName(String name);


    /**
     * 查询多个
     *
     * @param map 条件
     * @return 实体list
     */
    List<V> list(Map<String, Object> map);


    /**
     * 查询记录的条数
     *
     * @param map 条件
     * @return 根据条件查询的总行数
     */
    int count(Map<String, Object> map);

}
