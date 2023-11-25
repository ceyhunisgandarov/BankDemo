package az.orient.online.course.bankdemo.enums;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public enum EnumAvailableStatus {

    ACTIVE(1), DEACTIVE(0);

    public int value;

}
