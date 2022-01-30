package ir.maktab.web;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping(value = "/login")
    public String loginView() {

        return "login";
    }

    @GetMapping(value = "/register")
    public String registerView() {

        return "register";
    }
}

