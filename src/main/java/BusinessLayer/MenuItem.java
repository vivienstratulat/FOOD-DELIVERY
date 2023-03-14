package BusinessLayer;

import java.util.Objects;

public class MenuItem {
    private String title;
    private int noOrders;

    public MenuItem(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNoOrders() {
        return noOrders;
    }

    public void setNoOrders(int noOrders) {
        this.noOrders = noOrders;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    @Override
    public boolean equals(Object obj) {
        if(this ==obj) return true;
        if(obj==null || getClass()!=obj.getClass()) return false;
        MenuItem other=(MenuItem) obj;
        //return title.equals(item.title) && noOrders==item.noOrders;
        return Objects.equals(title,other.title);
    }
}
