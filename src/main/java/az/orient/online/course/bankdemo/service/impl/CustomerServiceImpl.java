package az.orient.online.course.bankdemo.service.impl;

import az.orient.online.course.bankdemo.enums.EnumAvailableStatus;
import az.orient.online.course.bankdemo.exception.BankException;
import az.orient.online.course.bankdemo.exception.ExceptionConstant;
import az.orient.online.course.bankdemo.model.account.request.TokenRequest;
import az.orient.online.course.bankdemo.model.account.response.AccountResponse;
import az.orient.online.course.bankdemo.model.entity.Account;
import az.orient.online.course.bankdemo.model.entity.Customer;
import az.orient.online.course.bankdemo.model.customer.request.CustomerRequest;
import az.orient.online.course.bankdemo.model.customer.response.CustomerResponse;
import az.orient.online.course.bankdemo.model.customer.response.ResponseEntityClass;
import az.orient.online.course.bankdemo.model.customer.response.ResponseStatus;
import az.orient.online.course.bankdemo.repository.AccountRepository;
import az.orient.online.course.bankdemo.repository.CustomerRepository;
import az.orient.online.course.bankdemo.service.CustomerService;
import az.orient.online.course.bankdemo.util.Utility;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository cRepo;
    private final AccountRepository aRepo;
    private final Utility utility;
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);


    @Override
    public ResponseEntityClass<List<CustomerResponse>> getCustomerList(TokenRequest tokenRequest) {
        ResponseEntityClass<List<CustomerResponse>> response = new ResponseEntityClass<>();
        try {
            LOGGER.info("GetCustomerList request: " + tokenRequest);
            utility.checkToken(tokenRequest);
            List<Customer> customerList = cRepo.findAllByActive(EnumAvailableStatus.ACTIVE.value);
            if (customerList.isEmpty()) {
                LOGGER.warn("GetCustomer list response : customerList is empty");
                throw new BankException(ExceptionConstant.CUSTOMER_NOT_FOUND, "Customer data not found");
            }
            List<CustomerResponse> customerResponseList = customerList.stream().map(this::mapping).collect(Collectors.toList());
            LOGGER.info("GetCustomerList response: Success");
            response.setT(customerResponseList);
            response.setStatus(ResponseStatus.getSuccessMessage());
        } catch (BankException ex) {
            LOGGER.error("GetCustomerList error: " +  ex.getMessage());
            response.setStatus(new ResponseStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            LOGGER.error("GetCustomerList internal error: " +  ex.getMessage());
            response.setStatus(new ResponseStatus(ExceptionConstant.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }

    @Override
    public ResponseEntityClass<CustomerResponse> getOnceById(Long customerId) {
        ResponseEntityClass<CustomerResponse> response = new ResponseEntityClass<>();
        try {
            LOGGER.info("GetOnceById request: " + customerId);
            if (customerId == null) {
                LOGGER.warn("GetOnceById response: invalid request customerId-" + customerId);
                throw new BankException(ExceptionConstant.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Customer customer = cRepo.findByIdAndActive(customerId, EnumAvailableStatus.ACTIVE.value);
            if (customer == null) {
                LOGGER.warn("GetOnceById response: customer not found-" + customer);
                throw new BankException(ExceptionConstant.CUSTOMER_NOT_FOUND, "Customer not found");
            }
            CustomerResponse customerResponse = mapping(customer);
            LOGGER.info("GetOnceById response: Success");
            response.setT(customerResponse);
            response.setStatus(ResponseStatus.getSuccessMessage());
        } catch (BankException ex) {
            LOGGER.error("GetOnceById error: " +  ex.getMessage());
            response.setStatus(new ResponseStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            LOGGER.error("GetOnceById internal error: " +  ex.getMessage());
            response.setStatus(new ResponseStatus(ExceptionConstant.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }

    @Override
    public ResponseEntityClass saveCustomer(CustomerRequest customerRequest) {
        ResponseEntityClass response = new ResponseEntityClass();
        try {
            LOGGER.info("SaveCustomer request: " + customerRequest);
            utility.checkToken(customerRequest.getTokenRequest());
            String name = customerRequest.getName();
            String surname = customerRequest.getSurname();
            if (name == null || surname == null) {
                LOGGER.warn("SaveCustomer response: Invalid request customerRequest-" + customerRequest);
                throw new BankException(ExceptionConstant.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Customer customer = mappingEntity(customerRequest);
            customer = cRepo.save(customer);
            LOGGER.info("SaveCustomer response: Success");
            response.setT(customer);
            response.setStatus(ResponseStatus.getSuccessMessage());
        } catch (BankException ex) {
            LOGGER.error("SaveCustomer error: " +  ex.getMessage());
            response.setStatus(new ResponseStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            LOGGER.error("SaveCustomer internal error: " +  ex.getMessage());
            response.setStatus(new ResponseStatus(ExceptionConstant.INTERNAL_EXCEPTION, "Internal exception"));
        }

        return response;
    }

    @Override
    public ResponseEntityClass updateCustomer(CustomerRequest customerRequest) {
        ResponseEntityClass response = new ResponseEntityClass();
        try {
            LOGGER.info("UpdateCustomer request: " + customerRequest);
            Long customerId = customerRequest.getId();
            String name = customerRequest.getName();
            String surname = customerRequest.getSurname();
            if (name == null || surname == null || customerId == null) {
                LOGGER.warn("UpdateCustomer response: Invalid request customerRequest-" + customerRequest);
                throw new BankException(ExceptionConstant.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Customer customer = cRepo.findByIdAndActive(customerId, EnumAvailableStatus.ACTIVE.value);
            if (customer == null) {
                LOGGER.warn("UpdateCustomer response: Customer not found by id" + customerId);
                throw new BankException(ExceptionConstant.CUSTOMER_NOT_FOUND, "Customer not found");
            }
            customer.setId(customerId);
            customer.setName(name);
            customer.setSurname(surname);
            customer.setCif(customerRequest.getCif());
            customer.setAddress(customerRequest.getAddress());
            customer.setPhone(customerRequest.getPhone());
            customer.setDob(customerRequest.getDob());
            customer.setPin(customerRequest.getPin());
            customer.setSerialNumber(customerRequest.getSerialNumber());
            customer = cRepo.save(customer);
            LOGGER.info("UpdateCustomer response: Success");
            response.setT(customer);
            response.setStatus(ResponseStatus.getSuccessMessage());
        } catch (BankException ex) {
            LOGGER.error("UpdateCustomer error: " +  ex.getMessage());
            response.setStatus(new ResponseStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            LOGGER.error("UpdateCustomer internal error: " +  ex.getMessage());
            response.setStatus(new ResponseStatus(ExceptionConstant.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }

    @Override
    public ResponseEntityClass deleteCustomerById(Long id) {
        ResponseEntityClass response = new ResponseEntityClass();
        try {
            LOGGER.info("DeleteCustomerById request: " + id);
            if (id == null) {
                LOGGER.warn("DeleteCustomerById response: Invalid request id=" + id);
                throw new BankException(ExceptionConstant.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Customer customer = cRepo.findByIdAndActive(id, EnumAvailableStatus.ACTIVE.value);
            if (customer == null) {
                LOGGER.warn("DeleteCustomerById response: Customer not found id" + id);
                throw new BankException(ExceptionConstant.CUSTOMER_NOT_FOUND, "Customer not found");
            }
            customer.setActive(EnumAvailableStatus.DEACTIVE.value);
            customer = cRepo.save(customer);
            LOGGER.info("DeleteCustomerById response: Success");
            response.setT(customer);
            response.setStatus(ResponseStatus.getSuccessMessage());
        } catch (BankException ex) {
            LOGGER.error("DeleteCustomerById error: " +  ex.getMessage());
            response.setStatus(new ResponseStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            LOGGER.error("DeleteCustomerById internal error: " +  ex.getMessage());
            response.setStatus(new ResponseStatus(ExceptionConstant.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }


    private CustomerResponse mapping(Customer customer) {

        List<Account> accounts = aRepo.findAllByCustomerAndActive(customer, EnumAvailableStatus.ACTIVE.value);
        List<AccountResponse> accountResponses = new ArrayList<>();
        for (Account account : accounts) {
            AccountResponse accountResponse = new AccountResponse();
            accountResponse.setId(account.getId());
            accountResponse.setName(account.getName());
            accountResponse.setCreatedAt(account.getCreatedAt());
            accountResponse.setCurrency(account.getCurrency());
            accountResponse.setAccountNo(account.getAccountNo());
            accountResponse.setIban(account.getIban());
            accountResponses.add(accountResponse);
        }

        CustomerResponse customerResponse = CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .surname(customer.getSurname())
                .address(customer.getAddress())
                .phone(customer.getPhone())
                .dob(customer.getDob())
//                .accounts(accountResponses)
                .cif(customer.getCif())
                .serialNumber(customer.getSerialNumber())
                .pin(customer.getPin())
                .createdAt(customer.getCreatedAt())
                .build();

        return customerResponse;
    }

    private Customer mappingEntity(CustomerRequest customerRequest) {


        Customer customer = Customer.builder()
                .name(customerRequest.getName())
                .surname(customerRequest.getSurname())
                .address(customerRequest.getAddress())
                .phone(customerRequest.getPhone())
                .dob(customerRequest.getDob())
                .cif(customerRequest.getCif())
                .serialNumber(customerRequest.getSerialNumber())
                .pin(customerRequest.getPin())
                .build();

        return customer;
    }

}
