package web;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import common.Common;
import common.DateUtil;
import environment.FileUtil;
import fitnesse.slim.StopTestSlimException;
import io.github.bonigarcia.wdm.WebDriverManager;
import tools.ELKReport;
import tools.Extend;
import tools.TestMigration;
import tools.qmetry;

/**
 * Exposes method to write automation for web UI.
 */
public class Ui {
	public static WebDriver webDriver = null;
	private static String url = "";
	private static final int timeout = 15;
	private static String logFile = "Selenium.log";
	private static String logLevel = "ERROR";
	private static String download = "download";
	private static String testAttachment = "testAttachment";
	private static String loadingAppElementIdentifier = "//*[contains(@class,'loading') or contains(@class,'spinner')]";
	private static String logToELK= "no";
	public static String testcase = null;

	/**
	 * Section for Extend Report configuration Start Here
	 * @throws IOException 
	 */
	public void startTestCase(String tc, String tag, String testCasePath, String logToELK) throws IOException {
		Ui.logToELK = logToELK;
		Extend.testcase(tc, tag, testCasePath);
		
		if (Ui.logToELK.equalsIgnoreCase("yes")) {
			ELKReport.logTestCaseStartStatus(tc, "Started");
		}
	}
	
	public String getTestCaseDescription(String filePath) throws IOException {
		String filepath = System.getProperty("user.dir") + File.separator + "FitNesseRoot" + File.separator + filePath.replaceAll("\\.", Matcher.quoteReplacement(File.separator))+".wiki";
		return TestMigration.getTestCaseDescription(filepath);
	}

	public void endTestCase(String testCycle, String testcase, String result, String reference, String testClient, String testTag, String testCasePath, String testDescription) throws Exception {
		qmetry.updateTestcase(testCycle, testcase, result, reference, testClient, testTag, testCasePath, testDescription, Ui.logToELK);
		Extend.end();
	}

	public String errorPath() throws IOException {
		String path = DateUtil.getCurrentDateTimeStamp("yMMddHHmmss");
		takeScreenshot(System.getProperty("user.dir") + "/er/" + path);
		return path + ".png";
	}

	/**
	 * Section for Extend Report configuration End Here
	 */
	public void setBaseUrl(String baseUrl) {
		url = baseUrl;
	}

