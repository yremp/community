package live.yremp.community.controller;

import live.yremp.community.dto.PageDto;
import live.yremp.community.dto.QuesDto;
import live.yremp.community.entity.User;
import live.yremp.community.service.QuesDtoService;
import live.yremp.community.service.QuesService;
import live.yremp.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String IndexController(HttpServletRequest request,
                                  Model model,
                                  @RequestParam(value = "page",defaultValue = "1")Integer page,
                                  @RequestParam(value = "size",defaultValue = "5")Integer size){
        Cookie [] cookies ;
        try{
           cookies = request.getCookies();
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
            PageDto pagetion =quesDtoService.list(page,size);
            model.addAttribute("pagetion",pagetion);
            return "index";

        }catch (Exception e){

            return "index";
        }

    }
}
