package org.example.elearning.exception;

public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Lỗi chưa được phân loại !!!"),
    EMAIL_EXITED(1001, "Email đã tồn tại"),
    USERNAME_INVALID(1002,"username phải đủ 3 kí tự trở lên !"),
    PASSWORD_INVALID(1003,"Password phải đủ 8 kí tự trở lên !"),
    KEY_INVALID(1004,"Nhập sai key trong exception"),
    EMAIL_NOT_EXITED(1005, "Email ko tồn tại"),
    UNCATEGORIZED(1006, "Lỗi chưa được phân loại !!!"),
    ;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
