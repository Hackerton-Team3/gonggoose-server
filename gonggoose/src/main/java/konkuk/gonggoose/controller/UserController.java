package konkuk.gonggoose.controller;

import konkuk.gonggoose.common.response.BaseErrorResponse;
import konkuk.gonggoose.common.response.BaseResponse;
import konkuk.gonggoose.common.response.status.BaseExceptionResponseStatus;
import konkuk.gonggoose.common.response.status.ResponseStatus;
import konkuk.gonggoose.dto.SignupRequest;
import konkuk.gonggoose.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{kakaoId}")
    public ResponseStatus isExistedUser(@PathVariable long kakaoId) {
        log.info("[UserController.isExistedUser]");

        if (userService.isExistedUser(kakaoId)) {
            return new BaseResponse<>(null);
        }
        return new BaseErrorResponse(BaseExceptionResponseStatus.USER_NOT_FOUND, "사용자가 존재하지 않습니다.");
    }

    @PostMapping("")
    public ResponseStatus signup(@RequestBody SignupRequest signupRequest) {
        log.info("[UserController.signup]");

        if (userService.signup(signupRequest)) {
            return new BaseResponse<>(null);
        }
        return new BaseErrorResponse(BaseExceptionResponseStatus.DUPLICATE_KAKAO_ID, "이미 존재하는 카카오 ID입니다.");
    }
}
