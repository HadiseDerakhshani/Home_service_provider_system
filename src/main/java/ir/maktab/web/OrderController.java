package ir.maktab.web;

import ir.maktab.data.dto.CustomerDto;
import ir.maktab.data.dto.OrderDto;
import ir.maktab.data.dto.ServiceDto;
import ir.maktab.data.dto.SubServiceDto;
import ir.maktab.data.model.serviceSystem.Service;
import ir.maktab.service.implemention.CustomerServiceImpl;
import ir.maktab.service.implemention.OrderServiceImpl;
import ir.maktab.service.implemention.ServiceServiceImpl;
import ir.maktab.service.implemention.SubServiceServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Component
@Controller
@RequiredArgsConstructor
public class OrderController {
    @Lazy
    private final CustomerServiceImpl customerService;
    @Lazy
    private final OrderServiceImpl orderService;
    @Lazy
    private final SubServiceServiceImpl subServiceService;
    @Lazy
    private final ServiceServiceImpl service;

    @GetMapping("/order")
    public ModelAndView showRegisterOrder() {

        List<ServiceDto> serviceDtoList = service.findAll();

        return new ModelAndView("/order/choose_service", "serviceDtoList",serviceDtoList);
    }

    @PostMapping("/order/registerOrder")
    public ModelAndView registerOrder(@Valid @ModelAttribute("order") OrderDto orderDto,@RequestParam("name")String name,
                                      @ModelAttribute("customer") CustomerDto customerDto, BindingResult br) {


        if (br.hasErrors())
            return new ModelAndView("order/order_register");
        else {
            OrderDto saveOrder = orderService.save(orderDto);
            customerDto.getOrderList().add(saveOrder);
            customerService.save(customerDto);
            return new ModelAndView("customer/success_register", "message",
                    "success register order for customer");
        }
    }

    @PostMapping("/order/selectService/{name}")
    public String selectService(@PathVariable String name,Model model) {
        ServiceDto serviceDto = service.findByName(name);
        Set<SubServiceDto> subServiceList = serviceDto.getSubServiceList();
        model.addAttribute("subServiceList",subServiceList);
        model.addAttribute("order", new OrderDto());
        return "order/order_register";
    }
}
