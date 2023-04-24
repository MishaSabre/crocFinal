/**
 * Класс описывающий сущность "товар"
 */


public class Product {
    private long id;
    private String name;

    Product(long id, String name){
        this.id = id;
        this.name = name;
    }

    public Product() {

    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
