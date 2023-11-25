package az.orient.online.course.bankdemo.repository;

import az.orient.online.course.bankdemo.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByUsernameAndPasswordAndActive(String username, String password, Integer active);

    User findUserByIdAndActive(Long id, Integer active);
}
