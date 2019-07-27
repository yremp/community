package live.yremp.community.controller;

import live.yremp.community.entity.Question;
import live.yremp.community.entity.User;
import live.yremp.community.service.QuesService;
import live.yremp.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    QuesService quesService;
    @Autowired
    UserService userService;

    @GetMapping ("/publish")
    public String publish(HttpServletRequest request){
        Cookie [] cookies = request.getCookies();
        if(cookies!=null&&cookies.length!=0)
        {
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
        }
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam("title")String title,
                            @RequestParam("description")String desc,
                            @RequestParam("tags")String tags,
                            HttpServletRequest request,
                            Model model){
        User user=new User();
        Cookie [] cookies = request.getCookies();
        if(cookies!=null&&cookies.length!=0)
        {
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
        }
        if(user==null){
            model.addAttribute("error","未登录用户");
            return "/publish";
        }
        Question ques =new Question();
        ques.setQues_title(title);
        ques.setQues_desc(desc);
        ques.setQues_tags(tags);
        ques.setQues_userid(user.getUser_id());
        ques.setGmt_create(System.currentTimeMillis());
        ques.setGmt_modified(ques.getGmt_create());
        ques.setQues_comment(0);
        ques.setQues_like(0);
        ques.setQues_read(0);
        quesService.Insert(ques);
        return "redirect:/";
    }
}
