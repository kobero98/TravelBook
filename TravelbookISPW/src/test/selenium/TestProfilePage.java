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
		//testing that after logging in, the profile page displays the right information
		if(System.getProperty("os.name").startsWith("Windows")){
            System.setProperty("webdriver.chrome.driver", "Driver/chromedriver.exe");
        }
        if(System.getProperty("os.name").startsWith("Mac OS X")) {
            System.setProperty("webdriver.chrome.driver", "Driver/chromedriver");
        }
		WebDriver driver2 = new ChromeDriver();
		driver2.get("http://localhost:8080/TravelbookISPW/login.jsp");
		driver2.findElement(By.xpath("//*[@id=\"Username\"]")).sendKeys("admin");
		driver2.findElement(By.xpath("//*[@id=\"pswd\"]")).sendKeys("admin");
		driver2.findElement(By.xpath("//*[@id=\"loginTable\"]/div/input[2]")).click();
		driver2.findElement(By.xpath("/html/body/div[2]/div[1]/div[1]/button[1]")).click();
		String user = driver2.findElement(By.xpath("/html/body/div[2]/div[1]/div[1]/div[2]/p[1]")).getText();
		String descr = driver2.findElement(By.xpath("//*[@id=\"descText\"]")).getText();
		driver2.close();
		boolean right = true;
		if(!user.contentEquals("Admin Admin"))
			right = false;
		if(!descr.contentEquals("Description"))
			right = false;
		assertEquals(right,true);
		
	}
}
