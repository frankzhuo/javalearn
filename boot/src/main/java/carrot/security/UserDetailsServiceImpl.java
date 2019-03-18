package carrot.security;

import carrot.bean.User;
import carrot.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserMapper userMapper;

    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userMapper.getUser(name);
        if(user==null){
            throw new UsernameNotFoundException("");
        }
        Set<GrantedAuthority> authorities = new HashSet();
        GrantedAuthority authority=new SimpleGrantedAuthority("ROLE_USER");
        authorities.add(authority);
        org.springframework.security.core.userdetails.User userdetail = new org.springframework.security.core.userdetails.User(
                user.getName(),user.getPasswd(),
                true,true,true,true,
                authorities
        );
        return userdetail;
    }
}
