package live.yremp.community.controller;

import jdk.nashorn.internal.objects.annotations.Getter;
import live.yremp.community.dto.PageDto;
import live.yremp.community.entity.User;
import live.yremp.community.service.QuesDtoService;
import live.yremp.community.service.QuesService;
import live.yremp.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {
    @Autowired
    private UserService userService;
    @Autowired
    private QuesDtoService quesDtoService;
    @GetMapping("/profile/{action}")
    public String profile(@PathVariable("action") String action,
                          @RequestParam(value = "page",defaultValue = "1")Integer page,
                          @RequestParam(value = "size",defaultValue = "5")Integer size,
                          Model model,
                          HttpServletRequest request){
        User user=null;
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie:cookies){
            if(cookie.getName().equals("token")){
                String token=cookie.getValue();
                user = userService.findByToken(token);
                if(user!=null){
                    request.getSession().setAttribute("user",user);
                }
                break;
            }
        }
        if(user==null){
            return "redirect:/";
        }
        if(action.equals("question")){
            model.addAttribute("action","question");
            model.addAttribute("actioname","我的提问");
        }
        else if(action.equals("reply")){
            model.addAttribute("action","reply");
            model.addAttribute("actioname","最新回复");
        }
        PageDto pagenation=quesDtoService.list(user.getUser_id(),page,size);
        model.addAttribute("pagenation",pagenation);
        return "profile";
    }
}
