package test.selenium;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
/*
 * @author Matteo Ciccaglione
 */
public class TestAddDays {
	@Test
	public void testAddDaysNumber() {
		System.setProperty("webdriver.chrome.driver", "Driver/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("http://localhost:8080/TravelbookISPW/login.jsp");
		driver.findElement(By.xpath("//*[@id=\"username\"]")).sendKeys("matt");
		driver.findElement(By.xpath("//*[@id=\"pswd\"]")).sendKeys("computer");
		driver.findElement(By.xpath("//*[@id=\"loginTable\"]/div/input[2]")).click();
		driver.findElement(By.xpath("/html/body/div[2]/div[1]/div[1]/form/input[2]")).click();
		driver.findElement(By.xpath("//*[@id=\"s-date\"]")).sendKeys("01/02/2021");
		driver.findElement(By.xpath("//*[@id=\"e-date\"]")).sendKeys("03/02/2021");
		Select elem=new Select(driver.findElement(By.xpath("//*[@id=\"days\"]")));
		List<WebElement> options=elem.getOptions();
		driver.close();
		assertEquals(3,options.size());
	}
}
