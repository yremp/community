package live.yremp.community.enums;

public enum NoticeStatusEnum {
    UNREAD(1),
    READ(2),
    ;
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    NoticeStatusEnum(Integer status) {
        this.status = status;
    }

}
