package az.orient.online.course.bankdemo.repository;

import az.orient.online.course.bankdemo.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findAllByActive(Integer active);

    Customer findByIdAndActive(Long customerId, Integer active);
}
