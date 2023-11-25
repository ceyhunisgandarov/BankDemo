package az.orient.online.course.bankdemo.service;

import az.orient.online.course.bankdemo.model.customer.response.ResponseEntityClass;
import az.orient.online.course.bankdemo.model.entity.Transaction;
import az.orient.online.course.bankdemo.model.transaction.TransactionRequest;
import az.orient.online.course.bankdemo.model.transaction.TransactionResponse;

import java.util.List;

public interface TransactionService {


    ResponseEntityClass<List<TransactionResponse>> getTransactionByAccount(Long accountId);

    ResponseEntityClass saveTransaction(TransactionRequest transactionRequest);
}
