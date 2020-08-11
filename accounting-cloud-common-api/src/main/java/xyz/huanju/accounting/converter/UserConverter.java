package xyz.huanju.accounting.converter;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import xyz.huanju.accounting.domain.User;
import xyz.huanju.accounting.domain.vo.UserVO;

/**
 * @author HuanJu
 * @date 2020/8/11 16:08
 * @see BaseConverter
 */
@Mapper
public interface UserConverter extends BaseConverter<User, UserVO> {

    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);

}
