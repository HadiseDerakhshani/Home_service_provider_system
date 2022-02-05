package ir.maktab.web;

import ir.maktab.data.dto.ServiceDto;
import ir.maktab.data.entity.serviceSystem.Service;
import ir.maktab.exception.DuplicateServiceException;
import ir.maktab.service.implemention.ServiceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/manager")
public class ManagerController {
    private final ServiceServiceImpl service;
@Autowired
    public ManagerController(ServiceServiceImpl service) {
        this.service = service;
    }

    @GetMapping
    public String showViewManager(){
        return "manager/page_manager";
    }
    @GetMapping("/manager/addService")
    public String showAddServicePage(Model model, @SessionAttribute("serviceList")List<ServiceDto> serviceList){
     model.addAttribute("serviceList",serviceList);
        return "services/add_service";
    }
    @PostMapping("/newService")
    public String addService(@RequestParam("name") String newName,Model model,
                             @SessionAttribute("serviceList")List<ServiceDto> serviceList){
    try{
    service.save(newName);
    }catch (DuplicateServiceException e){
        model.addAttribute("alert",e.getMessage());
       return "services/add_service";
    }
        serviceList = service.findAll();
        model.addAttribute("serviceList",serviceList);
    model.addAttribute("message", "service add successfully");
    return "services/add_service";
}
}
