public class ProductCell extends Product{

    private Seller seller;
    private int count;

    ProductCell(long id, String name) {
        super(id, name);
    }

    ProductCell(long id, String name, Seller seller, int count){

        super(id, name);

        if (seller == null){
            this.seller = new Seller(0, "-","-");
        }else {
            this.seller = seller;
        }
        this.count = count;

    }

    public int getCount() {
        return count;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString(){
        return "{" +  getId() + " " + getName() + " "  + getCount() + " " + getSeller().toString() + "}";
    }
}
