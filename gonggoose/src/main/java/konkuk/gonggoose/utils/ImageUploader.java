package konkuk.gonggoose.utils;

import konkuk.gonggoose.common.exception.InternalServerErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

import static konkuk.gonggoose.common.response.status.BaseExceptionResponseStatus.IMAGE_UPLOAD_FAILED;

@Slf4j
@Component
public class ImageUploader {
    @Value("${bulletin.image.path}")
    private String imagePath; // 업로드된 이미지가 저장되는 디렉토리 경로
    public String saveImage_temp(MultipartFile image) {
        final String extension = image.getContentType().split("/")[1];
        final String imageName = UUID.randomUUID() + "." + extension;

        log.info("extension=" + extension + ", imageName=" + imageName);

        // 파일을 경로에 저장
        try {
            final File file = new File(imagePath + imageName);
            image.transferTo(file);
        } catch (Exception e) {
            throw new InternalServerErrorException(IMAGE_UPLOAD_FAILED);
        }
        return imageName;
    }
}
