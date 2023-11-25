package az.orient.online.course.bankdemo.util;

import az.orient.online.course.bankdemo.enums.EnumAvailableStatus;
import az.orient.online.course.bankdemo.enums.EnumTokenAvailableStatus;
import az.orient.online.course.bankdemo.exception.BankException;
import az.orient.online.course.bankdemo.exception.ExceptionConstant;
import az.orient.online.course.bankdemo.model.account.request.TokenRequest;
import az.orient.online.course.bankdemo.model.entity.User;
import az.orient.online.course.bankdemo.model.entity.UserToken;
import az.orient.online.course.bankdemo.repository.UserRepository;
import az.orient.online.course.bankdemo.repository.UserTokenRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class Utility {

    private final UserRepository userRepository;
    private final UserTokenRepository userTokenRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(Utility.class);

    public UserToken checkToken(TokenRequest tokenRequest) {
        LOGGER.info("CheckToken request: " + tokenRequest);
        Long userId = tokenRequest.getUserId();
        String token = tokenRequest.getToken();
        if (userId==null || token==null) {
            LOGGER.warn("CheckToken response: Invalid request data tokenRequest-" + tokenRequest);
            throw new BankException(ExceptionConstant.INVALID_REQUEST_DATA, "Invalid request data");
        }
        User user = userRepository.findUserByIdAndActive(userId, EnumAvailableStatus.ACTIVE.value);
        if (user==null) {
            LOGGER.warn("CheckToken response: User not found by userId" + userId);
            throw new BankException(ExceptionConstant.USER_NOT_FOUND, "User not found");
        }
        UserToken userToken = userTokenRepository.findUserTokenByUserAndTokenAndActive(user, token, EnumAvailableStatus.ACTIVE.value);
        if (userToken==null) {
            LOGGER.warn("CheckToken response: Invalid token by user" + user.getFullName());
            throw new BankException(ExceptionConstant.INVALID_TOKEN, "Invalid token");
        }
        LOGGER.info("CheckToken response: Success");
        return userToken;
    }

    public void setExpiredToken (UserToken userToken) {
        userToken.setActive(EnumTokenAvailableStatus.EXPIRED.value);
        userTokenRepository.save(userToken);
    }
}
