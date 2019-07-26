package live.yremp.community.controller;

import live.yremp.community.dto.QuesDto;
import live.yremp.community.entity.Question;
import live.yremp.community.entity.User;
import live.yremp.community.service.QuesDtoService;
import live.yremp.community.service.QuesService;
import live.yremp.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private UserService userService;
    @Autowired
    private QuesDtoService quesDtoService;
    @RequestMapping("/")
    public String IndexController(HttpServletRequest request, Model model){
        Cookie [] cookies = request.getCookies();
        for(Cookie cookie:cookies){
            if(cookie.getName().equals("token")){
                String token=cookie.getValue();
                User user = userService.findByToken(token);
                if(user!=null){
                    request.getSession().setAttribute("user",user);
                }
                break;
            }
        }
        List<QuesDto> quesDtoList =quesDtoService.list();
        model.addAttribute("questions",quesDtoList);
        return "index";

    }
}
