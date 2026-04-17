package journalApp.Controller;


import journalApp.Entities.User;
import journalApp.Repository.UserRepository;


import journalApp.services.UserService;
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
    public ResponseEntity<?> DeleteUserInfo(Authentication authentication)
    {

        String userName = authentication.getName();

        User user = userService.findByUserName(userName);

        if(user == null){
            return new ResponseEntity<>("User not found!",HttpStatus.NOT_FOUND);
        }


        userRepository.deleteByUserId(user.getUserId());


        return new ResponseEntity<>("Account Deleted Successfully",HttpStatus.NO_CONTENT);
    }


}
