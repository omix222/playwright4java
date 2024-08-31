package com.example.demo4test;

import com.microsoft.playwright.Page;
import java.nio.file.Paths;
    public class IndexPage {
        private final Page page;

        public IndexPage(Page page, int port) {
            this.page = page;
            page.navigate("http://localhost:" + port);
        }

        public String getTitle() {
            return page.title();
        }

        public String getMainHeadingText() {
            return page.waitForSelector("h1").innerText();
        }

        public String getSubHeadingText() {
            return page.waitForSelector("h2").innerText();
        }

        public void takeScreenshot(String path) {
            page.screenshot(new Page.ScreenshotOptions().setFullPage(true).setPath(Paths.get(path)));
        }

        public void clickScreenTransition() {
            page.click("text=画面遷移");
        }

        public void clickBack() {
            page.click("id=back");
        }
    }