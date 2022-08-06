package holybible.religious.christianity.entity;

import java.util.ArrayList;

public class FestivalInfo {

    private String month;

    private ArrayList<Festival> festivalList = new ArrayList<Festival>();
    
    public String getMonth() {
	return month;
    }
    
    public void addFestival(Festival festival)
    {
	festivalList.add(festival);
    }
    
    public ArrayList<Festival> getFestivalList()
    {
	return festivalList;
    }

    public void setMonth(String month) {
	this.month = month;
    }
    

    public static class Festival {

	private String date;
	private String weekday;
	private String festivalName;

	public String getDate() {
	    return date;
	}

	public void setDate(String date) {
	    this.date = date;
	}

	public String getWeekday() {
	    return weekday;
	}

	public void setWeekday(String weekday) {
	    this.weekday = weekday;
	}

	public String getFestivalName() {
	    return festivalName;
	}

	public void setFestivalName(String festivalName) {
	    this.festivalName = festivalName;
	}
    }

}
