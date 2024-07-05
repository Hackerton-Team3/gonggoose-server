package konkuk.gonggoose.controller;

import konkuk.gonggoose.common.exception.BadRequestException;
import konkuk.gonggoose.common.exception.InternalServerErrorException;
import konkuk.gonggoose.common.response.BaseResponse;
import konkuk.gonggoose.common.dto.BulletinPostRequest;
import konkuk.gonggoose.common.dto.BulletinPostResponse;
import konkuk.gonggoose.service.BulletinImageService;
import konkuk.gonggoose.service.BulletinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static konkuk.gonggoose.common.response.status.BaseExceptionResponseStatus.BAD_REQUEST_PARAM;
import static konkuk.gonggoose.common.response.status.BaseExceptionResponseStatus.SERVER_ERROR;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("bulletins")
public class BulletinController {
    private final BulletinService bulletinService;
    private final BulletinImageService bulletinImageService;
    @PostMapping
    public BaseResponse<BulletinPostResponse> postBulletin(@RequestPart(required = false) List<MultipartFile> images, @RequestPart BulletinPostRequest request, BindingResult bindingResult){
        log.info("BulletinController::postBulletin()");
        log.info("request=" + request);

        if(bindingResult.hasErrors()){
            throw new BadRequestException(BAD_REQUEST_PARAM);
        }

        Long bulletinId;
        try{
            bulletinId = bulletinService.createBulletin(request);
            if(!images.isEmpty()){
                bulletinImageService.saveImages(bulletinId, images);
            }
        }
        catch(Exception e){
            throw new InternalServerErrorException(SERVER_ERROR);
        }

        return new BaseResponse<>(new BulletinPostResponse(bulletinId));
    }

    @DeleteMapping("/{bulletinId}")
    public BaseResponse<Object> deleteBulletin(@PathVariable Long bulletinId){
        bulletinImageService.deleteImages(bulletinId);
        bulletinService.deleteBulletin(bulletinId);
        return new BaseResponse<>(null);
    }
}


