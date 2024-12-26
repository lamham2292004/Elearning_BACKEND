package org.example.elearning.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Lỗi chưa được phân loại !!!", HttpStatus.INTERNAL_SERVER_ERROR),
    EMAIL_EXITED(1001, "Email đã tồn tại",HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1002,"username phải đủ 3 kí tự trở lên !", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1003,"Password phải đủ 8 kí tự trở lên !", HttpStatus.BAD_REQUEST),
    KEY_INVALID(1004,"Nhập sai key trong exception", HttpStatus.BAD_REQUEST),
    EMAIL_NOT_EXITED(1005, "Email ko tồn tại", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Lỗi không thể đăng nhập !!!",HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "bạn không có quyền !!!",HttpStatus.FORBIDDEN),
    ;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private int code;
    private String message;
    private HttpStatusCode statusCode;

}
