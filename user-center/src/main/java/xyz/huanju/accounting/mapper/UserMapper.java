package xyz.huanju.accounting.mapper;

import org.apache.ibatis.annotations.Mapper;
import xyz.huanju.accounting.domain.User;

/**
 * @author HuanJu
 * @date 2020/8/9 12:15
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
