package com.heima.wemedia.service;

import com.heima.wemedia.WemediaApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @PROJECT_NAME: heima-leadnews
 * @PACKAGE_NAME: com.heima.wemedia.service
 * @CLASS_NAME: WmNewsAutoScanServiceTest
 * @USER: hongyaoyao
 * @DATETIME: 2023/8/22 16:17
 * @Emial: 1299694047@qq.com
 */
@SpringBootTest(classes = WemediaApplication.class)
@RunWith(SpringRunner.class)
public class WmNewsAutoScanServiceTest {

    @Autowired
    private WmNewsAutoScanService wmNewsAutoScanService;

    @Test
    public void autoScanWmNews() {
        wmNewsAutoScanService.autoScanWmNews(6237);
    }
}