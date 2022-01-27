package ir.maktab.web;

import ir.maktab.config.LastViewInterceptor;
import ir.maktab.data.dto.*;
import ir.maktab.data.model.serviceSystem.Service;
import ir.maktab.service.implemention.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    @Lazy
    private final AddressServiceImpl addressService;

    @GetMapping("/order")
    public ModelAndView showRegisterOrder() {

        List<ServiceDto> serviceDtoList = service.findAll();

        return new ModelAndView("/order/choose_service", "serviceDtoList",serviceDtoList);
    }

    @PostMapping("/order/registerOrder")
    public ModelAndView registerOrder(@Validated @ModelAttribute("order") OrderDto orderDto, @RequestParam("name")String name,
                                      @ModelAttribute("customer") CustomerDto customerDto,@ModelAttribute("order") AddressDto addressDto) {

       /* SubServiceDto serviceDto = subServiceService.findByName(name);
        orderDto.setService(serviceDto);*/

        //TODO
      addressService.save(addressDto);
      orderDto.setAddress(addressDto);
        OrderDto saveOrder = orderService.save(orderDto);
            customerDto.getOrderList().add(saveOrder);
            customerService.save(customerDto);
            return new ModelAndView("customer/success_register", "message",
                    "success register order for customer");
    }
    @ExceptionHandler(value = BindException.class)
    public ModelAndView bindExceptionHandler(BindException ex, HttpServletRequest request) {
        String lastView = (String) request.getSession().getAttribute(LastViewInterceptor.LAST_VIEW_ATTRIBUTE);
        return new ModelAndView(lastView, ex.getBindingResult().getModel());
    }
    @GetMapping("/order/selectService/{name}")
    public String selectService(@PathVariable String name,Model model) {
        ServiceDto serviceDto = service.findByName(name);
        Set<SubServiceDto> subServiceList = serviceDto.getSubServiceList();
        model.addAttribute("subServiceList",subServiceList);
        model.addAttribute("order", new OrderDto());
        model.addAttribute("address",new AddressDto());
        return "order/order_register";
    }
}
