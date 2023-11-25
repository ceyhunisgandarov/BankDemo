package az.orient.online.course.bankdemo.controller;

import az.orient.online.course.bankdemo.model.account.request.TokenRequest;
import az.orient.online.course.bankdemo.model.account.request.UserRequest;
import az.orient.online.course.bankdemo.model.customer.response.ResponseEntityClass;
import az.orient.online.course.bankdemo.model.customer.response.UserResponse;
import az.orient.online.course.bankdemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class LoginController {

    private final UserService userService;

    @PostMapping("/auth")
    public ResponseEntityClass<UserResponse> login (@RequestBody UserRequest userRequest) {
        return userService.auth(userRequest);
    }

    @PostMapping("/logout")
    public ResponseEntityClass<UserResponse> logout (@RequestBody TokenRequest tokenRequest) {
        return userService.logout(tokenRequest);
    }

}
