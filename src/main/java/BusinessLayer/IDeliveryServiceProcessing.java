package BusinessLayer;



import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface IDeliveryServiceProcessing {
    public ArrayList<MenuItem> getMenuItems();
    public void setMenuItems(ArrayList<MenuItem> items);
    public ArrayList<Client> getClients();
    public void setClients(ArrayList<Client> clients);
    public HashMap<Order,ArrayList<MenuItem>> getHashMap();
    public void setHashMap(HashMap<Order,ArrayList<MenuItem>> hashMap);
    public void generateTimeReport(LocalTime startDate, LocalTime endDate) throws IOException;
    public void generateClientsReport(int noOrders,int price) throws IOException;
    public void generateProductsReport(int noOrders) throws IOException;
    public void generateDayReport(LocalDateTime date) throws IOException;
    public List<MenuItem> searchProductName(String name);
    public List<MenuItem> searchProductRating(float rating);
    public List<MenuItem> searchProductCalories(int calories);
    public List<MenuItem> searchProductFats(int fats);
    public List<MenuItem> searchProductProteins(int proteins);
    public List<MenuItem> searchProductSodium(int sodium);
    public List<MenuItem> searchProductPrice(int price);
    public ArrayList<MenuItem> searchCriteria(int price,float rating,int protein,int sodium,int fat,int calories);
    public void importProducts() throws FileNotFoundException;
    public void deleteItem(String name);
    public MenuItem searchItem(String name);
    public void addOrder(Order order,ArrayList<MenuItem> items);
    public Order searchOrder(int idOrder);
    public void editProduct(String name,BaseProduct item);
    public void generateBill(int id) throws IOException;

}
