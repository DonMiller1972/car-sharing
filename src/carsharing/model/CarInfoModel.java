package carsharing.model;

public class CarInfoModel {
    private String carName;
    private String companyName;

    public CarInfoModel(){
        this.carName = carName;
        this.companyName = companyName;
    }
    public String getCarName() {
        return carName;
    }
    public String getCompanyName() {
        return companyName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return "CarInfoModel{" +
               "carName='" + carName + '\'' +
               ", companyName='" + companyName + '\'' +
               '}';
    }
}
