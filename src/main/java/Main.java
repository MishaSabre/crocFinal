import com.google.gson.Gson;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

public class Main {

    public static void main(String[] args) {
        ArrayList<Seller> sellers = new ArrayList<>();
        sellers.add(new Seller(1, "kuk","kuk"));
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product(1,"pivo"));
        ArrayList<Availability> availabilities = new ArrayList<>();
        availabilities.add(new Availability(1,1,1, 1));
        ArrayList<Sale> sales = new ArrayList<>();
        sales.add(new Sale(1,1,1, 1,new Date(1,1,1)));
        ArrayList<ProductCell> productCells = new ArrayList<>();

        String path = "src/main/resources/input/";
        File inputSellers = new File(path + "sellers.json");
        File inputProducts = new File(path + "products.json");
        File inputAvailabilities = new File(path + "products-sellers.json");
        File inputSales = new File(path + "sales.json");

        readJson(inputSellers, sellers);
        readJson(inputProducts, products);
        readJson(inputAvailabilities, availabilities);

        maxProducts(products,availabilities,productCells, sellers);

        String fileName = "src/main/resources/output/products.xml";

        try {


            writeToXMLusingJDOM(productCells, fileName);
        }catch (IOException ex){
            ex.printStackTrace();
        }



    }

    public static void readJson(File file, ArrayList arrayList){
        try {

            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();

            if(arrayList.get(0) instanceof Seller) {
                arrayList.remove(0);
                while (line != null) {
                    arrayList.add(new Gson().fromJson(line, Seller.class));
                    line = reader.readLine();
                }
            }else if(arrayList.get(0) instanceof Product){
                arrayList.remove(0);
                while (line != null) {
                    arrayList.add(new Gson().fromJson(line, Product.class));
                    line = reader.readLine();
                }
            }
            else if(arrayList.get(0) instanceof Availability){
                arrayList.remove(0);
                while (line != null) {
                    arrayList.add(new Gson().fromJson(line, Availability.class));
                    line = reader.readLine();
                }
            }
            else if(arrayList.get(0) instanceof Sale){
                arrayList.remove(0);
                while (line != null) {
                    arrayList.add(new Gson().fromJson(line, Sale.class));
                    line = reader.readLine();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void maxProducts(ArrayList<Product> xProducts, ArrayList<Availability> yAvailabilities, ArrayList<ProductCell> zProductCell, ArrayList<Seller> sellers){

        for (Product i : xProducts){
            Seller seller = null;
            int maxCount = 0;
            for (Availability j : yAvailabilities){
                if(i.getId() == j.getIdProduct() && j.getCount() > maxCount){
                    maxCount = j.getCount();
                    for (Seller k : sellers) {
                        if( k.getId() == j.getIdSeller() ) {
                            seller = k;
                            System.out.println(seller);
                        }
                    }

                }
            }

            zProductCell.add(new ProductCell(i.getId(), i.getName(), seller, maxCount));

        }

    }

    private static void writeToXMLusingJDOM(ArrayList<ProductCell> productCells, String fileName) throws IOException {
        Document doc = new Document();
        // создаем корневой элемент с пространством имен
        doc.setRootElement(new Element("Products",
                Namespace.getNamespace("")));
        // формируем JDOM документ из объектов Student
        for (ProductCell productCell : productCells) {
            Element studentElement = new Element("Product",
                    Namespace.getNamespace(""));
            studentElement.setAttribute("product", String.valueOf(productCell.getName()));
            studentElement.addContent(new Element("seller",
                    Namespace.getNamespace("")).setText(productCell.getSeller().getName() + " "
                    + productCell.getSeller().getSurname()));
            studentElement.addContent(new Element("count",
                    Namespace.getNamespace("")).setText(String.valueOf(productCell.getCount())));

            doc.getRootElement().addContent(studentElement);
        }
        // Документ JDOM сформирован и готов к записи в файл
        XMLOutputter xmlWriter = new XMLOutputter(Format.getPrettyFormat());
        // сохнаряем в файл
        xmlWriter.output(doc, new FileOutputStream(fileName));
    }




}
