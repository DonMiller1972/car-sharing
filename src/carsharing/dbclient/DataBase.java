package carsharing.dbclient;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {
    //static final String JDBC_DRIVER = "org.h2.Driver";

    static final String DB_URL = "jdbc:h2:./src/carsharing/db/";

    private final String url;

    private Connection cnn;

    public DataBase(String fileName) {
        this.url = DB_URL + fileName;
    }

    public void init() throws SQLException {
        this.cnn = DriverManager.getConnection(url);
        createTable();

    }

    public void closeConnection() {
        if (cnn != null) {
            try {
                cnn.close();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void createTable() {
        String sqlCompany =  "CREATE TABLE IF NOT EXISTS COMPANY " +
                      "(ID INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY, " +
                      " NAME VARCHAR UNIQUE NOT NULL);";

        String sqlCar = "CREATE TABLE IF NOT EXISTS CAR ("+
                "ID INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, "+
                "NAME VARCHAR(40) UNIQUE NOT NULL, "+
                "COMPANY_ID INT NOT NULL, "+
                "CONSTRAINT fk_car_company FOREIGN KEY (COMPANY_ID) "+
                "REFERENCES COMPANY(ID) "+
                "ON DELETE CASCADE "+
                "ON UPDATE CASCADE);";

        String sqlCustomer = "CREATE TABLE IF NOT EXISTS CUSTOMER (" +
                             " ID INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, " +
                             " NAME VARCHAR(40) UNIQUE NOT NULL, " +
                             " RENTED_CAR_ID INT, " +
                             " CONSTRAINT fk_customer_car FOREIGN KEY (RENTED_CAR_ID) " +
                             " REFERENCES CAR(ID) " +
                             " ON DELETE SET NULL" +
                             ");";

        try (Statement stmt = cnn.createStatement()) {

            stmt.execute(sqlCompany);
            stmt.execute(sqlCar);
            stmt.execute(sqlCustomer);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return cnn;
    }
}
