package konkuk.gonggoose.service;

import konkuk.gonggoose.dao.UserDao;
import konkuk.gonggoose.dto.SignupRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;

    public boolean isExistedUser(long kakaoId) {
        return userDao.isExistedUser(kakaoId);
    }

    public void signup(SignupRequest signupRequest) {
        String createdAt = LocalDate.now().toString();
        String updatedAt = "";

        userDao.signup(signupRequest, createdAt, updatedAt);
    }
}
