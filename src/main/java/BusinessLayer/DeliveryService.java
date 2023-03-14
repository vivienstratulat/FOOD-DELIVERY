package BusinessLayer;

import com.sun.scenario.effect.impl.prism.PrDrawable;

import javax.security.auth.login.AccountExpiredException;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DeliveryService extends Observable implements IDeliveryServiceProcessing {
    ArrayList<Client> clients=new ArrayList<>();
    ArrayList<MenuItem> menuItems=new ArrayList<>();
    HashMap<Order, ArrayList<MenuItem>> hashMap=new HashMap<>();
    private int billNumber;

    public DeliveryService(ArrayList<MenuItem> menuItems, ArrayList<Client> clients, HashMap<Order, ArrayList<MenuItem>> hashMap) {
        this.menuItems = menuItems;
        this.clients = clients;
        this.hashMap = hashMap;
    }


    /**
     * <p>
     *     Returneaza produsele din meniu
     * </p>
     *
     */
    @Override
    public ArrayList<MenuItem> getMenuItems() {
        return this.menuItems;
    }

    /**
     * <p>
     *     Seteaza produsele meniului
     * </p>
     * @param items produsele din meniu
     */
    @Override
    public void setMenuItems(ArrayList<MenuItem> items) {
    this.menuItems=items;
    }

    /**
     * <p>
     * Returneaza lista de clienti
     * </p>
     */
    @Override
    public ArrayList<Client> getClients() {
        return this.clients;
    }

    /**
     * <p>
     *     Seteaza lista de clienti
     *
     * </p>
     * @param clients lista de clienti
     */
    @Override
    public void setClients(ArrayList<Client> clients) {
        this.clients=clients;

    }

    /**
     * <p>
     *     Returneaza hashmap-ul
     * </p>
     */
    @Override
    public HashMap<Order, ArrayList<MenuItem>> getHashMap() {
        return this.hashMap;
    }


    /**
     * <p>
     *     Seteaza hashmapul
     * </p>
     * @param hashMap hashmap ul
     */
    @Override
    public void setHashMap(HashMap<Order, ArrayList<MenuItem>> hashMap) {
this.hashMap=hashMap;
    }

    /**
     * <p>
     *     Meotda ce genereaza un raport cu toate produsele cumparate in acel interval
     * </p>
     * @param startDate data de inceput
     * @param endDate data de final
     * @pre numarul de produse mai mare de 0
     * @post numarul de produse mai mare de 0
     * @invariant numarul de produse mai mare de 0
     */
    @Override
    public void generateTimeReport(LocalTime startDate, LocalTime endDate) throws IOException {

        FileWriter fw=new FileWriter("raportTimp.txt",true);
        fw.write("RAPORT: COMENZI INTRE: " + startDate + "SI DATA" + endDate + "\n");
       //am decupar acele comenzi ale caror data se afla in acel interval
        List<Order> comenziTimp=hashMap.keySet().stream().filter(e-> e.getDate().getHour()>=startDate.getHour()&&e.getDate().getMinute()>=startDate.getMinute()&&e.getDate().getHour()<=endDate.getHour()&& e.getDate().getMinute()<=endDate.getMinute()).collect(Collectors.toList());

        for(Order or: comenziTimp)
        {
            System.out.println("offff");
            System.out.println("id client din intervalul orar: "+or.getIdOrder());}
        comenziTimp.stream().forEach(e->{
            try {
                System.out.println("generare raport timp");
                fw.write("ORDER ID" + e.getIdOrder() + "CLIENT ID " + e.getIdClient() + "DATE " + e.getDate() + "\n");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        //afisez si pe consola comenzile ce se afla in interval
        comenziTimp.stream().forEach(e->System.out.println(  "ORDER ID" + e.getIdOrder() + "CLIENT ID " + e.getIdClient() + "DATE " + e.getDate() + "\n"));
        fw.close();

    }

    /**
     * <p>
     *     Metoda ce genereaza raport cu clientii care au avut un anumit nr de comenzi de o anumita suma
     * </p>
     * @param noOrders numarul de comenzi
     * @param amount suma peste care se verifica
     * @pre numarul de produse mai mare de 0
     * @post numarul de produse mai mare de 0
     * @invariant numarul de produse mai mare de 0
     *
     */

    @Override
    public void generateClientsReport(int noOrders, int amount) throws IOException {
    FileWriter fw=new FileWriter("raportClienti.txt",true);
    fw.write("RAPORT CLIENTI CU MAI MULT DE " + noOrders + " si o suma mai mare de " + amount + "\n");
    ArrayList<Client> c=new ArrayList<>();
    List<Client> listaClienti=clients.stream().filter(cl -> cl.getNoOrders()>=noOrders).collect(Collectors.toList());
    hashMap.keySet().stream().forEach(order->listaClienti.stream().forEach(cl->{
        if(order.getIdClient()==cl.getIdUser()){
            if(order.getTotalPrice()>=amount)
                c.add(cl);
        }
    }));
    c.stream().distinct().forEach(client -> {
        try {
            fw.write("CLIENTUL: " + client.getIdUser() + " are mai mult de " + noOrders + " nr comenzi si una din ele are val mai mare de: " + amount + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    });
    fw.close();
    assert invariant();


    }

    /**
     * <p>
     *     Metoda ce genereaza un raport cu produse ce au fost cumparate de un anumit nr de ori
     * </p>
     *
     * @param noOrders numarul de comenzi
     * @pre numarul de produse mai mare de 0
     * @post numarul de produse mai mare de 0
     */
    @Override
    public void generateProductsReport(int noOrders) throws IOException {
        FileWriter fw=new FileWriter("RaportProduse.txt",true);
        fw.write("RAPORT: PRODUSE CE AU FOST CUMPARATE MAI MULT DE : " + noOrders + "ori\n ");
        List<MenuItem> items=getMenuItems().stream().filter(item->item.getNoOrders()>=noOrders).collect(Collectors.toList());
        items.stream().forEach(item->{
            try {
                fw.write("PRODUSUL: " + item.getTitle() + "A FOST CUMPARAT DE MAI MULT DE " + noOrders + " ori\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        fw.close();
       assert invariant();

    }

    /**
     *<p>
     *     Metoda ce genereaza un raport cu toate produsele ce au fost cumparate din acea zi
     *</p>
     * @param date data de unde ne intereseaza comenzile
     * @pre numarul de produse mai mare de 0
     * @post numarul de produse mai mare de 0
     * @invariant numarul de produse mai mare de 0
     */
    @Override
    public void generateDayReport(LocalDateTime date) throws IOException {
    FileWriter fw=new FileWriter("raportZilnic.txt");
    fw.write("RAPORT: PRODUSE CE AU FOST CUMPARATE IN DATA:" + date + " SI DE CATE ORI AU FOST CUMPARATE ");
    menuItems.stream().forEach(items->{
        hashMap.keySet().stream().forEach(order->hashMap.get(order).stream().forEach(item2->{
            if(order.getDate().getDayOfMonth()==date.getDayOfMonth()&&item2.getTitle()==items.getTitle()){
                System.out.println(items.getNoOrders());
                try {

                    fw.write("\nPRODUSUL: " + item2.getTitle() + "A FOST CUMPARAT IN ZIUA " + date.getDayOfMonth() + " si a fost cumparat de " + items.getNoOrders() + " ori\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }));
    });
    fw.close();
    assert invariant();

    }

    /**
     * <p>
     *     Metoda pt cautarea unui produs dupa nume
     * </p>
     * @param name numele produsului
     * @pre numarul de produse mai mare de 0
     * @post numarul de produse mai mare de 0
     * @invariant numarul de produse mai mare de 0 name diferit de null
     * @return List <MenuItem>
     */
    @Override
    public List<MenuItem> searchProductName(String name) {
        assert (name!=null);
        List<MenuItem> items = menuItems.stream().filter(e -> e.getTitle().matches("(.*)" + name + "(.*)")).collect(Collectors.toList());
        return items;
    }

    /**
     * <p>Metoda pt cautarea produselor dupa rating</p>
     * @param rating ce nota are
     * @pre numarul de produse mai mare de 0
     * @invariant numarul de produse mai mare de 0 rating mai mare sau egal 0;
     * @return List <MenuIteM>
     */

    @Override
    public List<MenuItem> searchProductRating(float rating) {
        assert (rating>=0);
        List<MenuItem> items = menuItems.stream().filter(e -> e instanceof BaseProduct && ((BaseProduct) e).getRating() == rating || e instanceof CompositeProduct && ((CompositeProduct) e).getRating() == rating).collect(Collectors.toList());
       assert invariant();
        return items;
    }

    /**
     * <p>Metoda pt cautarea produselor dupa calorii</p>
     * @param calories numarul de calorii
     * @pre numarul de produse mai mare de 0
     * @post numarul de produse mai mare de 0
     * @invariant numarul de produse mai mare de 0 calories mai mare sau egal 0
     * @return List < MenuItem >
     */

    @Override
    public List<MenuItem> searchProductCalories(int calories) {
        assert calories>=0;
        List<MenuItem> items = menuItems.stream().filter(e -> e instanceof BaseProduct && ((BaseProduct) e).getCalories() == calories || e instanceof CompositeProduct && ((CompositeProduct) e).getCalories() == calories).collect(Collectors.toList());
        assert  invariant();
        return items;
    }

    /**
     * <p>Metoda pt cautarea produselor dupa grasimi</p>
     * @param fats cantitatea de grasimi
     * @pre numarul de produse mai mare de 0
     * @post numarul de produse mai mare de 0
     * @invariant numarul de produse mai mare de 0
     * @return List < MenuItem>
     */

    @Override
    public List<MenuItem> searchProductFats(int fats) {
        assert(fats>=0);
        List<MenuItem> items = menuItems.stream().filter(e -> e instanceof BaseProduct && ((BaseProduct) e).getFats() == fats || e instanceof CompositeProduct && ((CompositeProduct) e).getFats() == fats).collect(Collectors.toList());
      assert invariant();
        return items;
    }

    /**
     * <p>Metoda pt cautarea produselor dupa proteine</p>
     * @param proteins cantitatea de proteine
     * @pre numarul de produse mai mare de 0
     * @post numarul de produse mai mare de 0
     * @invariant numarul de produse mai mare de 0  proteins mai mare de 0
     * @return List<MenuItem>
     */
    @Override
    public List<MenuItem> searchProductProteins(int proteins) {
        assert proteins>=0;
        List<MenuItem> items = menuItems.stream().filter(e -> e instanceof BaseProduct && ((BaseProduct) e).getProteins() == proteins || e instanceof CompositeProduct && ((CompositeProduct) e).getProteins() == proteins).collect(Collectors.toList());
        assert invariant();
        return items;
    }

    /**
     * <p>Metoda pt cautarea produselor dupa sodidu</p>
     * @pre numarul de produse mai mare de 0
     * @post numarul de produse mai mare de 0
     * @invariant numarul de produse mai mare de 0 sodiu mai mare de 0
     * @param sodium cantitatea de sodiu
     * @return List<MenuItem>
     */
    @Override
    public List<MenuItem> searchProductSodium(int sodium) {
        assert sodium>=0;
        List<MenuItem> items = menuItems.stream().filter(e -> e instanceof BaseProduct && ((BaseProduct) e).getSodium() == sodium || e instanceof CompositeProduct && ((CompositeProduct) e).getSodium() == sodium).collect(Collectors.toList());
       assert invariant();
        return items;
    }

    /**
     * <p> MEtoda pt cautarea produselor dupa pret</p>
     * @param price pretul produsului
     * @pre numarul de produse mai mare de 0
     * @post numarul de produse mai mare de 0
     * @invariant numarul de produse mai mare de 0
     * @return List<MenuItem>
     */
    @Override
    public List<MenuItem> searchProductPrice(int price) {
        assert price>=0;
        List<MenuItem> items = menuItems.stream().filter(e -> e instanceof BaseProduct && ((BaseProduct) e).getPrice() == price || e instanceof CompositeProduct && ((CompositeProduct) e).getPrice() == price).collect(Collectors.toList());
       assert invariant();
        return items;
    }

    @Override
    public ArrayList<MenuItem> searchCriteria( int price, float rating, int protein, int sodium, int fat, int calories) {
        ArrayList<MenuItem> items = new ArrayList<MenuItem>();
        for(MenuItem e : menuItems){
            boolean ok=true;
            if(e instanceof BaseProduct){
               // if(name != null && !(e.getTitle().matches("(.*)"+name+"(.*)")))
                //    ok=false;
                if(price != 0 && price != ((BaseProduct) e).getPrice())
                    ok=false;
                if(rating != 0 && rating != ((BaseProduct) e).getRating())
                    ok=false;
                if(protein != 0 && protein != ((BaseProduct) e).getProteins())
                    ok=false;
                if(fat != 0 && fat !=((BaseProduct) e).getFats())
                    ok=false;
                if(calories!=0 && calories!=((BaseProduct) e).getCalories())
                    ok=false;
            }
            if(e instanceof CompositeProduct){
              //  if(name != null && !(e.getTitle().matches("(.*)"+name+"(.*)")))
              //      ok=false;
                if(price != 0 && price != ((CompositeProduct) e).getPrice())
                    ok=false;
                if(rating != 0 && rating != ((CompositeProduct) e).getRating())
                    ok=false;
                if(protein != 0 && protein != ((CompositeProduct) e).getProteins())
                    ok=false;
                if(fat != 0 && fat !=((CompositeProduct) e).getFats())
                    ok=false;
                if(calories!=0 && calories!=((CompositeProduct) e).getCalories())
                    ok=false;
            }
            if(ok==true){
                items.add(e);
            }
        }
        return items;
    }

    /**
     * <p>MEtoda pt importarea produselor din fisierul .csv</p>
     * @pre numarul de produse mai mare de 0
     * @post numarul de produse mai mare de 0
     * @invariant numarul de produse mai mare de 0
     */

    @Override
    public void importProducts() throws FileNotFoundException {
        ArrayList<MenuItem> items=new ArrayList<>();
        String path="C:\\Users\\VIVIEN STRATULAT\\OneDrive\\Desktop\\products.csv";
        BufferedReader br=new BufferedReader(new FileReader(path));
        Stream<String> str=br.lines();
        str.skip(1).distinct().map(e-> Arrays.asList(e.split(","))).forEach(e2->items.add(new BaseProduct(e2.get(0), Float.parseFloat(e2.get(1)), Integer.parseInt(e2.get(2)), Integer.parseInt(e2.get(3)), Integer.parseInt(e2.get(4)), Integer.parseInt(e2.get(5)), Integer.parseInt(e2.get(6)))));
         this.menuItems= (ArrayList<MenuItem>) items.stream().distinct().collect(Collectors.toList());

     /*   for(MenuItem m:menuItems){
            System.out.println(m.getTitle());
        }*/
      //  this.setMenuItems(menuItems2);
        //return menuItems2;
        assert invariant();
    }

    /**
     * <p>Metoda pt stergerea unui produs</p>
     * @param name numele produsului ce va fi sters
     * @pre numarul de produse mai mare de 0
     * @post numarul de produse mai mare de 0
     * @invariant numarul de produse mai mare de 0 name diferit de null
     */
    @Override
    public void deleteItem(String name) {
        assert name!=null;
            MenuItem itemToDelete=null;
            boolean ok=false; //fals daca nu am gasit elementul de sters
            for(int i=0;i<menuItems.size();i++){
                if(menuItems.get(i).getTitle().equals(name)){
                itemToDelete=menuItems.get(i);
                menuItems.remove(i);
                ok=true;
                break;
            }}
        assert itemToDelete != null;
        System.out.println("Itemul ce a fost sters: "+itemToDelete.getTitle());
            //in cazul in care elementul ce l am sters era base product,stergem si produsele compuse ce-l contineau
            if(ok && itemToDelete instanceof BaseProduct){
                for (int i=0;i<menuItems.size();i++){
                    if(menuItems.get(i) instanceof CompositeProduct){
                        System.out.println("PRodusul compus din care stergem "+menuItems.get(i).getTitle());
                        for(int j=0;j<((CompositeProduct)menuItems.get(i)).getProducts().size();j++){
                            {
                                System.out.println(((CompositeProduct) menuItems.get(i)).getProducts().get(j).getTitle());
                                System.out.println(itemToDelete.getTitle());
                                if(((CompositeProduct) menuItems.get(i)).getProducts().get(j).getTitle().equals(itemToDelete.getTitle())){
                                    menuItems.remove(i);
                                    i--;
                                }
                            }
                        }
                    }
                }
            }
           assert invariant();
    }

    /**
     * <p><Metoda pt a gasi anumite produse</p>
     * @param name numele produsului ce se cauta
     * @pre numarul de produse mai mare de 0
     * @post numarul de produse mai mare de 0
     * @invariant  name diferit de null
     * @return MenuItem
     */
    @Override
    public MenuItem searchItem(String name) {
        assert name!=null;
       MenuItem item=null;
               for(MenuItem m:menuItems){
                   if(m.getTitle().equals(name)){
                       item=m;

                       m.setNoOrders(m.getNoOrders()+1);
                   }
               }
               return item;
    }

    /**
     * <p>Metoda pt a adauga o comanda noua</p>
     * @param order comanda
     * @param items produsele comenzii
     * @pre numarul de produse mai mare de 0
     * @post numarul de produse mai mare de 0
     * @invariant numarul de produse mai mare de 0
     */
    @Override
    public void addOrder(Order order, ArrayList<MenuItem> items) {
        order.setTotalPrice(order.calculateTotalPrice(items));
        order.setNoProducts(items.size());
        clients.get(order.getIdClient()).setNoOrders(clients.get(order.getIdClient()).getNoOrders()+1);
        System.out.println("CLIENTUL: " + order.getIdClient() + " are " + clients.get(order.getIdClient()).getNoOrders() + " comenzi\n");



        this.hashMap.put(order,items);
        System.out.println();
        for(Order o:hashMap.keySet()){
            //System.out.println("nu exista comenzi???");
            System.out.println();
            System.out.println("ID order: "+o.getIdOrder()+ " cu produsele: ");
            for(MenuItem item:hashMap.get(o)){
                System.out.print(item.getTitle()+", ");

            }
        }
        assert invariant();

    }

    /**
     * <p> Metoda pt cautarii unor comenzi</p>
     * @param idOrder id ul comenzii
     * @pre numarul de produse mai mare de 0
     * @post numarul de produse mai mare de 0
     * @invariant numarul de produse mai mare de 0
     * @return Order
     */

    @Override
    public Order searchOrder(int idOrder) {
        assert idOrder>0;
        Order order=null;
        for(Order or:hashMap.keySet()){
            if(or.getIdOrder()==idOrder)
                order=or;
        }
        return order;
    }

    /**
     * <p>Metoda pt modificarea unui produs</p>
     * @param name numele produsului ce se doresete a fi modificat
     * @param item produsul cu noile valori
     * @pre numarul de produse mai mare de 0
     * @post numarul de produse mai mare de 0
     * @invariant numarul de produse mai mare de 0
     */
    @Override
    public void editProduct(String name, BaseProduct item) {
        assert name!=null;
        //stergem elementul vechi si il adaugam pe cel nou introdus=modificare
        boolean ok=false; //fals daca nu am gasit elementul ce trebuia sa-l editez
        MenuItem itemToEdit=null;
        for(int i=0;i< menuItems.size();i++){
            if(menuItems.get(i).getTitle().equals(name)){
                if(menuItems.get(i) instanceof BaseProduct){
                    itemToEdit=menuItems.get(i);
                    menuItems.remove(i);
                    menuItems.add(item);
                    ok=true;

                }
                else {
                    System.out.println("Nu se poate edita un produs compus");
                    JOptionPane.showMessageDialog(null,"NU SE POATE MODIFICA PRODUSUL COMPUS");

                }
                break;
            }
        }
        if(ok==true){
            for(int i=0;i<menuItems.size();i++){
                if(menuItems.get(i) instanceof CompositeProduct){
                    for(int j=0;j<((CompositeProduct) menuItems.get(i)).getProducts().size();j++){
                        if(((CompositeProduct) menuItems.get(i)).getProducts().get(j).getTitle().equals(itemToEdit.getTitle())){
                            ((CompositeProduct) menuItems.get(i)).deleteBaseProduct((BaseProduct) itemToEdit);
                            ((CompositeProduct) menuItems.get(i)).addBaseProduct(item);
                        }
                    }
                }
            }
        }
      assert invariant();
    }

    /**
     * <p>Metoda ce genereaza o factura cu informatii legate de comenzi</p>
     * @param id id ul comenzii
     * @pre numarul de produse mai mare de 0
     * @post numarul de produse mai mare de 0
     * @invariant numarul de produse mai mare de 0
     */

    @Override
    public void generateBill(int id) throws IOException {
        assert id>0;
        for(Order or:hashMap.keySet()){
            if(or.getIdOrder()==id){
                String bill="BILL "+billNumber;
                float totalPrice=0;


                FileWriter fw=new FileWriter(bill,true);
                fw.write("BILL NUMBER: "+billNumber+" ORDER ID: "+or.getIdOrder()+"\n");
                fw.write("CLIENT ID: "+or.getIdClient()+"\n");
                fw.write("DATA: "+or.getDate()+"\n");

                for(MenuItem item:hashMap.get(or)){
                    if(item instanceof BaseProduct){
                        fw.write(item.getTitle()+" "+ ((BaseProduct)item).getPrice()+"\n");
                        totalPrice+=((BaseProduct) item).getPrice();
                    }
                    if(item instanceof CompositeProduct){
                        fw.write(item.getTitle()+" "+((CompositeProduct) item).getPrice()+"\n");
                        totalPrice+=((CompositeProduct) item).getPrice();
                    }
                }
                fw.write("TOTAL: "+totalPrice+"\n");
                fw.write("\n");
                fw.close();
            }
        }
        assert this.menuItems.size()>0;
    }

    /**
     * <p>
     * Metoda invariant
     *
     * </p>
     *
     *
     */

    public boolean invariant(){
        if(this.menuItems.size()>0) return true;
        return false;
    }
}