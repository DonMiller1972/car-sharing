package carsharing.model;

public class CustomerModel {
    int id;
    String name;
    Integer rented_car_id;

    public CustomerModel() {}

    public CustomerModel(int id, String name, Integer rented_car_id) {
        this.id = id;
        this.name = name;
        this.rented_car_id = rented_car_id;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRented_car_id() {
        return rented_car_id;
    }

    public void setRented_car_id(Integer rented_car_id) {
        this.rented_car_id = rented_car_id;
    }

    @Override
    public String toString() {
        return "CustomerModel{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", rented_car_id=" + rented_car_id +
               '}';
    }
}
