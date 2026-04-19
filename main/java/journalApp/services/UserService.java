package journalApp.services;


import journalApp.Entities.User;
import journalApp.Repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@Service
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
    @Transactional
    public void updateUser(User existingUser, User newUserData) {

        if (newUserData.getUsername() != null && !newUserData.getUsername().isBlank() &&
        !newUserData.getUsername().equals(existingUser.getUsername()))
        {
            User check = userrepo.findByUsername(newUserData.getUsername());
            if(check == null)
            { existingUser.setUsername(newUserData.getUsername());}
            else{
                throw new RuntimeException("A user with this name already exists");
            }
        }

        if (newUserData.getPassword() != null && !newUserData.getPassword().isBlank()) {
            existingUser.setPassword(passwordEncoder.encode(newUserData.getPassword()));
        }

        userrepo.save(existingUser);
    }
    @Transactional
    public void deleteUser(User user){
        userrepo.deleteByUserId(user.getUserId());
    }



    public List<User> getAll() //To get data through the repository for GET
    {
        return userrepo.findAll();
    }
    public Optional<User> usersbyId(ObjectId ID){
        return userrepo.findById(ID);
    }


    public User findByUserName(String username){

        if(username == null || username.isBlank()){
            throw new IllegalArgumentException("User name is Empty");
        }
        User userFound = userrepo.findByUsername(username);

        if(userFound == null){
            throw new RuntimeException("No user found");
        }

        return  userFound;
    }

}
