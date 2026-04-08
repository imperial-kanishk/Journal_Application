package net.sampleproject.journalApp.Controller;

import net.sampleproject.journalApp.Entities.User;
import net.sampleproject.journalApp.services.UserService;

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
        userService.createUser(entry);
    }
}
