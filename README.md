
![Logo](logo.png)

[![GPLv3 License](https://img.shields.io/badge/License-GPL%20v3-yellow.svg)](https://opensource.org/licenses/)


# ThinkerSol

An open source project for constructing educator-focused CCR tools that inspire greatness, inside and outside of the classroom.

## Website 

Our website detailing our mission and product: [ThinkerSol](https://sites.google.com/uw.edu/thinkersol/home?authuser=1)
## Founding Team

- [@Safwaan Taher](https://www.github.com/safwaant)
- [@Cole Lance](https://github.com/cole-lance)
- [@Rahul Pillalamarri](https://www.github.com/rahulpil)
- [@Rosie Shen](https://github.com/sheros21)
- [@Yeshwanth Darekar](https://github.com/Tbox7)

## Features
- ThinkerSol has 3 main "flows" 

    1. The Admin flow
    2. The Student flow
    3. The Parent flow

- Below are details about core features for each flow
### Admin Features
* Easily create & save tasks for students of default and custom groups, template career guides/notes for students of default and custom groups, and template tasks for students of default and custom groups. 
* View and filter student data down to only the necessary parameters 
* Create custom groups for students based off any variable you choose. Manage your default student groups for outlier students
* Toggle between plans to understand student usage and levels of career interest
* Easily understand where all students are at in the career planning process
### Student Features 
* Search classes, institutions and careers. Data is pulled from BLS ONET databases
* Create up to five 4-year comprehensive plans detailing classes taken per grade, subject requirements, colleges, and career direction
* Be a part of a custom group, recieving announcements and important info sent to the group
### Parent Features
* Monitor student progress on each plan with detailed reports
* View updates for student plans and career, keeping in sync with student career planning

## Run Locally

First clone the repo

```
git clone 
```

### Database

To run locally an instance of the `PostgreSQL` database must first be configured. 
To do this first log into `PostgreSQL` and run the following commands 

To create the database

```postgres
CREATE DATABASE thinkersol;
```
Connect to database

```bash
\c thinkersol
```

Now run all the SQL scripts in the `db-v1.11` file found in the `Backend` folder under `SQL Scripts`
Once this is done you will have a empty database 

To copy over the sample data run a copy command for **each** CSV file:
[PSQL Command Help](https://learnsql.com/blog/how-to-import-csv-to-postgresql/)


### Backend
First configure `application.yml` in SpringBoot: [Application.yml Config Example](https://www.geeksforgeeks.org/spring-boot-application-yml-application-yaml-file/)

Go to API folder

```bash
cd /Backend/API
```
Install dependencies and run a build

```bash
./mvnw clean package
```

Run the SpringBoot application
```bash
java -jar ./target/thinkersol-API.jar
```

Now the backend should be live on `localhost:8080`!

### Frontend

Go to the frontend directory

```bash
  cd /Frontend
```

Install dependencies

```bash
  npm install
```

Start the server

```bash
  npm run start
```

You now have the frontend hosted locally!

## License

[GPlv3](https://choosealicense.com/licenses/gpl-3.0/)

## Acknowledgements 

The following are contributors were not founders, but held a significant part in the development process

 - [Shakeel Khan](https://king-shak.github.io/)
 - [Mustafa Asadullah](https://github.com/mustafaasadullah)
 - [Ashley Mead](https://github.com/concodeia)

