package journalApp.services;

import journalApp.Entities.User;
import journalApp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailServiceIMPL implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User Not Found");
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                // Uncomment below when roles are properly set up:
                // .authorities(user.getRoles().stream().map(r -> "ROLE_" + r).toList())
                .roles(user.getRoles() != null
                        ? user.getRoles().toArray(new String[0])
                        : new String[]{"USER"})
                .build();
    }
}
