package main;

import java.time.LocalDate;

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

}
