package xyz.huanju.accounting.controller.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import xyz.huanju.accounting.domain.response.CommonResult;
import xyz.huanju.accounting.domain.response.ResultCode;
import xyz.huanju.accounting.exception.AccountingException;

import java.util.List;

/**
 * @author HuanJu
 * @date 2020/8/9 23:25
 */
@RestControllerAdvice
@Slf4j
public class ExceptionHandleController {

    private static final String UN_SUPPORT_METHOD = "该接口不支持当前请求方法";

    private static final String REQUEST_PARAM_ERROR = "请求参数缺失或错误";

    private static final String REQUEST_PARAM_ILLEGAL = "请求参数不合法";

    private static final String REQUEST_PARAM_FORMAT_ERROR = "请求参数格式错误";

    private static final String SERVER_ERROR = "服务器错误";

    /**
     * 处理自定义业务异常
     *
     * @param e
     */
    @ExceptionHandler(AccountingException.class)
    public CommonResult accountingException(AccountingException e) {
        log.info(e.getMessage());
        return new CommonResult(e.getErrorCode(), e.getMessage(), null);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public CommonResult methodNotSupport(HttpRequestMethodNotSupportedException e) {
        log.info(e.getMessage());
        StringBuilder sb = new StringBuilder();

        return new CommonResult(ResultCode.METHOD_NOT_ALLOWED, UN_SUPPORT_METHOD, null);
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public CommonResult messageNotReadable(HttpMessageNotReadableException e) {
        log.info(e.getMessage());

        return new CommonResult(ResultCode.BAD_REQUEST, REQUEST_PARAM_ERROR, null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResult argumentNotValid(MethodArgumentNotValidException e) {
        log.info(e.getMessage());
        BindingResult bindingResult = e.getBindingResult();
        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            List<ObjectError> errors = bindingResult.getAllErrors();
            for (int i = 0; i < errors.size(); i++) {
                sb.append(errors.get(i).getDefaultMessage());
                if (i != errors.size() - 1) {
                    sb.append(" ");
                }
            }
            return new CommonResult(ResultCode.BAD_REQUEST, sb.toString(), null);
        }

        return new CommonResult(ResultCode.BAD_REQUEST, REQUEST_PARAM_FORMAT_ERROR, null);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public CommonResult argumentTypeMismatch(MethodArgumentTypeMismatchException e) {
        log.info(e.getMessage());
        return new CommonResult(ResultCode.BAD_REQUEST, REQUEST_PARAM_ILLEGAL, null);
    }

    @ExceptionHandler(RuntimeException.class)
    public CommonResult runtime(RuntimeException e) {
        log.error(e.getMessage());
        e.printStackTrace();
        return new CommonResult(ResultCode.INTERNAL_SERVER_ERROR, SERVER_ERROR, null);
    }
}
