package az.orient.online.course.bankdemo.service;

import az.orient.online.course.bankdemo.model.account.request.TokenRequest;
import az.orient.online.course.bankdemo.model.customer.request.CustomerRequest;
import az.orient.online.course.bankdemo.model.customer.response.CustomerResponse;
import az.orient.online.course.bankdemo.model.customer.response.ResponseEntityClass;

import java.util.List;

public interface CustomerService {

    ResponseEntityClass<List<CustomerResponse>> getCustomerList(TokenRequest tokenRequest);

    ResponseEntityClass<CustomerResponse> getOnceById(Long customerId);

    ResponseEntityClass saveCustomer(CustomerRequest customerRequest);

    ResponseEntityClass updateCustomer(CustomerRequest customerRequest);

    ResponseEntityClass deleteCustomerById(Long id);
}
