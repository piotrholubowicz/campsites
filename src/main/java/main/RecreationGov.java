package main;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RecreationGov implements Finder {

	private static final DateTimeFormatter START_FORMATTER = DateTimeFormatter.ofPattern("ccc LLL dd uuuu",
			new Locale("en"));
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
	// days in 2w starting Monday we look at
	int[] OFFSETS = { 4, 5, 11, 12 };
	private final LocalDate LAST_DATE = LocalDate.of(2017, 9, 30);
	private final WebDriver driver;
	private String campName;

	public RecreationGov(WebDriver driver) {
		this.driver = driver;
	}

	@Override
	public List<Result> find(String startUrl) {
		LocalDate referenceDate = LocalDate.now().plusDays(4);
		// move to next Monday
		DayOfWeek dayOfWeek = referenceDate.getDayOfWeek();
		if (dayOfWeek != DayOfWeek.MONDAY) {
			referenceDate = referenceDate.plusDays(8 - dayOfWeek.getValue());
		}
		driver.get(startUrl);
		campName = driver.findElement(By.id("cgroundName")).getText();
		System.out.println(campName);
		driver.findElement(By.id("arrivalDate")).sendKeys(START_FORMATTER.format(referenceDate));
		new Select(driver.findElement(By.id("flexDates"))).selectByValue("4w");
		driver.findElement(By.id("filter")).click();
		// driver.findElement(By.id("campCalendar")).click();
		List<Result> results = new ArrayList<>();
		do {
			while (changeSites("Previous"))
				;
			do {
				results.addAll(searchAllSites(referenceDate));
			} while (changeSites("Next"));
			referenceDate = referenceDate.plusDays(14);
		} while (referenceDate.isBefore(LAST_DATE) && loadNextWeeks());
		System.out.println(String.format("Found %d days until %s", results.size(), FORMATTER.format(referenceDate)));
		for (Result r : results) {
			System.out.println(r);
		}
		return results;
	}

	private List<Result> searchAllSites(LocalDate referenceDate) {
		List<Result> results = new ArrayList<>();
		List<WebElement> rows = driver.findElements(By.xpath("//table[@id='calendar']//tr[./td[@class='sn']]"));
		for (WebElement row : rows) {
			String siteLabel = row.findElement(By.xpath(".//div[@class='siteListLabel']/a")).getText();
			List<WebElement> statuses = row.findElements(By.xpath("./td[contains(@class, 'status')]"));
			for (int offset : OFFSETS) {
				if (statuses.get(offset).getAttribute("class").contains("status a")) {
					results.add(new Result(referenceDate.plusDays(offset), campName, siteLabel));
				}
			}
		}
		return results;
	}

	private boolean changeSites(String text) {
		WebElement next = driver.findElement(By.xpath("//span[@class='pagenav']/a[contains(text(), '" + text + "')]"));
		if (next.getAttribute("class").equals("disabled")) {
			return false;
		}
		WebElement siteLabel = driver.findElement(By.xpath("//div[@class='siteListLabel']/a"));
		next.click();
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.stalenessOf(siteLabel));
		return true;
	}

	private boolean loadNextWeeks() {
		WebElement siteLabel = driver.findElement(By.xpath("//div[@class='siteListLabel']/a"));
		driver.findElement(By.id("nextWeek")).click();
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.stalenessOf(siteLabel));
		return true;
	}

}
