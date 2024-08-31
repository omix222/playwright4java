package com.example.demo4test;

import com.microsoft.playwright.Page;

import java.nio.file.Paths;

public class HelloPage {
    private final Page page;

    public HelloPage(Page page) {
        this.page = page;
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

    public void clickBack() {
        page.click("id=back");
    }
}