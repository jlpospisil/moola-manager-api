Getting Started
---

1. Copy example config file into place and modify as needed
    
    ```
    cp src/main/resources/example.application.properties src/main/resources/application.properties
    ```

3. Create the database to be used
    1. Start the database container:

    ```
    docker-compose up db
    ```
    
    2. In a seperate tab/window:
    
    ```
    mysql -h 127.0.0.1 -u root -ppassword -P 3310
    create database moola_manager;
    quit
    docker-compose down
    ```
    

Starting application
---

    docker-compose up