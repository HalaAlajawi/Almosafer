package mosafer;
import java.awt.im.InputContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestMosafer extends TestData{
	WebDriver driver = new ChromeDriver();

	@BeforeTest
	public void mySetup() {

		driver.get("https://global.almosafer.com/ar");

		driver.manage().window().maximize();
	}
	@Test (priority = 1)
	public void currencySAR () {
	    WebElement currencySAR = driver.findElement(By.cssSelector(".sc-jTzLTM.eJkYKb.cta__button.cta__saudi.btn.btn-primary"));
	    currencySAR.click();


	}
	@Test (priority = 2)
	public void checkthedefualtlanguage () {
	    String actualLanguage = driver.findElement(By.tagName("html")).getDomAttribute("lang");
		Assert.assertEquals(actualLanguage, expectedlanguage);
		
		
	}
	@Test (priority = 3)
	public void checkcontactnumber () {
		WebElement contactnumber = driver.findElement(By.className("sc-cjHlYL"));
		String actualcontactnumber = contactnumber.getText();
	}
	@Test (priority = 4)
	public void verifyCurrencyIsSAR() {
	    WebElement currencyButton = driver.findElement(By.cssSelector("[data-testid='Header__CurrencySelector']"));
	    String actualCurrency = currencyButton.getText();
	    Assert.assertEquals(actualCurrency, "SAR", "Currency is not SAR as expected.");
	}
	@Test (priority = 5)
	public void verifyQitafLogoIsDisplayed() {
	    WebElement qitafLogo = driver.findElement(By.cssSelector("[data-testid='Footer__QitafLogo']"));
	    Assert.assertTrue(qitafLogo.isDisplayed(), "Qitaf logo is not displayed in the footer.");
	}
	@Test(priority = 6)
	public void verifyFlightsTabIsSelected() {
	    WebElement flightsTab = driver.findElement(By.cssSelector("[data-rb-event-key='flights']"));
	    String ariaSelected = flightsTab.getDomAttribute("aria-selected");
	    Assert.assertEquals(ariaSelected, "true", "Flights tab is not selected.");
	}
	@Test(priority = 7)
	public void verifyDefaultFlightDates() {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    // Expected day and month in Arabic
	    LocalDate today = LocalDate.now();
	    LocalDate expectedDeparture = today.plusDays(1);
	    LocalDate expectedReturn = today.plusDays(2);

	    DateTimeFormatter arabicMonthFormatter = DateTimeFormatter.ofPattern("MMMM", new Locale("ar"));
	    String expectedDepMonth = expectedDeparture.format(arabicMonthFormatter); 
	    String expectedDepDay = String.format("%02d", expectedDeparture.getDayOfMonth()); // "06"

	    String expectedReturnMonth = expectedReturn.format(arabicMonthFormatter);
	    String expectedReturnDay = String.format("%02d", expectedReturn.getDayOfMonth());

	    // Get departure elements
	    WebElement departureMonth = wait.until(ExpectedConditions.visibilityOfElementLocated(
	        By.cssSelector("div[data-testid='FlightSearchBox__FromDateButton'] span:nth-of-type(1)")));
	    WebElement departureDay = driver.findElement(By.cssSelector("div[data-testid='FlightSearchBox__FromDateButton'] span:nth-of-type(2)"));

	    // Get return elements
	    WebElement returnMonth = wait.until(ExpectedConditions.visibilityOfElementLocated(
	        By.cssSelector("div[data-testid='FlightSearchBox__ToDateButton'] span:nth-of-type(1)")));
	    WebElement returnDay = driver.findElement(By.cssSelector("div[data-testid='FlightSearchBox__ToDateButton'] span:nth-of-type(2)"));

	    // Combine
	    String actualDeparture = departureDay.getText().trim() + " " + departureMonth.getText().trim(); // e.g.
	    String actualReturn = returnDay.getText().trim() + " " + returnMonth.getText().trim();

	    String expectedDep = expectedDepDay + " " + expectedDepMonth;
	    String expectedRet = expectedReturnDay + " " + expectedReturnMonth;

	    System.out.println("Expected Departure: " + expectedDep);
	    System.out.println("Actual Departure: " + actualDeparture);
	    System.out.println("Expected Return: " + expectedRet);
	    System.out.println("Actual Return: " + actualReturn);

	    Assert.assertEquals(actualDeparture, expectedDep, "Departure date mismatch");
	    Assert.assertEquals(actualReturn, expectedRet, "Return date mismatch");
	}
	@Test(priority = 8)
	public void testLanguageByUrl() throws InterruptedException {
	    String[] languages = {"ar", "en"};
	    Random rand = new Random();
	    String chosenLanguage = languages[rand.nextInt(languages.length)];

	   
	    driver.get("https://www.almosafer.com/" + chosenLanguage );

	  
	    Thread.sleep(2000);

	   
	    String actualLanguage = driver.findElement(By.tagName("html")).getDomAttribute("lang");
	    System.out.println(" language on the page: " + actualLanguage);
	    Assert.assertEquals(actualLanguage, chosenLanguage);
	Thread.sleep(2000);
	}
	@Test(priority = 9)
	public void searchHotelWithRandomLocation() {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

	    // Step 1: Switch to hotel tab
	    WebElement hotelTab = driver.findElement(By.cssSelector("[data-rb-event-key='hotels']"));
	    hotelTab.click();
	    String lang = driver.findElement(By.tagName("html")).getDomAttribute("lang");
	    String selectedCity = "";
	    Random random = new Random();

	    // Step 4: Choose based on language using IF
	    if (lang.equalsIgnoreCase("en")) {
	        String[] citiesEN = {"Dubai", "Jeddah", "Riyadh"};
	        selectedCity = citiesEN[random.nextInt(citiesEN.length)];
	    } else if (lang.equalsIgnoreCase("ar")) {
	        String[] citiesAR = {"دبي", "جدة", "الرياض"};
	        selectedCity = citiesAR[random.nextInt(citiesAR.length)];
	    } 
	    WebElement locationInput = wait.until(ExpectedConditions.elementToBeClickable(
	            By.cssSelector("input[data-testid='AutoCompleteInput']")));
	        locationInput.click();
	        locationInput.clear();
	        locationInput.sendKeys(selectedCity);


	        WebElement firstSuggestion = wait.until(ExpectedConditions.elementToBeClickable(
	            By.cssSelector("[data-testid='AutoCompleteResultItem0']")));
	        firstSuggestion.click();
	    

	   
	}
	 @Test(priority = 10)
	    public void randomlySelectPplOption() {
	        WebElement PplDropdown = driver.findElement(By.cssSelector("select[data-testid='HotelSearchBox__ReservationSelect_Select']"));

	        Select select = new Select(PplDropdown);

	        List<String> validOptions = Arrays.asList("A", "B");

	        String randomValue = validOptions.get(new Random().nextInt(validOptions.size()));

	        select.selectByValue(randomValue);

	    }
	 @Test(priority = 11)
	    public void clickSearchButton() {
	        WebElement searchButton = driver.findElement(By.cssSelector("button[data-testid='HotelSearchBox__SearchButton']"));

	        searchButton.click();
	 }
	 




	
	

	
	
	
}

