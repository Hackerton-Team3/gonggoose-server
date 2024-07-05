package konkuk.gonggoose.service;

import konkuk.gonggoose.common.dto.BulletinGetDto;
import konkuk.gonggoose.common.dto.UserBulletinDto;
import konkuk.gonggoose.dao.BulletinDao;
import konkuk.gonggoose.common.dto.BulletinPostRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BulletinService {
    private final BulletinDao bulletinDAO;

    public long createBulletin(BulletinPostRequest request) {
        log.info("BulletinService::createBulletin()");
        return bulletinDAO.createBulletin(request);
    }

    public long createUserBulletin(UserBulletinDto dto){
        return bulletinDAO.createUserBulletin(dto);
    }

    public void deleteBulletin(Long bulletinId) {
        log.info("BulletinService::deleteBulletin()");
        bulletinDAO.deleteBulletin(bulletinId);
    }

    public List<BulletinGetDto> getBulletinListByKeyword(String keyword) {
        log.info("BulletinService::getBulletinListByKeyword()");
        return bulletinDAO.getBulletinListByKeyword(keyword);
    }

    public BulletinGetDto getBulletin(Long bulletinId) {
        log.info("BulletinService::getBulletin()");

        return bulletinDAO.getBulletin(bulletinId);
    }
}
