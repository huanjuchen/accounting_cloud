package xyz.huanju.accounting.converter;

import java.util.List;

/**
 * mapStruct mapper 模板
 *
 * @param <S> pojo 类型
 * @param <T> vo 类型
 * @author HuanJu
 */
public interface BaseConverter<S, T> {

    /**
     * POJO TO VO
     *
     * @param pojo 源对象
     * @return 目标对象
     */
    T convertToVo(S pojo);


    /**
     * VO TO POJO
     *
     * @param vo 源对象
     * @return 目标对象
     */
    S convertToPojo(T vo);

    /**
     * POJO LIST TO VO LIST
     *
     * @param pojoList pojo 集合
     * @return vo集合
     */
    List<T> convertToVoList(List<S> pojoList);


    /**
     * VO LIST TO POJO LIST
     *
     * @param voList VO LIST
     * @return POJO LIST
     */
    List<S> convertToPojoList(List<T> voList);


}
