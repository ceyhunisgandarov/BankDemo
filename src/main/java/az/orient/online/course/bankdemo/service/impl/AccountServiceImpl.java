package az.orient.online.course.bankdemo.service.impl;

import az.orient.online.course.bankdemo.enums.EnumAvailableStatus;
import az.orient.online.course.bankdemo.exception.BankException;
import az.orient.online.course.bankdemo.exception.ExceptionConstant;
import az.orient.online.course.bankdemo.model.account.request.AccountRequest;
import az.orient.online.course.bankdemo.model.account.response.AccountResponse;
import az.orient.online.course.bankdemo.model.customer.response.CustomerResponse;
import az.orient.online.course.bankdemo.model.customer.response.ResponseEntityClass;
import az.orient.online.course.bankdemo.model.customer.response.ResponseStatus;
import az.orient.online.course.bankdemo.model.entity.Account;
import az.orient.online.course.bankdemo.model.entity.Customer;
import az.orient.online.course.bankdemo.repository.AccountRepository;
import az.orient.online.course.bankdemo.repository.CustomerRepository;
import az.orient.online.course.bankdemo.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Override
    public ResponseEntityClass<List<AccountResponse>> getAccountListById(Long customerId) {
        ResponseEntityClass<List<AccountResponse>> response = new ResponseEntityClass<>();
        try {
            LOGGER.info("GetAccountListById request: " + customerId);
            if (customerId == null) {
                LOGGER.warn("GetAccountListById response: Invalid request customerId-" + customerId);
                throw new BankException(ExceptionConstant.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Customer customer = customerRepository.findByIdAndActive(customerId, EnumAvailableStatus.ACTIVE.value);
            if (customer == null) {
                LOGGER.warn("GetAccountListById response: Customer not found by Id-" + customerId);
                throw new BankException(ExceptionConstant.CUSTOMER_NOT_FOUND, "Customer not found");
            }
            List<Account> accountListByCustomer = accountRepository.findAllByCustomerAndActive(customer, EnumAvailableStatus.ACTIVE.value);
            if (accountListByCustomer.isEmpty()) {
                LOGGER.warn("GetAccountListById response: Invalid request accounts by customer-" + customer);
                throw new BankException(ExceptionConstant.ACCOUNT_NOT_FOUND, "Account data not found");
            }
            List<AccountResponse> responseList = accountListByCustomer.stream().map(this::convertResponse).collect(Collectors.toList());
            LOGGER.info("GetAccountListById response: Success");
            response.setT(responseList);
            response.setStatus(ResponseStatus.getSuccessMessage());
        } catch (BankException ex) {
            LOGGER.error("GetAccountListById error: " + ex.getMessage());
            response.setStatus(new ResponseStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            LOGGER.error("GetAccountListById internal error: " + ex.getMessage());
            response.setStatus(new ResponseStatus(ExceptionConstant.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }


    @Override
    public ResponseEntityClass saveAccount(AccountRequest accountRequest) {
        ResponseEntityClass response = new ResponseEntityClass();
        try {
            LOGGER.info("SaveAccount request: " + accountRequest);
            String name = accountRequest.getName();
            String accountNo = accountRequest.getAccountNo();
            String iban = accountRequest.getIban();
            Long customerId = accountRequest.getCustomerId();
            if (name == null || accountNo == null || customerId == null || iban == null) {
                LOGGER.warn("SaveAccount response: Invalid request accounts by account-" + accountRequest);
                throw new BankException(ExceptionConstant.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Customer customer = customerRepository.findByIdAndActive(customerId, EnumAvailableStatus.ACTIVE.value);
            if (customer == null) {
                LOGGER.warn("SaveAccount response: Customer not found by customerId-" + customerId);
                throw new BankException(ExceptionConstant.CUSTOMER_NOT_FOUND, "Customer not found");
            }
            Account account = Account.builder()
                    .name(name)
                    .accountNo(accountNo)
                    .iban(iban)
                    .currency(accountRequest.getCurrency())
                    .customer(customer)
                    .build();
            account = accountRepository.save(account);
            LOGGER.info("SaveAccount response: Success");
            response.setT(account);
            response.setStatus(ResponseStatus.getSuccessMessage());
        } catch (BankException ex) {
            LOGGER.error("SaveAccount error: " + ex.getMessage());
            response.setStatus(new ResponseStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            LOGGER.error("SaveAccount internal error: " + ex.getMessage());
            response.setStatus(new ResponseStatus(ExceptionConstant.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }

    @Override
    public ResponseEntityClass updateAccount(AccountRequest accountRequest) {
        ResponseEntityClass response = new ResponseEntityClass();
        try {
            LOGGER.info("UpdateAccount request: " + accountRequest);
            Long id = accountRequest.getId();
            String name = accountRequest.getName();
            String accountNo = accountRequest.getAccountNo();
            String iban = accountRequest.getIban();
            Long customerId = accountRequest.getCustomerId();
            if (id == null || name == null || accountNo == null || customerId == null || iban == null) {
                LOGGER.warn("UpdateAccount response: Invalid request data by accountRequest-" + accountRequest);
                throw new BankException(ExceptionConstant.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Account account = accountRepository.findByIdAndActive(id, EnumAvailableStatus.ACTIVE.value);
            if (account == null) {
                LOGGER.warn("UpdateAccount response: Account not found by id-" + id);
                throw new BankException(ExceptionConstant.ACCOUNT_NOT_FOUND, "Account not found");
            }
            Customer customer = customerRepository.findByIdAndActive(customerId, EnumAvailableStatus.ACTIVE.value);
            if (customer == null) {
                LOGGER.warn("UpdateAccount response: Customer not found by customerId-" + customerId);
                throw new BankException(ExceptionConstant.CUSTOMER_NOT_FOUND, "Customer not found");
            }
            account.setId(id);
            account.setName(name);
            account.setAccountNo(accountNo);
            account.setCurrency(accountRequest.getCurrency());
            account.setCustomer(customer);
            account.setIban(iban);
            account = accountRepository.save(account);
            LOGGER.info("UpdateAccount response: Success");
            response.setT(account);
            response.setStatus(ResponseStatus.getSuccessMessage());
        } catch (BankException ex) {
            LOGGER.error("UpdateAccount error: " + ex.getMessage());
            response.setStatus(new ResponseStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            LOGGER.error("UpdateAccount internal error: " + ex.getMessage());
            response.setStatus(new ResponseStatus(ExceptionConstant.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }

    @Override
    public ResponseEntityClass deleteAccount(Long id) {
        ResponseEntityClass response = new ResponseEntityClass();
        try {
            LOGGER.info("DeleteAccount request: accountId-" + id);
            if (id == null) {
                LOGGER.warn("DeleteAccount response: Invalid request accountId-" + id);
                throw new BankException(ExceptionConstant.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Account account = accountRepository.findByIdAndActive(id, EnumAvailableStatus.ACTIVE.value);
            if (account == null) {
                LOGGER.warn("DeleteAccount response: Account not found by accountId-" + id);
                throw new BankException(ExceptionConstant.ACCOUNT_NOT_FOUND, "Account not found");
            }
            account.setActive(EnumAvailableStatus.DEACTIVE.value);
            accountRepository.save(account);
            LOGGER.warn("DeleteAccount response: Success");
            response.setStatus(ResponseStatus.getSuccessMessage());
        } catch (BankException ex) {
            LOGGER.error("DeleteAccount error: " + ex.getMessage());
            response.setStatus(new ResponseStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            LOGGER.error("DeleteAccount internal error: " + ex.getMessage());
            response.setStatus(new ResponseStatus(ExceptionConstant.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }

    private AccountResponse convertResponse(Account account) {

        Customer customer = account.getCustomer();
        CustomerResponse customerResponse = CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .surname(customer.getSurname())
                .phone(customer.getPhone())
                .address(customer.getAddress())
                .cif(customer.getCif())
                .dob(customer.getDob())
                .pin(customer.getPin())
                .serialNumber(customer.getSerialNumber())
                .createdAt(customer.getCreatedAt())
                .build();

        AccountResponse convertedAccount = AccountResponse.builder()
                .id(account.getId())
                .name(account.getName())
                .accountNo(account.getAccountNo())
                .currency(account.getCurrency())
                .iban(account.getIban())
                .customerResponse(customerResponse)
                .build();

        return convertedAccount;
    }

}
