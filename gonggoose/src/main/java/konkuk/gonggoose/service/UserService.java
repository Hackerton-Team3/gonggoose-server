package konkuk.gonggoose.service;

import konkuk.gonggoose.dao.UserDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;

    public boolean isExistedUser(long kakaoId) {
        return userDao.isExistedUser(kakaoId);
    }
}
