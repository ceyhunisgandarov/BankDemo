package az.orient.online.course.bankdemo.service.impl;

import az.orient.online.course.bankdemo.enums.EnumAvailableStatus;
import az.orient.online.course.bankdemo.enums.EnumTokenAvailableStatus;
import az.orient.online.course.bankdemo.exception.BankException;
import az.orient.online.course.bankdemo.exception.ExceptionConstant;
import az.orient.online.course.bankdemo.model.account.request.TokenRequest;
import az.orient.online.course.bankdemo.model.account.request.UserRequest;
import az.orient.online.course.bankdemo.model.customer.response.ResponseEntityClass;
import az.orient.online.course.bankdemo.model.customer.response.ResponseStatus;
import az.orient.online.course.bankdemo.model.customer.response.TokenResponse;
import az.orient.online.course.bankdemo.model.customer.response.UserResponse;
import az.orient.online.course.bankdemo.model.entity.User;
import az.orient.online.course.bankdemo.model.entity.UserToken;
import az.orient.online.course.bankdemo.repository.UserRepository;
import az.orient.online.course.bankdemo.repository.UserTokenRepository;
import az.orient.online.course.bankdemo.service.UserService;
import az.orient.online.course.bankdemo.util.Utility;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserTokenRepository userTokenRepository;
    private final Utility utility;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public ResponseEntityClass<UserResponse> auth(UserRequest userRequest) {
        ResponseEntityClass<UserResponse> response = new ResponseEntityClass<>();
        UserResponse userResponse = new UserResponse();
        try {
            LOGGER.info("Auth request: " + userRequest);
            String username = userRequest.getUsername();
            String password = userRequest.getPassword();
            if (username==null || password==null) {
                LOGGER.warn("Auth response: Invalid request data-" + userRequest.getUsername());
                throw new BankException(ExceptionConstant.INVALID_REQUEST_DATA, "Invalid request data");
            }
            User user = userRepository.findUserByUsernameAndPasswordAndActive(username, password, EnumAvailableStatus.ACTIVE.value);
            if (user==null) {
                LOGGER.warn("Auth response: User not found-" + userRequest.getUsername() + " or don't match password");
                throw new BankException(ExceptionConstant.USER_NOT_FOUND, "User not found");
            }
            String token = UUID.randomUUID().toString();
            UserToken userToken = new UserToken();
            userToken.setUser(user);
            userToken.setToken(token);
            userTokenRepository.save(userToken);
            userResponse.setUsername(username) ;
            userResponse.setFullName(user.getFullName());
            TokenResponse tokenResponse = new TokenResponse();
            tokenResponse.setUserId(user.getId());
            tokenResponse.setToken(token);
            userResponse.setTokenResponse(tokenResponse);
            LOGGER.info("Auth response: Success");
            response.setT(userResponse);
            response.setStatus (ResponseStatus.getSuccessMessage ());
        } catch (BankException ex) {
            LOGGER.error("Auth error: " + ex.getMessage());
            response.setStatus(new ResponseStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            LOGGER.error("Auth internal error: " + ex.getMessage());
            response.setStatus(new ResponseStatus(ExceptionConstant.INTERNAL_EXCEPTION, "Internal exception"));
        }

        return response;
    }

    @Override
    public ResponseEntityClass logout(TokenRequest tokenRequest) {
        ResponseEntityClass response = new ResponseEntityClass();
        try {
            LOGGER.info("Logout request: " + tokenRequest);
            UserToken userToken = utility.checkToken(tokenRequest);
            userToken.setActive(EnumTokenAvailableStatus.DEACTIVE.value);
            userTokenRepository.save(userToken);
            LOGGER.info("Logout response: Success");
            response.setStatus(ResponseStatus.getSuccessMessage());
        } catch (BankException ex) {
            LOGGER.error("Auth error: " + ex.getMessage());
            response.setStatus(new ResponseStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            LOGGER.error("Auth internal error: " + ex.getMessage());
            response.setStatus(new ResponseStatus(ExceptionConstant.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }

    @Override
    public ResponseEntityClass<UserRequest> addUser(UserRequest userRequest) {
        ResponseEntityClass<UserRequest> response = new ResponseEntityClass<>();
        try {
            LOGGER.info("AddUser request: " + userRequest);
            String username = userRequest.getUsername();
            String password = userRequest.getPassword();
            if (username==null || password == null) {
                LOGGER.warn("AddUser response: Invalid request data by userRequest-" + userRequest);
                throw new BankException(ExceptionConstant.INVALID_REQUEST_DATA, "Invalid request data");
            }
            User user = User.builder()
                    .username(userRequest.getUsername())
                    .password(userRequest.getPassword())
                    .build();
            user = userRepository.save(user);
            LOGGER.info("AddUser response: Success");
            response.setStatus(ResponseStatus.getSuccessMessage());
        } catch (BankException ex) {
            LOGGER.error("AddUser error: " + ex.getMessage());
            response.setStatus(new ResponseStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            LOGGER.error("AddUser internal error: " + ex.getMessage());
            response.setStatus(new ResponseStatus(ExceptionConstant.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }
}
