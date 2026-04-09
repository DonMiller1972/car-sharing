# Car Sharing

Console application for managing a car-sharing service.

## Features

- Create companies
- Add cars to a company
- Create customers
- Rent a car
- Return a rented car
- View rented car details (car + company)

## Technologies

- Java
- JDBC
- H2 Database
- DAO pattern

## How to run

Compile and run the program with a database file name:


java Main -databaseFileName carsharing


## Project structure

- `dbclient` – database connection and DAO classes  
- `model` – data models  
- `Interaction` – user interface logic  
- `Main` – entry point  

## Notes

- The database is created automatically if it does not exist
- `.db` files are excluded from the repository

## Status

Completed as part of a learning project.
