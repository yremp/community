package live.yremp.community.controller;

import live.yremp.community.dto.NoticeDTO;
import live.yremp.community.dto.PageDTO;
import live.yremp.community.entity.User;
import live.yremp.community.provider.UserProvider;
import live.yremp.community.service.NoticeService;
import live.yremp.community.service.QuesDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ProfileController {
    @Autowired
    NoticeService noticeService;
    @Autowired
    private QuesDtoService quesDtoService;
    @Autowired
    UserProvider userProvider;

    //  根据用户点击的action决定展示页面
    @GetMapping("/profile/{action}")
    public String profile(@PathVariable("action") String action,
                          @RequestParam(value = "page",defaultValue = "1")Integer page,
                          @RequestParam(value = "size",defaultValue = "5")Integer size,
                          Model model,
                          HttpServletRequest request){
//  没有登陆则跳转至首页
        User user=null;
        user=userProvider.login(request,user);
        if(user==null){
            return "redirect:/";
        }
//  跳转页面
        if(action.equals("question")){
            model.addAttribute("action","question");
            model.addAttribute("actioname","我的提问");
        } else if(action.equals("reply")){
            model.addAttribute("action","reply");
            model.addAttribute("actioname","最新回复");
        }
//加载通知
        PageDTO pagenation=quesDtoService.list2(user.getUser_id(),page,size);
        List<NoticeDTO> notices = noticeService.listall(user);
        if (user != null) {
            Integer count = noticeService.listunread(user);
            model.addAttribute("noticescount", count);
        } else {
            model.addAttribute("noticescount", 0);
        }
        model.addAttribute("notices", notices);
        model.addAttribute("pagenation",pagenation);
        return "profile";
    }

//用户资料
    @GetMapping("/user/{action}")
    public String profile(@PathVariable("action") String action,
                          Model model,
                          HttpServletRequest request){
        User user=null;
        user=userProvider.login(request,user);
        if(user==null){
            return "redirect:/";
        }
        if(action.equals("info")){
            model.addAttribute("action","info");
            model.addAttribute("actioname","我的信息");
        }
        else if(action.equals("edit")){
            model.addAttribute("action","edit");
            model.addAttribute("actioname","编辑资料");
        }
        return "user";
    }
}
