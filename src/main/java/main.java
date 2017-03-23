import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Rolan on 16-Mar-17.
 */
public class main {

    public static void main(String[] args) throws InterruptedException {

        //Which browser
        //Chrome or Firefox
        final String browser = "chrome";

        //Inlog gmail
        //email
        String email = "";
        //password
        String pswd = "";

        //Sending emails
        boolean sending = true;
        //Amount emails
        int amountEmails = 2;
        //Sending to
        String receiverEmail = "";
        String mailSubject = "Selenium test";
        String mailContent = "Dit is een verzonden mail test";

        //cluster
        boolean emailControleren = false;
        //Options
        boolean mailVerwijderen = true;
        boolean bijlageGevonden = false;
        boolean zoekenOpTekst = false;
        if (mailVerwijderen == true || bijlageGevonden == true || zoekenOpTekst == true) {
            emailControleren = true;
        }

        //zoekterm
        Matcher match;
        String onderwerp = "Selenium test";
        String zoekTerm = "is";
        Pattern pattern = Pattern.compile(zoekTerm + ".[a-zA-Z0-9]*");
        Pattern patternSubString = Pattern.compile("[a-zA-Z0-9]+$");


        //counters
        int counterFound = 0;
        int counterContentNotFound = 0;
        int counterNotFoundEmail = 0;
        int counterRemovedEmail = 0;


        driverClass.setProperties(browser);
        WebDriver driver = driverClass.setDriver();
        String xpathTekst = null;



        driver.get("https://mail.google.com");

        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        String pageTitel = driver.getTitle();
        System.out.println(pageTitel + "\n");
        System.out.println("TEST:\nEmail Controle: " + emailControleren + "\nBijlage: " + bijlageGevonden + "\nZoeken op Tekst: " + zoekenOpTekst + "\nMail sending: " + sending + "\n");
        driver.findElement(By.xpath("//*[@id='Email']")).sendKeys(email);
        driver.findElement(By.xpath("//*[@id='next']")).click();
        waitClass.waithere(driver, "//*[@id='Passwd']");
        driver.findElement(By.xpath("//*[@id='Passwd']")).sendKeys(pswd);
        driver.findElement(By.xpath("//*[@id='signIn']")).click();
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);

        //-------------------------------------------------------------------------------------
        if (sending == true) {
            for (int i = 0; i < amountEmails; i++) {
//                driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
//                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='T-I J-J5-Ji T-I-KE L3']")));

                waitClass.waithere(driver, "//*[@class='T-I J-J5-Ji T-I-KE L3']");
                driver.findElement(By.xpath("//*[@class='T-I J-J5-Ji T-I-KE L3']")).click();
//                driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
                try {
                    //invoer van elementen
                    Thread.sleep(2000);
//                    waitClass.waithere(driver, "//*[@class='T-I J-J5-Ji T-I-KE L3']");
                    driver.findElement(By.name("to")).sendKeys(receiverEmail);
                    driver.findElement(By.name("subjectbox")).sendKeys(mailSubject);
                    driver.findElement(By.xpath("//*[@class='GQ']/div/div[1]/div[2]/div[1]/div/table/tbody/tr/td[2]/div[2]/div")).sendKeys(mailContent);
                    driver.findElement(By.xpath("//*[@class='T-I J-J5-Ji aoO T-I-atl L3']")).click();
                    Thread.sleep(500);
                } catch (Exception ex) {
                    System.out.println(ex);
                }

            }
            System.out.println(amountEmails);
            if (amountEmails != 0) {
                Thread.sleep(500);
                driver.navigate().refresh();
            }
        }
        //-------------------------------------------------------------------------------------

