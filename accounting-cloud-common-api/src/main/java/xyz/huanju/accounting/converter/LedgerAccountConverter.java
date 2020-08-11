package xyz.huanju.accounting.converter;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import xyz.huanju.accounting.domain.LedgerAccount;
import xyz.huanju.accounting.domain.vo.LedgerAccountVO;

/**
 * @author HuanJu
 * @date 2020/8/11 16:39
 * @see BaseConverter
 */
@Mapper
public interface LedgerAccountConverter extends BaseConverter<LedgerAccount, LedgerAccountVO> {

    LedgerAccountConverter INSTANCE = Mappers.getMapper(LedgerAccountConverter.class);

}
