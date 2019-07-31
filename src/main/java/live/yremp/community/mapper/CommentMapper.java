package live.yremp.community.mapper;

import live.yremp.community.entity.Comment;

import java.util.List;

public interface CommentMapper {
    public void Insert(Comment comment);
    public Comment SelectById(Integer id);
    public void UpDateCount(Comment comment);
    public List<Comment> ListByQuestionId(Integer ques_id,Integer comm_type);
}
