import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.Assert;

public class SeleniumBaiduExample {
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "/Users/a140/Desktop/chromedriver");

        WebDriver driver = new ChromeDriver();
        driver.navigate().to("http://www.baidu.com");
        WebElement searchInput = driver.findElement(By.name("wd"));
        searchInput.sendKeys("极客时间");
        searchInput.submit();
        Thread.sleep(3000);
        Assert.assertEquals("极客时间_百度搜索", driver.getTitle());
        driver.quit();
    }
}
