package net.engineeringdigest.journalApp.Controller;

import net.engineeringdigest.journalApp.Entities.User;
import net.engineeringdigest.journalApp.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private UserService userService;

    @GetMapping("/health-check")
    public String health(){
        return "Health OK";
    }


    @PostMapping("create-user") //For Creating User
    public void Create_User(@RequestBody User entry){
        userService.SaveEntry(entry);
    }
}
