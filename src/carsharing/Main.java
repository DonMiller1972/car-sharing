package carsharing;
import carsharing.dbclient.DataBase;
import carsharing.dbclient.daoCar;
import carsharing.dbclient.daoCustomer;
import carsharing.dbclient.dbOperation;

import java.sql.Connection;

import java.sql.SQLException;

import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws SQLException {

        String fileName = "carsharing";
        System.out.println("WD: " + new java.io.File(".").getAbsolutePath());// дефолт
        if (args.length >= 2 && "-databaseFileName".equals(args[0])) {
            fileName = args[1];
        }
        DataBase db = new DataBase(fileName);
        db.init();

        try {
            Scanner sc = new Scanner(System.in);
            Connection conn = db.getConnection();
            dbOperation dao = new dbOperation(conn);
            daoCar daoCar = new daoCar(conn);
            daoCustomer daoCustomer = new daoCustomer(conn);
            Interaction interaction = new Interaction(dao, daoCar, daoCustomer);
            interaction.mainMenu(sc);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.closeConnection();

    }
  }