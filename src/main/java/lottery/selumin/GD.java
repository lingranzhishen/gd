package lottery.selumin;

import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.eclipse.jetty.util.log.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 *
 */
public class GD {

	public static final String lottoryType = "r_cqss";
	public static final String domain = "https://s.gdbet999.com/Account/Login";
	public static final String index = "https://s.gdbet999.com";

	public static int count = 10;
	public static File log = new File(Constant.LOG_PATH + LocalDate.now().toString() + "后一");
	static {
		if (!log.exists()) {
			try {
				log.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 会员名称：lingran120
	 * 预设密码：h523588
	 * 会员类型：会员
	 * 会员奖金：1850
	 * 当前线路：https://s.gdbet999.com
	 * @param args
	 */
	public static void main(String[] args) {

		System.setProperty("webdriver.chrome.driver", Constant.DERIVER_PATH);
		WebDriver driver = new ChromeDriver();
		// 登录
		while (!login(driver)) {
			;
		}
		refreshWait(driver);
		while (true) {
			for(GDLotteryType gd:GDLotteryType.values()){
				refreshWait(driver);
				uploadGdRecordType(driver,gd);
			}
		}

		// driver.quit();
	}

	private static void uploadGdRecordType(WebDriver driver,GDLotteryType gd) {
		try{
			driver.navigate().to(gd.getUrl());
			waitSec(1000);
			WebElement historyBoxTable = driver.findElement(By.id("historyBoxTable"));
			WebElement historyBoxTableBody  =driver.findElement(By.xpath("//*[@id=\"historyBoxTable\"]/tbody"));
			 List<WebElement> webEelements=	historyBoxTableBody.findElements(By.cssSelector("tr"));
			for(WebElement tr:webEelements){
				 List<WebElement> tds=	tr.findElements(By.cssSelector("td"));
				 if(CollectionUtils.isEmpty(tds)){
					 continue;
				 }
				 WebElement lno=tds.get(0);
				 String lotteryNo=lno.getText();
				 String digits=getDigits(tds.get(1));
				 Map<String,String> map=new HashMap();
				 map.put("lotteryNo", lotteryNo);
				 map.put("digits", digits);
				 if(digits.contains("-1")){
				 	continue;
				 }
				 map.put("type", gd.getType()+"");
				 System.out.println(JSON.toJSONString(map));
				System.out.println(HttpUtil.doGet("http://115.28.155.40:9200/insertLottery.json",map));
			}
		}catch(Exception e){
			
		}
	}

	private static String getDigits(WebElement webElement) {
		 List<WebElement> spans=	webElement.findElements(By.cssSelector("span"));
		 String result="";
		 for(WebElement span:spans){
			 result+=span.getText();
		 }
		 return result;
	}

	private static boolean login(WebDriver driver) {
		driver.get(domain);
		/*// 通过 id 找到 input 的 DOM
		WebElement userName = driver.findElement(By.id("LoginID"));
		WebElement password = driver.findElement(By.id("Password"));
		WebElement verifyCode = driver.findElement(By.id("CaptchaInputText"));
		WebElement submit = driver.findElement(By.xpath("//*[@id=\"form\"]/button"));

		// 输入关键字
		userName.sendKeys("lingran");
		password.sendKeys("h523588");
		String code = readVerifyCode();
		verifyCode.sendKeys(code);
		// 提交 input 所在的 form
		submit.click();*/
		String code = readVerifyCode();
		waitSec(1000);
		return true;
//		return doesWebElementExist(driver, By.className("logout"));

	}

	
	public static void waitSec(int seconds){
		try {
			Thread.sleep(seconds);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static boolean doesWebElementExist(WebDriver driver, By selector) {

		try {
			driver.findElement(selector);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	

	public static void refreshWait(WebDriver driver) {
		boolean isEnd = false;
		while (!isEnd) {
			try {
				driver.navigate().to(index);
				Thread.sleep(5000);
				driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
				if (LocalDateTime.now().getHour() >= 10 || LocalDateTime.now().getHour() <= 2) {
					isEnd = true;
				}
			} catch (Exception e) {
				isEnd = false;
				Log.info(e.getMessage());
			}
		}
	}

	/**
	 * 输入验证码
	 */
	public static String readVerifyCode() {
		System.out.println("请输入验证码:");
		Scanner s = new Scanner(System.in);
		return s.nextLine();
	}
}
