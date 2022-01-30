package ir.maktab.web;

import ir.maktab.data.dto.CustomerDto;
import ir.maktab.data.dto.ExpertDto;
import ir.maktab.data.entity.enums.UserRole;
import ir.maktab.service.implemention.CustomerServiceImpl;
import ir.maktab.service.implemention.ExpertServiceImpl;
import ir.maktab.service.implemention.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {
private  final UserServiceImpl userService;
private final ExpertServiceImpl expertService;
private CustomerServiceImpl customerService;
   @PostMapping("/login")
    public ModelAndView showRegisterPage(@RequestParam("password")String pass,@RequestParam("email")String email) {
       UserRole role = userService.findByEmail(email, pass);
       if(!role.equals(null)){
       if(role.equals(UserRole.EXPERT)){
           ExpertDto expertDto = expertService.find(email);
           return new ModelAndView("expert_profile","expertDto",expertDto);
       }else if(role.equals(UserRole.CUSTOMER)) {
           CustomerDto customerDto = customerService.find(email);
           return new ModelAndView("customer_profile", "customerDto", customerDto);
       }
       }

    return new ModelAndView("login","message","user is not exit");
   }

}
