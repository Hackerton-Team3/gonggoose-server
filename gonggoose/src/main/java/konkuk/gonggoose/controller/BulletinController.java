package konkuk.gonggoose.controller;

import konkuk.gonggoose.common.dto.ChatRoomCreateEventDTO;
import konkuk.gonggoose.common.event.ChatRoomCreateEvent;
import konkuk.gonggoose.common.exception.BadRequestException;
import konkuk.gonggoose.common.response.BaseResponse;
import konkuk.gonggoose.common.dto.BulletinPostRequest;
import konkuk.gonggoose.common.dto.BulletinPostResponse;
import konkuk.gonggoose.service.BulletinImageService;
import konkuk.gonggoose.service.BulletinService;
import konkuk.gonggoose.common.event.ChatRoomCreateEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static konkuk.gonggoose.common.response.status.BaseExceptionResponseStatus.BAD_REQUEST_PARAM;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("bulletins")
public class BulletinController {
    private final BulletinService bulletinService;
    private final BulletinImageService bulletinImageService;
    private final ChatRoomCreateEventPublisher eventPublisher;
    @PostMapping
    public BaseResponse<BulletinPostResponse> postBulletin(@RequestPart(required = false) List<MultipartFile> images, @RequestPart BulletinPostRequest request, BindingResult bindingResult){
        log.info("BulletinController::postBulletin()");
        log.info("request=" + request);

        if(bindingResult.hasErrors()){
            throw new BadRequestException(BAD_REQUEST_PARAM);
        }

        Long bulletinId;
        bulletinId = bulletinService.createBulletin(request);
        String thumbnailUrl = null;
        if(images != null){
            thumbnailUrl = bulletinImageService.saveImages(bulletinId, images);
        }

        eventPublisher.publish(new ChatRoomCreateEvent(this, thumbnailUrl, request.getTitle(), bulletinId));

        return new BaseResponse<>(new BulletinPostResponse(bulletinId));
    }

    @DeleteMapping("/{bulletinId}")
    public BaseResponse<Object> deleteBulletin(@PathVariable Long bulletinId){
        bulletinImageService.deleteImages(bulletinId);
        bulletinService.deleteBulletin(bulletinId);
        return new BaseResponse<>(null);
    }

    @GetMapping
    public BaseResponse<Object> getBulletinList(@RequestParam(defaultValue = "") String keyword){
        bulletinService.getBulletinListByKeyword(keyword);
        return new BaseResponse<>(null);
    }

}


