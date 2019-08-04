package live.yremp.community.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import live.yremp.community.entity.Question;
import live.yremp.community.entity.User;
import live.yremp.community.exception.PeculiarException;
import live.yremp.community.exception.PeculiarExceptionCodeAndMessage;
import live.yremp.community.provider.UserProvider;
import live.yremp.community.service.NoticeService;
import live.yremp.community.service.QuesDtoService;
import live.yremp.community.service.QuesService;
import live.yremp.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    QuesService quesService;
    @Autowired
    UserService userService;
    @Autowired
    QuesDtoService quesDtoService;
    @Autowired
    UserProvider userProvider;
    @Autowired
    NoticeService noticeService;
//          返回发布对应的html页面
    @GetMapping ("/publish")
    public String publish(HttpServletRequest request, Model model){
        User user =null;
        user=userProvider.login(request,user);
        if(user!=null){
//            登录返回页面右上角notice 数量 不登录默认 为0
            Integer count = noticeService.listunread(user);
            model.addAttribute("noticescount", count);
        }else {
            model.addAttribute("noticescount", 0);
        }
        return "publish";
    }

//    执行发布功能
    @PostMapping("/publish")
    public String doPublish(@RequestParam(value = "title",required = false)String ques_title,
                            @RequestParam(value = "description",required = false)String ques_desc,
                            @RequestParam(value = "tags",required = false)String ques_tags,
                            @RequestParam(value = "ques_id",required = false) Integer ques_id,
                            HttpServletRequest request,
                            Model model){
        User user=null;
        if(ques_title==null){
            model.addAttribute("error","请输入问题摘要");
            return "/publish";
        }
        if(ques_desc==null){
            model.addAttribute("error","请输入问题描述");
            return "/publish";
        }
        if(ques_tags==null){
            model.addAttribute("error","请输入问题标签");
            return "/publish";
        }
        user=userProvider.login(request,user);
        if(user == null){
            model.addAttribute("error","未登录用户");
            model.addAttribute("noticescount", 0);
            return "/publish";
        }
        Question ques =new Question();
        ques.setQues_title(ques_title);
        ques.setQues_desc(ques_desc);
        ques.setQues_tags(ques_tags);
        ques.setQues_userid(user.getUser_id());
        ques.setQues_id(ques_id);
        quesDtoService.CreateOrUpdate(ques);
        return "redirect:/";
    }

//    编辑问题
    @RequestMapping("/publish/{ques_id}")
    public String edit(@PathVariable("ques_id")Integer ques_id,
                       Model model,
                       HttpServletRequest request){
        Question question =quesService.findById(ques_id);
        model.addAttribute("ques_tetle",question.getQues_title());
        model.addAttribute("ques_desc",question.getQues_desc());
        model.addAttribute("ques_tags",question.getQues_tags());
        model.addAttribute("ques_id",ques_id);
        User user=null;
        user =userProvider.login(request,user);
        Question db_ques=quesService.findById(ques_id);
//        判断当前登录用户是否为问题的 user ，不是则抛出异常提示
        if(db_ques.getQues_userid()!=user.getUser_id()){
            throw new PeculiarException(PeculiarExceptionCodeAndMessage.QUESTION_NOT_BELANG_TO_YOU);
        }
        Integer count = noticeService.listunread(user);
        model.addAttribute("noticescount", count);
        return "publish";
    }

}
