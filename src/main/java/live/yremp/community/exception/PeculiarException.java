package live.yremp.community.exception;

public class PeculiarException extends RuntimeException {

    private String message;
    private Integer code;

    public PeculiarException(PeculiarExceptionCodeAndMessage e) {
        this.message = e.getMessage();
        this.code = e.getCode();
    }

    @Override
    public String getMessage() {
        return message;
    }


    public Integer getCode() {
        return code;
    }

}
