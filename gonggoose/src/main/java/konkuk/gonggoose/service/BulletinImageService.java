package konkuk.gonggoose.service;

import konkuk.gonggoose.dao.BulletinImageDao;
import konkuk.gonggoose.common.dto.BulletinImageDTO;
import konkuk.gonggoose.utils.ImageUploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BulletinImageService {
    private final BulletinImageDao bulletinImageDao;
    private final ImageUploader imageUploader;
    public String saveImages(Long bulletinId, List<MultipartFile> images) {
        log.info("BulletinImageService::saveImages()");
        String returnUrl = null;
        for (int i = 0; i < images.size(); i++) {
            String imageUrl = imageUploader.saveImage_temp(images.get(i));
            if(i == 0){
                returnUrl = imageUrl;
            }
            bulletinImageDao.save(BulletinImageDTO.builder()
                    .imageUrl(imageUrl)
                    .bulletinId(bulletinId)
                    .build());
        }
        return returnUrl;
    }

    public void deleteImages(Long bulletinId) {
        log.info("BulletinImageService:deleteImages()");
        bulletinImageDao.deleteImagesByBulletinId(bulletinId);
    }
}
