package az.orient.online.course.bankdemo.service.impl;

import az.orient.online.course.bankdemo.enums.EnumAvailableStatus;
import az.orient.online.course.bankdemo.exception.BankException;
import az.orient.online.course.bankdemo.exception.ExceptionConstant;
import az.orient.online.course.bankdemo.model.account.response.AccountResponse;
import az.orient.online.course.bankdemo.model.customer.response.ResponseEntityClass;
import az.orient.online.course.bankdemo.model.customer.response.ResponseStatus;
import az.orient.online.course.bankdemo.model.entity.Account;
import az.orient.online.course.bankdemo.model.entity.Transaction;
import az.orient.online.course.bankdemo.model.transaction.TransactionRequest;
import az.orient.online.course.bankdemo.model.transaction.TransactionResponse;
import az.orient.online.course.bankdemo.repository.AccountRepository;
import az.orient.online.course.bankdemo.repository.TransactionRepository;
import az.orient.online.course.bankdemo.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Override
    public ResponseEntityClass<List<TransactionResponse>> getTransactionByAccount(Long accountId) {
        ResponseEntityClass<List<TransactionResponse>> response = new ResponseEntityClass<>();
        try {
            LOGGER.info("GetTransactionByAccount request: accountId-" + accountId);
            if (accountId == null) {
                LOGGER.warn("GetTransactionByAccount response: Invalid request data-" + accountId);
                throw new BankException(ExceptionConstant.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Account account = accountRepository.findAccountByIdAndActive(accountId, EnumAvailableStatus.ACTIVE.value);
            if (account == null) {
                LOGGER.warn("GetTransactionByAccount response: Account not found by accountId-" + accountId);
                throw new BankException(ExceptionConstant.ACCOUNT_NOT_FOUND, "Account not found");
            }
            List<Transaction> transactions = transactionRepository.findAllByFromAccountAndActive(account, EnumAvailableStatus.ACTIVE.value);
            if (transactions.isEmpty()) {
                LOGGER.warn("GetTransactionByAccount response: Transaction not found by Account-" + account);
                throw new BankException(ExceptionConstant.TRANSACTION_NOT_FOUND, "Transaction not found");
            }
            List<TransactionResponse> transactionResponses = transactions.stream().map(this::mapping).toList();
            LOGGER.info("GetTransactionByAccount response: Success");
            response.setT(transactionResponses);
            response.setStatus(ResponseStatus.getSuccessMessage());
        } catch (BankException ex) {
            LOGGER.error("GetTransactionByAccount error: " +  ex.getMessage());
            response.setStatus(new ResponseStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            LOGGER.error("GetTransactionByAccount internal error: " +  ex.getMessage());
            response.setStatus(new ResponseStatus(ExceptionConstant.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }

    @Override
    public ResponseEntityClass saveTransaction(TransactionRequest transactionRequest) {
        ResponseEntityClass response = new ResponseEntityClass();
        try {
            LOGGER.info("SaveTransaction request: " + transactionRequest);
            Long accountId = transactionRequest.getFromAccountId();
            String iban = transactionRequest.getIban();
            String currency = transactionRequest.getCurrency();
            String toAccount = transactionRequest.getToAccount();
            Double amount = transactionRequest.getAmount();
            if (accountId == null || iban == null || currency == null || toAccount == null) {
                LOGGER.warn("SaveTransaction response: Invalid request data: " + transactionRequest);
                throw new BankException(ExceptionConstant.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Account account = accountRepository.findAccountByIdAndActive(accountId, EnumAvailableStatus.ACTIVE.value);
            if (account == null) {
                LOGGER.warn("SaveTransaction response: Account not found by accountId-" + accountId);
                throw new BankException(ExceptionConstant.ACCOUNT_NOT_FOUND, "Account not found");
            }
            Transaction transaction = Transaction.builder()
                    .fromAccount(account)
                    .toAccount(toAccount)
                    .currency(currency)
                    .iban(iban)
                    .note(transactionRequest.getNote())
                    .amount(amount)
                    .build();
            transaction = transactionRepository.save(transaction);
            LOGGER.info("SaveTransaction request: Success");
            response.setT(transaction);
            response.setStatus(ResponseStatus.getSuccessMessage());
        } catch (BankException ex) {
            LOGGER.error("SaveTransaction error: " +  ex.getMessage());
            response.setStatus(new ResponseStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            LOGGER.error("SaveTransaction internal error: " +  ex.getMessage());
            response.setStatus(new ResponseStatus(ExceptionConstant.INTERNAL_EXCEPTION, "Internal exception"));
        }

        return response;
    }

    private TransactionResponse mapping(Transaction transaction) {

        Account fromAccount = transaction.getFromAccount();

        AccountResponse accountResponse = AccountResponse.builder()
                .id(fromAccount.getId())
                .name(fromAccount.getName())
                .accountNo(fromAccount.getAccountNo())
                .currency(fromAccount.getCurrency())
                .createdAt(fromAccount.getCreatedAt())
                .build();

        TransactionResponse transactionResponse = TransactionResponse.builder()
                .id(transaction.getId())
                .fromAccount(accountResponse)
                .amount(transaction.getAmount())
                .note(transaction.getNote())
                .toAccount(transaction.getToAccount())
                .iban(transaction.getIban())
                .currency(transaction.getCurrency())
                .createdAt(transaction.getCreatedAt())
                .build();

        return transactionResponse;
    }
}
