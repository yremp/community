package live.yremp.community.exception;

public enum PeculiarExceptionCodeAndMessage implements Exception {

    QUESTION_NOT_FOUND(107,"你查看的帖子找不到了，换一个试试？"),
    USER_NOT_LOGIN(101,"当前操作需要先登录"),
    SYSTEM_ERROR(102,"错误，系统可能发生故障"),
    PARAM_NOT_FOUND(103,"未选择任何评论或者回复"),
    PARAM_ERROR(104,"错误，评论类型错误"),
    COMMENT_NOT_FOUND(105,"错误，评论不存在"),
    COMMENT_EMPTY(108,"提示，评论不能为空"),
    FILE_UPLOAD_ERROR(109,"文件上传败"),
    DELETE_QUESTION_NOT_FOUND(110,"删除的帖子不存在"),
    COMMENT_NOT_FOUNDSON(111,"当前评论没有二级评论"),
    NOTICE_ERROR(112,"错误的请求"),
    QUESTION_NOT_BELANG_TO_YOU(113,"这不是你的帖子，不能编辑哦！"),
    USER_PAGE_NOT_BELANG_TO_YOU(114,"这不是你的资料页面哦，不能编辑哦！")
    ;


    private String message;
    private Integer code;

    PeculiarExceptionCodeAndMessage(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }


}
