package live.yremp.community.controller;

import com.alibaba.fastjson.JSONObject;
import javafx.geometry.Pos;
import live.yremp.community.dto.CommentDTO;
import live.yremp.community.dto.QuesDTO;
import live.yremp.community.dto.ResultDTO;
import live.yremp.community.entity.User;
import live.yremp.community.enums.CommenTypeEnum;
import live.yremp.community.exception.PeculiarExceptionCodeAndMessage;
import live.yremp.community.provider.UserProvider;
import live.yremp.community.service.CommentService;
import live.yremp.community.service.QuesDtoService;
import live.yremp.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    UserProvider userProvider;
    @GetMapping("/question/{ques_id}")

    public String question(@PathVariable("ques_id")Integer ques_id,
                           Model model,
                           HttpServletRequest request) {
        userProvider.login(request);
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

    @RequestMapping(value = "/profile/question", method = RequestMethod.POST)
    @ResponseBody
    public Object delete(@RequestBody JSONObject data,
                         HttpServletRequest request){
        User user =(User)request.getSession().getAttribute("user");
        if(user==null){
            return ResultDTO.ERROROF(PeculiarExceptionCodeAndMessage.USER_NOT_LOGIN);
        }
        quesDtoService.delete(data.getInteger("ques_id"));
        ResultDTO resultDTO=ResultDTO.OKOF(100,"操作成功");
        return resultDTO;
    }
}
