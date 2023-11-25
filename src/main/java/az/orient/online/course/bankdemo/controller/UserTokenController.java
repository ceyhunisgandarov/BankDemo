package az.orient.online.course.bankdemo.controller;

import az.orient.online.course.bankdemo.model.account.request.TokenRequest;
import az.orient.online.course.bankdemo.model.customer.response.ResponseEntityClass;
import az.orient.online.course.bankdemo.model.entity.UserToken;
import az.orient.online.course.bankdemo.service.UserTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
public class UserTokenController {

    private final UserTokenService userTokenService;

    @PostMapping("/update")
    public ResponseEntityClass updateToken(@RequestBody TokenRequest tokenRequest) {
        return userTokenService.updateTokenUpdateDate(tokenRequest);
    }

}
