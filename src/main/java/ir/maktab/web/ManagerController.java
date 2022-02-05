package ir.maktab.web;

import ir.maktab.data.dto.ExpertDto;
import ir.maktab.data.dto.ServiceDto;
import ir.maktab.data.dto.SubServiceDto;
import ir.maktab.exception.DuplicateServiceException;
import ir.maktab.service.implemention.ExpertServiceImpl;
import ir.maktab.service.implemention.ServiceServiceImpl;
import ir.maktab.service.implemention.SubServiceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/manager")
public class ManagerController {
    private final ServiceServiceImpl service;
    private final SubServiceServiceImpl subServiceImpl;
    private final ExpertServiceImpl expertService;
@Autowired
    public ManagerController(@Lazy  ServiceServiceImpl service, @Lazy SubServiceServiceImpl subServiceImpl,
                             @Lazy ExpertServiceImpl expertService) {
        this.service = service;
        this.subServiceImpl=subServiceImpl;
        this.expertService=expertService;
    }

    @GetMapping
    public String showViewManager(){
        return "manager/page_manager";
    }


    @GetMapping("/addService")
    public String showAddServicePage(Model model){
        List<ServiceDto> serviceList = service.findAll();
     model.addAttribute("serviceList",serviceList);
        return "services/add_service";
    }
    @PostMapping("/newService")
    public String addService(@RequestParam("name") String newName,Model model){
    try{
    service.save(newName);
    }catch (DuplicateServiceException e){
        model.addAttribute("alert",e.getMessage());
       return "services/add_service";
    }
        List<ServiceDto> serviceList = service.findAll();
        model.addAttribute("serviceList",serviceList);
    model.addAttribute("message", "service add successfully");
    return "services/add_service";
}
    @GetMapping("/addSubService/{name}")
    public String showAddSubServicePage(@PathVariable("name")String name, Model model, HttpSession session){
        ServiceDto serviceDto = service.findByName(name);
        session.setAttribute("service",serviceDto);
        Set<SubServiceDto> subServiceList = serviceDto.getSubServiceList();
        model.addAttribute("subServiceList",subServiceList);
        model.addAttribute("subService",new SubServiceDto());
        return "services/add_subService";
    }
    @PostMapping("/newSubService")
    public String addSubService(@ModelAttribute("subService")SubServiceDto subServiceDto, Model model,
                                @SessionAttribute("service")ServiceDto serviceDto){

        SubServiceDto saveSubService;
        try{
            saveSubService  = subServiceImpl.save(subServiceDto);
        }catch (DuplicateServiceException e){
            model.addAttribute("alert",e.getMessage());
            return "services/add_subService";
        }

        ServiceDto serviceUpdated = this.service.addSubService(serviceDto, saveSubService);
        Set<SubServiceDto> subServiceList = serviceUpdated.getSubServiceList();
        model.addAttribute("subServiceList",subServiceList);
        model.addAttribute("message", "sub service add successfully");
        return "services/add_subService";
    }
    @GetMapping("/selectExpert")
    public String showAddExpert(Model model){
        long record = expertService.totalRecord();
        List<ExpertDto> expertDtoList = expertService.findAll(1, (int) record);
        model.addAttribute("expertList",expertDtoList);
        return "/manager/select_expert";
    }
}
