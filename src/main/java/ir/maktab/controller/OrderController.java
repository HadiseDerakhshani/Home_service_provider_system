package ir.maktab.controller;

import ir.maktab.data.dto.OrderDto;
import ir.maktab.service.implemention.CustomerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final CustomerServiceImpl customerService;


    @GetMapping("/order")
    public ModelAndView showRegisterOrder() {

        return new ModelAndView("order/order_register", "order", new OrderDto());
    }

    @PostMapping("/order/registerOrder")
    public String registerOrder(@Valid @ModelAttribute("order") OrderDto orderDto, BindingResult br) {
        if (br.hasErrors())
            return "order/order_register";
        else
            return "customer/success_register";
    }

}
