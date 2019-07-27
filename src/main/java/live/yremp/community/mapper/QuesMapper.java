package live.yremp.community.mapper;

import live.yremp.community.entity.Question;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface QuesMapper {
    public void Insert(Question ques);
    public List<Question> QueryAll(Integer offset,Integer size);
    public Integer count();
}
