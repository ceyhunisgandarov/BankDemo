package az.orient.online.course.bankdemo.controller;

import az.orient.online.course.bankdemo.model.account.request.UserRequest;
import az.orient.online.course.bankdemo.model.customer.response.ResponseEntityClass;
import az.orient.online.course.bankdemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {

    private final UserService userService;

    @PostMapping("/addUser")
    public ResponseEntityClass<UserRequest> addUser(@RequestBody UserRequest userRequest){
        return userService.addUser(userRequest);
    }

}
