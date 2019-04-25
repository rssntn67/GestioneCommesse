package it.arsinfo.gc.ui.security;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import it.arsinfo.gc.entity.UserInfo;
import it.arsinfo.gc.repository.UserInfoDao;
import it.arsinfo.gc.ui.vaadin.HomeUI;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(HomeUI.class);
    private final UserInfoDao userService;

    @Autowired
    public UserDetailsServiceImpl(UserInfoDao userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        UserInfo user = userService.findByUsername(username);
        if (null == user) {
            log.warn("user authentication failed for username: '{}'", username);
            throw new UsernameNotFoundException("No user present with username: "
                    + username);
        } else {
            log.info("authentication success for username: '{}'", username);
            return new org.springframework.security.core.userdetails.User(user.getUsername(),
                                                                          user.getPasswordHash(),
                                                                          Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name())));
        }
    }
}