package main;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Result {
	private final LocalDate date;
	private final String siteLabel;

	public Result(LocalDate date, String siteLabel) {
		this.date = date;
		this.siteLabel = siteLabel;
	}

	public String getSiteLabel() {
		return siteLabel;
	}

	public LocalDate getDate() {
		return date;
	}

	@Override
	public String toString() {
		return String.format("%s: %s", siteLabel, DateTimeFormatter.ISO_LOCAL_DATE.format(date));
	}

}
