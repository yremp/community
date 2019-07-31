package live.yremp.community.advice;

import com.alibaba.fastjson.JSON;
import live.yremp.community.dto.ResultDTO;
import live.yremp.community.exception.PeculiarException;
import live.yremp.community.exception.PeculiarExceptionCodeAndMessage;
import live.yremp.community.service.QuesDtoService;
import live.yremp.community.service.QuesService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//异常处理
@ControllerAdvice
public class PeculiarExceptionHandler {

    @ExceptionHandler(Exception.class)
//    设置返回ModelAndView-(html)
    ModelAndView handler(HttpServletRequest request, Model model, Throwable e,HttpServletResponse response) {
       String contentType=request.getContentType();
        //           判断是否为json广化寺
        if("application/json".equals(contentType)){
//            异常
            System.out.println("---------------->Json请求");
            ResultDTO resultDTO;
//            PeculiarException 异常
            if(e instanceof PeculiarException) {
                resultDTO= ResultDTO.ERROROF((PeculiarException)e);
            }
            else {
//                其他异常
                resultDTO= ResultDTO.ERROROF(PeculiarExceptionCodeAndMessage.SYSTEM_ERROR);
            }
            try{
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("UTF-8");
                PrintWriter writer =response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            }catch (IOException ignored){

            }
            return null;
       }else {
//           GET请求错误页面跳转
            if(e instanceof PeculiarException){

            }
            else {
                model.addAttribute("message",PeculiarExceptionCodeAndMessage.SYSTEM_ERROR.getMessage()) ;
            }
        }model.addAttribute("message",e.getMessage()) ;
        return new ModelAndView("error");


    }




}
