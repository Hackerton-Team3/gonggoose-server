package konkuk.gonggoose.controller;

import konkuk.gonggoose.common.response.BaseErrorResponse;
import konkuk.gonggoose.common.response.BaseResponse;
import konkuk.gonggoose.common.response.status.BaseExceptionResponseStatus;
import konkuk.gonggoose.common.response.status.ResponseStatus;
import konkuk.gonggoose.dto.BulletinResponse;
import konkuk.gonggoose.dto.PatchImageUrlRequest;
import konkuk.gonggoose.dto.SignupRequest;
import konkuk.gonggoose.dto.SignupResponse;
import konkuk.gonggoose.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{kakaoId}")
    public ResponseStatus isExistedUser(@PathVariable long kakaoId) {
        log.info("[UserController.isExistedUser]");

        long findResult = userService.isExistedUser(kakaoId);
        if (findResult != -1) {
            return new BaseResponse<>(findResult);
        }
        return new BaseErrorResponse(BaseExceptionResponseStatus.USER_NOT_FOUND, "사용자가 존재하지 않습니다.");
    }

    @PostMapping("")
    public ResponseStatus signup(@RequestBody SignupRequest signupRequest) {
        log.info("[UserController.signup]");

        long signupResult = userService.signup(signupRequest);
        if (signupResult != -1) {
            return new BaseResponse<>(new SignupResponse(signupResult));
        }
        return new BaseErrorResponse(BaseExceptionResponseStatus.DUPLICATE_KAKAO_ID, "이미 존재하는 카카오 ID입니다.");
    }

    @PatchMapping("/{userId}/image")
    public BaseResponse<String> updateImageUrl(@RequestBody PatchImageUrlRequest patchImageUrlRequest, @PathVariable long userId) {
        log.info("[UserController.updateImageUrl]");

        userService.updateImageUrl(patchImageUrlRequest, userId);

        return new BaseResponse<>(null);
    }

//    @GetMapping("/{userId}/bulletins")
//    public BaseResponse<List<BulletinResponse>> getBulletinsByUserId(@PathVariable long userId) {
//        log.info("[UserController.getBulletinsByUserId]");
//
//        return new BaseResponse<>(userService.getBulletinsByUserId(userId));
//    }
}
