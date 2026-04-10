package carsharing.model;

public class CarModel {
    int id;
    String name;
    int company_id;

    public CarModel() {

    }

    public CarModel(int id, String name, int company_id) {
        this.id = id;
        this.name = name;
        this.company_id = company_id;
    }

    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    @Override
    public String toString() {
        return "CarModel{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", company_id=" + company_id +
               '}';
    }
}
