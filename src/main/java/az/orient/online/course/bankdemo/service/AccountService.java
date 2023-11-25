package az.orient.online.course.bankdemo.service;


import az.orient.online.course.bankdemo.model.account.request.AccountRequest;
import az.orient.online.course.bankdemo.model.account.response.AccountResponse;
import az.orient.online.course.bankdemo.model.customer.response.ResponseEntityClass;

import java.util.List;

public interface AccountService {

    ResponseEntityClass<List<AccountResponse>> getAccountListById(Long id);

    ResponseEntityClass saveAccount(AccountRequest accountRequest);

    ResponseEntityClass updateAccount(AccountRequest accountRequest);

    ResponseEntityClass deleteAccount(Long id);
}
