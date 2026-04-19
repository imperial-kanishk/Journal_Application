package journalApp.Controller;

import jakarta.validation.Valid;
import journalApp.Configuration.JwtUtil;
import journalApp.Entities.User;
import journalApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/health-check")
    public String health() {
        return "Health OK";
    }

//    @PostMapping("create-admin")
//    public ResponseEntity<String> createAdmin(@RequestBody User entry) {
//        entry.setRoles(new ArrayList<>(List.of("ADMIN")));
//        userService.createUser(entry);
//        return ResponseEntity.status(HttpStatus.CREATED).body("Admin created successfully");
//    }

    @PostMapping("/signup") //For creating new user
    public ResponseEntity<String> createUser(@Valid @RequestBody User entry) {
        try {
            userService.createUser(entry);
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already taken");
        }
    }

    @PostMapping("/login") //for logging in
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );
            String token = jwtUtil.generateToken(user.getUsername());
            return ResponseEntity.ok(Map.of("token", token));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}
