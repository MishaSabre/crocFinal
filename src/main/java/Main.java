import com.google.gson.Gson;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

/**
 * Стартовый класс.
 */
public class Main {

    public static void main(String[] args) {
        /*
         * Создаем различные переменные для дальнейшей работы программы
         * в них мы будем вносить данные из файлов формата JSON
         */
        ArrayList<Seller> sellers = new ArrayList<>();
        sellers.add(new Seller(1, "kuk","kuk"));
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product(1,"pivo"));
        ArrayList<Availability> availabilities = new ArrayList<>();
        availabilities.add(new Availability(1,1,1, 1));
        ArrayList<Sale> sales = new ArrayList<>();
        sales.add(new Sale(1,1,1, 1,new Date(1,1,1)));
        ArrayList<ProductCell> productCells = new ArrayList<>();

        /*
          Задаем пути к файлам JSON
         */
        String path = "src/main/resources/input/";
        File inputSellers = new File(path + "sellers.json");
        File inputProducts = new File(path + "products.json");
        File inputAvailabilities = new File(path + "products-sellers.json");
        File inputSales = new File(path + "sales.json");


        /*
        Считываем данные из JSON файлов и заносим их в переменные
         */
        readJson(inputSellers, sellers);
        readJson(inputProducts, products);
        readJson(inputAvailabilities, availabilities);
        readJson(inputSales, sales);

        /*
        Методы, работа которого описанна ниже
         */
        maxProducts(products,availabilities,productCells, sellers);

        String fileName = "src/main/resources/output/products.xml";

        try {
            writeSellerProduct(productCells, fileName);
        }catch (IOException ex){
            ex.printStackTrace();
        }

        fileName = "src/main/resources/output/dates.xml";

        ArrayList<Sale> sales1 = dataCounter(sales);

        try {
            writeSale(sales1, fileName);
        }catch (IOException ex){
            ex.printStackTrace();
        }


    }

    /*
    Метод для демаршализации данных из JSON
    если первый эллемент в arrayList соотвествует определнному классу,
    то мы удаляем его и построчно считываем данные из файла
     */
    private static void readJson(File file, ArrayList arrayList){
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

    /*
    Метод для поиска наличия максимального количества товара у продавца
    для каждого товара мы ищем соответствующий в списке с расспределением товаров у продавцов
    и проходим по всем списку в поисках максимального, если ни один продавец не имеет данного товара,
    то count = 0, а имя и фамилия продавца равны "-"
     */
    private static void maxProducts(ArrayList<Product> xProducts, ArrayList<Availability> yAvailabilities, ArrayList<ProductCell> zProductCell, ArrayList<Seller> sellers){

        for (Product i : xProducts){
            Seller seller = null;
            int maxCount = 0;
            for (Availability j : yAvailabilities){
                if(i.getId() == j.getIdProduct() && j.getCount() > maxCount){
                    maxCount = j.getCount();
                    for (Seller k : sellers) {
                        if( k.getId() == j.getIdSeller() ) {
                            seller = k;

                        }
                    }

                }
            }

            zProductCell.add(new ProductCell(i.getId(), i.getName(), seller, maxCount));

        }

    }

    /*
    Метод для маршалинга в формат XML, просто берем наши объекты из массива и постепенно вносим их в файл
     */
    private static void writeSellerProduct(ArrayList<ProductCell> productCells, String fileName) throws IOException {
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

    /*
    Метод для маршалинга в формат XML, просто берем наши объекты из массива и постепенно вносим их в файл
     */
    private static void writeSale(ArrayList<Sale> sales, String fileName) throws IOException {
        Document doc = new Document();
        // создаем корневой элемент с пространством имен
        doc.setRootElement(new Element("Dates",
                Namespace.getNamespace("")));
        // формируем JDOM документ из объектов Student
        for (Sale sale : sales) {
            Element studentElement = new Element("Date",
                    Namespace.getNamespace(""));
            studentElement.setAttribute("date", String.valueOf(sale.getDate()));
            studentElement.addContent(new Element("count",
                    Namespace.getNamespace("")).setText(String.valueOf(sale.getCount())));

            doc.getRootElement().addContent(studentElement);
        }
        // Документ JDOM сформирован и готов к записи в файл
        XMLOutputter xmlWriter = new XMLOutputter(Format.getPrettyFormat());
        // сохнаряем в файл
        xmlWriter.output(doc, new FileOutputStream(fileName));
    }

    /*
    Метод для подсчета того, сколько в определенный день было всего проданно товаров
     */
    private static ArrayList<Sale> dataCounter(ArrayList<Sale> arrayList){
        ArrayList<Sale> output = new ArrayList<>();

        for(Sale j : arrayList){
            boolean flag = false;
            for (Sale k : output){
                if(j.getDate().equals(k.getDate())){
                    flag = true;

                    k.setCount(k.getCount() + j.getCount());

                }
            }
            if(!flag){
                output.add(j);
            }
        }


        return output;
    }


}
