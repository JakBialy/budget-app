package jakub.budgetapp.budgetapp.controllers;

import jakub.budgetapp.budgetapp.dtos.UserDto;
import jakub.budgetapp.budgetapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity userController (@Valid @ModelAttribute UserDto userDto){
        // errors should be validate somehow more than just returning 500
        userService.saveUser(userDto);

        return ResponseEntity
                .ok()
                .build();
    }

    @GetMapping("/check")
    public String checkIt (){
        return "Works!";
    }



}
