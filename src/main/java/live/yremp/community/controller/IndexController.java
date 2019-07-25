package live.yremp.community.controller;

import live.yremp.community.entity.User;
import live.yremp.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    @Autowired
    private UserService userService;
    @RequestMapping("/")
    public String IndexController(HttpServletRequest request){
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
        return "index";
    }
}
