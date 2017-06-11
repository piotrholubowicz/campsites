package main;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class Finder {

	DateTimeFormatter START_FORMATTER = DateTimeFormatter.ofPattern("ccc LLL dd uuuu", new Locale("en"));
	DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
	private final WebDriver driver;

	public Finder(WebDriver driver) {
		this.driver = driver;
	}

	public void find(String startUrl) {
		LocalDate referenceDate = LocalDate.now().plusDays(4);
		driver.get(startUrl);
		driver.findElement(By.id("arrivalDate")).sendKeys(START_FORMATTER.format(referenceDate));
		new Select(driver.findElement(By.id("flexDates"))).selectByValue("4w");
		driver.findElement(By.id("filter")).click();
		driver.findElement(By.id("campCalendar")).click();
		List<Result> available = findAvailable(referenceDate);
		System.out.println(String.format("Found %d weekends from %s to %s", available.size(),
				FORMATTER.format(referenceDate), FORMATTER.format(referenceDate.plusDays(14))));
	}

	private List<Result> findAvailable(LocalDate referenceDate) {
		List<WebElement> weekdays = driver.findElements(By.xpath("//th[class='calendar']/div[class='weekday']"));
		int offset = 0;
		for (WebElement weekday : weekdays) {
			if (weekday.getText().equals("F")) {
				break;
			} else {
				offset++;
			}
		}
		List<Result> results = new ArrayList<>();
		results.addAll(searchAllSites(referenceDate, offset));
		return results;
	}

	private List<Result> searchAllSites(LocalDate referenceDate, int offset) {
		List<Result> results = new ArrayList<>();
		List<WebElement> rows = driver.findElements(By.xpath("//table[@id='calendar']//tr[/td[@class='sn']]"));
		for (WebElement row : rows) {
			String siteLabel = row.findElement(By.xpath("./div[@class='siteListLabel']/a")).getText();
			List<WebElement> statuses = driver.findElements(By.xpath("./td[contains(@class, 'status')]"));

		}
		return results;
	}

	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "/Users/piotrhol/Documents/chromedriver");
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Finder f = new Finder(driver);
		f.find("https://www.recreation.gov/camping/kirk-creek-campground/r/campgroundDetails.do?contractCode=NRSO&parkId=71993");
		Thread.sleep(100000);
		driver.close();
	}

}
