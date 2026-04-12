package net.sampleproject.journalApp.Controller;

import net.sampleproject.journalApp.Entities.User;
import net.sampleproject.journalApp.Repository.UserRepository;
import net.sampleproject.journalApp.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
   private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user, Authentication authentication) {

        String username = authentication.getName();

        User existingUser = userService.findByUserName(username);

        if (existingUser == null) {
            return ResponseEntity.notFound().build();
        }

        userService.updateUser(existingUser, user);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<?> DeleteUserInfo(@RequestBody User user)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        userRepository.deleteByUserId(user.getUserId());


        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
