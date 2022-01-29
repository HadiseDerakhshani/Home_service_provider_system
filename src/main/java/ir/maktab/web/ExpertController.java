package ir.maktab.web;

import ir.maktab.config.LastViewInterceptor;
import ir.maktab.data.dto.ExpertDto;
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
        model.addAttribute("serviceList", subServiceDtoList);
        model.addAttribute("expert", new ExpertDto());
        return "expert/expert_register";
    }

    @PostMapping(value = "/expert/registerExpert")
    public String registerExpert(@RequestParam("image") CommonsMultipartFile image, @Validated @ModelAttribute("expert")
            ExpertDto expertDto, @RequestParam("name") String name) {
        expertDto.setPhoto(image.getBytes());
        expertService.addSubServiceToExpert(expertDto, name);
        expertService.save(expertDto);
        return "expert/success_register";
    }

    @ExceptionHandler(value = BindException.class)
    public ModelAndView bindExceptionHandler(BindException ex, HttpServletRequest request) {
        String lastView = (String) request.getSession().getAttribute(LastViewInterceptor.LAST_VIEW_ATTRIBUTE);
        return new ModelAndView(lastView, ex.getBindingResult().getModel());
    }


}
