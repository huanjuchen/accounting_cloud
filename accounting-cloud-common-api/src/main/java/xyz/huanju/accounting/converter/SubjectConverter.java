package xyz.huanju.accounting.converter;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import xyz.huanju.accounting.domain.Subject;
import xyz.huanju.accounting.domain.vo.SubjectVO;

/**
 * @author HuanJu
 * @date 2020/8/11 16:32
 * @see BaseConverter
 */
@Mapper
public interface SubjectConverter extends BaseConverter<Subject, SubjectVO> {

    SubjectConverter INSTANCE = Mappers.getMapper(SubjectConverter.class);

}
