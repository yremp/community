package live.yremp.community.entity;

public class Question {
    private Integer ques_id;
    private String ques_title;
    private String ques_desc;
    private String ques_userid;
    private Integer ques_comment;
    private Integer ques_read;
    private Integer ques_like;
    private Long gmt_create;
    private Long gmt_modified;
    private String ques_tags;

    public Integer getQues_id() {
        return ques_id;
    }

    public void setQues_id(Integer ques_id) {
        this.ques_id = ques_id;
    }

    public String getQues_title() {
        return ques_title;
    }

    public void setQues_title(String ques_title) {
        this.ques_title = ques_title;
    }

    public String getQues_desc() {
        return ques_desc;
    }

    public void setQues_desc(String ques_desc) {
        this.ques_desc = ques_desc;
    }

    public String getQues_userid() {
        return ques_userid;
    }

    public void setQues_userid(String ques_userid) {
        this.ques_userid = ques_userid;
    }

    public Integer getQues_comment() {
        return ques_comment;
    }

    public void setQues_comment(Integer ques_comment) {
        this.ques_comment = ques_comment;
    }

    public Integer getQues_read() {
        return ques_read;
    }

    public void setQues_read(Integer ques_read) {
        this.ques_read = ques_read;
    }

    public Integer getQues_like() {
        return ques_like;
    }

    public void setQues_like(Integer ques_like) {
        this.ques_like = ques_like;
    }

    public Long getGmt_create() {
        return gmt_create;
    }

    public void setGmt_create(Long gmt_create) {
        this.gmt_create = gmt_create;
    }

    public Long getGmt_modified() {
        return gmt_modified;
    }

    public void setGmt_modified(Long gmt_modified) {
        this.gmt_modified = gmt_modified;
    }

    public String getQues_tags() {
        return ques_tags;
    }

    public void setQues_tags(String ques_tags) {
        this.ques_tags = ques_tags;
    }
}
