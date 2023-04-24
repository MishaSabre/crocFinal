import java.util.Date;

public class Sale {
    private long id;
    private long idSeller;
    private long idProduct;
    private int count;
    private Date date;

    Sale(long id, long idSeller, long idProduct, int count, Date date){
        this.id = id;
        this.count = count;
        this.date = date;
        this.idProduct = idProduct;
        this.idSeller = idSeller;
    }

    public long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public int getCount() {
        return count;
    }

    public long getIdProduct() {
        return idProduct;
    }

    public long getIdSeller() {
        return idSeller;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setIdProduct(long idProduct) {
        this.idProduct = idProduct;
    }

    public void setIdSeller(long idSeller) {
        this.idSeller = idSeller;
    }
}
