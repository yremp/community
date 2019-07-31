package live.yremp.community.dto;

import live.yremp.community.entity.User;
import lombok.Data;

@Data
public class QuesDTO {
    private Integer ques_id;
    private String ques_title;
    private String ques_desc;
    private Integer ques_userid;
    private Integer ques_comment;
    private Integer ques_read;
    private Integer ques_like;
    private Long gmt_create;
    private Long gmt_modified;
    private String ques_tags;
    private User user;
}
