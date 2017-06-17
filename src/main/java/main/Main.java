package main;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Main {

	private static final String[] RECREATION_GOV_SITES = {
			"https://www.recreation.gov/camping/plaskett-creek-campground/r/campgroundDetails.do?contractCode=NRSO&parkId=70161",
			"https://www.recreation.gov/camping/kirk-creek-campground/r/campgroundDetails.do?contractCode=NRSO&parkId=71993",
			// "https://www.recreation.gov/camping/pinnacles-campground/r/campgroundDetails.do?contractCode=NRSO&parkId=73984",
			// "https://www.recreation.gov/camping/point-reyes-national-seashore-campground/r/campgroundDetails.do?contractCode=NRSO&parkId=72393"
	};

	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver", "/Users/piotrhol/Documents/chromedriver");
		for (String site : RECREATION_GOV_SITES) {
			WebDriver driver = new ChromeDriver();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			Finder f = new RecreationGov(driver);
			f.find(site);
			driver.quit();
		}
	}

}
