package az.orient.online.course.bankdemo.controller;

import az.orient.online.course.bankdemo.model.customer.response.ResponseEntityClass;
import az.orient.online.course.bankdemo.model.entity.Transaction;
import az.orient.online.course.bankdemo.model.transaction.TransactionRequest;
import az.orient.online.course.bankdemo.model.transaction.TransactionResponse;
import az.orient.online.course.bankdemo.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/list")
    public ResponseEntityClass<List<TransactionResponse>> getTransactionListByAccount(@RequestParam Long accountId) {
        return transactionService.getTransactionByAccount(accountId);
    }

    @PostMapping("save")
    public ResponseEntityClass saveTransaction(@RequestBody TransactionRequest transactionRequest) {
        return transactionService.saveTransaction(transactionRequest);
    }

}
