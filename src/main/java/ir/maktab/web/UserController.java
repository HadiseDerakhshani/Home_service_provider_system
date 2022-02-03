package ir.maktab.web;

import ir.maktab.data.dto.CustomerDto;
import ir.maktab.data.dto.ExpertDto;
import ir.maktab.data.dto.OrderDto;
import ir.maktab.data.entity.enums.UserRole;
import ir.maktab.service.implemention.CustomerServiceImpl;
import ir.maktab.service.implemention.ExpertServiceImpl;
import ir.maktab.service.implemention.OrderServiceImpl;
import ir.maktab.service.implemention.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

@SessionAttributes({"customerProfile"})
@Controller
@RequestMapping("/user")
public class UserController {
    private final UserServiceImpl userService;
    private final ExpertServiceImpl expertService;
    private final CustomerServiceImpl customerService;
    private final OrderServiceImpl orderService;

    @Autowired
    public UserController(@Lazy UserServiceImpl userService, @Lazy ExpertServiceImpl expertService,
                          @Lazy CustomerServiceImpl customerService, @Lazy OrderServiceImpl orderService) {
        this.userService = userService;
        this.expertService = expertService;
        this.customerService = customerService;
        this.orderService = orderService;
    }

    @PostMapping("/login")
    public String showRegisterPage(@RequestParam("email") String email, @RequestParam("password") String password,
                                   Model model) {
        UserRole role = userService.findByEmail(email, password);

        if (role.equals(UserRole.EXPERT)) {
            ExpertDto expertDto = expertService.find(email);
            //////////////
            return null;

        } else if (role.equals(UserRole.CUSTOMER)) {
            CustomerDto customerProfile = customerService.find(email);
            List<OrderDto> orderList = orderService.findOrder(customerProfile);
            model.addAttribute("customerProfile", customerProfile);
         model.addAttribute("order", orderList);
            return "customer/customer_profile";
        }
        return "register";
    }


}
