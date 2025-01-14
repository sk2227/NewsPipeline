package com.sahilkamat.services;
import com.sahilkamat.model.Article;
import com.sahilkamat.utils.GoogleTranslateUtil;
import com.sahilkamat.utils.HeaderAnalyzationUtil;
import com.sahilkamat.utils.ImageDownloadUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.*;
import org.openqa.selenium.JavascriptExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




import java.time.Duration;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;


public class ArticleScrapperService {

    private static final Logger logger = LoggerFactory.getLogger(ArticleScrapperService.class);

    private WebDriver driver;

    //to test
    public ArticleScrapperService() {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver-win64\\chromedriver.exe");
        System.setProperty("webdriver.chrome.driver.disable-manager", "true");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    // For browserstack
    public ArticleScrapperService(WebDriver driver) {
        this.driver = driver;
    }


    public void fetchArticles() {
        try {

            //selenium to navigate to website

            driver.get("https://elpais.com/");
            logger.info("[Thread: {}] Navigated to website: https://elpais.com/", Thread.currentThread().getName());

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));

            WebElement opinionLink = driver.findElement(By.xpath("//a[text()='Opinión']"));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", opinionLink);

            wait.until(ExpectedConditions.urlContains("/opinion"));
            logger.info("[Thread: {}] Navigated to opinion page", Thread.currentThread().getName());

            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.tagName("article"), 10));

            //parsing articles
            List<WebElement> articles = driver.findElements(By.tagName("article"));
            logger.info("No of articles fetched: " + articles.stream().count());
            ConcurrentLinkedQueue<Article> articleList = new ConcurrentLinkedQueue<>();
            List<String> imgURLs = new ArrayList<>();
            int count = 0;

            for (WebElement article : articles) {
                if (count >= 5) break;
                try {

                    logger.info(String.valueOf("Failure checker" + article));
                    WebElement titleElement = article.findElement(By.cssSelector("h2.c_t a"));
                    String title = titleElement.getText();
                    String summary = article.findElement(By.cssSelector("p.c_d")).getText();

                    List<WebElement> imgElements = article.findElements(By.cssSelector("img"));
                    if (!imgElements.isEmpty()) {
                        imgURLs.add(imgElements.get(0).getAttribute("src"));
                    }

                    String translatedText = GoogleTranslateUtil.translateText(title);
                    articleList.add(new Article(title, summary, translatedText));
                    logger.info("[Thread: {}] Parsed article no: {} with title: {}", Thread.currentThread().getName(), count + 1, title);
                    count++;

                } catch (Exception e) {
                    logger.warn("[Thread: {}] Error parsing an article. Skipping...", Thread.currentThread().getName(), e);
                }
            }


            //Download images
            ImageDownloadUtil imgd = new ImageDownloadUtil();
            imgd.downloadImage(imgURLs);

            for (Article article : articleList) {
                System.out.println(article.toString());
                logger.info("[Thread: {}] {}", Thread.currentThread().getName(), article.toString());
            }

            HeaderAnalyzationUtil hau = new HeaderAnalyzationUtil();
            hau.AnalyzeHeader(articleList);

        } catch (Exception e) {
            logger.error("[Thread: {}] Error during article scraping", Thread.currentThread().getName(), e);
        } finally {
            driver.quit();
        }
    }
}
