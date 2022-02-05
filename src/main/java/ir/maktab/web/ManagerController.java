package ir.maktab.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manager")
public class ManagerController {
    @GetMapping
    public String showViewManager(){
        return "manager/page_manager";
    }
    @GetMapping("/manager/addService")
    public String showAddServicePage(Model model){

        return "manager/page_manager";
    }
}
