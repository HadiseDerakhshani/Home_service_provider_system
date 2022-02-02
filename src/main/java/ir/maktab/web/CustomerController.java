package ir.maktab.web;

import ir.maktab.config.LastViewInterceptor;
import ir.maktab.data.dto.CustomerDto;
import ir.maktab.service.implemention.CustomerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SessionAttributes({"customer", "expert", "orderDto", "customerProfile"})
@Controller
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerServiceImpl customerService;

    @GetMapping
    public ModelAndView showRegisterPage() {

        return new ModelAndView("customer/customer_register", "customer", new CustomerDto());
    }

    @PostMapping("/register")
    public ModelAndView register(@Validated @ModelAttribute("customer") CustomerDto customerDto) {
        customerService.save(customerDto);
        return new ModelAndView("customer/success_register", "customer", customerDto);
    }

    @ExceptionHandler(value = BindException.class)
    public ModelAndView bindExceptionHandler(BindException ex, HttpServletRequest request) {
        String lastView = (String) request.getSession().getAttribute(LastViewInterceptor.LAST_VIEW_ATTRIBUTE);
        return new ModelAndView(lastView, ex.getBindingResult().getModel());
    }



 @PostMapping("/pass")
 public ModelAndView StringChangePassword(@RequestParam("password")String password) {
     // customerService.changePassword(customerDto,password);
     System.out.println(password);
     return new ModelAndView("customer/customer_profile","message","change password successfully");
 }
    @PostMapping("/phone")
    public ModelAndView StringChangePhone(@RequestParam("phone")String phone) {
        // customerService.changePassword(customerDto,password);
        System.out.println(phone);
        return new ModelAndView("customer/customer_profile","message","change phone number successfully");
    }


}
