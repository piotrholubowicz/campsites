package main;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Site {

	enum Type {
		RESERVE_AMERICA, RECREATION_GOV
	}

	private final String url;
	private final Type finderClass;
	private final Set<String> blacklist;

	public Site(String url, Type finderClass, String[] blacklist) {
		this.url = url;
		this.finderClass = finderClass;
		this.blacklist = new HashSet<>(Arrays.asList(blacklist));
	}

	public Site(String url, Type finderClass) {
		this(url, finderClass, new String[0]);
	}

	public String getUrl() {
		return url;
	}

	public Type getFinderClass() {
		return finderClass;
	}

	public Set<String> getBlacklist() {
		return blacklist;
	}

}
