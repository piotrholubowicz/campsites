package main;

import java.util.List;

public interface Finder {

	List<Result> find(String startUrl);

}