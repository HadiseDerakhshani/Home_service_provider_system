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
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class ExpertController {
    private final ExpertServiceImpl expertService;
    private final SubServiceServiceImpl subServiceService;

    @GetMapping("/expert")
    public ModelAndView showRegisterPage() {

        return new ModelAndView("expert/expert_register", "expert", new ExpertDto());
    }

    @PostMapping(value = "/expert/initializer")
    public String initializer(@RequestParam("image") CommonsMultipartFile image,@Validated @ModelAttribute("expert") ExpertDto expertDto,
                              BindingResult br) {

       /* List<SubServiceDto> list ;
           if(subServiceService.findAll().size()!=0) {
               list = subServiceService.findAll();
               list.forEach(System.out::println);
               model.addAttribute("serviceList", list);*/

        expertDto.setImage(image.getBytes());
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