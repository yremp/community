package live.yremp.community.service;

import live.yremp.community.dto.NoticeDTO;
import live.yremp.community.entity.Comment;
import live.yremp.community.entity.Notice;
import live.yremp.community.entity.Question;
import live.yremp.community.entity.User;
import live.yremp.community.enums.NoticeStatusEnum;
import live.yremp.community.enums.NoticeTypeEnum;
import live.yremp.community.exception.PeculiarException;
import live.yremp.community.exception.PeculiarExceptionCodeAndMessage;
import live.yremp.community.mapper.NoticeMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoticeService {
    @Autowired
    NoticeMapper noticeMapper;
    @Autowired
    UserService userService;
    @Autowired
    QuesService quesService;
    @Autowired
    CommentService commentService;
//返回当前用户的所有的通知
    public List<NoticeDTO> listall(User user) {
        List<Notice> notices = noticeMapper.SelectByRecriverId(user.getUser_id());
        if (notices != null) {
            List<NoticeDTO> list = new ArrayList<>();
            for (Notice notice : notices) {

                NoticeDTO noticeDTO = new NoticeDTO();
                noticeDTO.setSender(userService.findById(notice.getNotice_senderid()));
                noticeDTO.setNotice_receiverid(notice.getNotice_receiverid());
                noticeDTO.setGmt_create(notice.getGmt_create());
                noticeDTO.setStatus(notice.getStatus());
                noticeDTO.setNotic_id(notice.getNotice_id());

                if (notice.getType().equals(NoticeTypeEnum.REPLY_COMMENT.getType())) {
                    noticeDTO.setType(NoticeTypeEnum.REPLY_COMMENT.getName());
                    Question question = quesService.findById(notice.getNotice_qcparentid());
                    noticeDTO.setTitle(question.getQues_title());
                    noticeDTO.setNotice_qcparentid(question.getQues_id());
                } else {
                    noticeDTO.setType(NoticeTypeEnum.REPLY_QUESTION.getName());
                    Question question = quesService.findById(notice.getNotice_qcparentid());
                    noticeDTO.setTitle(question.getQues_title());
                    noticeDTO.setNotice_qcparentid(question.getQues_id());
                }
                list.add(noticeDTO);
            }
            return list;
        }
        return new ArrayList<>();
    }

//    返回当前用户没有阅读的notice数量
    public Integer listunread(User user) {
         List<Notice> list=noticeMapper.SelectUnReadByRecriverId(user.getUser_id());
        if(list==null){
            return 0;
        }
        else {
            return list.size();
        }
    }

//处理notice的阅读状态，使其变为已读
    public NoticeDTO read(User user, Integer notice_id) {
        Notice notice = noticeMapper.SelectById(notice_id);
        if (user.getUser_id() != notice.getNotice_receiverid()) {
            throw new PeculiarException(PeculiarExceptionCodeAndMessage.NOTICE_ERROR);
        }
        notice.setStatus(NoticeStatusEnum.READ.getStatus());
        noticeMapper.UpDateStatus(notice);
        NoticeDTO noticeDTO = new NoticeDTO();
        BeanUtils.copyProperties(notice, noticeDTO);
        noticeDTO.setType(NoticeTypeEnum.NameOf(notice.getType()));
        return noticeDTO;
    }
//根据notice_qcparentid 删除notice在删除问题时 同时删除通知
    public void deleteByParentId(Integer ques_id) {

        noticeMapper.deleteByParentId(ques_id);

    }
//根据notice_id 删除notice 个人中心面板移除通知
    public void delete(Integer notice_id) {
        noticeMapper.deleteById(notice_id);
    }
}
