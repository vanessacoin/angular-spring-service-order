
Projeto pessoal com a finalidade de solucionar uma demanda recorrente de gerar ordens de serviço em uma oficina automotiva. Funcionamento e recursos baseados nas necessidades do usuário.

![Badge EM ANDAMENTO](https://img.shields.io/badge/EM%20ANDAMENTO-STATUS?style=for-the-badge&label=STATUS&color=%23eb7bc0)

# CrudAngular

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 17.3.7.

## Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The application will automatically reload if you change any of the source files.

## Code scaffolding

Run `ng generate component component-name` to generate a new component. You can also use `ng generate directive|pipe|service|class|guard|interface|enum|module`.

## Build

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory.

## Running unit tests

Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).

## Running end-to-end tests

Run `ng e2e` to execute the end-to-end tests via a platform of your choice. To use this command, you need to first add a package that implements end-to-end testing capabilities.

## Further help

To get more help on the Angular CLI use `ng help` or go check out the [Angular CLI Overview and Command Reference](https://angular.io/cli) page.


## Database Configuration

This project uses MySQL as the database. Follow the instructions below to configure the database locally:

### 1. Install MySQL

Ensure that MySQL is installed and running. You can download the MySQL Community Server [here](https://dev.mysql.com/downloads/mysql/).

### 2. Create Database and User

Run the following SQL commands to create the database and user:

```sql
CREATE DATABASE my_database;
CREATE USER 'my_user'@'localhost' IDENTIFIED BY 'my_password';
GRANT ALL PRIVILEGES ON my_database.* TO 'my_user'@'localhost';
FLUSH PRIVILEGES;


Before running the application, you need to configure the database credentials.

1. **Create a `.env` File**

   Create a file named `.env` in the root of the project with the following content:

   ```env
   DB_NAME=my_database
   DB_USER=my_user
   DB_PASSWORD=my_password
