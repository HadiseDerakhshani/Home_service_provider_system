package ir.maktab.controller;

import ir.maktab.data.dto.ExpertDto;
import ir.maktab.service.implemention.ExpertServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class ExpertController {
    private final ExpertServiceImpl expertService;

    @GetMapping("/expert")
    public ModelAndView showRegisterPage() {

        return new ModelAndView("expert/expert_register", "expert", new ExpertDto());
    }

    @PostMapping(value = "/expert/register")
    public String register(@RequestParam CommonsMultipartFile image, @Valid @ModelAttribute("expert") ExpertDto expertDto,
                           BindingResult br) {
        if (br.hasErrors())
            return "expert/expert_register";
        else {
            ExpertDto dto = expertService.addPicture(expertDto, image.getStorageDescription());
            expertService.save(dto);
            // model.addAttribute("suggest",new SuggestionDto());
            return "login";
        }
    }
}
