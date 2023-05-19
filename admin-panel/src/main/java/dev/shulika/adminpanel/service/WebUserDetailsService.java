package dev.shulika.adminpanel.service;

import dev.shulika.adminpanel.repository.WebUserRepository;
import dev.shulika.adminpanel.security.WebUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class WebUserDetailsService implements UserDetailsService {
    private final WebUserRepository webUserRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        log.info("+++++ IN WebUserDetailsService :: loadUserByUsername :: START +++++");
        var webUser = webUserRepository.findWebUserByUsername(s);
        if (webUser.isEmpty()) {
            log.error("----- IN WebUserDetailsService :: loadUserByUsername :: FAIL - user not found -----");
            throw new UsernameNotFoundException("Пользователь не найден!");
        }
        log.info("+++++ IN WebUserDetailsService :: loadUserByUsername ::  FINISHED SUCCESSFULLY +++++");
        return new WebUserDetails(webUser.get());
    }
}