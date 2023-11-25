package az.orient.online.course.bankdemo.model.customer.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseStatus {

    private Integer code;
    private String message;

    private static final int SUCCESS_CODE = 1;
    private static final String SUCCESS_MESSAGE = "Success operate";

    public static ResponseStatus getSuccessMessage() {
        return new ResponseStatus(SUCCESS_CODE, SUCCESS_MESSAGE);
    }

}
