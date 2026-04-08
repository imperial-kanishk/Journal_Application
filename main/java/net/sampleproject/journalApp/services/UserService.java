package net.sampleproject.journalApp.services;

import net.sampleproject.journalApp.Entities.User;
import net.sampleproject.journalApp.Repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    private UserRepository userrepo;


    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userrepo, PasswordEncoder passwordEncoder) {
        this.userrepo = userrepo;
        this.passwordEncoder = passwordEncoder;
    }

    public void createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(List.of("USER"));
        }

        userrepo.save(user);
    }

    public void saveUser(User user) {
        userrepo.save(user);
    }

//    public void SaveEntry(User user)
//    {
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        user.setRoles(Arrays.asList("USER"));
//        userrepo.save(user);
//
//    }

    public void updateUser(User existingUser, User newUserData) {

        if (newUserData.getUsername() != null) {
            existingUser.setUsername(newUserData.getUsername());
        }

        if (newUserData.getPassword() != null && !newUserData.getPassword().isBlank()) {
            existingUser.setPassword(passwordEncoder.encode(newUserData.getPassword()));
        }

        userrepo.save(existingUser);
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
