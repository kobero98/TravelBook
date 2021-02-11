package test.selenium;
import static org.junit.Assert.*;


import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
/*
 * @author Sara Da Canal
 */

public class TestProfilePage {
	@Test
	public void testCorrectUser() {
		if(System.getProperty("os.name").startsWith("Windows")){
            System.setProperty("webdriver.chrome.driver", "Driver/chromedriver.exe");
        }
        if(System.getProperty("os.name").startsWith("Mac OS X")) {
            System.setProperty("webdriver.chrome.driver", "Driver/chromedriver");
        }
		WebDriver driver = new ChromeDriver();
		driver.get("http://localhost:8080/TravelbookISPW/login.jsp");
		driver.findElement(By.xpath("//*[@id=\"username\"]")).sendKeys("Alalea");
		driver.findElement(By.xpath("//*[@id=\"pswd\"]")).sendKeys("1234");
		driver.findElement(By.xpath("//*[@id=\"loginTable\"]/div/input[2]")).click();
		driver.findElement(By.xpath("/html/body/div[2]/div[1]/div[1]/button[1]")).click();
		String user = driver.findElement(By.xpath("/html/body/div[2]/div[1]/div[1]/div[2]/p[1]")).getText();
		String descr = driver.findElement(By.xpath("/html/body/div[2]/div[1]/div[1]/div[2]/p[2]")).getText();
		driver.close();
		boolean right = true;
		if(!user.contentEquals("Sara"))
			right = false;
		if(!descr.contentEquals("Description"))
			right = false;
		assertEquals(right,true);
		
	}
}
