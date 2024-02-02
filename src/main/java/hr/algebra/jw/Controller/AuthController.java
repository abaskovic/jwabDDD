package hr.algebra.jw.Controller;

import hr.algebra.jw.Dto.UserDto;
import hr.algebra.jw.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    UserDetailsService  userDetailsService;

    @GetMapping("/register")
    public String showRegistrationForm(@ModelAttribute("user") UserDto userDto) {
        return "register";
    }
    @PostMapping("register")
    public String saveUser(@ModelAttribute("user")UserDto userDto, Model model){
        userDto.setRole("USER");
        userService.save(userDto);

        model.addAttribute("message", "REGISTRED SUCCESSFULY");
        return "register";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/user")
    public String userPage(Model model, Principal principal) {
        UserDetails userDetails= userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        return "user/index";
    }
    @GetMapping("/admin")
    public String adminPage(Model model, Principal principal) {
        UserDetails userDetails= userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        return "admin/index";

    }

}
