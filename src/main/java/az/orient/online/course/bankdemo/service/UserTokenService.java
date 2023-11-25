package az.orient.online.course.bankdemo.service;

import az.orient.online.course.bankdemo.model.account.request.TokenRequest;
import az.orient.online.course.bankdemo.model.customer.response.ResponseEntityClass;
import az.orient.online.course.bankdemo.model.customer.response.TokenResponse;
import az.orient.online.course.bankdemo.model.entity.UserToken;

import java.util.List;

public interface UserTokenService {

    ResponseEntityClass<List<TokenResponse>> getTokenList();

    ResponseEntityClass<UserToken> getTokenById(Long id);

    ResponseEntityClass updateTokenUpdateDate(TokenRequest tokenRequest);
}
