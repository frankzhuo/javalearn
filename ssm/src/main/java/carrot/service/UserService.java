package carrot.service;

import beans.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    public User getUser() {
        printUser();
        return new User();
    }

    public User printUser() {
        System.out.println("print User");
        return getUser();
    }

}
