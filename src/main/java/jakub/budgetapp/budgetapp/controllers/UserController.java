package jakub.budgetapp.budgetapp.controllers;

import jakub.budgetapp.budgetapp.dtos.UserDto;
import jakub.budgetapp.budgetapp.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void userController (@Valid @ModelAttribute UserDto userDto){
        // errors should be validate somehow more than just returning 500
        userService.saveUser(userDto);
    }

    /**
     * For checking connection after authorizing
     * @return String "Works!"
     */
    @GetMapping("/check")
    public String checkIt (){
        return "Works!";
    }

}
