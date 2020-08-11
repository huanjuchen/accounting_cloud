package xyz.huanju.accounting.converter;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import xyz.huanju.accounting.domain.BankAccount;
import xyz.huanju.accounting.domain.vo.BankAccountVO;

/**
 * @author HuanJu
 * @date 2020/8/11 16:34
 * @see BaseConverter
 */
@Mapper
public interface BankAccountConverter extends BaseConverter<BankAccount, BankAccountVO> {

    public BankAccountConverter INSTANCE = Mappers.getMapper(BankAccountConverter.class);

}
