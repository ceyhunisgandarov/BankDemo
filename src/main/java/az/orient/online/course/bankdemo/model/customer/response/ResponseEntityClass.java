package az.orient.online.course.bankdemo.model.customer.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResponseEntityClass <T> {

    @JsonProperty(value = "listResponse")
    private T t;
    private ResponseStatus status;

}
