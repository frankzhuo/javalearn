package carrot.service;

import carrot.bean.User;
import carrot.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public User getUser(String name){
        return userMapper.getUser(name);
    }

}
