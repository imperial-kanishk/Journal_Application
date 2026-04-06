package net.engineeringdigest.journalApp.Controller;

import net.engineeringdigest.journalApp.Entities.JournalEntries;
import net.engineeringdigest.journalApp.Entities.User;
import net.engineeringdigest.journalApp.Repository.UserRepository;
import net.engineeringdigest.journalApp.services.JournalService;
import net.engineeringdigest.journalApp.services.UserService;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
   private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PutMapping
    public ResponseEntity<?> Update_UserInfo(@RequestBody User user)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();


        User byUserID = userService.findByUserName(userName);
        if(byUserID != null){
            byUserID.setUsername(user.getUsername());
            byUserID.setPassword(user.getPassword());
            userService.SaveEntry(byUserID);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> DeleteUserInfo(@RequestBody User user)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        userRepository.deleteByUserId(userName);


        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
