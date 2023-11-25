package az.orient.online.course.bankdemo.service.impl;

import az.orient.online.course.bankdemo.enums.EnumAvailableStatus;
import az.orient.online.course.bankdemo.enums.EnumTokenAvailableStatus;
import az.orient.online.course.bankdemo.exception.BankException;
import az.orient.online.course.bankdemo.exception.ExceptionConstant;
import az.orient.online.course.bankdemo.model.account.request.TokenRequest;
import az.orient.online.course.bankdemo.model.account.response.AccountResponse;
import az.orient.online.course.bankdemo.model.customer.response.*;
import az.orient.online.course.bankdemo.model.entity.Account;
import az.orient.online.course.bankdemo.model.entity.Customer;
import az.orient.online.course.bankdemo.model.entity.User;
import az.orient.online.course.bankdemo.model.entity.UserToken;
import az.orient.online.course.bankdemo.repository.UserTokenRepository;
import az.orient.online.course.bankdemo.service.UserTokenService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserTokenServiceImpl implements UserTokenService {

    private final UserTokenRepository userTokenRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserTokenServiceImpl.class);


    @Override
    public ResponseEntityClass<List<TokenResponse>> getTokenList() {
        ResponseEntityClass<List<TokenResponse>> response = new ResponseEntityClass<>();
        try {
            LOGGER.info("GetTokenList request: List");
            List<UserToken> tokenList = userTokenRepository.findAllByActive(EnumTokenAvailableStatus.ACTIVE.value);
            if (tokenList.isEmpty()) {
                LOGGER.warn("GetTokenList response: TokenList is empty");
                throw new BankException(ExceptionConstant.INVALID_TOKEN, "Token not found");
            }
            List<TokenResponse> tokenResponses = tokenList.stream().map(this::mapping).collect(Collectors.toList());
            LOGGER.info("GetTokenList response: Success");
            response.setT(tokenResponses);
            response.setStatus(ResponseStatus.getSuccessMessage());
        } catch (BankException ex) {
            LOGGER.error("GetTokenList error: " + ex.getMessage());
            response.setStatus(new ResponseStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            LOGGER.error("GetTokenList internal error: " + ex.getMessage());
            response.setStatus(new ResponseStatus(ExceptionConstant.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }

    @Override
    public ResponseEntityClass<UserToken> getTokenById(Long id) {
        ResponseEntityClass<UserToken> response = new ResponseEntityClass<>();
        try{
            LOGGER.info("GetTokenById request: " + id);
            if (id == null) {
                LOGGER.warn("GetTokenById response: Invalid request data tokenId-" + id);
                throw new BankException(ExceptionConstant.INVALID_REQUEST_DATA, "Invalid request data");
            }
            UserToken userToken = userTokenRepository.findUserTokenByIdAndActive(id, EnumTokenAvailableStatus.ACTIVE.value);
            LOGGER.info("GetTokenById response: Success");
            response.setT(userToken);
            response.setStatus(ResponseStatus.getSuccessMessage());
        } catch (BankException ex) {
            LOGGER.error("GetTokenList error: " + ex.getMessage());
            response.setStatus(new ResponseStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            LOGGER.error("GetTokenList internal error: " + ex.getMessage());
            response.setStatus(new ResponseStatus(ExceptionConstant.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }

    @Override
    public ResponseEntityClass updateTokenUpdateDate(TokenRequest tokenRequest) {
        ResponseEntityClass response = new ResponseEntityClass();
        try{
            LOGGER.info("updateTokenUpdateDate request: " + tokenRequest);
            String token = tokenRequest.getToken();
            if (token == null) {
                LOGGER.warn("updateTokenUpdateDate response: Invalid request data token-" + token);
                throw new BankException(ExceptionConstant.INVALID_REQUEST_DATA, "Invalid request data");
            }
            UserToken userToken = userTokenRepository.findUserTokenByTokenAndActive(token, EnumTokenAvailableStatus.ACTIVE.value);
            if (userToken == null) {
                LOGGER.warn("updateTokenUpdateDate response: Token not found by id-" + token);
                throw new BankException(ExceptionConstant.INVALID_TOKEN, "Invalid token");
            }

            ZoneId defaultZoneId = ZoneId.systemDefault();
            LocalDate localDate = LocalDate.now();
            //local date + atStartOfDay() + default time zone + toInstant() = Date
            Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
            userToken.setUpdatedAt(date);
            userTokenRepository.save(userToken);
            LOGGER.info("updateTokenUpdateDate response: Success");
            response.setT(userToken);
            response.setStatus(ResponseStatus.getSuccessMessage());
        } catch (BankException ex) {
            LOGGER.error("updateTokenUpdateDate error: " + ex.getMessage());
            response.setStatus(new ResponseStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            LOGGER.error("updateTokenUpdateDate internal error: " + ex.getMessage());
            response.setStatus(new ResponseStatus(ExceptionConstant.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }

    private TokenResponse mapping(UserToken userToken) {

        TokenResponse tokenResponse = TokenResponse.builder()
                .id(userToken.getId())
                .userId(userToken.getUser().getId())
                .token(userToken.getToken())
                .updatedAt(userToken.getUpdatedAt())
                .build();

        return tokenResponse;
    }
}
