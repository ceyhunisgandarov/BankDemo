package az.orient.online.course.bankdemo.service;

import az.orient.online.course.bankdemo.model.account.request.TokenRequest;
import az.orient.online.course.bankdemo.model.account.request.UserRequest;
import az.orient.online.course.bankdemo.model.customer.response.ResponseEntityClass;
import az.orient.online.course.bankdemo.model.customer.response.UserResponse;

public interface UserService {

    ResponseEntityClass<UserResponse> auth(UserRequest userRequest);

    public ResponseEntityClass logout(TokenRequest tokenRequest);

    ResponseEntityClass<UserRequest> addUser(UserRequest userRequest);
}
