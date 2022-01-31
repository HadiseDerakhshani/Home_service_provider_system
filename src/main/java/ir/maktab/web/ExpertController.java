package ir.maktab.web;

import ir.maktab.config.LastViewInterceptor;
import ir.maktab.data.dto.ExpertDto;
import ir.maktab.data.dto.OrderDto;
import ir.maktab.data.dto.SubServiceDto;
import ir.maktab.data.dto.SuggestionDto;
import ir.maktab.service.implemention.ExpertServiceImpl;
import ir.maktab.service.implemention.OrderServiceImpl;
import ir.maktab.service.implemention.SubServiceServiceImpl;
import ir.maktab.service.implemention.SuggestionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
@RequestMapping("/expert")
public class ExpertController {

    private final ExpertServiceImpl expertService;

    private final SubServiceServiceImpl subServiceService;

    private final OrderServiceImpl orderService;

    private final SuggestionServiceImpl suggestionService;

@Autowired
    public ExpertController(@Lazy ExpertServiceImpl expertService, @Lazy SubServiceServiceImpl subServiceService,
                            @Lazy  OrderServiceImpl orderService, @Lazy SuggestionServiceImpl suggestionService) {
        this.expertService = expertService;
        this.subServiceService = subServiceService;
        this.orderService = orderService;
        this.suggestionService = suggestionService;
    }


    @GetMapping
    public String showRegisterPage(Model model) {
        List<SubServiceDto> subServiceDtoList = subServiceService.findAll();
        model.addAttribute("expert", new ExpertDto());
        model.addAttribute("subServiceDtoList", subServiceDtoList);
        return "expert/expert_register";
    }

    @PostMapping(value = "/registerExpert")
    public ModelAndView registerExpert(@RequestParam("image") CommonsMultipartFile image, @Validated @ModelAttribute("expert")
            ExpertDto expertDto, @RequestParam("name") String name) {
        expertDto.setPhoto(image.getBytes());
        expertService.addSubServiceToExpert(expertDto, name);
        expertService.save(expertDto);
        return new ModelAndView("expert/success_register", "expert", expertDto);
    }

    @ExceptionHandler(value = BindException.class)
    public ModelAndView bindExceptionHandler(BindException ex, HttpServletRequest request) {
        String lastView = (String) request.getSession().getAttribute(LastViewInterceptor.LAST_VIEW_ATTRIBUTE);
        return new ModelAndView(lastView, ex.getBindingResult().getModel());
    }

    @GetMapping("/suggestion")
    // public ModelAndView showRegisterSuggestPage() {
    public String showRegisterSuggestPage() {
        //List<OrderDto> list = orderService.findOrderToSuggest();
        List<OrderDto> list = orderService.findAll();
        //  return new ModelAndView("suggestion/choose_order", "list", list);
        return "suggestion/choose_order";
    }

    @GetMapping("/selectOrder/{number}")
    public String selectOrder(@PathVariable long number, Model model) {

        /// OrderDto orderDto = orderService.find(number);
        model.addAttribute("suggest", new SuggestionDto());
        //   model.addAttribute("orderDto", orderDto);
        return "suggestion/suggestion_register";
    }

    @PostMapping(value = "/registerSuggestion")
    public ModelAndView registerSuggestion(@Validated @ModelAttribute("suggest") SuggestionDto suggestionDto,
                                           @SessionAttribute("expert") ExpertDto expertDto,
                                           @SessionAttribute("orderDto") OrderDto orderDto) {
        SuggestionDto saveSuggest = suggestionService.save(suggestionDto, orderDto, expertDto);
        //  orderService.addSuggestionToOrder(orderDto,saveSuggest);
        return new ModelAndView("suggestion/success_register", "expert", expertDto);
    }
}
