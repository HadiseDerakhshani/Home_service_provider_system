package ir.maktab.web;

import ir.maktab.config.LastViewInterceptor;
import ir.maktab.data.dto.CustomerDto;
import ir.maktab.data.dto.OrderDto;
import ir.maktab.data.dto.ServiceDto;
import ir.maktab.data.dto.SubServiceDto;
import ir.maktab.data.entity.enums.OrderStatus;
import ir.maktab.service.implemention.CustomerServiceImpl;
import ir.maktab.service.implemention.OrderServiceImpl;
import ir.maktab.service.implemention.ServiceServiceImpl;
import ir.maktab.service.implemention.SubServiceServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

@Component
@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    @Lazy
    private final CustomerServiceImpl customerService;
    @Lazy
    private final OrderServiceImpl orderService;
    @Lazy
    private final SubServiceServiceImpl subServiceService;
    @Lazy
    private final ServiceServiceImpl service;


    @GetMapping
    public ModelAndView showRegisterOrder() {

        List<ServiceDto> serviceDtoList = service.findAll();

        return new ModelAndView("services/choose_service", "serviceDtoList", serviceDtoList);
    }

    @PostMapping("/registerOrder")
    public ModelAndView registerOrder(@Validated @ModelAttribute("order") OrderDto orderDto,
                                      @SessionAttribute("customer") CustomerDto customerDto,
                                      @RequestParam("name") String name) {

        SubServiceDto serviceDto = subServiceService.findByName(name);
        orderDto.setStatus(OrderStatus.WAITING_FOR_EXPERT_SUGGESTION);
        OrderDto saveOrder = orderService.save(orderDto);
        orderService.addCustomerToOrder(customerDto, saveOrder);
        orderService.addServiceToOrder(serviceDto, saveOrder);
        customerService.updateOrder(customerDto, saveOrder);
        return new ModelAndView("order/success_register", "message",
                "success register order for customer");
    }

    @ExceptionHandler(value = BindException.class)
    public ModelAndView bindExceptionHandler(BindException ex, HttpServletRequest request) {
        String lastView = (String) request.getSession().getAttribute(LastViewInterceptor.LAST_VIEW_ATTRIBUTE);
        return new ModelAndView(lastView, ex.getBindingResult().getModel());
    }

    @GetMapping("/selectService/{name}")
    public String selectService(@PathVariable String name, Model model) {
        ServiceDto serviceDto = service.findByName(name);
        Set<SubServiceDto> subServiceList = serviceDto.getSubServiceList();
        model.addAttribute("subServiceList", subServiceList);
        model.addAttribute("order", new OrderDto());
        return "order/order_register";
    }
}
