package carsharing;

import carsharing.dbclient.*;
import carsharing.dbclient.daoCustomer;
import carsharing.model.AccountModel;
import carsharing.model.CarInfoModel;
import carsharing.model.CarModel;
import carsharing.model.CustomerModel;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;



public class Interaction {
    private dbOperation dao;
    private daoCar daoCar;
    private daoCustomer daoCustomer;

    public Interaction(dbOperation dbOperation, daoCar daoCar, daoCustomer daoCustomer) {
        this.dao = dbOperation;
        this.daoCar = daoCar;
        this.daoCustomer = daoCustomer;
    }

    public int readMenu(Scanner sc) {
        while (true) {
            
            String input = sc.nextLine().trim();
            if (input.matches("\\d+")) {
                return Integer.parseInt(input);
            } else {
                System.out.println("Please enter a number.");
            }
        }
    }

    public void printMenu() {
        System.out.println("""
                1. Log in as a manager
                2. Log in as a customer
                3. Create a customer
                0. Exit
                """
        );
    }

    public void printMenuLogIn() {
        System.out.println("""
                1. Company list
                2. Create a company
                0. Back
                """
        );
    }
    public void printMenuCars() {
        System.out.println("""
                1. Car list
                2. Create a car
                0. Back
                """
        );
    }
    public void printMenuCustomer() {
        System.out.println("""
                1. Rent a car
                2. Return a rented car
                3. My rented car
                0. Back
                """
        );
    }

    public void mainMenu(Scanner sc) throws SQLException {
        //AccountModel acc;
        boolean loopMenu = true;
        int menu = -1;
        while (loopMenu) {
            printMenu();
            menu = readMenu(sc);
            switch (menu) {
                case 1 -> {
                    loginAsMng(sc);

                }
                case 2 -> {
                    List<CustomerModel> customers = daoCustomer.getAllCustomer();
                    if(customers.isEmpty()){
                        System.out.println("The customer list is empty!");
                    }else{
                        int i = 1;
                        for(CustomerModel c : customers){
                            System.out.println(i + ". " + c.getName());
                            i++;
                        }
                        System.out.println("0. Back");
                        int answer = sc.nextInt();
                        if(answer != 0){
                            loginAsCustomer(customers.get(answer-1), sc);
                        }
                    }
                }
                case 3 -> {
                    System.out.println("Enter the customer name:");
                    String name = sc.nextLine();
                    daoCustomer.addCustomer(name);

                }
                case 0 -> {
                    loopMenu = false;
                }
            }
            ;
        }
    }

    public boolean loginAsMng(Scanner sc) throws SQLException {
        String nameCompany = "";
        boolean circles = true;
        int menu = -1;
        boolean ret = true;
        boolean addComp = false;
        List<AccountModel> listAccontModels = null;
        AccountModel currentAccount = new AccountModel();
        System.out.println();
        while (circles) {
            printMenuLogIn();
            menu = readMenu(sc);
            System.out.println();
            switch (menu) {
                case 1 -> {
                    listAccontModels = dao.getAllCompanies();
                    if (listAccontModels.isEmpty()) {
                        System.out.println("The company list is empty!");
                        System.out.println();
                    } else {

                        circles = loginCarMenu(listAccontModels, sc);

                    }
                }
                case 2 -> {
                    System.out.println("Enter the company name:");
                    nameCompany = sc.nextLine();
                    System.out.println((addComp = dao.addCompany(nameCompany))?"The company was created!":"The company wasn't created!");
                    System.out.println();
                }
                case 0 -> {
                    circles = false;
                }
            }
        }

        return ret;
    }
    public boolean loginCarMenu(List<AccountModel> companies, Scanner sc) throws SQLException {
        List<CarModel> cars = null;
        int menu = -1;
        boolean circleCar = true;

        //CarModel car;
        String nameCar;
        System.out.println("Company list:");
        for (int i = 0; i < companies.size(); i++) {

            System.out.println(i + 1 + ". " + companies.get(i).getName());
        }
        System.out.println("0. Back");

        menu = readMenu(sc);
        if (menu != 0) {
            AccountModel acc = companies.get(menu - 1);
            String nameCompany = acc.getName();
            System.out.printf("\'%s\' company\n", nameCompany);
            System.out.println();

            while (circleCar) {
                printMenuCars();
                menu = readMenu(sc);
                switch (menu) {
                    case 1 -> {
                        cars = daoCar.getAllCarsCompany(acc,0);
                        if (cars.isEmpty()) {
                            System.out.println("The car list is empty!");
                        } else {
                            System.out.println("Car list:");
                            for (int i = 0; i < cars.size(); i++) {
                                System.out.println(i + 1 + ". " + cars.get(i).getName());
                            }
                            System.out.println("");
                        }
                    }
                    case 2 -> {
                        System.out.println("Enter the car name:");
                        nameCar = sc.nextLine();
                        daoCar.addCar(nameCar, acc);
                        System.out.println();
                    }
                    case 0 -> {
                        circleCar = false;
                    }
                }

            }
        }
        return true;
    }

