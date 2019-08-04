package live.yremp.community.service;

import live.yremp.community.dto.CommentDTO;
import live.yremp.community.entity.Comment;
import live.yremp.community.entity.Notice;
import live.yremp.community.entity.Question;
import live.yremp.community.entity.User;
import live.yremp.community.enums.CommenTypeEnum;
import live.yremp.community.enums.NoticeStatusEnum;
import live.yremp.community.enums.NoticeTypeEnum;
import live.yremp.community.exception.PeculiarException;
import live.yremp.community.exception.PeculiarExceptionCodeAndMessage;
import live.yremp.community.mapper.CommentMapper;
import live.yremp.community.mapper.NoticeMapper;
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
    @Autowired
    private NoticeMapper noticeMapper;

    @Transactional
    public void Insert(Comment comment,User user) {
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
//            新建一个评论
            commentMapper.Insert(comment);
//            找到一级评论 comm_cout++
            Comment commentparent = commentMapper.SelectById(comment.getComm_parent_id());
            commentparent.setComm_count(commentparent.getComm_count() + 1);
            commentMapper.UpDateCount(commentparent);
            Notice notice = new Notice();
            notice.setGmt_create(System.currentTimeMillis());
            notice.setType(NoticeTypeEnum.REPLY_COMMENT.getType());
            notice.setNotice_qcparentid(commentparent.getComm_parent_id());
            notice.setNotice_senderid(comment.getComm_user_id());
            notice.setStatus(NoticeStatusEnum.UNREAD.getStatus());
            notice.setNotice_receiverid(commentparent.getComm_user_id());
            notice.setNotice_sendername(userService.findById(comment.getComm_user_id()).getUser_name());
            noticeMapper.Insert(notice);

        } else {
//            回复问题操作
            Question question = quesService.findById(comment.getComm_parent_id());
//            问题不存在
            if (question == null) {
                throw new PeculiarException(PeculiarExceptionCodeAndMessage.QUESTION_NOT_FOUND);
            } else {
                commentMapper.Insert(comment);
//              问题回复数 ques_comment++
                quesDtoService.AddCommentCount(question.getQues_id());
                Notice notice = new Notice();
                notice.setGmt_create(System.currentTimeMillis());
                notice.setType(NoticeTypeEnum.REPLY_QUESTION.getType());
                notice.setNotice_qcparentid(question.getQues_id());
                notice.setNotice_senderid(comment.getComm_user_id());
                notice.setStatus(NoticeStatusEnum.UNREAD.getStatus());
                notice.setNotice_receiverid(question.getQues_userid());
                notice.setNotice_sendername(userService.findById(comment.getComm_user_id()).getUser_name());
                noticeMapper.Insert(notice);
            }
        }

    }
// 返回二级评论
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

    public Comment SelectById(Integer id){return  commentMapper.SelectById(id);}
}
