package BusinessLayer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class Order {
    private int idClient;
    private int idOrder;
    private LocalDateTime date;
    private int totalPrice;
    private int noProducts;

    public int getNoProducts() {
        return noProducts;
    }

    public void setNoProducts(int noProducts) {
        this.noProducts = noProducts;
    }

    public Order(int idClient, int idOrder, LocalDateTime date) {
        this.idClient = idClient;
        this.idOrder = idOrder;
        this.date = date;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    public int calculateTotalPrice(ArrayList<MenuItem> menuItems){
        int totalPrice=0;
        for(MenuItem m:menuItems){
            if(m instanceof BaseProduct){
                totalPrice+=((BaseProduct) m).getPrice();
            }
            if(m instanceof CompositeProduct){
                totalPrice+=((CompositeProduct) m).getPrice();
            }
        }
        return totalPrice;

    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public boolean equals(Object obj) {
        if(this ==obj) return true;
        if(obj==null || getClass()!=obj.getClass()) return false;
        Order order=(Order)obj;
        return idOrder==order.idOrder && idClient==order.idClient && date.equals(order.date);

    }

    @Override
    public int hashCode() {
        return Objects.hash(idOrder,idClient,date);
    }
}
