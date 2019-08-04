package live.yremp.community.enums;

public enum NoticeTypeEnum {
    REPLY_QUESTION(1,"回复了你的帖子"),

    REPLY_COMMENT(2,"回复了你的评论")
    ;
    private Integer type;
    private String name;

    NoticeTypeEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public static String NameOf(Integer type) {
        if(type==1){
            return NoticeTypeEnum.REPLY_QUESTION.getName();
        }
        return NoticeTypeEnum.REPLY_COMMENT.getName();
    }

    public String getName() {
        return name;
    }

    public Integer getType() {
        return type;
    }


}