	/**
	 * Initializes the Selenium object <code>webDriver</code> Usage: | initialize|
	 * browser | locale |
	 * 
	 * @param browser   the possible values are 'ie', 'chrome', 'safari' or
	 *                  'firefox'. In case this value is set anything apart from
	 *                  these values or blank, the default browser set is firefox.
	 * @param remoteURL this is the remote url in which the browser will open. The
	 *                  values vary depending on the browser.
	 * @throws Exception
	 */
	public void initialize(String browser, String remoteURL) throws Exception {
		if (Ui.webDriver != null) {
			return;
		}

		String downloadFilepath = System.getProperty("user.dir") + File.separator + download;
		String testAttachmentPath = System.getProperty("user.dir") + File.separator + testAttachment;
		new File(downloadFilepath).mkdirs();
		new File(testAttachmentPath).mkdirs();

		switch (browser.toLowerCase()) {

		case "chrome":
			WebDriverManager.chromedriver().setup();
			Extend.info("Initializing driver for " + browser);
			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
			chromePrefs.put("profile.default_content_settings.popups", 0);
			Extend.info("File Download location : " + downloadFilepath);
			chromePrefs.put("download.default_directory", downloadFilepath);
			chromePrefs.put("download.prompt_for_download", false);
			chromePrefs.put("download.directory_upgrade", true);
			chromePrefs.put("plugins.plugins_disabled", "Chrome PDF Viewer");
			chromePrefs.put("plugins.always_open_pdf_externally", true);
			chromePrefs.put("pdfjs.disabled", true);
			chromePrefs.put("profile.default_content_setting_values.notifications", 2);
			ChromeOptions coptions = new ChromeOptions();
			coptions.addArguments("--disable-extensions");
			coptions.addArguments("--window-size=1920,1080");
			coptions.addArguments("--no-sandbox");
			coptions.addArguments("--disable-dev-shm-usage");
			coptions.addArguments("--no-sandbox");
			coptions.setExperimentalOption("prefs", chromePrefs);
			Ui.webDriver = new ChromeDriver(coptions);
			break;

		case "headless":
			WebDriverManager.chromedriver().setup();
			Extend.info("Initializing driver for " + browser);
			HashMap<String, Object> chrPrefs = new HashMap<String, Object>();
			chrPrefs.put("profile.default_content_settings.popups", 0);
			Extend.info("File Download location : " + downloadFilepath);
			chrPrefs.put("download.default_directory", downloadFilepath);
			chrPrefs.put("download.prompt_for_download", false);
			chrPrefs.put("download.directory_upgrade", true);
			chrPrefs.put("plugins.plugins_disabled", "Chrome PDF Viewer");
			chrPrefs.put("plugins.always_open_pdf_externally", true);
			chrPrefs.put("pdfjs.disabled", true);
			chrPrefs.put("profile.default_content_setting_values.notifications", 2);
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--headless");
			options.addArguments("--window-size=1920,1080");
			options.addArguments("--disable-extensions"); // to disable browser extension popup
			options.addArguments("--no-sandbox");
			options.addArguments("--disable-dev-shm-usage");
			options.setExperimentalOption("prefs", chrPrefs);
			Ui.webDriver = new ChromeDriver(options);
			break;

		case "remote":
			WebDriverManager.chromedriver().setup();
			HashMap<String, Object> chromeremotePrefs = new HashMap<String, Object>();
			chromeremotePrefs.put("profile.default_content_settings.popups", 0);
			Extend.info("File Download location : " + downloadFilepath);
			chromeremotePrefs.put("download.default_directory", downloadFilepath);
			chromeremotePrefs.put("download.prompt_for_download", false);
			chromeremotePrefs.put("download.directory_upgrade", true);
			chromeremotePrefs.put("plugins.plugins_disabled", "Chrome PDF Viewer");
			chromeremotePrefs.put("plugins.always_open_pdf_externally", true);
			chromeremotePrefs.put("pdfjs.disabled", true);
			ChromeOptions croptions = new ChromeOptions();
			croptions.addArguments("--disable-extensions"); // to disable browser extension popup
			croptions.addArguments("--no-sandbox");
			croptions.addArguments("--disable-dev-shm-usage");
			croptions.addArguments("--window-size=1920,1080");
			croptions.setExperimentalOption("prefs", chromeremotePrefs);
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability(ChromeOptions.CAPABILITY, croptions);
			Ui.webDriver = new RemoteWebDriver(new URL(remoteURL + "/wd/hub"), capabilities);
			((RemoteWebDriver) Ui.webDriver).setFileDetector(new LocalFileDetector());
			Ui.webDriver.manage().window().maximize();
			break;

		default:
			WebDriverManager.firefoxdriver().setup();
			Ui.webDriver = new FirefoxDriver();
			Extend.info("Initializing driver for Firefox");
			System.setProperty("webdriver.firefox.driver.logfile", logFile);
			System.setProperty("webdriver.firefox.driver.loglevel", logLevel);
			break;
		}

		// Wait for browser to load.
		Ui.webDriver.manage().window().maximize();
		// Ui.webDriver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		// Ui.webDriver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
		// Ui.webDriver.manage().window().setSize(new Dimension(1920,1080));
		// Ui.webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	/**
	 * Launch Browser with given URL 
	 * Usage: | launchBrowser| pageUrl|
	 * @param pageUrl
	 * @throws Exception
	 */
	public void launchBrowser(String pageUrl) throws Exception {
		if (url.isEmpty() || pageUrl.contains("http")) {
			url = pageUrl;
		} else if (!url.endsWith("/") && !url.startsWith("/")) {
			url = url + "/" + pageUrl;
		} else {
			url = url + pageUrl;
		}
		Extend.verificationMessage("Setting URL: " + url);
		Ui.webDriver.get(url);
	}
	
	/**
	 * Find the element and click on it 
	 * Usage: | click| elementIdentifier| findBy|
	 * @param elementId - element identifier value
	 * @param findBy    - element identifier type e.g. id, xpath, css, class
	 * @throws IOException
	 */
	public void click(String elementIdentifier, String findBy) throws IOException {
		try {
			WebElement element = waitForElementToBeClickable(elementIdentifier, findBy, String.valueOf(timeout));
			if (element != null) {
				highLightElement(element);
				element.click();
				Extend.info("Clicking on element : " + elementIdentifier + " using " + findBy);
			}
		} catch (ElementClickInterceptedException e) {
			Extend.takeScreenshot("Element Click intercepted : "+e, errorPath());
			waitForElementToBeInvisible(loadingAppElementIdentifier, findBy, String.valueOf(timeout));
			clickJS(elementIdentifier, findBy);
		} catch (StaleElementReferenceException e) {
			Extend.takeScreenshot("Element is stale : ", errorPath());
			wait(5);
			getElement(elementIdentifier, findBy).click();
		} catch (Exception e) {
			Extend.error(e.getMessage(), errorPath());
		}
	}

	/**
	 * Find the element and click on it using Java script (Effective when element is not visible on screen) 
	 * Usage: | clickJS| elementIdentifier| findBy|
	 * @param elementId - element identifier value
	 * @param findBy    - element identifier type e.g. id, xpath, css, class
	 * @throws IOException
	 */
	public void clickJS(String elementIdentifier, String findBy) throws IOException {
		try {
			WebElement element = waitForElementToBePresent(elementIdentifier, findBy, String.valueOf(timeout));
			if (element != null) {
				highLightElement(element);
				JavascriptExecutor js = (JavascriptExecutor) webDriver;
				js.executeScript("arguments[0].click();", element);
				Extend.info("JS Clicking on element: " + elementIdentifier);
			}
		} catch (Exception e) {
			Extend.error(e.getMessage(), errorPath());
		}
	}

	/**
	 * Find the elements and click on it all 
	 * Usage: | clickAll| elementIdentifier| findBy|
	 * @param elementId - element identifier value
	 * @param findBy    - element identifier type e.g. id, xpath, css, class
	 * @throws IOException
	 */
	public void clickAll(String elementIdentifier, String findBy) throws IOException {
		try {
			waitForElementToBeClickable(elementIdentifier, findBy, String.valueOf(timeout));
			List<WebElement> elements = getElements(elementIdentifier, findBy);
			Extend.info("Clicking on all elements: " + elements);
			for (WebElement element : elements) {
				element.click();
			}
		} catch (Exception e) {
			Extend.error("Clickable element not found : " + e.getMessage(), errorPath());
		}
	}

	/**
	 * Find the elements and click on it all 
	 * Usage: | clickByIndex| elementIdentifier| findBy| index|
	 * 
	 * @param elementId - element identifier value
	 * @param findBy    - element identifier type e.g. id, xpath, css, class
	 * @throws IOException
	 */
	public void clickByIndex(String elementIdentifier, String findBy, int index) throws IOException {
		try {
			waitForElementToBeClickable(elementIdentifier, findBy, String.valueOf(timeout));
			List<WebElement> elements = getElements(elementIdentifier, findBy);
			Extend.info("Clicking on respective element: " + elements);
			elements.get(index).click();
		} catch (Exception e) {
			Extend.error(e.getMessage(), errorPath());
		}
	}

	/**
	 * Mouse Hover to element provided.
	 * Usage: | mouseHover| element|
	 * @param element the WebElement on which to hover the mouse pointer.
	 * @throws IOException
	 */
	public void mouseHover(WebElement element) throws IOException {
		try {
			if (waitForElementToBeDisplayed(element)) {
				highLightElement(element);
				Actions actions = new Actions(Ui.webDriver);
				actions.moveToElement(element);
				actions.perform();
				Extend.info("Mouse Hovering on element: " + element);
			} else {
				Extend.error("Mouse hover failed on element: " + element);
			}
		} catch (Exception e) {
			Extend.error(e.getMessage(), errorPath());
		}
	}

	/**
	 * Mouse Hover to element provided. 
	 * Usage: | mouseHover| elementIdentifier| findBy|
	 * @param elementIdentifier
	 * @param findBy
	 * @throws IOException
	 */
	public void mouseHover(String elementIdentifier, String findBy) throws IOException {
		try {
			if (waitForElementToBeDisplayed(elementIdentifier, findBy, String.valueOf(timeout))) {
				WebElement element = getElement(elementIdentifier, findBy);
				Actions actions = new Actions(Ui.webDriver);
				actions.moveToElement(element);
				actions.perform();
				Extend.info("Mouse hover on element: " + elementIdentifier);
			} else {
				Extend.error("Mouse hover failed on element: " + elementIdentifier);
			}
		} catch (Exception e) {
			Extend.error(e.getMessage(), errorPath());
		}
	}

	/**
	 * Mouse Click to element provided 
	 * Usage: | mouseClick| elementIdentifier| findBy|
	 * 
	 * @param elementIdentifier
	 * @param findBy
	 * @throws IOException
	 */
	public void mouseClick(String elementIdentifier, String findBy) throws IOException {
		try {
			WebElement element = waitForElementToBePresent(elementIdentifier, findBy, String.valueOf(timeout));
			if (element != null) {
				highLightElement(element);
				Actions actions = new Actions(Ui.webDriver);
				actions.moveToElement(element);
				actions.click().build().perform();
				Extend.info("Mouse Click on : " + elementIdentifier);
			}
		} catch (Exception e) {
			Extend.error(e.getMessage(), errorPath());
		}
	}

	/**
	 * Find the element and type the text in it 
	 * Usage: | sendKeys|elementIdentifier| findBy| text|
	 * 
	 * @param elementId - element identifier value
	 * @param findBy    - element identifier type e.g. id, xpath, css, class
	 * @param text      - values to type
	 * @throws IOException
	 */
	public void sendKeys(String elementIdentifier, String findBy, String text) throws IOException {
		try {
			WebElement element = waitForElementToBePresent(elementIdentifier, findBy, String.valueOf(timeout));
			if (element != null) {
				highLightElement(element);
				Extend.info("Sending keys : [" + text + "] to element : [" + elementIdentifier + "] using locator : "
						+ findBy);
				// element.clear();
				element.sendKeys(text);
			}
		} catch (Exception e) {
			Extend.error(
					"Sending keys failed : [" + text + "] to element : [" + elementIdentifier + "] -" + e.getMessage(),
					errorPath());
		}
	}

	/**
	 * Find the elements with lists and place with position and type the text in it
	 * Usage: | sendKeys| elementIdentifier| findBy| position| text|
	 * 
	 * @param elementId - element identifier value
	 * @param findBy    - element identifier type e.g. id, xpath, css, class
	 * @param text      - values to type
	 * @throws IOException
	 */
	public void sendKeys(String elementIdentifier, String findBy, int position, String text) throws IOException {
		try {
			if (waitForElementToBePresent(elementIdentifier, findBy, String.valueOf(timeout)) != null) {
				List<WebElement> elements = getElements(elementIdentifier, findBy);
				highLightElement(elements);
				// elements.clear();
				elements.get(position).sendKeys(text);
				Extend.info("Sending text " + text + " to Element " + elementIdentifier);
			} else {
				Extend.error("Sending text " + text + " to Element " + elementIdentifier + " is failed");
			}
		} catch (Exception e) {
			Extend.error(
					"Sending keys failed : [" + text + "] to element : [" + elementIdentifier + "] -" + e.getMessage(),
					errorPath());
		}
	}

	/**
	 * Send Keys with one by one character 
	 * Usage: | sendHumanKeys| elementIdentifier| findBy| text|
	 * 
	 * @param elementIdentifier
	 * @param findBy
	 * @param text
	 * @throws IOException
	 */
	public void sendHumanKeys(String elementIdentifier, String findBy, String text) throws IOException {

		try {
			Random r = new Random();
			WebElement element = waitForElementToBePresent(elementIdentifier, findBy, String.valueOf(timeout));

			if (element != null) {
				for (int i = 0; i < text.length(); i++) {
					try {
						Thread.sleep((int) (r.nextGaussian() * 15 + 100));
					} catch (InterruptedException e) {
						Extend.error(e.getMessage());
					}
					String s = new StringBuilder().append(text.charAt(i)).toString();
					element.sendKeys(s);
				}
				Extend.info("Sending text [" + text + "] to Element : " + elementIdentifier);
			}
		} catch (Exception e) {
			Extend.error(
					"Sending keys failed : [" + text + "] to element : [" + elementIdentifier + "] -" + e.getMessage(),
					errorPath());
		}

	}

	/**
	 * Find the element and send control keys like Enter, Shift, Control.
	 * Usage: | sendControlKey| elementIdentifier| findBy| keyName|
	 * @param elementIdentifier
	 * @param findBy
	 * @param keyName
	 * @throws IOException 
	 */
	public void sendControlKey(String elementIdentifier, String findBy, String keyName) throws IOException {
		try {
			WebElement element = waitForElementToBePresent(elementIdentifier, findBy, String.valueOf(timeout));
			if (element != null) {
				Keys key = Keys.valueOf(keyName);
				Extend.info("Pressing the keyboard key: " + keyName);
				element.sendKeys(key);
			}
		} catch (Exception exception) {
			Extend.error("Error in pressing [" + keyName + "] key on ["+elementIdentifier+"]"+ exception.getMessage(), errorPath());
		}
	}

	/**
	 * Find the element according to given identifier and identifier type
	 * @param elementIdentifier - element identifier value
	 * @param identifierType    - element identifier type e.g. id, xpath, css, class
	 * @return element
	 */
	private WebElement getElement(String elementIdentifier, String findBy) {
		
		By by = null;
		
		switch (findBy.toLowerCase()) {
		case "name":
			by = By.name(elementIdentifier);
			break;
		case "id":
			by = By.id(elementIdentifier);
			break;
		case "xpath":
			by = By.xpath(elementIdentifier);
			break;
		case "linktext":
			by = By.linkText(elementIdentifier);
			break;
		case "cssselector":
			by = By.cssSelector(elementIdentifier);
			break;
		case "tagname":
			by = By.tagName(elementIdentifier);
			break;
		case "classname":
			by = By.className(elementIdentifier);
			break;
		}
		
		WebElement element = null;
		
		try {
			element = Ui.webDriver.findElement(by);
			highLightElement(element);
		} catch (NoSuchElementException e) {
			
		}
		
		return element;
	}

	/**
	 * Find the elements according to given identifier and identifier type
	 * @param elementIdentifier - element identifier value
	 * @param identifierType    - element identifier type e.g. id, xpath, css, class
	 * @return element
	 */
	private List<WebElement> getElements(String elementIdentifier, String findBy) {

		By element = null;
		
		switch (findBy.toLowerCase()) {
		case "name":
			element = By.name(elementIdentifier);
			break;
		case "id":
			element = By.id(elementIdentifier);
			break;
		case "xpath":
			element = By.xpath(elementIdentifier);
			break;
		case "linktext":
			element = By.linkText(elementIdentifier);
			break;
		case "cssselector":
			element = By.cssSelector(elementIdentifier);
			break;
		case "tagname":
			element = By.tagName(elementIdentifier);
			break;
		case "classname":
			element = By.className(elementIdentifier);
			break;
		}
		
		List<WebElement> elements = null;
		
		try {
			elements = Ui.webDriver.findElements(element);
			highLightElement(elements);
			Extend.info("Finding element by identifier : " + elementIdentifier + " using " + findBy.toString());
		} catch (NoSuchElementException e) {
			Extend.info("Element not found for identifier : " + elementIdentifier + " using " + findBy.toString());
		}
		
		return elements;
	}
	
	/**
	 * Find a By which locates elements by the value of the given parameter
	 * @param elementIdentifier - element identifier value
	 * @param findBy            - element identifier type e.g. id, xpath, css, class
	 * @return byObject - the By object of the element provided
	 */
	private By getByObjectOfElement(String elementIdentifier, String findBy) {
		By byObject = null;
		try {
			switch (findBy.toLowerCase()) {
			case "name":
				byObject = By.name(elementIdentifier);
				break;
			case "id":
				byObject = By.id(elementIdentifier);
				break;
			case "xpath":
				byObject = By.xpath(elementIdentifier);
				break;
			case "linktext":
				byObject = By.linkText(elementIdentifier);
				break;
			case "cssselector":
				byObject = By.cssSelector(elementIdentifier);
				break;
			case "tagname":
				byObject = By.tagName(elementIdentifier);
				break;
			case "classname":
				byObject = By.className(elementIdentifier);
				break;
			}
		} catch (Exception e) {
			Extend.info("Finding element by identifier : " + elementIdentifier + " using " + findBy.toString()+ " is failed " + e.toString());
		}
		return byObject;
	}

	/**
	 * Get the text of object/element 
	 * Usage: | getText| elementIdentifier| findBy|
	 * @param elementIdentifier - element identifier value
	 * @param identifierType    - element identifier type e.g. id, xpath, css, class
	 * @return text value
	 * @throws IOException
	 */
	public String getText(String elementIdentifier, String findBy) throws IOException {
		try {
			if (waitForElementToBeDisplayed(elementIdentifier, findBy, String.valueOf(timeout))) {
				WebElement element = getElement(elementIdentifier, findBy);
				Extend.info("Get text Value is: [" + element.getText() + "] from indentifier " + elementIdentifier);
				return element.getText();
			} else {
				Extend.info("Get text Value is from indentifier " + elementIdentifier + " is failed");
				return null;
			}
		} catch (Exception e) {
			Extend.error(e.getMessage(), errorPath());
			return null;
		}
	}
	
	/**
	 * Get Empty text => this is used to assign empty string to dynamic variable
	 * Usage: | getEmptyText|
	 */
	public String getEmptyText() {
		String result = "";
	    return result;
	}
	
	/**
	 * Getting Elements Count 
	 * Usage: | getCountOfElements| elementIdentifier|findBy| 
	 * @param elementIdentifier - element identifier value
	 * @param identifierType - element identifier type e.g. id, xpath, css, class
	 * @return counts
	 * @throws IOException
	 */
	public int getCountOfElements(String elementIdentifier, String findBy) throws IOException {
		int count = 0;
		try {
			if (isElementDisplayed(elementIdentifier, findBy)) {
				List<WebElement> elements = getElements(elementIdentifier, findBy);
				count = elements.size();
				Extend.info("Total Count of Elements " + elementIdentifier + " using "+findBy+" is : " + count);
			} else {
				Extend.info("Element is not found for : " + elementIdentifier);
			}
		} catch (Exception e) {
			Extend.error(e.getMessage(), errorPath());
		}
		return count;
	}
	
	/**
	 * Get the value from text box or text field 
	 * Usage: | getValue|elementIdentifier| findBy|
	 * @param elementIdentifier - element identifier value
	 * @param identifierType    - element identifier type e.g. id, xpath, css, class
	 * @return text value
	 * @throws IOException
	 */
	public String getValue(String elementIdentifier, String findBy) throws IOException {
		String result = "";
		try {
			if (waitForElementToBeDisplayed(elementIdentifier, findBy, String.valueOf(timeout))) {
				WebElement element = getElement(elementIdentifier, findBy);
				result = element.getAttribute("value");
				Extend.info("The text value is:" + result.toString() + " for identifier : " + elementIdentifier);
			} else {
				Extend.error("Element is not found for : " + elementIdentifier);
			}
		} catch (Exception e) {
			Extend.error(e.getMessage(), errorPath());
		}
		return result;
	}
	
	/**
	 * Get the values from all text box or text field with same elementIdentifier
	 * Usage: | getValues| elementIdentifier| findBy|
	 * @param elementIdentifier - element identifier value
	 * @param identifierType    - element identifier type e.g. id, xpath, css, class
	 * @return text value
	 * @throws IOException
	 */
	public String getValues(String elementIdentifier, String findBy) throws IOException {
		Iterator<WebElement> results;
		String result = "";
		try {
			if (waitForElementToBeDisplayed(elementIdentifier, findBy, String.valueOf(timeout))) {
				List<WebElement> elements = getElements(elementIdentifier, findBy);
				results = elements.iterator();
				while (results.hasNext()) {
					Object element = results.next().getText();
					result += element.toString() + ",";
				} 
				Extend.info("The text value is:" + result  + " for identifier : " + elementIdentifier);
			} else {
				Extend.error("Element is not found for : " + elementIdentifier);
			}
		} catch (Exception e) {
			Extend.error(e.getMessage(), errorPath());
		}
		return result;
	}
	
	/**
	 * This method will scroll down the page based on elementIdentifier we mention
	 * Usage: | scrollToView| elementIdentifier| findBy|
	 * @param elementIdentifier - element identifier value
	 * @param identifierType    - element identifier type e.g. id, xpath, css, class
	 * @return void
	 * @throws IOException
	 */
	public void scrollToView(String elementIdentifier, String findBy) throws IOException {
		try {
			if (isElementPresentNoReporting(elementIdentifier, findBy)) {
				//wait(3);
				JavascriptExecutor js = (JavascriptExecutor) Ui.webDriver;
				WebElement element = getElement(elementIdentifier, findBy);
				js.executeScript("arguments[0].scrollIntoView(true)", element);
				Extend.info("Scroll to Element " + elementIdentifier);
			}
		} catch (Exception e) {
			Extend.error(e.getMessage(), errorPath());
		}
	}
	 
	/**
	 * This method will scroll down or scroll up the page based 
	 * Usage: | scrollUp|
	 * @return void
	 * @throws IOException
	 */
	public void scrollUp() throws IOException {
		try {
			JavascriptExecutor js = (JavascriptExecutor) Ui.webDriver;
			Ui.webDriver.manage().window().maximize();
			js.executeScript("window.scrollBy(0,-1000)");
			Extend.info("Scrolling up");
		} catch (Exception e) {
			Extend.error(e.getMessage(), errorPath());
		}
	}
	 
	/**
	 * This method will scroll down or scroll up the page based 
	 * Usage: | scrollDown|
	 * @return void
	 * @throws IOException
	 */
	public void scrollDown() throws IOException {
		try {
			JavascriptExecutor js = (JavascriptExecutor) Ui.webDriver;
			Ui.webDriver.manage().window().maximize();
			js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
			Extend.info("Scrolling down");
		} catch (Exception e) {
			Extend.error(e.getMessage(), errorPath());
		}
	}
	
	/**
	 * Clear the value in a element 
	 * Usage: | clearValue| elementIdentifier| findBy|
	 * @param elementIdentifier - element identifier value
	 * @param identifierType    - element identifier type e.g. id, xpath, css, class
	 * @throws IOException
	 */
	public void clearValue(String elementIdentifier, String findBy) throws IOException {
		try {
			if (waitForElementToBeDisplayed(elementIdentifier, findBy, String.valueOf(timeout))) {
				WebElement element = getElement(elementIdentifier, findBy);
				element.clear();
				Extend.info("The Value is cleared in " + elementIdentifier + " element");
			} else {
				Extend.error("The Value is cleared in " + elementIdentifier + " element is failed");
			}
		} catch (Exception e) {
			Extend.error(e.getMessage(), errorPath());
		}
	}
	 
	/**
	 * Clear the value and send new text to the element 
	 * Usage: | clearAndSend |elementIdentifier| findBy| value|
	 * @param elementIdentifier - element identifier value
	 * @param identifierType    - element identifier type e.g. id, xpath, css, class
	 * @param ValueToSend       - Value to send
	 * @throws IOException
	 */
	public void clearAndSend(String elementIdentifier, String findBy, String value) throws IOException {
		try {
			if (waitForElementToBeDisplayed(elementIdentifier, findBy, String.valueOf(timeout))) {
				WebElement element = getElement(elementIdentifier, findBy);
				Actions actionList = new Actions(Ui.webDriver);
				actionList.click(element).keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL)
						.sendKeys(Keys.BACK_SPACE).sendKeys(value).release().build().perform();
				//element.sendKeys(Keys.HOME, Keys.chord(Keys.SHIFT, Keys.END), value);
				Extend.info("Cleared and Sending [" + value + "] Values to : " + elementIdentifier + "element");
			} else {
				Extend.error("Clear and Send [" + value + "] Values to : " + elementIdentifier + " element is failed");
			}
		} catch (Exception e) {
			Extend.error("clear And Send [" + value + "] value " + elementIdentifier + " is failed", errorPath());
		}
	}
	
