package konkuk.gonggoose.controller;

import konkuk.gonggoose.api.KakaoApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final KakaoApi kakaoApi;

    @RequestMapping("/login/oauth2/code/kakao")
    public String login(@RequestParam String code) {
        // 1. 인가 코드 받기 (@RequestParam String code)

        // 2. 토큰 받기
        String accessToken = kakaoApi.getAccessToken(code);

        // 3. 사용자 정보 받기
        Map<String, Object> userInfo = kakaoApi.getUserInfo(accessToken);
        log.info(userInfo.toString());

        String nickname = (String)userInfo.get("nickname");
        String id = (String)userInfo.get("id");

        System.out.println("nickname = " + nickname);
        System.out.println("id = " + id);
        System.out.println("accessToken = " + accessToken);

        return "redirect:/login";
    }

}
