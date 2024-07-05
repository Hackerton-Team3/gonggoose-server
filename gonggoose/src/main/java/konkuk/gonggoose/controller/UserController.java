package konkuk.gonggoose.controller;

import konkuk.gonggoose.common.response.BaseErrorResponse;
import konkuk.gonggoose.common.response.BaseResponse;
import konkuk.gonggoose.common.response.status.BaseExceptionResponseStatus;
import konkuk.gonggoose.common.response.status.ResponseStatus;
import konkuk.gonggoose.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{kakaoId}")
    public ResponseStatus isExistedUser(@PathVariable long kakaoId){
        log.info("[UserController.isExistedUser");

        if(userService.isExistedUser(kakaoId)) {
            return new BaseResponse<>(null);
        }
        return new BaseErrorResponse(BaseExceptionResponseStatus.USER_NOT_FOUND, "사용자가 존재하지 않습니다.");
    }
}
