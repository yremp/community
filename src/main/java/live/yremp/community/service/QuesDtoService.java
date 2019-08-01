package live.yremp.community.service;

import live.yremp.community.dto.PageDTO;
import live.yremp.community.dto.QuesDTO;
import live.yremp.community.entity.Comment;
import live.yremp.community.entity.Question;
import live.yremp.community.entity.User;
import live.yremp.community.enums.CommenTypeEnum;
import live.yremp.community.exception.PeculiarException;
import live.yremp.community.exception.PeculiarExceptionCodeAndMessage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuesDtoService {
    @Autowired
    QuesService quesService;
    @Autowired
    UserService userService;
    @Autowired
    CommentService commentService;

    public void delete(Integer ques_id) {
        Question question = quesService.findById(ques_id);
        if (question == null) {
            throw new PeculiarException(PeculiarExceptionCodeAndMessage.DELETE_QUESTION_NOT_FOUND);
        }
//        找到问题的回复
        List<Comment> comments = commentService.findByParentId(ques_id, CommenTypeEnum.QUESTION.getType());
        if (comments != null) {
            for (Comment comment : comments) {
//                找到问题的二级评论
                List<Comment> commentsons = commentService.findByParentId(comment.getComm_id(), CommenTypeEnum.COMMENT.getType());
                if (commentsons != null) {
//                    删除所有二级评论
                    for (Comment comment1 : commentsons) {
                        commentService.deleteById(comment1.getComm_id());
                    }
                } else {
                    throw new PeculiarException(PeculiarExceptionCodeAndMessage.COMMENT_NOT_FOUNDSON);
                }
//                删除所有问题回复
                commentService.deleteById(comment.getComm_id());
            }

        } else {
            throw new PeculiarException(PeculiarExceptionCodeAndMessage.COMMENT_NOT_FOUND);
        }
//        删除问题
        quesService.DeleteById(ques_id);
    }

    public PageDTO list1(Integer page, Integer size) {

        Integer totalPage;
        PageDTO pageDto = new PageDTO();
        Integer totalCount = quesService.count();
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        pageDto.setPagenation(totalPage, page);
        Integer offset = size * (page - 1);
        List<Question> ques = quesService.QueryAll(offset, size);
        List<QuesDTO> quesDTOList = new ArrayList<>();
            for(Question question:ques){
                User user =userService.findById(question.getQues_userid());
                QuesDTO quesDto = new QuesDTO();
                BeanUtils.copyProperties(question,quesDto);
                quesDto.setUser(user);
                quesDTOList.add(quesDto);
            }
        pageDto.setQuestions(quesDTOList);
            return pageDto;
    }

    public PageDTO list2(Integer user_id, Integer page, Integer size) {
        Integer totalPage;
        PageDTO pageDto = new PageDTO();
        Integer totalCount=quesService.countByUserId(user_id);
        if (totalCount == 0) {
            pageDto = null;
            return pageDto;
        }
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        pageDto.setPagenation(totalPage, page);

        Integer offset =  size*(page-1);
        List<Question> ques=quesService.QueryAllByUserId(user_id,offset,size);
        List<QuesDTO> quesDTOList = new ArrayList<>();

        for(Question question:ques){
            User user =userService.findById(question.getQues_userid());
            QuesDTO quesDto = new QuesDTO();
            BeanUtils.copyProperties(question,quesDto);
            quesDto.setUser(user);
            quesDTOList.add(quesDto);
        }
        pageDto.setQuestions(quesDTOList);
        return pageDto;
    }

    public QuesDTO findById(Integer ques_id) {
        Question question = quesService.findById(ques_id);
        if (question == null) {
            throw new PeculiarException(PeculiarExceptionCodeAndMessage.QUESTION_NOT_FOUND);
        }
        User user = userService.findById(question.getQues_userid());
        QuesDTO quesDto = new QuesDTO();
        quesDto.setUser(user);
        BeanUtils.copyProperties(question, quesDto);
        return quesDto;
    }

    public void CreateOrUpdate(Question ques) {
        if (ques.getQues_id() == null) {
//            创建
            ques.setQues_read(0);
            ques.setQues_like(0);
            ques.setQues_comment(0);
            ques.setGmt_create(System.currentTimeMillis());
            ques.setGmt_modified(ques.getGmt_create());
            quesService.Insert(ques);
        } else {
//            更新
            ques.setGmt_modified(ques.getGmt_create());
            quesService.upData(ques);


        }
    }

    public void AddReadCount(Integer ques_id) {
        Question question = quesService.findById(ques_id);
        question.setQues_read(question.getQues_read() + 1);
        quesService.UpDateRead(question);
    }

    public void AddCommentCount(Integer ques_id) {
        Question question = quesService.findById(ques_id);
        question.setQues_comment(question.getQues_comment() + 1);
        quesService.UpDateComment(question);
    }

    public List<QuesDTO> selectRealated(QuesDTO quesDto) {
        if (quesDto.getQues_tags() == null) {
            return new ArrayList<>();
        } else {

            String[] TAGS = quesDto.getQues_tags().split(",");
            String regexTags=Arrays.stream(TAGS).collect(Collectors.joining("|"));
            Question question = new Question();
            question.setQues_id(quesDto.getQues_id());
            question.setQues_tags(regexTags);
            List<Question> questions = quesService.SelectRelatated(question);
            List<QuesDTO> quesDTOS = questions.stream().map(q -> {
                QuesDTO quesDTO=new QuesDTO();
                BeanUtils.copyProperties(q,quesDTO);
                return quesDTO;
            }).collect(Collectors.toList());
            return quesDTOS;
        }
    }
}