	/**
	 * Clear the text field values using the JavaScript Executor 
	 * Usage: |clearValueJS| elementIdentifier| findBy|
	 * @param elementIdentifier - element identifier value
	 * @param identifierType    - element identifier type e.g. id, xpath, css, class
	 * @throws IOException
	 */
	public void clearValueJS(String elementIdentifier, String findBy) throws IOException {
		try {
			if (waitForElementToBeDisplayed(elementIdentifier, findBy, String.valueOf(timeout))) {
				WebElement element = getElement(elementIdentifier, findBy);
				JavascriptExecutor js = (JavascriptExecutor) webDriver;
				js.executeScript("arguments[0].value='';", element);
				Extend.info("Cleared values with JS for " + elementIdentifier);
			} else {
				Extend.error("Clear Values with JS for " + elementIdentifier + " element is failed");
			}
		} catch (Exception e) {
			Extend.error("clearValue with JS for " + elementIdentifier + " was failed", errorPath());
		}
	}
	
	/**
	 * Clear the text field values using the sendKeys and Keys its an workaround
	 * Usage: | clearValueWA| elementIdentifier| findBy|
	 * @param elementIdentifier - element identifier value
	 * @param identifierType    - element identifier type e.g. id, xpath, css, class
	 * @throws IOException
	 */
	public void clearValueWA(String elementIdentifier, String findBy) throws IOException {
		try {
			if (waitForElementToBeDisplayed(elementIdentifier, findBy, String.valueOf(timeout))) {
				WebElement element = getElement(elementIdentifier, findBy);
				element.click();
				element.sendKeys(Keys.CONTROL + "a");
				element.sendKeys(Keys.DELETE);
				Extend.info("Cleared values with JS for " + elementIdentifier);
			} else {
				Extend.error("Clear Values with JS for " + elementIdentifier + " element is failed");
			}
		} catch (Exception e) {
			Extend.error("clearValue with WA for " + elementIdentifier + " was failed", errorPath());
		}
	}
	
