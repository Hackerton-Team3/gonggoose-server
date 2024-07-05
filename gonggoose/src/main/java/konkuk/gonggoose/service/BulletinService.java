package konkuk.gonggoose.service;

import konkuk.gonggoose.dao.BulletinDAO;
import konkuk.gonggoose.dto.BulletinPostRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BulletinService {
    private final BulletinDAO bulletinDAO;

    public long createBulletin(BulletinPostRequest request) {
        log.info("BulletinService::createBulletin()");
        return bulletinDAO.createBulletin(request);
    }
}
