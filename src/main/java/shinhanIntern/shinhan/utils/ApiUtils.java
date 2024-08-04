package shinhanIntern.shinhan.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ApiUtils<T> {
    public static <T> ApiResult<T> success(T data) {
        return new ApiResult<>(true, data, null);
    }

    public static <M> ApiResult<M> error(M errorMessages, HttpStatus status) {
        return new ApiResult<>(false, null, new ApiError(errorMessages, status));
    }
    public static <M>ApiResult<M> error(String errorMessage, HttpStatus status) {
        return (ApiResult<M>) new ApiResult<>(false, errorMessage, new ApiError(errorMessage, status));
    }

    @Getter
    @AllArgsConstructor
    public static class ApiResult<T> {
        boolean success;
        T response;
        ApiError error;
    }

    @Getter
    static class ApiError<M> {
        //String message;
        M errorMessage;
        HttpStatus httpStatus;

        ApiError(M errorMessage, HttpStatus httpStatus) {
            this.errorMessage = errorMessage;
            this.httpStatus = httpStatus;
        }
    }
}
