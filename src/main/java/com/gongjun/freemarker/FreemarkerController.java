package com.gongjun.freemarker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map; 

/**
 * @Description:
 * @Author: GongJun
 * @Date: Created in 11:06 2021/8/24
 */
@Controller
@RequestMapping("index")
public class FreemarkerController {
    @Value("${my.name}")
    private String name;
    @GetMapping("/myfree")
    public String myfree(Model model, HttpServletRequest request){
        model.addAttribute("name",name);
        return "index";
    }
}
