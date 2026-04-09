package carsharing.dbclient;
import carsharing.model.AccountModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class dbOperation {

        Connection connection = null;

        public dbOperation(Connection connection) {
            this.connection = connection;
        }

        public boolean addCompany(String company) {
            String sql = "INSERT INTO COMPANY(NAME) VALUES (?);";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, company);
                ps.executeUpdate();

            } catch (SQLException e) {

                return false;
            }
            return true;
        }
        public List<AccountModel> getAllCompanies() {
        String sql = "SELECT * FROM COMPANY;";

                List<AccountModel> list = new ArrayList<>();

                try {
                    Statement statement = connection.createStatement();
                    ResultSet company = statement.executeQuery(sql);
                    while (company.next()) {
                        int id = company.getInt("id");
                        String name = company.getString("name");
                        AccountModel account = new AccountModel(id, name);
                        list.add(account);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                return list;
            }

            public void deleteCard(String name) throws SQLException {
                String sql = "DELETE FROM COMPANY WHERE name = ?;";
                try (PreparedStatement ps = connection.prepareStatement(sql)) {
                    ps.setString(1, name);
                    ps.executeUpdate();
                }
    }


}

