package BusinessLayer;


import java.util.ArrayList;

public class CompositeProduct extends MenuItem{
    ArrayList<BaseProduct> products=new ArrayList<BaseProduct>();
    private float rating;
    private int price;
    private int calories;
    private int proteins;
    private int fats;
    private int sodium;

    public CompositeProduct(String title, ArrayList<BaseProduct> products, int rating, int price, int calories, int proteins, int fats, int sodium) {
        super(title);
        this.products = products;
        this.rating = rating;
        this.price = price;
        this.calories = calories;
        this.proteins = proteins;
        this.fats = fats;
        this.sodium = sodium;
    }

    public CompositeProduct(String title,ArrayList<BaseProduct> products,float rating){
        super(title);
        this.products=products;
        for(BaseProduct b:products){
            this.calories+=b.getCalories();
            this.fats+=b.getFats();
            this.price+=b.getPrice();
            this.proteins+=b.getProteins();
            this.rating=rating;
            this.sodium+=b.getSodium();
        }
    }

    public ArrayList<BaseProduct> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<BaseProduct> products) {
        this.products = products;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getProteins() {
        return proteins;
    }

    public void setProteins(int proteins) {
        this.proteins = proteins;
    }

    public int getFats() {
        return fats;
    }

    public void setFats(int fats) {
        this.fats = fats;
    }

    public int getSodium() {
        return sodium;
    }

    public void setSodium(int sodium) {
        this.sodium = sodium;
    }

    public void addBaseProduct(BaseProduct b){
        this.products.add(b);
        this.calories+=b.getCalories();
        this.fats+=b.getFats();
        this.rating+=b.getRating();
        this.proteins+=b.getProteins();
        this.sodium+=b.getSodium();
        this.price+=b.getPrice();
    }

    public void deleteBaseProduct(BaseProduct b){
        this.products.remove(b);
        this.calories-=b.getCalories();
        this.fats-=b.getFats();
        this.rating-=b.getRating();
        this.proteins-=b.getProteins();
        this.sodium-=b.getSodium();
        this.price-=b.getPrice();
    }

    //TO DO OVERRIDE HASHCODE AND EQUALS
}
