
![Logo](logo.png)

[![GPLv3 License](https://img.shields.io/badge/License-GPL%20v3-yellow.svg)](https://opensource.org/licenses/)


# ThinkerSol

Description about what thinkersol is
## Website 

Our website detailing our mission and product: [ThinkerSol](https://www.thinkersol.com)
## Founding Team

- [@Safwaan Taher](https://www.github.com/safwaant)
- [@Cole Lance](https://github.com/cole-lance)
- [@Rahul Pillalamarri](https://www.github.com/rahulpil)
- [@Rosie Shen](https://github.com/sheros21)
- [@Yeshwanth Darekar](https://github.com/Tbox7)

## Features

- Light/dark mode toggle
- Live previews
- Fullscreen mode
- Cross platform


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

The following are contributors who were not founders, but had a significant part in the development process

 - 
 - 


