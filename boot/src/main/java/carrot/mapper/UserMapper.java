package carrot.mapper;

import carrot.bean.User;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {

    @Select("select * from user where name=#{name}")
    User getUser(String name);
}
