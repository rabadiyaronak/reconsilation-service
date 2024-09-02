package com.paymentology.reconsilation_service.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobleExceptionHandler {


    @ExceptionHandler(value = {RequestTranslationException.class, ResponseParsingException.class, FileParsingException.class})
    public ModelAndView handleCustomException(HttpServletRequest r, Exception e) {
        int code = 500;
        if (e instanceof RequestTranslationException) {
            code = ((RequestTranslationException) e).getCode();
        }
        if (e instanceof ResponseParsingException) {
            code = ((ResponseParsingException) e).getCode();
        }
        if (e instanceof FileParsingException) {
            code = ((FileParsingException) e).getCode();
        }

        ModelAndView mv = new ModelAndView();
        mv.setViewName("error");
        mv.addObject("errorMessage", e.getMessage());
        mv.addObject("errorCode", String.valueOf(code));

        return mv;
    }

    @ExceptionHandler(value = {Exception.class})
    public ModelAndView handleDefaultException(HttpServletRequest r, Exception e) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("error");
        mv.addObject("errorMessage", e.getMessage());
        mv.addObject("errorCode","500");

        return mv;
    }
}
