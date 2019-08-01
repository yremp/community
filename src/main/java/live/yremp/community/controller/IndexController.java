package live.yremp.community.controller;

import live.yremp.community.dto.PageDTO;
import live.yremp.community.entity.User;
import live.yremp.community.provider.UserProvider;
import live.yremp.community.service.QuesDtoService;
import live.yremp.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Controller
public class IndexController {
    @Autowired
    private UserService userService;
    @Autowired
    private QuesDtoService quesDtoService;
    @Autowired
    UserProvider userProvider;
    @RequestMapping("/")
    public String IndexController(HttpServletRequest request,
                                  Model model,
                                  @RequestParam(value = "page",defaultValue = "1")Integer page,
                                  @RequestParam(value = "size",defaultValue = "5")Integer size){
            userProvider.login(request);
            PageDTO pagenation=quesDtoService.list1(page,size);
            model.addAttribute("pagenation",pagenation);
            return "index";
    }
}
