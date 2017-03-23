import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Created by Rolan on 22-Mar-17.
 */
public class driverClass {

    private static String driver;
    private static String webPropertiesDriver;
    private static WebDriver webDriver;

    public static void getPropertiesDriver(String browser) {
        if (browser.equalsIgnoreCase("firefox")) {
            driver = "geckodriver.exe";
            webPropertiesDriver = "gecko";
            webDriver = new FirefoxDriver();
        }
        if (browser.equalsIgnoreCase("Chrome")) {
            driver = "chromedriver.exe";
            webPropertiesDriver = "chrome";
            webDriver = new ChromeDriver();
        } else {
            driver = "invalid class but why";
        }

    }

    public static WebDriver setDriver() {
        return webDriver;
    }

    public static void setProperties(String browser) {
        getPropertiesDriver(browser);
        try {
            System.setProperty("webdriver." + webPropertiesDriver + ".driver", driver);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

}
