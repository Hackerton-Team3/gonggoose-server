package konkuk.gonggoose.controller;

import konkuk.gonggoose.common.response.BaseResponse;
import konkuk.gonggoose.dto.BulletinPostRequest;
import konkuk.gonggoose.dto.BulletinPostResponse;
import konkuk.gonggoose.service.BulletinImageService;
import konkuk.gonggoose.service.BulletinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("bulletins")
public class BulletinController {
    private final BulletinService bulletinService;
    private final BulletinImageService bulletinImageService;
    @PostMapping
    public BaseResponse<BulletinPostResponse> postBulletin(@RequestPart List<MultipartFile> images, @RequestPart BulletinPostRequest request){
        log.info("BulletinController::postBulletin()");
        log.info("request=" + request);
        Long bulletinId = bulletinService.createBulletin(request);
        bulletinImageService.saveImages(bulletinId, images);
        return new BaseResponse<>(new BulletinPostResponse(bulletinId));
    }


}


