package com.example.app.api;

import com.example.app.utils.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(Model model) {

        String role = Utils.getCurrentRole();

        model.addAttribute("role", role);
        System.out.println(role);

        return "index";
    }
}
