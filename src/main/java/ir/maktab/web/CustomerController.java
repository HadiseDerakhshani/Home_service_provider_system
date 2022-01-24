package ir.maktab.web;

import ir.maktab.data.dto.CustomerDto;
import ir.maktab.service.implemention.CustomerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerServiceImpl customerService;

    @GetMapping("/customer")
    public ModelAndView showRegisterPage() {

        return new ModelAndView("customer/customer_register", "customer", new CustomerDto());
    }

    @PostMapping("/customer/register")
    public ModelAndView register(@Valid @ModelAttribute("customer") CustomerDto customerDto, BindingResult br, Model model) {
        if (br.hasErrors())
            return new ModelAndView("customer/customer_register", "message", "form is not full");
        else {
            customerService.save(customerDto);
            return new ModelAndView("customer/success_register", "customer", customerDto);

        }
    }


}
