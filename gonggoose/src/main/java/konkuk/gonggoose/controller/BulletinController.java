package konkuk.gonggoose.controller;

import konkuk.gonggoose.common.dto.BulletinGetDto;
import konkuk.gonggoose.common.dto.UserBulletinDto;
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
import java.util.UUID;

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

        Long bulletinId = bulletinService.createBulletin(request);
        bulletinService.createUserBulletin(new UserBulletinDto(request.getWriter_id(), bulletinId));
        String thumbnailUrl = null;
        if(images != null){
            thumbnailUrl = bulletinImageService.saveImages(bulletinId, images);
        }


        String uuid = UUID.randomUUID().toString();
        eventPublisher.publish(new ChatRoomCreateEvent(request.getWriter_id(), thumbnailUrl, request.getTitle(), bulletinId, uuid));
        return new BaseResponse<>(new BulletinPostResponse(bulletinId, uuid));
    }

    @DeleteMapping("/{bulletinId}")
    public BaseResponse<Object> deleteBulletin(@PathVariable Long bulletinId){
        log.info("BulletinController::deleteBulletin()");

        bulletinImageService.deleteImages(bulletinId);
        bulletinService.deleteBulletin(bulletinId);
        return new BaseResponse<>(null);
    }

    @GetMapping
    public BaseResponse<List<BulletinGetDto>> getBulletinList(@RequestParam String keyword){
        log.info("BulletinController::getBulletinList()");

        return new BaseResponse<>(bulletinService.getBulletinListByKeyword(keyword));
    }
}


