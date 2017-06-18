package main;

import java.util.HashMap;
import java.util.Map;

import main.Site.Type;

public class Main {

	private static final Site[] SITES = {
			new Site("https://www.recreation.gov/camping/plaskett-creek-campground/r/"
					+ "campgroundDetails.do?contractCode=NRSO&parkId=70161", Type.RECREATION_GOV),
			new Site("https://www.recreation.gov/camping/kirk-creek-campground/r/"
					+ "campgroundDetails.do?contractCode=NRSO&parkId=71993", Type.RECREATION_GOV),
			new Site(
					"https://www.reserveamerica.com/camping/big-basin-redwoods-sp/r/"
							+ "facilityDetails.do?contractCode=CA&parkId=120009",
					Type.RESERVE_AMERICA, new String[] { "134B", "135B", "137B", "139B", "140B", "187S", "163S", "069H",
							"072H", "SQ Group 1" }), };
	// "https://www.recreation.gov/camping/pinnacles-campground/r/campgroundDetails.do?contractCode=NRSO&parkId=73984",
	// "https://www.recreation.gov/camping/point-reyes-national-seashore-campground/r/campgroundDetails.do?contractCode=NRSO&parkId=72393"

	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver", "/Users/piotrhol/Documents/chromedriver");
		Map<Type, Finder> finders = new HashMap<>();
		finders.put(Type.RECREATION_GOV, new RecreationGov());
		finders.put(Type.RESERVE_AMERICA, new ReserveAmerica());

		for (Site site : SITES) {
			Finder finder = finders.get(site.getFinderClass());
			finder.find(site);
		}

		for (Finder f : finders.values()) {
			f.close();
		}
	}

}
