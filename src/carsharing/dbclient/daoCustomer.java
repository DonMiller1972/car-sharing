package carsharing.dbclient;

import carsharing.model.CarInfoModel;
import carsharing.model.CarModel;
import carsharing.model.CustomerModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class daoCustomer {
    Connection connection = null;
    CustomerModel customer;

    public daoCustomer(Connection connection) {
        this.connection = connection;
    }

    public void addCustomer(String name) {
        String sql = "INSERT INTO CUSTOMER(NAME) VALUES (?);";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.executeUpdate();
            System.out.println("The customer was added!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public List<CustomerModel> getAllCustomer() {
        String sql = "SELECT * FROM CUSTOMER;";
        List<CustomerModel> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)){

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");

                int carId = rs.getInt("RENTED_CAR_ID");
                Integer rentedCarId = rs.wasNull() ? null : carId;

                customer = new CustomerModel(id, name, rentedCarId);
                list.add(customer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public CarInfoModel getRentedCarInfo(int customer_id){

        String sql = "SELECT CAR.NAME AS CAR_NAME, COMPANY.NAME AS COMPANY_NAME " +
                     "FROM CUSTOMER " +
                     "JOIN CAR ON CUSTOMER.RENTED_CAR_ID = CAR.ID " +
                     "JOIN COMPANY ON CAR.COMPANY_ID = COMPANY.ID " +
                     "WHERE CUSTOMER.ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1, customer_id);
            ResultSet rs = ps.executeQuery();

            CarInfoModel model = new CarInfoModel();
            if (rs.next()) {
                model.setCarName(rs.getString(1));
                model.setCompanyName(rs.getString(2));
                return model;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;

    }

    public void rentingCar(CarModel carModel, int customer_id){
        String sql = "UPDATE CUSTOMER SET RENTED_CAR_ID  = ? " +
                     "  WHERE ID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, carModel.getId()); // Новое значение поля
            ps.setInt(2, customer_id);              // ID записи

            int updated = ps.executeUpdate();

            System.out.println("You rented \'" + carModel.getName() + "\'");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void returnCar(int customer_id){
            String sql = "UPDATE CUSTOMER " +
                         "SET RENTED_CAR_ID = NULL " +
                         "WHERE ID = ?;";
       try( PreparedStatement ps = connection.prepareStatement(sql)) {
           ps.setInt(1, customer_id);
           ps.executeUpdate();
           System.out.println("You've returned a rented car!");
       } catch (Exception e) {
        e.printStackTrace();
       }
    }

    public CustomerModel get(int id) {
        String sql = "SELECT * FROM CUSTOMER WHERE ID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                CustomerModel customer = new CustomerModel();
                customer.setId(rs.getInt("ID"));
                customer.setName(rs.getString("NAME"));

                int carId = rs.getInt("RENTED_CAR_ID");
                if (rs.wasNull()) {
                    customer.setRented_car_id(null);
                } else {
                    customer.setRented_car_id(carId);
                }

                return customer;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}