    public void loginAsCustomer(CustomerModel customer, Scanner sc) throws SQLException {

        CustomerModel customerModel = customer;
        List<AccountModel> listAccontModels = null;
        List<CarModel> listCarModels = null;
        boolean loopMenu = true;
        int menu = -1;
        while(loopMenu) {
            customer = daoCustomer.get(customer.getId());

            printMenuCustomer();
            menu = readMenu(sc);
            switch (menu) {
                case 1 -> {
                    customer = daoCustomer.get(customer.getId());
                    if (customer.getRented_car_id() == null) {
                        listAccontModels = dao.getAllCompanies();
                        System.out.println(listAccontModels.size());
                        if (listAccontModels.isEmpty()) {
                            System.out.println("The company list is empty!");
                        } else {
                            System.out.println("Choose a company:");
                            for (int i = 0; i < listAccontModels.size(); i++) {
                                System.out.println(i + 1 + ". " + listAccontModels.get(i).getName());
                            }
                            System.out.println("0. Back");
                            menu = readMenu(sc);
                            if (menu != 0) {
                                listCarModels = daoCar.getAllCarsCompany(listAccontModels.get(menu - 1), 1);
                                System.out.println("Choose a car:");
                                for (int i = 0; i < listCarModels.size(); i++) {
                                    System.out.println(i + 1 + ". " + listCarModels.get(i).getName());
                                }
                                System.out.println("0. Back");
                                menu = readMenu(sc);
                                if (menu != 0) {
                                    daoCustomer.rentingCar(listCarModels.get(menu - 1), customer.getId());
                                    System.out.println();
                                    customer = daoCustomer.get(customer.getId());
                                }
                            } else {
                                loopMenu = false;
                            }
                        }
                        }else{
                            System.out.println("You've already rented a car!");
                        }

                }
                case 2 -> {
                    System.out.println(customer.getName() + "   " + customer.getRented_car_id());
                    if(customer.getRented_car_id()==null){
                        System.out.println("You didn't rent a car!");
                        System.out.println();
                    }else{
                        daoCustomer.returnCar(customer.getId());
                        customer = daoCustomer.get(customer.getId());
                    }

                }
                case 3 -> {
                    customer = daoCustomer.get(customer.getId());
                    System.out.println(customer.getId()+" "+ customer.getName() + " " + customer.getRented_car_id());
                    if(customer.getRented_car_id()!=null){
                        CarInfoModel info = daoCustomer.getRentedCarInfo(customer.getId());
                        System.out.println("Your rented car:");
                        System.out.println(info.getCarName());
                        System.out.println("Company:");
                        System.out.println(info.getCompanyName());
                        System.out.println();
                    }else{

                    System.out.println("You didn't rent a car!");
                        System.out.println();
                    }
                    customer = daoCustomer.get(customer.getId());

                }
                case 0 -> {
                    loopMenu = false;
                }
            }
        }
    }
}



