package BusinessLayer;

public class Client extends User  {
    private int noOrders;
    public Client(int id,String username, String password, String address) {
        super(id,username, password, address);
        this.setRole("Client");
    }

    public int getNoOrders() {
        return noOrders;
    }

    public void setNoOrders(int noOrders) {
        this.noOrders = noOrders;
    }
}
