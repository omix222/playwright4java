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
class PraywrightTests {
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
                    assertEquals(page.waitForSelector("h1").innerText(),"SpringBootのテスト用アプリケーション");
                    page.screenshot(new Page.ScreenshotOptions().setFullPage(true)
                            .setPath(Paths.get("screenshot-" + browserType.name() + ".png")));
                }
            }
        }
	}

}
