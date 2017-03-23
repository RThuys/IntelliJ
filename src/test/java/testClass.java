import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

/**
 * Created by Rolan on 16-Mar-17.
 */
public class testClass {

    @Test
    public void firstTest(){
        WebDriver driver = new HtmlUnitDriver();

        driver.get("https://www.google.com");
        String titel = driver.getTitle();

        System.out.println(titel);

        driver.quit();
    }
}
