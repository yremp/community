package live.yremp.community.entity;

import lombok.Data;

@Data
public class Question {
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

}
