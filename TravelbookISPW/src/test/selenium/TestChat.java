package test.selenium;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

/*
 * @author Matteo Federico
 */
public class TestChat {

	@Test
	public void testAddChatUser() {
		System.setProperty("webdriver.chrome.driver", "Driver/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("http://localhost:8080/TravelbookISPW/login.jsp");
		driver.findElement(By.xpath("//*[@id=\"username\"]")).sendKeys("admin");
		driver.findElement(By.xpath("//*[@id=\"pswd\"]")).sendKeys("admin");
		driver.findElement(By.xpath("//*[@id=\"loginTable\"]/div/input[2]")).click();
		driver.findElement(By.xpath("/html/body/div[2]/div[1]/div[1]/form/input[4]")).click();
		driver.findElement(By.xpath("/html/body/div[2]/div[1]/form[2]/input[1]")).sendKeys("D");
		driver.findElement(By.xpath("/html/body/ul/li[1]/div")).click();
		String name=driver.findElement(By.xpath("/html/body/div[2]/div[1]/form[2]/input[1]")).getText();
		driver.findElement(By.xpath("/html/body/div[2]/div[1]/form[2]/input[2]")).click();
		Select element=new Select(driver.findElement(By.xpath("/html/body/div[2]/div[1]/div")));
		System.out.println(element.toString());
		driver.close();
		assertEquals("ciao",name);
	}
}
