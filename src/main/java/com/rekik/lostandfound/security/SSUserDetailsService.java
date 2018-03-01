package com.rekik.lostandfound.security;

import com.rekik.lostandfound.model.AppRole;
import com.rekik.lostandfound.model.AppUser;
import com.rekik.lostandfound.repository.AppUserRepo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Transactional
@Service
public class SSUserDetailsService implements UserDetailsService {
    private AppUserRepo appUserRepo;
    public SSUserDetailsService(AppUserRepo appUserRepo){
        this.appUserRepo=appUserRepo;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try {
            AppUser thisUser = appUserRepo.findAppUserByUsername(username);
            if (thisUser == null) {
              // throw new UsernameNotFoundException("Invalid username or password");
                return null;
            }
            return new org.springframework.security.core.userdetails.User(
                    thisUser.getUsername(),
                    thisUser.getPassword(),
                    getAuthorities(thisUser));
        } catch (Exception e) {
            throw new UsernameNotFoundException("user not found");

        }

    }
    private Set<GrantedAuthority> getAuthorities (AppUser user){
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        for (AppRole appRole : user.getRoles()) {
            GrantedAuthority grantedAuthority
                    = new SimpleGrantedAuthority(appRole.getRoleName());
            authorities.add(grantedAuthority);
        }
        return authorities;
    }
}