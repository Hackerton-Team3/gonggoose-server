package konkuk.gonggoose.service;

import konkuk.gonggoose.dao.BulletinImageDao;
import konkuk.gonggoose.common.dto.BulletinImageDto;
import konkuk.gonggoose.utils.ImageUploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BulletinImageService {
    private final BulletinImageDao bulletinImageDao;
    private final ImageUploader imageUploader;
    public void saveImages(Long bulletinId, List<MultipartFile> images) {
        log.info("BulletinImageService::saveImages()");
        for(MultipartFile image : images){
            String imageUrl = imageUploader.saveImage_temp(image);
            bulletinImageDao.save(BulletinImageDto.builder()
                    .imageUrl(imageUrl)
                    .bulletinId(bulletinId)
                    .build());
        }
    }

    public void deleteImages(Long bulletinId) {
        bulletinImageDao.deleteImagesByBulletinId(bulletinId);
    }
}
