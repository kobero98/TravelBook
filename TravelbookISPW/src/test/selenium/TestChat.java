package test.selenium;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/*
 * @author Matteo Federico aka Kobero
 */
public class TestChat {

	@Test
	public void testAddChatUser() throws InterruptedException {
		//testing that when searching a user in chat, the selected user is correctly add to the contact list
		if(System.getProperty("os.name").startsWith("Windows")){
			System.setProperty("webdriver.chrome.driver", "Driver/chromedriver.exe");
		}
		if(System.getProperty("os.name").startsWith("Mac OS")) {
			System.setProperty("webdriver.chrome.driver", "Driver/chromedriver");
		}
		WebDriver driver = new ChromeDriver();
		driver.get("http://localhost:8080/TravelbookISPW/login.jsp");
		driver.findElement(By.xpath("//*[@id=\"Username\"]")).sendKeys("admin");
		driver.findElement(By.xpath("//*[@id=\"pswd\"]")).sendKeys("admin");
		driver.findElement(By.xpath("//*[@id=\"loginTable\"]/div/input[2]")).click();
		driver.findElement(By.xpath("/html/body/div[2]/div[1]/div[1]/button[4]")).click();
		String [] alfabeto={"a","b","c","d","e","f","g","h","i","l","m","n","o","p","q","r","r","s","t","u","v","z"};
		Boolean flag=false;
		for(String s:alfabeto) 
		{
			driver.findElement(By.xpath("/html/body/div[2]/div[1]/form/input")).clear();
			driver.findElement(By.xpath("/html/body/div[2]/div[1]/form/input")).sendKeys(s);
			Thread.sleep(1000);
			if(!driver.findElement(By.xpath("/html/body/ul")).findElements(By.xpath("//li")).isEmpty()){
				
				String name=driver.findElement(By.xpath("/html/body/ul/li[1]/div")).getText();
				driver.findElement(By.xpath("/html/body/ul/li[1]/div")).click();
				driver.findElement(By.xpath("/html/body/div[2]/div[1]/form/button")).click();
				List<WebElement> childs =driver.findElement(By.xpath("/html/body/div[2]/div[1]/div[2]")).findElements(By.xpath(".//form"));

				for(WebElement c:childs)
				{
					
					String testo=c.findElement(By.xpath("./p")).getText();
					if(testo.equals(name)) {flag=true;}

				}
			}
			if(Boolean.TRUE.equals(flag)) break;	
			
		}
		assertEquals(true,flag);
		driver.quit();
		
	}
}
