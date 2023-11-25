package az.orient.online.course.bankdemo.controller;


import az.orient.online.course.bankdemo.model.account.request.AccountRequest;
import az.orient.online.course.bankdemo.model.account.response.AccountResponse;
import az.orient.online.course.bankdemo.model.customer.response.ResponseEntityClass;
import az.orient.online.course.bankdemo.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/list/{customerId}")
    public ResponseEntityClass<List<AccountResponse>> listAccountByCustomer(@PathVariable Long customerId) {
        return accountService.getAccountListById(customerId);
    }

    @PostMapping("/save")
    public ResponseEntityClass saveAccount(@RequestBody AccountRequest accountRequest) {
        return accountService.saveAccount(accountRequest);
    }

    @PutMapping("/update")
    public ResponseEntityClass updateAccount(@RequestBody AccountRequest accountRequest) {
        return accountService.updateAccount(accountRequest);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntityClass deleteAccount(@PathVariable Long id){
        return accountService.deleteAccount(id);
    }

}
