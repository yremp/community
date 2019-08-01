package live.yremp.community.service;

import live.yremp.community.dto.CommentDTO;
import live.yremp.community.entity.Comment;
import live.yremp.community.entity.Question;
import live.yremp.community.entity.User;
import live.yremp.community.enums.CommenTypeEnum;
import live.yremp.community.exception.PeculiarException;
import live.yremp.community.exception.PeculiarExceptionCodeAndMessage;
import live.yremp.community.mapper.CommentMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    @Resource
    private CommentMapper commentMapper;
    @Autowired
    private QuesService quesService;
    @Autowired
    private QuesDtoService quesDtoService;
    @Autowired
    private UserService userService;

    @Transactional
    public void Insert(Comment comment) {
//        评论对应的问题不出存在
        if (comment.getComm_parent_id() == null || comment.getComm_parent_id() == 0) {
            throw new PeculiarException(PeculiarExceptionCodeAndMessage.PARAM_NOT_FOUND);
        }
//        评论的类型为空或者不合法
        if (comment.getComm_type() == null || !CommenTypeEnum.isExist(comment.getComm_type())) {
            throw new PeculiarException(PeculiarExceptionCodeAndMessage.PARAM_ERROR);
        }
//        判断是评论回复还是问题回复
        if (comment.getComm_type() == CommenTypeEnum.COMMENT.getType()) {
            //回复评论操作
            Comment SQLCOMMENT = commentMapper.SelectById(comment.getComm_parent_id());
            if (SQLCOMMENT == null) {
                throw new PeculiarException(PeculiarExceptionCodeAndMessage.COMMENT_NOT_FOUND);
            }
            comment.setComm_count(0);
            commentMapper.Insert(comment);
//                    找到父类一级评论 comm_cout++
            Comment commentparent = commentMapper.SelectById(comment.getComm_parent_id());
            if (commentparent.getComm_count() == null || commentparent.getComm_count() == 0) {
                commentparent.setComm_count(1);
                commentMapper.UpDateCount(commentparent);
            } else {
                commentparent.setComm_count(commentparent.getComm_count() + 1);
                commentMapper.UpDateCount(commentparent);
            }

        } else {
//            回复问题操作
            Question question = quesService.findById(comment.getComm_parent_id());
//            问题不存在
            if (question == null) {
                throw new PeculiarException(PeculiarExceptionCodeAndMessage.QUESTION_NOT_FOUND);
            } else {
                commentMapper.Insert(comment);
                quesDtoService.AddCommentCount(question.getQues_id());
            }
        }

    }


    public List<CommentDTO> ListByQuestinId(Integer ques_id, Integer comm_type) {
        List<Comment> comments = commentMapper.ListByQuestionId(ques_id, comm_type);
        if (comments.size() == 0) {
            return new ArrayList<>();
        }
        List<CommentDTO> commDTOList = new ArrayList<>();
        for (Comment comment : comments) {
            User user = userService.findById(comment.getComm_user_id());
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(user);
            commDTOList.add(commentDTO);
        }
        return commDTOList;
    }

    public List<Comment> findByParentId(Integer comm_parent_id, Integer comm_type) {
        return commentMapper.findByParentId(comm_parent_id, comm_type);
    }

    public void deleteById(Integer comm_id){ commentMapper.deleteById(comm_id);}
}
