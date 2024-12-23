package com.sahilkamat.services;
import com.sahilkamat.config.BrowserCapabilities;
import com.sahilkamat.config.BrowserStackConfig;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class BrowserStackParallelExecutor {

    public static void main(String[] args) throws Exception {
        String hubUrl = BrowserStackConfig.HUB_URL;

        // Define browser configurations
        DesiredCapabilities[] capabilities = new DesiredCapabilities[]{
                BrowserCapabilities.getDesktopCapabilities("Windows", "10", "Chrome", "latest"),
                BrowserCapabilities.getDesktopCapabilities("Windows", "10", "Firefox", "latest"),
                BrowserCapabilities.getDesktopCapabilities("Windows", "10", "Chrome", "latest"),
                BrowserCapabilities.getMobileCapabilities("Samsung Galaxy S22", "13"),
                BrowserCapabilities.getMobileCapabilities("Samsung Galaxy S22", "13")
        };


        ExecutorService executorService = Executors.newFixedThreadPool(5);

        for (DesiredCapabilities cap : capabilities) {
            executorService.submit(() -> {
                WebDriver driver = null;
                try {
                    driver = new RemoteWebDriver(new URL(hubUrl), cap);

                    ArticleScrapperService scrapperService = new ArticleScrapperService(driver);
                    scrapperService.fetchArticles();

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (driver != null) {
                        driver.quit();
                    }
                }
            });
        }

        executorService.shutdown();  // This will prevent new tasks from being submitted
        if (!executorService.awaitTermination(120, TimeUnit.SECONDS)) {  // Adjust timeout as needed
            System.err.println("Executor did not terminate in the specified time.");
            List<Runnable> droppedTasks = executorService.shutdownNow();  // Shut down remaining tasks
            System.err.println("Dropped tasks: " + droppedTasks.size());
        }

    }
}
