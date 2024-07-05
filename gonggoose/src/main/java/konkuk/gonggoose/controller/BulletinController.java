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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.UUID;

import static konkuk.gonggoose.common.response.status.BaseExceptionResponseStatus.BAD_REQUEST_PARAM;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("bulletins")
public class BulletinController {
    @Value("${bulletin.image.path}")
    private String imagePath;

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

    @GetMapping("{bulletinId}")
    public BaseResponse<BulletinGetDto> getBulletin(@PathVariable Long bulletinId){
        return new BaseResponse<>(bulletinService.getBulletin(bulletinId));
    }

    @GetMapping(value = "/images/{fileName}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable("fileName") String fileName) throws IOException {
        String path = imagePath + fileName;

        log.info("path = " + path);

        File imgFile = new File(path);
        if (!imgFile.exists()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        InputStream in = new FileInputStream(imgFile);
        byte[] imageBytes = StreamUtils.copyToByteArray(in);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);

    }

//    @GetMapping(value = "/images/{fileOriginName}", produces = MediaType.IMAGE_PNG_VALUE)
//    public byte[] getItemImageByName(@PathVariable("fileOriginName") String fileName) throws IOException {
//        log.info("BulletinController::getItemImageByName()");
//
//        String res = imagePath + fileName;
//        FileInputStream in = new FileInputStream(res);
//        return StreamUtils.copyToByteArray(in);
//    }
}

//
//        FileSystemResource resource = new FileSystemResource(imagePath+fileName);
//        if (!resource.exists()) {
//            throw new InternalServerErrorException(IMAGE_NOT_FOUND);
//        }
//        HttpHeaders header = new HttpHeaders();
//        Path filePath = Paths.get(imagePath + fileName);
//        header.add("Content-Type", Files.probeContentType(filePath));
//        return new ResponseEntity<>(resource, header, HttpStatus.OK);


