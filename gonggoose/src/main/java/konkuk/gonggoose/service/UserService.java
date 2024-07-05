package konkuk.gonggoose.service;

import konkuk.gonggoose.dao.UserDao;
import konkuk.gonggoose.dto.PatchImageUrlRequest;
import konkuk.gonggoose.dto.SignupRequest;
import konkuk.gonggoose.dto.SignupResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;

    public List<SignupResponse> isExistedUser(long kakaoId) {
        return userDao.isExistedUser(kakaoId);
    }

    public long signup(SignupRequest signupRequest) {
        return userDao.signup(signupRequest);
    }

    public void updateImageUrl(PatchImageUrlRequest patchImageUrlRequest, long userId) {
        userDao.updateImageUrl(patchImageUrlRequest, userId);
    }
}
