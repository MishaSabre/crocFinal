public class Seller {
    private long id;
    private String surname;
    private String name;

    Seller(long id, String surname, String name){
        this.id = id;
        this.surname = surname;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString(){

        return "id - " + id + " Имя - " + name + ";";
    }
}
