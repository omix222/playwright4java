package com.example.demo4test;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class PlaywrightTests {
    @LocalServerPort
    private int port;
    @Test
    void tests() {
        try (Playwright playwright = Playwright.create()) {
            List<BrowserType> browserTypes = List.of(playwright.chromium(), playwright.webkit(), playwright.firefox());

            for (BrowserType browserType : browserTypes) {
                try (Browser browser = browserType.launch();
                     BrowserContext context = browser.newContext();
                     Page page = context.newPage()) {

                    // IndexPageオブジェクトの生成と操作
                    IndexPage homePage = new IndexPage(page, port);
                    homePage.takeScreenshot("target/testresult/screenshot-1-" + browserType.name() + ".png");
                    assertEquals("SpringBoot - テスト用画面", homePage.getTitle());
                    assertEquals("SpringBootのテスト用アプリケーション", homePage.getMainHeadingText());
                    assertEquals("メニューリスト", homePage.getSubHeadingText());

                    // TransitionPageオブジェクトの生成と操作
                    homePage.clickScreenTransition();
                    HelloPage transitionPage = new HelloPage(page);
                    transitionPage.takeScreenshot("target/testresult/screenshot-2-" + browserType.name() + ".png");
                    assertEquals("Hello Thymeleaf", transitionPage.getTitle());
                    assertEquals("Hello Thymeleaf!!!!!!", transitionPage.getMainHeadingText());
                    assertEquals("メインメニューへ戻る", transitionPage.getSubHeadingText());

                    // 戻る操作
                    transitionPage.clickBack();
                    homePage.takeScreenshot("target/testresult/screenshot-3-" + browserType.name() + ".png");
                    assertEquals("SpringBootのテスト用アプリケーション", homePage.getMainHeadingText());
                }
            }
        }

	}

}
