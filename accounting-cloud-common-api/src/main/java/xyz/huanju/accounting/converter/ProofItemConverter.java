package xyz.huanju.accounting.converter;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import xyz.huanju.accounting.domain.ProofItem;
import xyz.huanju.accounting.domain.vo.ProofItemVO;

/**
 * @author HuanJu
 * @date 2020/8/11 16:30
 * @see BaseConverter
 */
@Mapper
public interface ProofItemConverter extends BaseConverter<ProofItem, ProofItemVO> {

    ProofItemConverter INSTANCE = Mappers.getMapper(ProofItemConverter.class);

}
