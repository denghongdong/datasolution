package com.jdjr.datasolution.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by xiaodengliang on 16/2/29.
 */
@Controller
@RequestMapping("/")
public class HomeController {

    @RequestMapping(value = {"","/","index","home"})
    public String homePage(Model m) {
        return "/home";
    }
}
