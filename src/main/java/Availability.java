/**
 * Класс описывающий сущность, которая содержит данные о том, какик товары, имеет определенный продавец
 */

public class Availability {
    private long idSeller;
    private long idProduct;
    private int price;
    private int count;

    Availability(long idProduct, long idSeller, int price, int count){
        this.idProduct = idProduct;
        this.idSeller = idSeller;
        this.price = price;
        this.count = count;
    }

    public long getIdSeller() {
        return idSeller;
    }

    public long getIdProduct() {
        return idProduct;
    }

    public int getCount() {
        return count;
    }

    public int getPrice() {
        return price;
    }

    public void setIdSeller(long idSeller) {
        this.idSeller = idSeller;
    }

    public void setIdProduct(long idProduct) {
        this.idProduct = idProduct;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
