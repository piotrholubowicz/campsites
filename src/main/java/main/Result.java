package main;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Result {
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("ccc LLL/dd", new Locale("en"));

	private final LocalDate date;
	private final String campName;
	private final String siteLabel;

	public Result(LocalDate date, String campName, String siteLabel) {
		this.date = date;
		this.campName = campName;
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
		return String.format("%s %s: %s", campName, siteLabel, FORMATTER.format(date));
	}

}
