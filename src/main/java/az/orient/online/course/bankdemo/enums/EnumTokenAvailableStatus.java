package az.orient.online.course.bankdemo.enums;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public enum EnumTokenAvailableStatus {

    ACTIVE(1), DEACTIVE(0), EXPIRED(2);

    public int value;

}
