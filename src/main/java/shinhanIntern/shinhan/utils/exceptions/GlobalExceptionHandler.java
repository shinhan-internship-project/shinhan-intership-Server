package shinhanIntern.shinhan.utils.exceptions;


import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shinhanIntern.shinhan.utils.ApiUtils;
import shinhanIntern.shinhan.utils.ApiUtils.ApiResult;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResult handleValidationExceptions(MethodArgumentNotValidException errors) {

        Map<String, String> errorMessages = new HashMap<>();
        for(FieldError error:errors.getFieldErrors()){
            String errorField = error.getField(); // 예외 필드
            String errorMessage = error.getDefaultMessage(); // 예외 메세지
            errorMessages.put(errorField,errorMessage);
        }
        return ApiUtils.error(errorMessages, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResult handleNotFoundProductExceptions(NoSuchElementException error) {
        return ApiUtils.error(error.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResult handleIllegalArgumentException(IllegalArgumentException error) {
        return ApiUtils.error(error.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
