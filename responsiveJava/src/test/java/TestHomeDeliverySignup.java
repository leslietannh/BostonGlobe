package test.java;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.client.ReportiumClientFactory;
import com.perfecto.reportium.exception.ReportiumException;
import com.perfecto.reportium.model.CustomField;
import com.perfecto.reportium.model.PerfectoExecutionContext;
import com.perfecto.reportium.model.Project;
import com.perfecto.reportium.test.TestContext;
import com.perfecto.reportium.test.result.TestResultFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;



import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TestHomeDeliverySignup {

	public RemoteWebDriver driver;
	public boolean device;
	public boolean fast;
	ReportiumClient reportiumClient;
	String OS;
	int retry = 1; //number of times to retry
	int retryInterval = 5000; //retry in MS
	Exception ex = null;

	public RemoteWebDriver createDriver(String targetEnvironment) throws MalformedURLException {
		DesiredCapabilities capabilities = new DesiredCapabilities();

		//capabilities.setCapability("openDeviceTimeout", 5);

		switch (targetEnvironment) {
				case "iPhone11":
			capabilities.setCapability("platformName", "iOS");
			capabilities.setCapability("platformVersion", "13.0");
			capabilities.setCapability("location", "NA-US-BOS");
			capabilities.setCapability("resolution", "828x1792");
			capabilities.setCapability("manufacturer", "Apple");
			capabilities.setCapability("model", "iPhone-11");
			capabilities.setCapability("deviceId", "00008030-001258DE2E10802E");
		break;
		
			case "Smasung Galaxy Note10":
			capabilities.setCapability("platformName", "Android");
			capabilities.setCapability("platformVersion", "9");
			capabilities.setCapability("location", "NA-US-BOS");
			capabilities.setCapability("resolution", "1080x2280");
			capabilities.setCapability("manufacturer", "Samsung");
			capabilities.setCapability("model", "Galaxy Note10");
			capabilities.setCapability("deviceId", "R58M77SEH3E");
		break;
		
		case "Win10 Chrome Latest":
			device = false;
			fast = true;
			capabilities.setCapability("platformName", "Windows");
			capabilities.setCapability("platformVersion", "10");
			capabilities.setCapability("browserName", "Chrome");
			capabilities.setCapability("browserVersion", "80");
			capabilities.setCapability("resolution", "1280x1024");
			capabilities.setCapability("location", "US East");
			break;

		case "Win10 Chrome Latest-1":
			device = false;
			fast = true;
			capabilities.setCapability("platformName", "Windows");
			capabilities.setCapability("platformVersion", "10");
			capabilities.setCapability("browserName", "Chrome");
			capabilities.setCapability("browserVersion", "latest-1");
			capabilities.setCapability("resolution", "1280x1024");
			capabilities.setCapability("location", "US East");
			break;
			
		case "Win10 Chrome Latest-2":
			device = false;
			fast = true;
			capabilities.setCapability("platformName", "Windows");
			capabilities.setCapability("platformVersion", "10");
			capabilities.setCapability("browserName", "Chrome");
			capabilities.setCapability("browserVersion", "latest-2");
			capabilities.setCapability("resolution", "1280x1024");
			capabilities.setCapability("location", "US East");
			break;
			
		case "Win10 Chrome Latest-3":
			device = false;
			fast = true;
			capabilities.setCapability("platformName", "Windows");
			capabilities.setCapability("platformVersion", "10");
			capabilities.setCapability("browserName", "Chrome");
			capabilities.setCapability("browserVersion", "76");
			capabilities.setCapability("resolution", "1280x1024");
			capabilities.setCapability("location", "US East");
			break;
			
		case "Win8.1 Chrome Latest":
			device = false;
			fast = true;
			capabilities.setCapability("platformName", "Windows");
			capabilities.setCapability("platformVersion", "8.1");
			capabilities.setCapability("browserName", "Chrome");
			capabilities.setCapability("browserVersion", "latest");
			capabilities.setCapability("resolution", "1280x1024");
			capabilities.setCapability("location", "US East");
			break;

		case "Win8.1 Chrome Latest-1":
			device = false;
			fast = true;
			capabilities.setCapability("platformName", "Windows");
			capabilities.setCapability("platformVersion", "8.1");
			capabilities.setCapability("browserName", "Chrome");
			capabilities.setCapability("browserVersion", "latest-1");
			capabilities.setCapability("resolution", "1280x1024");
			capabilities.setCapability("location", "US East");
			break;
			
		case "Win8.1 Chrome Latest-2":
			device = false;
			fast = true;
			capabilities.setCapability("platformName", "Windows");
			capabilities.setCapability("platformVersion", "8.1");
			capabilities.setCapability("browserName", "Chrome");
			capabilities.setCapability("browserVersion", "latest-2");
			capabilities.setCapability("resolution", "1280x1024");
			capabilities.setCapability("location", "US East");
			break;
			
		case "Win8.1 Chrome Latest-3":
			device = false;
			fast = true;
			capabilities.setCapability("platformName", "Windows");
			capabilities.setCapability("platformVersion", "8.1");
			capabilities.setCapability("browserName", "Chrome");
			capabilities.setCapability("browserVersion", "76");
			capabilities.setCapability("resolution", "1280x1024");
			capabilities.setCapability("location", "US East");
			break;
		}

		// capabilities.setCapability("user", System.getProperty("PerfectoUsername"));
		// capabilities.setCapability("password", System.getProperty("PerfectoPassword"));		
		// Note: Using the system.env this is pulling the token from a local environment variable on the executing system
		capabilities.setCapability("securityToken","eyJhbGciOiJIUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICI4YmI4YmZmZS1kMzBjLTQ2MjctYmMxMS0zNTYyMmY1ZDkyMGYifQ.eyJqdGkiOiJhNDBmM2I5YS1iY2Y5LTQwOTAtYTE4ZC1hNzU4YjEwZDk4MGIiLCJleHAiOjAsIm5iZiI6MCwiaWF0IjoxNTg0NDcyNTE3LCJpc3MiOiJodHRwczovL2F1dGgucGVyZmVjdG9tb2JpbGUuY29tL2F1dGgvcmVhbG1zL2RlbW8tcGVyZmVjdG9tb2JpbGUtY29tIiwiYXVkIjoiaHR0cHM6Ly9hdXRoLnBlcmZlY3RvbW9iaWxlLmNvbS9hdXRoL3JlYWxtcy9kZW1vLXBlcmZlY3RvbW9iaWxlLWNvbSIsInN1YiI6IjNkNjcwZmE0LWE3OTktNGFjYy04ZmNjLTkzNTBjMjI1NGNjYiIsInR5cCI6Ik9mZmxpbmUiLCJhenAiOiJvZmZsaW5lLXRva2VuLWdlbmVyYXRvciIsIm5vbmNlIjoiZDE3ZDBiNTAtOWNjZS00MDIzLTljYjEtYzE0ZWM5OTZiZWYwIiwiYXV0aF90aW1lIjowLCJzZXNzaW9uX3N0YXRlIjoiODAxYjk0MTktNWFjNi00ZmIyLWI4ZmYtZDZhNjEwNmNmODNkIiwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6Im9wZW5pZCBvZmZsaW5lX2FjY2VzcyJ9.VxXuZ4DqgGeVlqAwwIkYjo9dz5MZ5zuJQ4Wgh4lglWU");   
		//System.out.println("Perfecto Token " + System.getenv("token"));
		
		capabilities.setCapability("newCommandTimeout", "30");
		if (device) { capabilities.setCapability("windTunnelPersona", "Georgia"); }
		//	if (device) { capabilities.setCapability("windTunnelPersona", "Ross"); }
		
		capabilities.setCapability("scriptName", "Boston Globe - " + targetEnvironment);
		capabilities.setCapability("outputVideo", true);
		capabilities.setCapability("outputReport", true);
		String tunnelId;
		tunnelId = System.getProperty("tunnelId");
		if(tunnelId != null){
			capabilities.setCapability("tunnelId",	tunnelId);
		}
		
		long startTime; 
		while(retry > 0 && driver == null) {
			startTime = System.nanoTime();
			try {
				System.out.println("Trying to aquire session: " + targetEnvironment);
				if(fast) {
					driver = new RemoteWebDriver(new URL("https://demo.perfectomobile.com/nexperience/perfectomobile/wd/hub/fast"),
						capabilities);
					System.out.println(targetEnvironment + ": " + (System.nanoTime() - startTime) / 1000000);
				} else {
					driver = new RemoteWebDriver(new URL("https://demo.perfectomobile.com/nexperience/perfectomobile/wd/hub"),
						capabilities);
					System.out.println(targetEnvironment + ": " + (System.nanoTime() - startTime) / 1000000);
				}
				
			} catch (Exception e) {
				retry--;
				
				System.out.println("Failed to aquire browser session: " + targetEnvironment + ". Retrying...");
				sleep(retryInterval);
				ex = e;
	            StringWriter sw = new StringWriter();
	            e.printStackTrace(new PrintWriter(sw));
	            String exceptionAsString = sw.toString();
	            System.out.println(exceptionAsString);
	            throw e; 
			}		
		}
		
		OS = capabilities.getCapability("platformName").toString();
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
		if(device) {startLogging();}
		
		/*
		try{
			System.out.println("Trying to get browser size");
			System.out.println(targetEnvironment + " - " + driver.manage().window().getSize());
		} catch (WebDriverException e) {
			//reportiumClient.reportiumAssert("Get browser size", false);
			System.out.println("Failed to get browser size for " + targetEnvironment);
		}
		*/
		
		return driver;
	}

	public byte[] takeScreenshot() {
		//System.out.println("Taking screenshot");
		return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	}

	@Test
	public void BostonGlobeTest() {


		try{
			if(device){
				Map<String, Object> params1 = new HashMap<>();
				params1.put("generateHarFile", "true");
				params1.put("profile", "4g_lte_good");
				Object result1 = driver.executeScript("mobile:vnetwork:start", params1);
			}
			openHomepage();
			enterZipCode();
			selectLength();
			enterDetails();			
		} catch (Exception e) {
			ex = e;
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            System.out.println(exceptionAsString);
            throw e; 
		}



		
	}

	public void openHomepage() {
		reportiumClient.stepStart("Open Homepage");
		//System.out.println("### Opening homepage ###");
		driver.get(
				"http://subscribe.bostonglobe.com/B0004/?rc=WW011964&globe_rc=WW011964&p1=BGHeader_HomeDeliverySubscription");
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='txtZip']")));
		takeScreenshot();
		reportiumClient.stepEnd("Open Homepage");


	}

	public void enterZipCode() {
		reportiumClient.stepStart("Enter Zip Code");
		
		//System.out.println("### Entering zipcode ###");
		driver.findElement(By.xpath("//input[@name='txtZip']")).clear();
		driver.findElement(By.xpath("//input[@name='txtZip']")).sendKeys("02110");
		//driver.findElement(By.xpath("//input[@name='txtZip']")).sendKeys("secured.eW1U4AHF/7fA0km7X2ty2w==");
		
		
		driver.findElement(By.xpath("//input[@id='cmdSubmit']")).click();
		takeScreenshot();
		reportiumClient.stepEnd("Enter Zip Code");
	}

	public void selectLength() {
		reportiumClient.stepStart("Select Subscription Length");
		//System.out.println("### Selecting subscription length ###");
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//label[1]/strong[1])[1]")));

		//JavascriptExecutor js = (JavascriptExecutor) driver;
		//js.executeScript("$('input:radio[name=rdSubscription][value=4]').trigger('click');");
		
		driver.findElement(By.xpath("//ul[@id='available_offers_list']/li[1]")).click();
		driver.findElement(By.xpath("//input[@id='continue_btn']")).click();
		takeScreenshot();

		reportiumClient.stepEnd("Select Subscription Length");
		
		
	}

	public void enterDetails() {
		reportiumClient.stepStart("Enter Subscription Details");
		sleep(1000);
		//System.out.println("### Entering subscription details ###");
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='txtDeliveryFirstName']")));
		driver.findElement(By.id("first-name")).sendKeys("Pat");
		driver.findElement(By.xpath("//input[@id='last-name']")).sendKeys("McCartney");
		driver.findElement(By.xpath("//input[@id='address1']")).sendKeys("28 Main St");
		driver.findElement(By.xpath("//input[@id='address2']")).sendKeys("Apt. 2");
		driver.findElement(By.xpath("//input[@id='area-code']")).sendKeys("781");
		driver.findElement(By.xpath("//input[@id='phone3']")).sendKeys("847");
		driver.findElement(By.xpath("//input[@id='phone4']")).sendKeys("4433");
		driver.findElement(By.xpath("//input[@id='email']")).sendKeys("patrickm@perfectomobile.com");
		takeScreenshot();
		
		
		reportiumClient.stepEnd("Enter Subscription Details");
	}

	@BeforeClass(alwaysRun = true)
	public void baseBeforeClass(ITestContext context) throws MalformedURLException {
		Map<String, String> params = context.getCurrentXmlTest().getAllParameters();

		driver = createDriver(params.get("targetEnvironment"));
		reportiumClient = getReportiumClient(driver);
	}

	@AfterClass(alwaysRun = true)
	public void baseAfterClass() {
		try{ System.out.println("Report url = " + reportiumClient.getReportUrl()); }
		catch (Exception e) {}
		
		if (driver != null) {
			//if(device){endLogging();}
			driver.quit();
		}
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTest(Method method) {
		String testName = method.getDeclaringClass().getSimpleName() + "." + method.getName();
		reportiumClient.testStart(testName,                               
				new TestContext.Builder()
                .withCustomFields(new CustomField("perfecto.vcs.filePath",
                                "responsiveJava/src/test/java/TestHomeDeliverySignup.java"))
                .build());
	}

	@AfterMethod(alwaysRun = true)
	public void afterTest(ITestResult testResult) {
		if(ex != null){
			testResult.setStatus(ITestResult.FAILURE);
			testResult.setThrowable(ex);
		}
		
		int status = testResult.getStatus();
		
		switch (status) {
		case ITestResult.FAILURE:
			reportiumClient.testStop(TestResultFactory.createFailure("An error occurred", testResult.getThrowable()));
			break;
		case ITestResult.SUCCESS_PERCENTAGE_FAILURE:
		case ITestResult.SUCCESS:
			reportiumClient.testStop(TestResultFactory.createSuccess());
			break;
		case ITestResult.SKIP:
			// Ignore
			break;
		default:
			throw new ReportiumException("Unexpected status " + status);
		}
		
		if (driver != null) {
			if(device){endLogging();}
			driver.close();
		}
	}

	protected static ReportiumClient getReportiumClient(RemoteWebDriver driver) {
	
		
		
		PerfectoExecutionContext perfectoExecutionContext = new PerfectoExecutionContext.PerfectoExecutionContextBuilder()
				.withCustomFields(new CustomField("perfecto.vcs.repositoryUrl", "https://github.com/perfectomobilepresales/responsiveJava"))
				.withProject(new Project("Boston Globe", "1.0")) // Optional
				.withContextTags("Build " + System.getProperty("BuildNumber"), "Software Version: 1.6", "Responsive Build Validation", "patrickm", System.getProperty("tunnelId")) // Optional
				.withWebDriver(driver).build();

		return new ReportiumClientFactory().createPerfectoReportiumClient(perfectoExecutionContext);
	}

	private static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {}
	}
	
	private void startLogging() {
		Map<String, Object> params = new HashMap<>();
		driver.executeScript("mobile:logs:start", params);
		
		params.clear();
		List<String> vitals3 = new ArrayList<>();
		vitals3.add("all");
		params.put("vitals", vitals3);
		params.put("interval", "1");
		driver.executeScript("mobile:monitor:start", params);
		
		
		Map<String, Object> params1 = new HashMap<>();
		params1.put("profile", "4g_lte_average");
		Object result1 = driver.executeScript("mobile:vnetwork:start", params1);
	}
	
	private void endLogging() {
		Map<String, Object> params2 = new HashMap<>();
		Object result2 = driver.executeScript("mobile:logs:stop", params2);
		
		Map<String, Object> params3 = new HashMap<>();
		Object result3 = driver.executeScript("mobile:vnetwork:stop", params3);
		
		
	}

}
