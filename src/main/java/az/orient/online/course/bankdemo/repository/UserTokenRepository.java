package az.orient.online.course.bankdemo.repository;

import az.orient.online.course.bankdemo.model.entity.User;
import az.orient.online.course.bankdemo.model.entity.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserTokenRepository extends JpaRepository<UserToken, Long> {

    UserToken findUserTokenByUserAndTokenAndActive(User user, String token, Integer active);

    List<UserToken> findAllByActive(Integer active);

    UserToken findUserTokenByIdAndActive(Long id, int value);

    UserToken findUserTokenByTokenAndActive(String token, int value);

}
