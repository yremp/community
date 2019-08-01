package live.yremp.community.controller;

import live.yremp.community.dto.CommentCreateDTO;
import live.yremp.community.dto.CommentDTO;
import live.yremp.community.dto.ResultDTO;
import live.yremp.community.entity.Comment;
import live.yremp.community.entity.User;
import live.yremp.community.enums.CommenTypeEnum;
import live.yremp.community.exception.PeculiarExceptionCodeAndMessage;
import live.yremp.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    //         一级评论处理逻辑
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    @ResponseBody
    public Object post(@RequestBody CommentCreateDTO commentDTO,
                       HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
//         从session中获取user判断当前用户是否登录
        if (user == null) {
            return ResultDTO.ERROROF(PeculiarExceptionCodeAndMessage.USER_NOT_LOGIN);
        }
        if(commentDTO.getComm_info().length()==0||commentDTO.getComm_info().isEmpty()){
            return ResultDTO.ERROROF(PeculiarExceptionCodeAndMessage.COMMENT_EMPTY);
        }
//        使用CommenDTO模型接收前端返回的json数据，给comment实例化
        Comment comment = new Comment();
        comment.setComm_parent_id(commentDTO.getComm_parent_id());
        comment.setComm_info(commentDTO.getComm_info());
        comment.setComm_type(commentDTO.getComm_type());
        comment.setComm_count(0);
        comment.setComm_like(0);
        comment.setGmt_create(System.currentTimeMillis());
        comment.setGmt_modified(System.currentTimeMillis());
        comment.setComm_user_id(user.getUser_id());
        //        在评论对应的Service中处理：问题回复/评论回复
        commentService.Insert(comment);
        ResultDTO resultDTO=ResultDTO.OKOF(100,"操作成功");
        return resultDTO;
    }
//    二级评论处理逻辑
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO<List<ResultDTO>> comments(@PathVariable("id")Integer id){
        List<CommentDTO> commentDTOS=  commentService.ListByQuestinId(id, CommenTypeEnum.COMMENT.getType());
        return ResultDTO.OKOF(commentDTOS);
    }
}
