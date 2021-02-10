package test.selenium;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/*
 * @author Matteo Federico
 */
public class TestChat {

	@Test
	public void testAddChatUser() throws InterruptedException {
	
		System.setProperty("webdriver.chrome.driver", "Driver/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("http://localhost:8080/TravelbookISPW/login.jsp");
		driver.findElement(By.xpath("//*[@id=\"username\"]")).sendKeys("admin");
		driver.findElement(By.xpath("//*[@id=\"pswd\"]")).sendKeys("admin");
		driver.findElement(By.xpath("//*[@id=\"loginTable\"]/div/input[2]")).click();
		driver.findElement(By.xpath("/html/body/div[2]/div[1]/div[1]/form/input[4]")).click();
		String [] alfabeto={"a","b","c","d","e","f","g","h","i","l","m","n","o","p","q","r","r","s","t","u","v","z"};
		Boolean flag=false;
		for(String s:alfabeto) 
		{
			driver.findElement(By.xpath("/html/body/div[2]/div[1]/form[2]/input[1]")).clear();
			driver.findElement(By.xpath("/html/body/div[2]/div[1]/form[2]/input[1]")).sendKeys(s);
			Thread.sleep(1000);
			if(!driver.findElement(By.xpath("/html/body/ul")).findElements(By.xpath("//li")).isEmpty()){
				
				String name=driver.findElement(By.xpath("/html/body/ul/li[1]/div")).getText();
				driver.findElement(By.xpath("/html/body/ul/li[1]/div")).click();
				driver.findElement(By.xpath("/html/body/div[2]/div[1]/form[2]/input[2]")).click();
				List<WebElement> childs =driver.findElement(By.xpath("/html/body/div[2]/div[1]/div")).findElements(By.xpath(".//form"));
				
				for(WebElement c:childs)
				{
					String testo=c.getText();
					if(testo.equals(name)) {flag=true;}
				}
			}
			if(Boolean.TRUE.equals(flag)) break;	
			
		}
		assertEquals(true,flag);
		driver.quit();
		
	}
}
