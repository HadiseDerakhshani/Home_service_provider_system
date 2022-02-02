package ir.maktab.web;


import ir.maktab.data.dto.UserDto;
import ir.maktab.data.entity.enums.UserRole;
import ir.maktab.service.implemention.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    private final UserServiceImpl userService;
@Autowired
    public HomeController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/login")
    public String loginView(Model model) {
        List<UserDto> userDtoList = userService.findAll();
        model.addAttribute("list",userDtoList);
        model.addAttribute("test",  new String());
        return "login";
    }

    @GetMapping(value = "/register")
    public String registerView() {

        return "register";
    }
}