        if (emailControleren == true) {
            String zenderSearch = "/td[5]/div[2]/span";
            //controleren op Zender
            //"/td[5]/div[2]/span"));
            //yW = alle mails geleze of ongelezen

            String onderwerpSearch = "/td[6]/div/div/div/span[1]";
            //controleren op onderwerp
            //"/td[6]/div/div/div/span[1]"));
            //Controleert op ongeopende emails
            //Controle op een bepaalde zin > keert terug.
            //zA yO = geleze mails controleren
            //zA zE = ongeleze mails controleren

//            Thread.sleep(5000);

            waitClass.waithere(driver, "//*[@class='gb_0a gb_5b']");
            List<WebElement> element = driver.findElements(By.xpath("//*[@class='zA zE']" + onderwerpSearch));
            System.out.println(element.size());
            List<String> passwoorden = new ArrayList<String>();
            if (element.size() != 0) {
                System.out.println("Aantal emails: " + element.size());
                for (int i = 0; i < element.size(); i++) {
                    System.out.println("Mail " + (i + 1) + "\n-----------------------------------------------");
                    if (element.get(i).getText().equalsIgnoreCase(onderwerp)) {
                        System.out.println("\"" + onderwerp + "\"" + " gevonden\n");
                        int j = 0;
                        counterFound++;
                        element.get(i).click();
                        driver.manage().timeouts().pageLoadTimeout(500, TimeUnit.SECONDS);
                        if (zoekenOpTekst == true) {
                            if (driver.findElement(By.xpath("//*[@class='gs']/div[7]")).getText().contains(zoekTerm)) {
                                //Afprinten van de tekst van de mail van google
                                System.out.println(driver.findElement(By.xpath("//*[@class='gs']/div[7]")).getText());
                                String tekst = driver.findElement(By.xpath("//*[@class='gs']/div[7]")).getText();
                                System.out.println("\nTekst found");
                                match = pattern.matcher(tekst);
                                if (match.find()) {
                                    String matching2 = match.group();
                                    match = patternSubString.matcher(matching2);
                                    if (match.find()) {
                                        String matching = match.group();
                                        System.out.println("Password: " + matching + "\n");
                                        passwoorden.add(j, matching);
                                        j++;
                                    }
                                }
                            } else {
                                System.out.println("\nTekst not found\n");
                                counterContentNotFound++;
                            }
                        }
                        if (bijlageGevonden == true) {
                            //bijlage controleren
                            try {
                                waitClass.waithere(driver,"//*[@class='aSK J-J5-Ji aYr']");
                                driver.findElement(By.xpath("//*[@class='aSK J-J5-Ji aYr']")).click();
                                System.out.println("Bijlage gevonden\n");
                            } catch (Exception ex) {
                                System.out.println("Geen bijlage gevonden\n");
                            }
                        }
                        if (mailVerwijderen == true) {
                            waitClass.waithere(driver, "html/body/div[7]/div[3]/div/div[2]/div[1]/div[2]/div/div/div/div/div[1]/div[2]/div[1]/div/div[2]/div[3]/div/div");
                            driver.findElement(By.xpath("html/body/div[7]/div[3]/div/div[2]/div[1]/div[2]/div/div/div/div/div[1]/div[2]/div[1]/div/div[2]/div[3]/div/div")).click();
                            counterRemovedEmail++;
                            Thread.sleep(700);
                        } else {
                            //--------------
//                            driver.manage().timeouts().pageLoadTimeout(700, TimeUnit.SECONDS);
                            Thread.sleep(500);
                            driver.navigate().back();
                            Thread.sleep(500);
//                            driver.manage().timeouts().pageLoadTimeout(700, TimeUnit.SECONDS);
                        }
                    } else {
                        System.out.println("Geen \"" + onderwerp + "\" gevonden\nOnderwerp mail: " + element.get(i).getText() + "\n");
                        counterNotFoundEmail++;
                    }
                    driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
                }
            } else {
                System.out.println("Geen nieuwe emails gevonden");
            }

            if (counterFound != 0) {
                System.out.println("\nAantal emails gevonden die overeenkomen: " + counterFound);
            }
            if (counterContentNotFound != 0) {
                System.out.println("Aantal emails met onderwerp gevonden, inhoud komt niet overeen: " + counterContentNotFound);
            }
            if (counterNotFoundEmail != 0) {
                System.out.println("Aantal emails ongelezen (niet het correcte onderwerp): " + counterNotFoundEmail);
            }
            if (counterRemovedEmail != 0) {
                System.out.println("Aantal verwijderde emails: " + counterRemovedEmail);
            }

            int p = 1;
            //terugkrijgen van de passwoorden
            if (passwoorden != null) {
                for (String psw : passwoorden
                        ) {
                    System.out.println("Passwoord " + p + ": " + psw);
                    p++;
                }
            }
        }
        //-------------------------------------------------------------------------------------
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.quit();
    }

}
