package carsharing.model;

public class AccountModel {
    int id;
    String name;

    public AccountModel(){ }

    public AccountModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public AccountModel setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "AccountModel{" +
               "id=" + id +
               ", name='" + name + '\'' +
               '}';
    }
}
