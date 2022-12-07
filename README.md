# JDBC training

## Prerequisites
- Install `Postgres` locally
- Be able to request the database (create database, tables...)

## Assignment 1 - connectivity and fetching data

- Create a database `demo`
- Create a table `Users`
```
- name: String
- age: Integer
```
- Insert some users (SQL request)
- Implement `UserRepository` interface to get a User by Id

## Assignment 2 - CRUD

- Implement the full `User` CRUD
- Bonus : read database configuration from a property file

## Assignment 3 - transactions

- Create multiple users
- Add +1 to their age in one transaction
- Constraint : make 1 update request by user
- If any error occurs during the update, rollback changes

## Assignment 4 - Products and Baskets

- `Users` should be able to add `Products` in a `Basket`
- Each product has a `name` and a `price`
- Users can add products in their basket and get the total sum of all products
- Constraint: if a new table creation is needed, please create a new database with `H2 in-memory database` and create the new tables inside

something