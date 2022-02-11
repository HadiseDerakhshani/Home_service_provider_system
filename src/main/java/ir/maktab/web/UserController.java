package ir.maktab.web;

import ir.maktab.data.dto.*;
import ir.maktab.data.entity.enums.UserRole;
import ir.maktab.service.implemention.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
@RequestMapping("/user")
public class UserController {
    private final UserServiceImpl userService;
    private final ExpertServiceImpl expertService;
    private final CustomerServiceImpl customerService;
    private final OrderServiceImpl orderService;
    private final SuggestionServiceImpl suggestionService;

    @Autowired
    public UserController(@Lazy UserServiceImpl userService, @Lazy ExpertServiceImpl expertService,
                          @Lazy CustomerServiceImpl customerService, @Lazy OrderServiceImpl orderService,
                          @Lazy SuggestionServiceImpl suggestionService) {
        this.userService = userService;
        this.expertService = expertService;
        this.customerService = customerService;
        this.orderService = orderService;
        this.suggestionService = suggestionService;
    }

    @PostMapping("/login")
    public String showRegisterPage(@RequestParam("email") String email, @RequestParam("password") String password,
                                   Model model, HttpSession session) {
        UserRole role = userService.findByEmail(email, password);

        if (role.equals(UserRole.EXPERT)) {
            ExpertDto expertProfile = expertService.find(email);
              List<OrderDto> orderDtoList = orderService.findOrderByExpert(expertProfile);
             System.out.println(orderDtoList);//todo null check

            List<SuggestionDto> suggestionDtoList = suggestionService.findByExpert(expertProfile);
            List<SubServiceDto> serviceList = expertProfile.getServiceList();
            session.setAttribute("expert", expertProfile);
            model.addAttribute("expert", expertProfile);
             model.addAttribute("orderList", orderDtoList);
            model.addAttribute("suggestionDtoList", suggestionDtoList);
            model.addAttribute("serviceList", serviceList);
            return "expert/expert_profile";


        } else if (role.equals(UserRole.CUSTOMER)) {
            CustomerDto customerProfile = customerService.find(email);
            session.setAttribute("customer", customerProfile);
            List<OrderDto> orderList = orderService.findOrderByCustomer(customerProfile);
            model.addAttribute("customer", customerProfile);
            model.addAttribute("orderList", orderList);
            return "customer/customer_profile";
        }
        return "register";
    }


}
