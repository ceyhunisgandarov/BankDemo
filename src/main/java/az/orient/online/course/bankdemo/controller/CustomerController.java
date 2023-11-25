package az.orient.online.course.bankdemo.controller;

import az.orient.online.course.bankdemo.model.account.request.TokenRequest;
import az.orient.online.course.bankdemo.model.customer.request.CustomerRequest;
import az.orient.online.course.bankdemo.model.customer.response.CustomerResponse;
import az.orient.online.course.bankdemo.model.customer.response.ResponseEntityClass;
import az.orient.online.course.bankdemo.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/testApi")
    public String testApi(){
        return "This is test";
    }

    @PostMapping("/list")
    public ResponseEntityClass<List<CustomerResponse>> getCustomers(@RequestBody TokenRequest tokenRequest) {
        return customerService.getCustomerList(tokenRequest);
    }

    @GetMapping("/getCustomerById")
    public ResponseEntityClass<CustomerResponse> getOnceCustomer(@RequestParam("id") Long customerId) {
        return customerService.getOnceById(customerId);
    }

    @PostMapping("/save")
    public ResponseEntityClass saveCustomer(@RequestBody CustomerRequest customerRequest) {
        return customerService.saveCustomer(customerRequest);
    }

    @PutMapping("/update")
    public ResponseEntityClass updateCustomer(@RequestBody CustomerRequest customerRequest) {
        return customerService.updateCustomer(customerRequest);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntityClass deleteCustomer (@PathVariable("id") Long id) {
        return customerService.deleteCustomerById(id);
    }

}
