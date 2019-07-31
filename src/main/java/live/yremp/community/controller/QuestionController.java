package live.yremp.community.controller;

import live.yremp.community.dto.CommentDTO;
import live.yremp.community.dto.QuesDTO;
import live.yremp.community.entity.User;
import live.yremp.community.enums.CommenTypeEnum;
import live.yremp.community.service.CommentService;
import live.yremp.community.service.QuesDtoService;
import live.yremp.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    QuesDtoService quesDtoService;
    @Autowired
    CommentService commentService;
    @Autowired
    UserService userService;
    @GetMapping("/question/{ques_id}")

    public String question(@PathVariable("ques_id")Integer ques_id,
                           Model model,
                           HttpServletRequest request) {
        Cookie[] cookies;
        cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userService.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }

        QuesDTO quesDto = quesDtoService.findById(ques_id);
        List<QuesDTO> relatedquestions = quesDtoService.selectRealated(quesDto);
        List<CommentDTO> comments =commentService.ListByQuestinId(ques_id, CommenTypeEnum.QUESTION.getType());
        quesDtoService.AddReadCount(ques_id);
//        返回QuestionDTO
        model.addAttribute("question",quesDto);
//        返回CommentDTO 回复列表
        model.addAttribute("comments",comments);
//        返回和标签相关的问题
        model.addAttribute("relatedquestions",relatedquestions);
        return "question" ;

    }
}
