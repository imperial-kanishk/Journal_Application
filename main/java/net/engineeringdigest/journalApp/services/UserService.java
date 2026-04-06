package net.engineeringdigest.journalApp.services;

import net.engineeringdigest.journalApp.Entities.User;
import net.engineeringdigest.journalApp.Repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {
    @Autowired
    private UserRepository userrepo;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void SaveEntry(User user)
    {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        userrepo.save(user);

    }
    public List<User> getAll() //To get data through the repository for GET
    {
        return userrepo.findAll();
    }
    public Optional<User> usersbyId(ObjectId ID){
        return userrepo.findById(ID);
    }
    public void delete_byID(ObjectId id){
        userrepo.deleteById(id);
    }
    public User findByUserName(String username){
        return userrepo.findByUsername(username);
    }

}
