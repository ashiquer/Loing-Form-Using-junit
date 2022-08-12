import io.ous.jtoml.ParseException;
import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class practiceForm {
    WebDriver driver;
    @Before
    public void setup(){
        System.setProperty("webdriver.chrome.driver","./src/test/resources/chromedriver.exe");
        ChromeOptions options=new ChromeOptions();
        options.addArguments("--headed");
        driver =new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }


    @Test
    public void fromFillUp() throws InterruptedException, IOException, ParseException {
        driver.get("https://demoqa.com/automation-practice-form");

        WebElement txtFirstName= driver.findElement(By.id("firstName"));
        txtFirstName.sendKeys("ashik");

        WebElement txtLastName  = driver.findElement(By.id("lastName"));
        txtLastName.sendKeys("Rahman");

        WebElement txtEmail=driver.findElement(By.xpath("//input[@id='userEmail']"));
        txtEmail.sendKeys("ashik@test.com");

        WebElement radiogendermale =driver.findElement(By.xpath("//*[@id=\"genterWrapper\"]/div[2]/div[1]/label"));
        radiogendermale.click();

        WebElement txtMobileNumber=driver.findElement(By.xpath("//input[@id='userNumber']"));
        txtMobileNumber.sendKeys("0168915044");

        WebElement calendarInput= driver.findElement(By.id("dateOfBirthInput"));
        calendarInput.sendKeys(Keys.CONTROL+"a");
        Thread.sleep(1000);
        calendarInput.sendKeys("11/13/1993");
        Thread.sleep(1000);
        calendarInput.sendKeys(Keys.ENTER);

        WebElement subject = driver.findElement(By.id("subjectsInput"));
        subject.sendKeys("English,CSE,Bangla");

        WebElement sportsCheckBox= driver.findElement(By.id("hobbies-checkbox-1"));
        Actions action =new Actions(driver);
        if(!sportsCheckBox.isSelected()) {
            action.moveToElement(sportsCheckBox).click().perform();
        }
        WebElement readingCheckBox=driver.findElement(By.id("hobbies-checkbox-2"));
        Thread.sleep(1000);
        if(sportsCheckBox.isEnabled()){
            action.moveToElement(readingCheckBox).click().perform();
        }

        driver.findElement(By.id("uploadPicture")).sendKeys("G:\\a.jpg");

        driver.findElement(By.id("currentAddress")).sendKeys("Mirpur, Dhaka 1216");

        driver.findElement(By.id("react-select-3-input")).sendKeys("NCR"+Keys.ARROW_DOWN+Keys.ENTER);
        driver.findElement(By.id("react-select-4-input")).sendKeys("Delhi"+Keys.ARROW_DOWN+Keys.ENTER);


        WebElement submitButton= driver.findElement(By.cssSelector("[type=submit]"));
        Thread.sleep(1000);
        action.moveToElement(submitButton).sendKeys(Keys.ENTER).perform();


        JSONObject studentObject=new JSONObject();
        WebElement stuTbleBody=driver.findElement(By.tagName("tbody"));
        List<WebElement> tRows=stuTbleBody.findElements(By.tagName("tr"));
        for(WebElement row: tRows)
        {
            List<WebElement> tData=row.findElements(By.tagName("td"));
            studentObject.put(tData.get(0).getText(),tData.get(1).getText());
        }
        FileWriter file = new FileWriter("./src/test/resources/students.json");
        file.write(studentObject.toJSONString());
        file.flush();







    }

    @After
    public void closeWindow()
    {
        driver.quit();
    }


}
