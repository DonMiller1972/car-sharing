package carsharing.dbclient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import carsharing.model.AccountModel;
import carsharing.model.CarModel;

public class daoCar {
    Connection connection = null;

    public daoCar(Connection connection) {
        this.connection = connection;
    }
    public boolean addCar(String carModel, AccountModel accountModel) {
        String sql = "INSERT INTO CAR(NAME, COMPANY_ID) VALUES (?, ?);";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, carModel);
            ps.setInt(2, accountModel.getId());
            ps.executeUpdate();
            System.out.println("The car was added!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public List<CarModel> getAllCarsCompany(AccountModel accountModel, int choice) {
        String sql = "";
        if (choice == 0) {
            sql = "SELECT * FROM CAR WHERE COMPANY_ID = ?;";
        }else if(choice == 1) {
            sql = "SELECT * " +
                  "FROM CAR T1 " +
                  "WHERE T1.COMPANY_ID = ? " +
                  "AND NOT EXISTS ( " +
                  "    SELECT 1 " +
                  "    FROM CUSTOMER T2 " +
                  "    WHERE T2.RENTED_CAR_ID = T1.ID " +
                  ");";
        }
        List<CarModel> list = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql)){

            ps.setInt(1, accountModel.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int company_id = rs.getInt("company_id");

                CarModel carModel = new CarModel(id, name, company_id);
                list.add(carModel);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<CarModel> getAllCars() {
        String sql = "SELECT * FROM CAR ;";

        List<CarModel> list = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql)){

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int company_id = rs.getInt("company_id");

                CarModel carModel = new CarModel(id, name,company_id);
                list.add(carModel);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }


}
