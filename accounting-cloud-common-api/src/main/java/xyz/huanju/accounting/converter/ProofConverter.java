package xyz.huanju.accounting.converter;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import xyz.huanju.accounting.domain.Proof;
import xyz.huanju.accounting.domain.vo.ProofVO;

/**
 * @author HuanJu
 * @date 2020/8/11 16:22
 * @see BaseConverter
 */
@Mapper
public interface ProofConverter extends BaseConverter<Proof, ProofVO> {

    ProofConverter INSTANCE = Mappers.getMapper(ProofConverter.class);
}
