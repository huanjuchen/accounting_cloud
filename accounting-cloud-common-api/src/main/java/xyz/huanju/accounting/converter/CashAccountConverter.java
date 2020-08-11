package xyz.huanju.accounting.converter;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import xyz.huanju.accounting.domain.CashAccount;
import xyz.huanju.accounting.domain.vo.CashAccountVO;

/**
 * @author HuanJu
 * @date 2020/8/11 16:37
 * @see BaseConverter
 */
@Mapper
public interface CashAccountConverter extends BaseConverter<CashAccount, CashAccountVO> {

    CashAccountConverter INSTANCE = Mappers.getMapper(CashAccountConverter.class);

}
