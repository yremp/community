package live.yremp.community.dto;

import live.yremp.community.exception.PeculiarException;
import live.yremp.community.exception.PeculiarExceptionCodeAndMessage;
import lombok.Data;

@Data
public class ResultDTO<T> {
    private int code;
    private String message;
    private T data;
    public static ResultDTO ERROROF(Integer CODE,String MESSAGE)
    {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(CODE);
        resultDTO.setMessage(MESSAGE);
        return resultDTO;
    }
    public static ResultDTO OKOF(Integer CODE,String MESSAGE)
    {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(CODE);
        resultDTO.setMessage(MESSAGE);
        return resultDTO;
    }

    public static ResultDTO ERROROF(PeculiarExceptionCodeAndMessage exceptionIMP)
    {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(exceptionIMP.getCode());
        resultDTO.setMessage(exceptionIMP.getMessage());
        return ERROROF(exceptionIMP.getCode(),exceptionIMP.getMessage());
    }

    public static <T> ResultDTO OKOF(T t)
    {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(100);
        resultDTO.setMessage("请求成功");
        resultDTO.setData(t);
        return  resultDTO;
    }

    public static ResultDTO ERROROF(PeculiarException e) {
        return ERROROF(e.getCode(),e.getMessage());
    }
}