	/**
	 * Get the current window title
	 * Usage: | getWindowTitle|
	 * @return Name of the window
	 * @throws IOException 
	 */
	public String getWindowTitle() throws IOException {
		String message = "";
		try {
			Extend.info("Getting the window title..." + message.toString());
			message = Ui.webDriver.getTitle();
			Extend.info("Window title is: " + message.toString());
		} catch (Exception ex) {
			Extend.error(ex.getMessage(), errorPath());
		}
		return message;
	}
	
	/**
	 * Check whether the element is present or not identifier and identifier type
	 * Usage: | isElementPresent| elementIdentifier| findBy|
	 * @param elementIdentifier - element identifier value
	 * @param identifierType    - element identifier type e.g. id, xpath, css, class
	 * @throws IOException
	 * @throws StopTestSlimException 
	 */
	public boolean isElementPresent(String elementIdentifier, String findBy) throws IOException, StopTestSlimException {
		try {
			// Ui.webDriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			waitForElementToBePresent(elementIdentifier, findBy, String.valueOf(timeout));
			highLightElement(getElement(elementIdentifier, findBy));
			Extend.success("Element is present for identifier : " + elementIdentifier + " using type : " + findBy);
			return true;
		} catch (Exception exception) {
			Extend.error("Element not present : " + elementIdentifier + " using type : " + findBy + exception.getMessage(),errorPath());
			//return false;
			throw new StopTestSlimException("Stopping execution : " + exception.getMessage());
		} finally {
			// Ui.webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
	}
	
	/**
	 * Check whether the element is present or not identifier and identifier type without throwing error
	 * Usage: | isElementPresentNoReporting| elementIdentifier| findBy|
	 * @param elementIdentifier - element identifier value
	 * @param identifierType    - element identifier type e.g. id, xpath, css, class
	 * @throws IOException
	 */
	public boolean isElementPresentNoReporting(String elementIdentifier, String findBy) throws IOException {
		//wait(3);
		try {
			//Ui.webDriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			List<WebElement> elements = getElements(elementIdentifier, findBy);
			int count = elements.size();
			if (count == 0) {
				Extend.info("Element is Not present in Page : " + elementIdentifier);
				return false;
			}
			Extend.success("Element is present in page " + elementIdentifier);
			return true;
		} catch (Exception exception) {
			Extend.error("Element not present : " + exception.getMessage(), errorPath());
			return false;
		} finally {
			//Ui.webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
	}
	
	/**
	 * Check whether the element does not exists type 
	 * Usage: | isElementNotPresent|elementIdentifier| findBy|
	 * @param elementIdentifier - element identifier value
	 * @param identifierType    - element identifier type e.g. id, xpath, css, class
	 * @throws IOException
	 */
	public boolean isElementNotPresent(String elementIdentifier, String findBy) throws IOException {
		try {
			//Ui.webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			List<WebElement> elements = getElements(elementIdentifier, findBy);
			int count = elements.size();
			if (count != 0) {
				Extend.error("Element is present in Page : " + elementIdentifier, errorPath());
				return false;
			}
			Extend.success("Element Not Identified successfully " + elementIdentifier);
			return true;
		} catch (Exception e) {
			Extend.error(e.getMessage(), errorPath());
			return false;
		} finally {
			//Ui.webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
	}
	
	/**
	 * Check whether the alert is present or not
	 * Usage: | isAlertPresent|
	 * @throws IOException 
	 */
	public boolean isAlertPresent() throws IOException {
		boolean result = false;
		try {
			Extend.info("Checking for alert...");
			Ui.webDriver.switchTo().alert();
			Extend.info("Alert is present");
			result = true;
		} catch (Exception exception) {
			Extend.error(exception.getMessage(), errorPath());
			result = false;
		}
		return result;
	}

	/**
	 * Accept the alert
	 * Usage: | acceptAlert|
	 */
	public void acceptAlert() {
		wait(2);
		Extend.info("Calling alert.accept");
		Alert alert = Ui.webDriver.switchTo().alert();
		alert.accept();
	}
	
	/**
	 * Prompt Alert
	 * Usage: | promptAlert| text|
	 */
	public void promptAlert(String text) {
		wait(2);
		Extend.info("Calling prompt alert.accept");
		Alert alert = Ui.webDriver.switchTo().alert();
		alert.sendKeys(text);
		alert.accept();
	}
	
	/**
	 * Dismiss the alert
	 * Usage: | dismissAlert|
	 */
	public void dismissAlert() {
		wait(2);
		Extend.info("Calling alert.dismiss");
		Alert alert = Ui.webDriver.switchTo().alert();
		alert.dismiss();
		Extend.info("Alert is dismissed");
	}

	/**
	 * Wait for the specified seconds
	 * Usage: | wait| number|
	 * @param interget
	 *            value
	 * @throws InterruptedException
	 */
	public void wait(int i) {
		try {
			java.util.concurrent.TimeUnit.SECONDS.sleep(i);
            Extend.info("Waited for time: " + i + " seconds");
		} catch (InterruptedException e) {
			Extend.error("Waiting for time: " + i + " seconds is failed : "+e.getMessage());
		}
	}

	/**
	 * Navigate back to last page
	 * Usage: | navigateBack|
	 * @throws IOException 
	 */
	public void navigateBack() throws IOException {
		try {
			Extend.info("Navigating to back");
			Ui.webDriver.navigate().back();
			//wait(2);
			Extend.info("Navigated to back to last page");
		} catch (Exception exception) {
			Extend.error(exception.getMessage(), errorPath());
		}
	}

	/**
	 * Navigate forward to next page
	 * Usage: | navigateForward|
	 * @throws IOException 
	 */
	public void navigateForward() throws IOException {
		try {
			Extend.info("Navigating forward to next page");
			Ui.webDriver.navigate().forward();
			//wait(2);
			Extend.info("Navigated forward to next page");
		} catch (Exception exception) {
			Extend.error(exception.getMessage(), errorPath());
		}
	}

	/**
	 * Refresh the current page
	 * Usage: | refresh|
	 * @throws IOException 
	 */
	public void refresh() throws IOException {
		try {
			Extend.info("Refreshing the current page");
			Ui.webDriver.navigate().refresh();
			//wait(2);
		} catch (Exception exception) {
			Extend.error(exception.getMessage(), errorPath());
		}
	}

	/**
	 * Get the pop up message
	 * Usage: | getPopupMessage|
	 * @return pop up message
	 */
	public String getPopupMessage() {
		String message = null;
		Extend.info("Getting the pop up message");
		Alert alert = Ui.webDriver.switchTo().alert();
		message = alert.getText();
		Extend.info("Pop up message is: " + message.toString());
		return message;
	}

	/**
	 * Get the tool tip text for element 
	 * Usage: | getToolTipText| locator|
	 * @param locator e.g. xPath, Id etc.
	 * @return tool tip text
	 */
	public String getToolTipText(By locator) {
		Extend.info("Getting the tooltip");
		String tooltip = Ui.webDriver.findElement(locator).getAttribute("title");
		Extend.info("Tooltip as: " + tooltip.toString());
		return tooltip;
	}

	/**
	 * Select provided value from drop down. 
	 * Usage: | selectDropdownValue|elementIdentifier| findBy| value|
	 * @param elementIdentifier e.g value of identifiers xPath, Id, CSS etc.
	 * @param findBy            type of identifier like xPath, Id, CSS etc.
	 * @param value             drop down value to select.
	 * @return true/false
	 */
	public boolean selectDropdownValue(String elementIdentifier, String findBy, String value) {
		boolean result = false;
		try {
			Extend.info("Selecting the dropdown element " + value);
			Select dropdown = new Select(getElement(elementIdentifier, findBy));
			dropdown.selectByValue(value);
			result = true;
		} catch (Exception exception) {
			System.err.println("Error in selecting dropdown value " + exception.getMessage());
		}
		return result;
	}

	/**
	 * Select provided value from drop down. 
	 * Usage: | selectDropdownValueByIndex|elementIdentifier| findBy| index|
	 * @param elementIdentifier e.g value of identifiers xPath, Id, CSS etc.
	 * @param findBy            type of identifier like xPath, Id, CSS etc.
	 * @param index             index of element to select form drop down.
	 * @return true/false
	 */
	public boolean selectDropdownValueByIndex(String elementIdentifier, String findBy, String index) {
		boolean result = false;
		try {
			Extend.info("Selecting the dropdown element by index " + index);
			Select dropdown = new Select(getElement(elementIdentifier, findBy));
			dropdown.selectByIndex(Integer.valueOf(index));
			result = true;
		} catch (Exception exception) {
			System.err.println("Error in selecting dropdown value " + exception.getMessage());
		}
		return result;
	}

	/**
	 * Getting Current URL from Browser
	 * Usage: | currentUrl|
	 */
	public String currentUrl() {
		Extend.info("Fetching Current URL");
		return Ui.webDriver.getCurrentUrl();
	}

	/**
	 * Navigate to Url 
	 * Usage: | navigateToUrl| Url|
	 * @param Url - Url to navigate to.
	 * @throws IOException
	 */
	public void navigateToUrl(String Url) throws IOException {
		try {
			verificationMessage("Navigating to Url " + Url);
			Ui.webDriver.navigate().to(Url);
			//Ui.webDriver.get(Url);
			waitForElementToDisappear(loadingAppElementIdentifier, "xpath", String.valueOf(timeout+10));
		} catch (ElementNotVisibleException e) {
			
		} catch (Exception exception) {
			Extend.error(exception.getMessage(), errorPath());
		}
	}

	/**
	 * Delete all cookies
	 * Usage: | deleteAllCookies||
	 * @throws IOException 
	 */
	public void deleteAllCookies() throws IOException {
		try {
			Extend.info("Deleting all cookies from browser");
			Ui.webDriver.manage().deleteAllCookies();
			wait(3);
		} catch (Exception exception) {
			Extend.error(exception.getMessage(), errorPath());
		}
	}

	/**
	 * Press keyboard key given in the parameter 
	 * Usage: | pressKey| key|
	 * @param Name of the key for. e.g BACK_SPACE
	 * @throws IOException
	 */
	public void pressKey(String key) throws IOException {
		try {
			Keys keyName = Keys.valueOf(key);
			Extend.info("Pressing the keyboard key: " + key);
			Actions action = new Actions(Ui.webDriver);
			action.keyDown(keyName);
		} catch (Exception exception) {
			Extend.error(exception.getMessage(), errorPath());
		}
	}

	/**
	 * Closes the browser instance associated with web driver.
	 * Usage: | closeBrowser|
	 * @throws IOException 
	 */
	public void closeBrowser() throws IOException {
		try {
			if (Ui.webDriver != null) {
				Ui.webDriver.close();
				Ui.webDriver.quit();
				Extend.info("Closing browser.");
				//wait(2);
				Ui.webDriver = null;
			}
		} catch (Exception exception) {
			System.err.println("Error in closing browser: " + exception.getMessage());
			Extend.error(exception.getMessage(), errorPath());
		}
	}
	
	/**
	 * Search for the given tag in the given element and return the first match
	 * Usage: | getSubElementWithTag|WebElement parentWebElement| tag|
	 * @param parentWebElement
	 * @param tag
	 * @return
	 */
	public WebElement getSubElementWithTag(WebElement parentWebElement, String tag) {
		List<WebElement> children = parentWebElement.findElements(By.tagName(tag));
		if (children.size() == 0) {
			System.err.println("Tag[" + tag + "]not found in parentWebElement");
			return null;
		}
		return children.get(0);
	}

	/**
	 * Returns the page title
	 * Usage: | getPageTitle |
	 * @return
	 */
	public String getPageTitle() {
		String pageTitle = Ui.webDriver.getTitle();
		Extend.info("Page title is :[" + pageTitle + "]");
		return pageTitle;
	}

	/**
	 * Waits for the element to be displayed on the UI
	 * @param element
	 * @throws IOException 
	 */
	private boolean waitForElementToBeDisplayed(WebElement element) throws IOException {
		try {
			WebDriverWait wait = new WebDriverWait(webDriver, timeout);
			wait.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.visibilityOf(element));
			Extend.info("Waited for element to be display : " + element.toString());
			return true;
		} catch (Exception e) {
			Extend.error("Waiting for element to be display failed " + e.getMessage(), errorPath());
			return false;
		}
	}
	
	/**
	 * Waits for the element to be displayed on the UI.
	 * Usage: | waitUntilElementDisplayed | elementIdentifier | findBy | timeOut |
	 * @param elementIdentifier - identifier of the WebElement
	 * @param findBy            - By object on the basis of which the elementIdentifier needs to be identified.
	 * @param timeOut           - in seconds
	 * @throws IOException
	 * @throws StopTestSlimException 
	 */
	public boolean waitUntilElementDisplayed(String elementIdentifier, String findBy, String timeOut) throws InterruptedException {
		By element = getByObjectOfElement(elementIdentifier, findBy);
		boolean isDisplayed = false;
		long end = System.currentTimeMillis()+(Integer.valueOf(timeOut)*1000);
		while (System.currentTimeMillis() < end) {
			try {
				if(Ui.webDriver.findElement(element).isDisplayed()) {
					isDisplayed = true;
					Extend.info("Waited until the element displayed : " + elementIdentifier);
					break;
				}
			} catch (Exception e) {
				
			}
			Thread.sleep(100);
		}
		return isDisplayed;
	}

	/**
	 * Waits for the element to be displayed on the UI.
	 * Usage: | waitForElementToBeDisplayed | elementIdentifier | findBy | timeOut |
	 * @param elementIdentifier - identifier of the WebElement
	 * @param findBy            - By object on the basis of which the elementIdentifier needs to be identified.
	 * @param timeOut           - in seconds
	 * @throws IOException
	 * @throws StopTestSlimException 
	 */
	public boolean waitForElementToBeDisplayed(String elementIdentifier, String findBy, String timeOut)
			throws IOException {
		WebDriverWait wait = new WebDriverWait(webDriver, Integer.parseInt(timeOut));
		try {
			WebElement el = wait.ignoring(StaleElementReferenceException.class)
					.until(ExpectedConditions
							.visibilityOfElementLocated(getByObjectOfElement(elementIdentifier, findBy)));
			Extend.info("Waited for element to be display : " + elementIdentifier);
			return el.isDisplayed();
		} catch (StaleElementReferenceException exception) {
			wait(5);
			WebElement el = wait.ignoring(StaleElementReferenceException.class)
					.withTimeout(Duration.ofSeconds(Integer.parseInt(timeOut))).until(ExpectedConditions
							.visibilityOfElementLocated(getByObjectOfElement(elementIdentifier, findBy)));
			Extend.info("Waited for element to be display : " + elementIdentifier);
			return el.isDisplayed();
		} catch (Exception exception) {
			Extend.error("Waiting for element to be display failed - " + exception.getMessage(), errorPath());
			return false;
		}
	}

	/**
	 * Waits for the element to be displayed on the UI.
	 * Usage: | waitForElementToBePresent | elementIdentifier | findBy | timeOut |
	 * @param elementIdentifier - identifier of the WebElement
	 * @param findBy            - By object on the basis of which the elementIdentifier needs to be identified.
	 * @param timeOut           - in seconds
	 * @throws IOException
	 */
	public WebElement waitForElementToBePresent(String elementIdentifier, String findBy, String timeOut)
			throws IOException {
		try {
			WebDriverWait wait = new WebDriverWait(webDriver, Integer.parseInt(timeOut));
			return wait.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.presenceOfElementLocated(getByObjectOfElement(elementIdentifier, findBy)));
		} catch (NumberFormatException e) {
			Extend.error("Waiting for element to be be present failed " + e.getMessage(), errorPath());
			return null;
		}
	}
	
	/**
	 * Waits for the element to be visible and enabled on the UI. 
	 * Usage: |waitForElementToBeEnabled| elementIdentifier| findBy| timeOut|
	 * @param elementIdentifier - identifier of the WebElement
	 * @param findBy            - By object on the basis of which the elementIdentifier needs to be identified.
	 * @param timeOut           - in seconds
	 * @throws IOException 
	 * @throws StopTestSlimException 
	 */
	public void waitForElementToBeEnabled(String elementIdentifier, String findBy, String timeOut) throws IOException {
		waitForElementToBeClickable(elementIdentifier, findBy, timeOut);
	}

	/**
	 * Waits for the element to be visible and enabled on the UI. 
	 * Usage: |waitForElementToBeClickable| elementIdentifier| findBy| timeOut|
	 * @param elementIdentifier - identifier of the WebElement
	 * @param findBy            - By object on the basis of which the elementIdentifier needs to be identified.
	 * @param timeOut           - in seconds
	 * @throws IOException 
	 * @throws StopTestSlimException 
	 */
	public WebElement waitForElementToBeClickable(String elementIdentifier, String findBy, String timeOut) throws IOException {
		try {
			WebDriverWait wait = new WebDriverWait(webDriver, Integer.parseInt(timeOut));
			return wait.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(getByObjectOfElement(elementIdentifier, findBy))));
		} catch (Exception e) {
			Extend.error("Waiting for element [" + elementIdentifier + "] to be clickable for " + timeOut
					+ " seconds is Failed - "+e.getMessage(), errorPath());
			return null;
			//throw new StopTestSlimException("Stopping execution element not found");
		}
	}

	/**
	 * Waits for the element to disappear from the UI. 
	 * Usage: | waitForElementToDisappear| elementIdentifier| findBy| timeOut|
	 * @param elementIdentifier - identifier of the WebElement
	 * @param findBy            - By object on the basis of which the elementIdentifier needs to be identified.
	 * @param timeOut           - in seconds
	 * @throws IOException
	 */
	public void waitForElementToDisappear(String elementIdentifier, String findBy, String timeOut) throws IOException {
		try {
			WebDriverWait wait = new WebDriverWait(webDriver, Integer.parseInt(timeOut));
			By invisiablewebElement = getByObjectOfElement(elementIdentifier, findBy);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(invisiablewebElement));
			Extend.info("Element has disappeared : "+elementIdentifier);
		} catch (Exception e) {
			Extend.info("Waiting for element to be disappear failed " + e.getMessage(), errorPath());
		}
	}

	/**
	 * Waits for the element to be invisible from the UI.
	 * Usage: | waitForElementToBeInvisible| elementIdentifier| findBy| timeOut|
	 * @param elementIdentifier
	 *            - identifier of the WebElement
	 * @param findBy
	 *            - By object on the basis of which the
	 *            <code>elementIdentifier</code> needs to be identified.
	 * @param timeOut
	 *            - in seconds
	 */
	public void waitForElementToBeInvisible(String elementIdentifier, String findBy, String timeOut) {
		try {
			
			List<WebElement> element = getElements(elementIdentifier, findBy);
			if (element.size() != 0) {
				WebDriverWait wait = new WebDriverWait(Ui.webDriver, timeout);
				wait.until(ExpectedConditions.invisibilityOf(getElement(elementIdentifier, findBy)));
			}
			Extend.info("Waited till the element has become invisible : "+elementIdentifier);
		} catch (NoSuchElementException e) {
			Extend.info("Page Loading is completed");
		}
	}
	
	/**
	 * Waits for the element to be visible and enabled on the UI. 
	 * Usage: |waitForTextToBePresentInElement| elementIdentifier| findBy| textValue|timeOut|
	 * @param elementIdentifier - identifier of the WebElement
	 * @param findBy            - By object on the basis of which the g<code>elementIdentifier</code> needs to be identified
	 * @param textValue         - text to be populated in the element
	 * @param timeOut           - in seconds
	 * @return true/false
	 */
	public String waitForTextToBePresentInElement(String elementIdentifier, String findBy, String textValue,
			String timeOut) {
		String isTextFound = null;
		try {
			WebDriverWait wait = new WebDriverWait(webDriver, Integer.parseInt(timeOut));
			Boolean isFound = wait.until(ExpectedConditions
					.textToBePresentInElementLocated(getByObjectOfElement(elementIdentifier, findBy), textValue));
			isTextFound = isFound.toString();
		} catch (NumberFormatException e) {
			Extend.error("Waiting for element [" + elementIdentifier + "] to populate '" + textValue
					+ "' value be displayed for " + timeOut + " seconds.");
		}
		return isTextFound;
	}

	/**
	 * Take screenshot of the page & saves it to specified location
	 * @param fileName - file path to save screenshot.
	 * @throws IOException
	 */
	public void takeScreenshot(String fileName) throws IOException {
		// take the screenshot at the end of every test
		File scrFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		// now save the screenshot to a file some place
		FileUtils.copyFile(scrFile, new File(fileName + ".png"));
		// Extend.info("Saving screenshot to: " + fileName);
	}

    /**
	 * Returns contents of list
	 * Usage: | getListContentsByIdentifier| elementIdentifier| findBy| seperator|
	 * @param elementIdentifier
	 * @param findBy
	 * @param seperator
	 * @return
	 */
	public String getListContentsByIdentifier(String elementIdentifier, String findBy, String seperator) {
		String listContents = "";
		WebElement list = getElement(elementIdentifier, findBy);
		List<WebElement> listElements = list.findElements(By.tagName("li"));
		boolean firstTime = true;
		for (WebElement listElement : listElements) {
			if (firstTime) {
				listContents = listElement.getText();
				firstTime = false;
			} else {
				listContents = listContents + seperator + listElement.getText();
			}
		}
		return listContents;
	}

	/**
	 * Verify if Given web Element is displayed
	 * Usage: | isElementDisplayed| elementIdentifier| findBy|
	 * @param elementIdentifier
	 * @param findBy
	 * @return
	 * @throws IOException 
	 */
	public boolean isElementDisplayed(String elementIdentifier, String findBy) throws IOException {
		try {
			WebElement element = waitForElementToBePresent(elementIdentifier, findBy, String.valueOf(timeout));
			highLightElement(element);
			boolean isDisplayed = element.isDisplayed();
			Extend.success("Element [" + elementIdentifier + "]  is Displayed : " + isDisplayed);
			return isDisplayed;
		} catch (Exception exception) {
			Extend.error("Element not displayed : " + exception.getMessage(), errorPath());
			return false;
		}
	}

	/**
	 * Verify if element is enabled
	 * Usage: | isElementEnabled| elementIdentifier| findBy|
	 * @param elementIdentifier
	 * @param findBy
	 * @return
	 */
	public String isElementEnabled(String elementIdentifier, String findBy) {
		String result = "fail";
		WebElement element = getElement(elementIdentifier, findBy);
		boolean enabled = element.isEnabled();
		if (enabled) {
			result = "pass";
		}
		return result;
	}

	/**
	 * Verify if element is Selected
	 * Usage: | isElementSelected| elementIdentifier| findBy|
	 * @param elementIdentifier
	 * @param findBy
	 * @return
	 */
	public String isElementSelected(String elementIdentifier, String findBy) {
		String result = "fail";
		WebElement element = getElement(elementIdentifier, findBy);
		boolean selected = element.isSelected();
		if (selected) {
			result = "pass";
		}
		return result;
	}

	/**
	 * Get count of the specfic HTML tag 
	 * Usage: | getTagCountInElement| elementIdentifier| findBy| tagNameForCount|
	 * @param elementIdentifier
	 * @param findBy
	 * @param tagNameForCount
	 * @return
	 */
	public String getTagCountInElement(String elementIdentifier, String findBy, String tagNameForCount) {
		WebElement element = getElement(elementIdentifier, findBy);
		List<WebElement> tagList = element.findElements(By.tagName(tagNameForCount));
		int tagCount = tagList.size();
		return Integer.toString(tagCount);
	}

	/**
	 * Select new browser tab *
	 * Usage: | selectBrowserTab| tabIndex|
	 * @param tabIndex
	 */
	public void selectBrowserTab(String tabIndex) {
		Set<String> winHandlesList = Ui.webDriver.getWindowHandles();
		int i = 1;
		for (String winHandle : winHandlesList) {
			if (i == Integer.parseInt(tabIndex)) {
				Ui.webDriver.switchTo().window(winHandle);
				Extend.info("Switched to window index #" + i + ". Window handle :: [" + winHandle + "].");
				Extend.info("Switched window title :: " + Ui.webDriver.getTitle());
				break;
			}
			i++;
		}
	}

	/**
	 * This method returns attribute values for given attribute of given element
	 * Usage: | getElementAttribute| elementIdentifier| findBy| attributeName|
	 * @param elementIdentifier
	 * @param findBy
	 * @param attributeName
	 * @return
	 * @throws IOException 
	 */
	public String getElementAttribute(String elementIdentifier, String findBy, String attributeName) throws IOException {
		try {
				WebElement element = waitForElementToBePresent(elementIdentifier, findBy, String.valueOf(timeout));
				String attributeValue = element.getAttribute(attributeName);
				highLightElement(element);
				Extend.info("Attribute [" + attributeName + "] of element [" + elementIdentifier + "] has value ["
						+ attributeValue + "]");
				return attributeValue;
		} catch (Exception e) {
			Extend.error(e.getMessage(), errorPath());
			return null;
		}
	}
	
	/**
	 * Working with frames This method returns frame based on framelist 
	 * Usage: |switchToFrame| elementIdentifier| findBy|
	 * @param elementIdentifier
	 * @param findBy
	 * @return
	 */
	public void switchToFrame(String elementIdentifier, String findBy) {
		WebElement element = getElement(elementIdentifier, findBy);
		Ui.webDriver.switchTo().frame(element);
	}
	
	/**
	 * This method will enable element based on javascript action mostly deals with visibility
	 * Usage: | jsChangeElementView| elementIdentifier| findBy| action|
	 * @param elementIdentifier
	 * @param findBy
	 * @param action
	 */
	public void jsChangeElementView(String elementIdentifier, String findBy, String action) {
		WebElement element = getElement(elementIdentifier, findBy);
		JavascriptExecutor executor = (JavascriptExecutor) Ui.webDriver;
		String js = null;
		
		switch (action) {
		case "block":
			js = "arguments[0].style.display='block';";
			executor.executeScript(js, element);
			Extend.info("block style has been executed successfully");
			break;
		case "visible":
			js = "arguments[0].style.visibility='visible';";
			executor.executeScript(js, element);
			Extend.info("visible style has been executed successfully");
			break;
		default:
			js = "arguments[0].style.display='none';";
			executor.executeScript(js, element);
			Extend.info("None style has been executed successfully");
			break;
		}
	}
    
	/**
	 * Changing class name for an element using javascript actions 
	 * Usage: |jsChangeClassName| elementIdentifier| findBy| className|
	 * @param elementIdentifier
	 * @param findBy
	 * @param className
	 */
	public void jsChangeClassName(String elementIdentifier, String findBy, String className) {
		WebElement element = getElement(elementIdentifier, findBy);
		JavascriptExecutor executor = (JavascriptExecutor) Ui.webDriver;
		String js = null;
		js = "arguments[0].className='" + className + "'";
		executor.executeScript(js, element);
		Extend.info("Class name has been changed successfully");
	}
      
	/**
	 * Trigger javaScript action to view HTML code 
	 * Usage: | jsInnerHtmlCode| findBy|element|
	 * @param findBy
	 * @param element
	 * @return InnerHtmlcode
	 */
	public String jsInnerHtmlCode(String findBy, String element) {
		JavascriptExecutor executor = (JavascriptExecutor) Ui.webDriver;
		String htmlCode;
		switch (findBy) {
		case "classname":
			htmlCode = (String) executor
					.executeScript("return document.getElementsByClassName" + "('" + element + "')[0].innerHTML");
			Extend.info("extract HTML code By id " + findBy);
			break;
		default:
			htmlCode = (String) executor
					.executeScript("return document.getElementById" + "('" + element + "').innerHTML");
			Extend.info("extract HTML code By id " + findBy);
			break;
		}
		return htmlCode;
	}
      
	/**
	 * Submitting form using javascript action 
	 * Usage: | jsFormSubmit| action|
	 * @param action
	 */
	public void jsFormSubmit(String action) {
		JavascriptExecutor executor = (JavascriptExecutor) Ui.webDriver;
		executor.executeScript("return document.forms." + action + ".submit();");
	}
      
	/**
	 * Printing verification message to report 
	 * Usage: | verificationMessage|message|
	 * @param message
	 */
	public void verificationMessage(String message) {
		Extend.verificationMessage(message);
	}
    
	/**
	 * Highlighting the element for easy identification during debug
	 */
	public void highLightElement(WebElement element) {
		try {
			((JavascriptExecutor) Ui.webDriver).executeScript("arguments[0].style.border='1px solid red'", element);
		} catch (Exception e) {
			Extend.info("Unable to highlight the element"+element+" : "+ e.toString());
		}
	}
	
	/**
	 * Highlighting the element for easy identification during debug
	 */
	public void highLightElement(List<WebElement> elements) {
		try {
			for (WebElement element : elements) {
				((JavascriptExecutor) Ui.webDriver).executeScript("arguments[0].style.border='1px solid red'", element);
			}
		} catch (Exception e) {
			Extend.info("Unable to highlight the element : "+ e.toString());
		}
	}
	
	/**
	 * To get the column number of the header in a web table dynamically
	 * Usage: | getHeaderColumnNumber|elementIdentifier|findBy|headerToFind|
	 * @param elementIdentifier
	 * @param findBy
	 * @param headerToFind
	 * @return
	 */
	public Integer getHeaderColumnNumber(String elementIdentifier, String findBy, String headerToFind) {
		List<WebElement> elements = getElements(elementIdentifier, findBy);
		int iteration = 0;

		String [] allTexts = new String[elements.size()];

		try {
			for (WebElement element : elements) {
				String text = element.getText().equals("") ? element.getAttribute("innerHTML") : element.getText();
				allTexts[iteration] = text;

				iteration++;

				if (text.trim().equalsIgnoreCase(headerToFind)) {
					Extend.info(text.trim() + " matches " + headerToFind + " on " + iteration);
					return iteration;
				}
			}
			Extend.info(headerToFind + " does not have matches in "+Arrays.toString(allTexts));
		} catch (Exception e) {
			Extend.info(e.getMessage());
		}

		return null;
	}
	
	/**
	 * To get the column number of the header in a web table dynamically
	 * Usage: | getTabluarColumnValues|headerElementIdentifier|valueElementIdentifier|findBy|
	 * @param headerElementIdentifier
	 * @param valueElementIdentifier
	 * @param findBy
	 * @return
	 */
	public HashMap<String, String> getTabluarColumnValues(String headerElementIdentifier, String valueElementIdentifier, String findBy) {
		List<WebElement> headerElements = getElements(headerElementIdentifier, findBy);
		List<WebElement> valueElements = getElements(valueElementIdentifier, findBy);
		
		int iteration = 0;
		
		String [] headers = new String [headerElements.size()];
		
		HashMap <String, String> tableWithValue = new HashMap<String, String>();
				
		try {
			for (WebElement element : headerElements) {
				headers[iteration] = element.getText().equals("") ? element.getAttribute("innerHTML") : element.getText();
				iteration++;
			}
			
			iteration = 0;
			
			for (WebElement element : valueElements) {
				tableWithValue.put(headers[iteration], element.getText().equals("") ? element.getAttribute("innerHTML") : element.getText());
				iteration++;
			}
		
			return tableWithValue;
		} catch (Exception e) {
			Extend.info(e.getMessage());
		}
		return null;
	}
	
	/**
	 * To refresh the page for the element to be visible in non-ajax page
	 * Usage: | refreshForElementToBeDisplayed |elementIdentifier|findBy |noOfTries | interval|
	 * @param elementIdentifier
	 * @param findBy
	 * @param noOfTries
	 * @throws IOException
	 * @throws InterruptedException 
	 */
	public void refreshForElementToBeDisplayed (String elementIdentifier, String findBy, String noOfTries, String interval) throws IOException, InterruptedException {
		Thread.sleep(5);
		for (int i = 1; i <= Integer.valueOf(noOfTries) ; i++) {
			if(isElementPresentNoReporting(elementIdentifier, findBy)) {
				break;
			} else {
				refresh();
				int waitInterval = interval.equals("") || interval.equals(null) ? (int)Math.floor(Math.random()*(10000-5000+1)+5000) : Integer.valueOf(interval)*1000;
				Thread.sleep(waitInterval);
			}
		}
	}
	
	/**
	 * Verify if Given web Element is not displayed
	 * Usage: | isElementNotDisplayed| elementIdentifier| findBy|
	 * @param elementIdentifier
	 * @param findBy
	 * @return
	 * @throws IOException 
	 */
	public boolean isElementNotDisplayed(String elementIdentifier, String findBy) throws IOException {
		try {
			List<WebElement> elements = getElements(elementIdentifier, findBy);
			
			if (elements.size() == 0) {
				Extend.success("Element Not Displayed successfully : " + elementIdentifier);
				return true;
			} else {
				for (WebElement element : elements) {
					if (element.isDisplayed()) {
						highLightElement(element);
						Extend.error("Element [" + elementIdentifier + "]  is Displayed in UI");
						return false;
					}
				}
				return true;
			}
		} catch (Exception exception) {
			Extend.error(exception.getMessage(), errorPath());
			return false;
		}
	}
	
	/**
	 * To upload the file using the Robot class. This mimick the real time user action of clicking
	 * the upload button and selecting path location and uploading
	 * Usage: | uploadFiles| filePath|
	 * @param filePath
	 * @throws IOException
	 */
	public void uploadFiles(String elementIdentifier, String findBy, String filePath, String waitTime) throws IOException {
		
		try {
			Extend.info("Uploading the file using the Robot class. FilePath : "+filePath);
			click(elementIdentifier, findBy);
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		    StringSelection str = new StringSelection(filePath);
		    clipboard.setContents(str, null);
		    wait(Integer.parseInt(waitTime));
		    
		    Robot robot = new Robot();
		    robot.keyPress(KeyEvent.VK_CONTROL);
		    robot.keyPress(KeyEvent.VK_V);
			wait(Integer.parseInt(waitTime));
		    robot.keyRelease(KeyEvent.VK_CONTROL);
		    robot.keyRelease(KeyEvent.VK_V);
			wait(Integer.parseInt(waitTime));
		    robot.keyPress(KeyEvent.VK_ENTER);
		    robot.keyRelease(KeyEvent.VK_ENTER);
			wait(Integer.parseInt(waitTime));
			Extend.info("Uploaded File successfully");
			
		} catch (Exception exception) {
			Extend.error(exception.getMessage(), errorPath());
		}
	}
	
	/**
	 * Find the element and type the text in it (for password to be hidden in reports)
	 * Usage: | sendSecuredKeys|elementIdentifier| findBy| text|
	 * 
	 * @param elementId - element identifier value
	 * @param findBy    - element identifier type e.g. id, xpath, css, class
	 * @param text      - values to type
	 * @throws IOException
	 */
	public void sendSecuredKeys(String elementIdentifier, String findBy, String text) throws IOException {
		try {
			WebElement element = waitForElementToBePresent(elementIdentifier, findBy, String.valueOf(timeout));
			if (element != null) {
				highLightElement(element);
				Extend.info("Sending keys : secured key to element : [" + elementIdentifier + "] using locator : "
						+ findBy);
				// element.clear();
				element.sendKeys(text);
			}
		} catch (Exception e) {
			Extend.error(
					"Sending keys failed : secured key to element : [" + elementIdentifier + "] -" + e.getMessage(),
					errorPath());
		}
	}

	
	public void growlTest() throws InterruptedException {

		JavascriptExecutor js = (JavascriptExecutor) webDriver;
		js.executeScript("if (!window.jQuery) {"
				+ "var jquery = document.createElement('script'); jquery.type = 'text/javascript';"
				+ "jquery.src = 'https://ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js';"
				+ "document.getElementsByTagName('head')[0].appendChild(jquery);" + "}");

		js.executeScript("$.getScript('https://the-internet.herokuapp.com/js/vendor/jquery.growl.js')");

		js.executeScript("$('head').append('<link rel=\"stylesheet\" "
				+ "href=\"https://the-internet.herokuapp.com/css/jquery.growl.css\" " + "type=\"text/css\" />');");

		js.executeScript("$.growl({ title: 'GET', message: '/' });");
		js.executeScript("$.growl.error({ title: 'ERROR', message: 'Some exception is coming' });");
		js.executeScript("$.growl.notice({ title: 'Notice', message: 'your notice message goes here' });");
		js.executeScript("$.growl.warning({ title: 'Warning!', message: 'your warning message goes here' });");
	}
  
	public static void main(String[] args) throws Exception {
		Ui ui = new Ui();
		try {

			ui.startTestCase("t1", "1", "", "no");
			ui.initialize("chrome", "");
			ui.navigateToUrl("https://jnj-auto.pandodev.in/");
			ui.sendKeys("//label[text()='User name:']/preceding-sibling::input", "xpath", "pandojnjauto@outlook.com");
			ui.sendKeys("//label[text()='Password:']/preceding-sibling::input", "xpath", "test@1234");
			ui.click("//button[contains(text(),'Log in')]", "xpath");
			ui.waitForElementToBeDisplayed("//main[@class='container-fluid main-content']", "xpath", "10");
			ui.navigateToUrl("https://jnj-auto.pandodev.in/v2/invoices/");
			ui.navigateBack();
			ui.wait(10);
			ui.click("//a[text()='Preview']", "xpath");
			ui.wait(10);
			String downloadFilepath = System.getProperty("user.dir") + File.separator + download + "\\generated.pdf";
			System.out.println(FileUtil.readPdfFile(downloadFilepath));
			Common.cleanFilesInDirectory(downloadFilepath);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ui.closeBrowser();
			Extend.end();
			System.out.println("test ends");
		}
	}
}
