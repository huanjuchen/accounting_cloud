package xyz.huanju.accounting.converter;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import xyz.huanju.accounting.domain.SubAccount;
import xyz.huanju.accounting.domain.vo.SubAccountVO;

/**
 * @author HuanJu
 * @date 2020/8/11 16:40
 * @see BaseConverter
 */
@Mapper
public interface SubAccountConverter extends BaseConverter<SubAccount, SubAccountVO> {

    SubAccountConverter INSTANCE= Mappers.getMapper(SubAccountConverter.class);

}
