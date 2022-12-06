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

## Assignment 3 - transactions

- Create 5 users
- During the creation of the 4th user, an "unexpected" exception will occur
- Find a solution to rollback the first created users

## Assignment 4 - Products and Baskets

- `Users` should be able to add `Products` in a `Basket`
- Each product has a `name` and a `price`
- Users can add products in their basket and get the total sum of all products
- Constraint: if a new table creation is needed, please create a new database with `H2 in-memory database` and create the new tables inside