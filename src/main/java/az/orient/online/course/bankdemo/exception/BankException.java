package az.orient.online.course.bankdemo.exception;

public class BankException extends RuntimeException{

    private Integer code;

    public BankException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public BankException(String message) {
        super(message);
    }

    public Integer getCode() {
        return code;
    }
}
