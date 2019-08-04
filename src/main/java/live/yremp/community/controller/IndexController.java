package live.yremp.community.controller;

import live.yremp.community.dto.PageDTO;
import live.yremp.community.entity.User;
import live.yremp.community.provider.UserProvider;
import live.yremp.community.service.NoticeService;
import live.yremp.community.service.QuesDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private QuesDtoService quesDtoService;
    @Autowired
    UserProvider userProvider;
    @RequestMapping("/")
    public String IndexController(HttpServletRequest request,
                                  Model model,
                                  @RequestParam(value = "page",defaultValue = "1")Integer page,
                                  @RequestParam(value = "size", defaultValue = "5") Integer size,
                                  @RequestParam(value = "search", required = false) String search
    ) {
//判断用户token
        User user = null;
        user = userProvider.login(request, user);
//拉取问题列表和分页
        PageDTO pagenation;
//判断是否有search输入，没有正常加载问题列表
        if (search != null) {
            pagenation = quesDtoService.list1(search, page, size);
            model.addAttribute("search", search);
        } else {
            pagenation = quesDtoService.list1(page, size);
        }
//判断登录状态，决定通知的数量，没有登录默认0
        if (user != null) {
            Integer count = noticeService.listunread(user);
            model.addAttribute("noticescount", count);
        } else {
            model.addAttribute("noticescount", 0);
        }
//返回问题列表
            model.addAttribute("pagenation",pagenation);
            return "index";
    }
}
