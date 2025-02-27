package Dice.TestBase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.math3.stat.inference.TestUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.log4testng.Logger;

import Dice.Utilities.SeleniumActions;
import Dice.Utilities.WebDriverListener;




public class TestBase {

	protected static WebDriver driver;
	protected static Properties properties;
	protected static SeleniumActions sele_Actions;
	protected static WebDriverListener eventListener;
	protected static EventFiringWebDriver e_driver;
	protected static ChromeOptions chromeOptions;
	protected static Logger log;

	/*
	 * protected ITestResult result; protected ExtentReports extent; protected
	 * ExtentTest extentTest;
	 */
	public TestBase() {

		try {
			properties = new Properties();
			FileInputStream ip = new FileInputStream(
					System.getProperty("user.dir") + "/src/main/java/com/crm/qa/config/config.properties");
			properties.load(ip);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("file not found");
		} catch (IOException e) {
			System.out.println("io exception");

		}

	}

	protected static void initializaton() {
		String browserName = properties.getProperty("browser");
		driver = getDriver(browserName);
		log.info(browserName + " is configured");

		e_driver = new EventFiringWebDriver(driver);
		eventListener = new WebDriverListener();
		e_driver.register(eventListener);
		driver = e_driver;

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(100, TimeUnit.SECONDS);

		driver.get(properties.getProperty("url"));

		sele_Actions = new SeleniumActions();

	}

	private static WebDriver getDriver(String browserName) {
		if (browserName.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver",
					/* TestUtils.WORKSAPCE_PATH +*/  "//drivers//chromedriver.exe");
			chromeOptions = new ChromeOptions();
			chromeOptions.addArguments("--start-maximized");
			return new ChromeDriver(chromeOptions);
		} else if (browserName.equalsIgnoreCase("FF")) {
			System.setProperty("webdriver.gecko.driver", "C://Users/Gorya/Desktop/Krishna_study/geckodriver.exe");
			return new FirefoxDriver();
		} else if (browserName.equalsIgnoreCase("IE")) {
			System.setProperty("webdriver.ie.driver", /* TestUtils.WORKSAPCE_PATH + */ "//drivers//IEDriverServer.exe");
			return new InternetExplorerDriver();
		}
		return null;
	}

	public void tearDownMain() {
		driver.manage().deleteAllCookies();
		driver.close();
	}

	/*
	 * public void setExtend() { extent = new
	 * ExtentReports(System.getProperty("user.dir") +
	 * "//test-output//+NewExtentReport.html", true); Map<String, String> info = new
	 * HashMap<String, String>(); info.put("host name", "Krishna Windows");
	 * info.put("user name", "Krishna"); info.put("Environment", "QA");
	 * 
	 * extent.addSystemInfo(info);
	 * 
	 * }
	 * 
	 * public void FormatResult() { if (result.getStatus() == ITestResult.FAILURE) {
	 * extentTest.log(LogStatus.FAIL, "Failed test case is ::" + result.getName());
	 * extentTest.log(LogStatus.FAIL, "Failed test case is ::" +
	 * result.getThrowable()); TestUtils.takeScreenShot(driver);
	 * extentTest.log(LogStatus.FAIL,
	 * extentTest.addScreenCapture(TestUtils.SCREENSHOT_PATH)); } else if
	 * (result.getStatus() == ITestResult.SKIP) { extentTest.log(LogStatus.SKIP,
	 * "Skipped test case is ::" + result.getName()); } else if (result.getStatus()
	 * == ITestResult.SUCCESS) { extentTest.log(LogStatus.PASS,
	 * "Passed testc case is ::" + result.getName()); }
	 * 
	 * extent.endTest(extentTest);
	 * 
	 */}


	
	
