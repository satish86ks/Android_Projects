package holybible.religious.christianity.entity;

public class NavDrawerItem {

    private String title;
    private int icon;
    private String count = "0";
    private int type = 0;
    public static final int TYPE_CHALISA = 0;
    public static final int TYPE_AARTI = 1;
    public static final int TYPE_MANTRA = 2;
    public static final int TYPE_CALENDAR = 3;
    public static final int TYPE_BABY_NAMES = 4;
    public static final int TYPE_QUOTES = 5;
    public static final int TYPE_BIBLE = 6;

    // boolean to set visiblity of the counter
    private boolean isCounterVisible = false;

    public NavDrawerItem() {
    }

    public NavDrawerItem(String title, int icon) {
        this.title = title;
        this.icon = icon;
    }

    public NavDrawerItem(String title, int icon, boolean isCounterVisible, String count) {
        this.title = title;
        this.icon = icon;
        this.isCounterVisible = isCounterVisible;
        this.count = count;
    }

    public NavDrawerItem(String title, int icon, int type) {
        this.title = title;
        this.icon = icon;
        this.type = type;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return this.title;
    }

    public int getIcon() {
        return this.icon;
    }

    public String getCount() {
        return this.count;
    }

    public boolean getVisibility() {
        return this.isCounterVisible;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public void setVisiblity(boolean isCounterVisible) {
        this.isCounterVisible = isCounterVisible;
    }
}
