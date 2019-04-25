package it.arsinfo.gc;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.test.context.junit4.SpringRunner;

import it.arsinfo.gc.repository.UserInfoDao;
import it.arsinfo.gc.ui.security.RedirectAuthenticationSuccessHandler;
import it.arsinfo.gc.ui.security.SecurityConfig;
import it.arsinfo.gc.ui.security.UserDetailsServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GCApplicationTests {

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    
    @Test
    public void testContextLoads() {
        assertNotNull(userInfoDao);
        assertNotNull(securityConfig);
        assertNotNull(userDetailsService);
        assertTrue(userDetailsService instanceof UserDetailsServiceImpl);
        assertNotNull(passwordEncoder);
        assertTrue(passwordEncoder instanceof BCryptPasswordEncoder);
        assertNotNull(authenticationSuccessHandler);
        assertTrue(authenticationSuccessHandler instanceof RedirectAuthenticationSuccessHandler);
   }
    
}
