package az.orient.online.course.bankdemo.schedule;

import az.orient.online.course.bankdemo.model.customer.response.ResponseEntityClass;
import az.orient.online.course.bankdemo.model.customer.response.TokenResponse;
import az.orient.online.course.bankdemo.model.entity.UserToken;
import az.orient.online.course.bankdemo.service.UserTokenService;
import az.orient.online.course.bankdemo.util.Utility;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static java.time.temporal.ChronoUnit.MINUTES;


@RequiredArgsConstructor
@Component
@PropertySource("classpath:config.properties")
public class BankSchedule implements Runnable {

    private final UserTokenService userTokenService;
    private final Utility utility;

    @Value("${token.expire.time}")
    private int tokenExpireTime;

    @Scheduled(cron = "*/30 * * * * *")
    public void run() {
        ResponseEntityClass<List<TokenResponse>> response = userTokenService.getTokenList();
        List<TokenResponse> tokenResponseList = response.getT();
        LocalDateTime currentDate = LocalDateTime.now();
        if (tokenResponseList == null) {
            System.out.println("List is Empty");
        } else {
            for (TokenResponse tokenResponse : tokenResponseList) {
                Date date = tokenResponse.getUpdatedAt();
                LocalDateTime updatedDate = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
                ResponseEntityClass<UserToken> userTokenResponse = userTokenService.getTokenById(tokenResponse.getId());
                if (userTokenResponse.getT()==null) {
                    System.out.println("userTokenResponse.getT() is null");
                } else {
                    UserToken userToken = userTokenResponse.getT();
                    int expiredTest = (int) MINUTES.between(updatedDate, currentDate);
                    System.out.println(expiredTest);
                    if (expiredTest >= tokenExpireTime) {
                        utility.setExpiredToken(userToken);
                    }
                }
            }
        }
    }
}


