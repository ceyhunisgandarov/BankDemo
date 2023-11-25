package az.orient.online.course.bankdemo.repository;

import az.orient.online.course.bankdemo.model.account.response.AccountResponse;
import az.orient.online.course.bankdemo.model.entity.Account;
import az.orient.online.course.bankdemo.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findAllByCustomerAndActive(Customer customer, int active);

    Account findByIdAndActive(Long id, int active);

    Account findAccountByIdAndActive(Long id, Integer active);
}
