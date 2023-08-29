package com.heima.wemedia.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @PROJECT_NAME: heima-leadnews
 * @PACKAGE_NAME: com.heima.wemedia.config
 * @CLASS_NAME: InitConfig
 * @USER: hongyaoyao
 * @DATETIME: 2023/8/22 17:06
 * @Emial: 1299694047@qq.com
 */
@Configuration
@ComponentScan("com.heima.apis.article.fallback")  //将服务降级代码扫描进容器中
public class InitConfig {
}
