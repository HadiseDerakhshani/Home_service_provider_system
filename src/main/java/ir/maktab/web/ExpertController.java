package ir.maktab.web;

import ir.maktab.config.LastViewInterceptor;
import ir.maktab.data.dto.ExpertDto;
import ir.maktab.data.dto.OrderDto;
import ir.maktab.data.dto.SubServiceDto;
import ir.maktab.service.implemention.ExpertServiceImpl;
import ir.maktab.service.implemention.SubServiceServiceImpl;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class ExpertController {
    private final ExpertServiceImpl expertService;
    private final SubServiceServiceImpl subServiceService;

    @GetMapping("/expert")
    public String showRegisterPage(Model model) {
        List<SubServiceDto> subServiceDtoList = subServiceService.findAll();
        model.addAttribute("serviceList",subServiceDtoList);
        model.addAttribute("expert", new ExpertDto());
        return "expert/expert_register";
    }

    @PostMapping(value = "/expert/initializer")
    public String initializer(@RequestParam("image") CommonsMultipartFile image,@Validated @ModelAttribute("expert")
            ExpertDto expertDto) {
               expertDto.setPhoto(image.getBytes());
               expertService.save(expertDto);
               return "services/service_selected";
    }
    @ExceptionHandler(value = BindException.class)
    public ModelAndView bindExceptionHandler(BindException ex, HttpServletRequest request) {
        String lastView = (String) request.getSession().getAttribute(LastViewInterceptor.LAST_VIEW_ATTRIBUTE);
        return new ModelAndView(lastView, ex.getBindingResult().getModel());
    }

    @PostMapping(value = "/expert/register")
    public ModelAndView register(@ModelAttribute("nameService") String nameService,
                                 @RequestParam("expert") ExpertDto expertDto,
                                 Model model) {
        SubServiceDto serviceDto = subServiceService.findByName(nameService);
        expertDto.getServiceList().add(serviceDto);
        expertService.save(expertDto);
        return new ModelAndView("/expert/success_register", "message", "Success add service to expert");
    }

}
