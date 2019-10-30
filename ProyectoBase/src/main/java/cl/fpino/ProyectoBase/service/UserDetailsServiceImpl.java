package cl.fpino.ProyectoBase.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // podemos agregar funcionalidad para buscar en los DAOS

        if ("user".equals(username)) {
            return this.userBuilder(username, new BCryptPasswordEncoder().encode("123456"), "USER");
        } else if ("manager".equals(username)) {
            return this.userBuilder(username, new BCryptPasswordEncoder().encode("123456"), "MANAGER");
        } else if ("admin".equals(username)) {
            return this.userBuilder(username, new BCryptPasswordEncoder().encode("123456"), "USER", "MANAGER", "ADMIN");
        } else {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
    }

    private User userBuilder(String username, String password, String... roles) {
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String rol: roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + rol));
        }
        // PARA ENCRIPTAR CLAVES
        // new BCryptPasswordEncoder().encode(password)
        return new User(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }
}
