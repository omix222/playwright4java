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
            List<BrowserType> browserTypes =
                    List.of(playwright.chromium(), playwright.webkit(), playwright.firefox());

            for (BrowserType browserType : browserTypes) {
                try (Browser browser = browserType.launch();
                     BrowserContext context = browser.newContext();
                     Page page = context.newPage()) {

                    page.navigate("http://localhost:"+port);
                    page.screenshot(new Page.ScreenshotOptions().setFullPage(true)
                            .setPath(Paths.get("target/testresult/screenshot-1-" + browserType.name() + ".png")));
                    assertEquals("SpringBoot - テスト用画面",page.title());
                    assertEquals("SpringBootのテスト用アプリケーション",page.waitForSelector("h1").innerText());
                    assertEquals("メニューリスト",page.waitForSelector("h2").innerText());
                    page.click("text=画面遷移");
                    page.screenshot(new Page.ScreenshotOptions().setFullPage(true)
                            .setPath(Paths.get("target/testresult/screenshot-2-" + browserType.name() + ".png")));
                    assertEquals("Hello Thymeleaf",page.title());
                    assertEquals("Hello Thymeleaf!!!!!!",page.waitForSelector("h1").innerText());
                    assertEquals("メインメニューへ戻る",page.waitForSelector("h2").innerText());
                    page.click("id=back");
                    page.screenshot(new Page.ScreenshotOptions().setFullPage(true)
                            .setPath(Paths.get("target/testresult/screenshot-3-" + browserType.name() + ".png")));
                    assertEquals("SpringBootのテスト用アプリケーション",page.waitForSelector("h1").innerText());
                }
            }
        }
	}

}
