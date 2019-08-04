package live.yremp.community.mapper;

import live.yremp.community.entity.Question;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface QuesMapper {
    public void Insert(Question ques);

    public List<Question> QueryAll(Integer offset, Integer size);

    public List<Question> QueryAllByUserId(Integer user_id, Integer offset, Integer size);

    public Integer count();

    public Integer countByUserId(Integer user_id);

    public Question findById(Integer ques_id);

    public void upDate(Question ques);

    public void AddReadCount(Integer readCount);

    public void UpDateRead(Question question);

    public void UpDateComment(Question question);

    public List<Question> SelectRelatated (Question question);
    
    public void DeleteById(Integer ques_id);

    public List<Question> QueryBySerchList(String search);

    public List<Question> QueryBySerch(String search, Integer offset, Integer size);
}
