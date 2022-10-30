package com.sanvalero.ejemplofxml.db;

import com.sanvalero.ejemplofxml.domain.Car;
import com.sanvalero.ejemplofxml.util.R;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CarDAO {

    private Connection connection;

    public void connect() throws ClassNotFoundException, SQLException, IOException {
        Properties configuration = new Properties();
        configuration.load(R.getProperties("database.properties"));
        String url = configuration.getProperty("url");
        String username = configuration.getProperty("username");
        String password = configuration.getProperty("password");

        connection = DriverManager.getConnection(url, username, password);
    }

    public void disconnect() throws SQLException {
        connection.close();
    }

    public void saveCar(Car car) throws SQLException {
        String sql = "INSERT INTO cars (license, brand, model, type) VALUES (?, ?, ?, ?)";

        PreparedStatement sentence = connection.prepareStatement(sql);
        sentence.setString(1, car.getLicense());
        sentence.setString(2, car.getBrand());
        sentence.setString(3, car.getModel());
        sentence.setString(4, car.getType());
        sentence.executeUpdate();
    }

    public void deleteCar(Car car) throws SQLException {
        String sql = "DELETE FROM cars WHERE license = ?";

        PreparedStatement sentence = connection.prepareStatement(sql);
        sentence.setString(1, car.getLicense());
        sentence.executeUpdate();
    }

    public void editCar(Car oldCar, Car newCar) throws SQLException {
        String sql = "UPDATE cars SET license = ?, brand = ?, model = ?, type = ? WHERE id = ?";

        PreparedStatement sentence = connection.prepareStatement(sql);
        sentence.setString(1, newCar.getLicense());
        sentence.setString(2, newCar.getBrand());
        sentence.setString(3, newCar.getModel());
        sentence.setString(4, newCar.getType());
        sentence.setInt(5, oldCar.getId());
        sentence.executeUpdate();
    }

    public List<Car> getCars() throws SQLException {
        List<Car> carList = new ArrayList<>();
        String sql = "SELECT * FROM cars";

        PreparedStatement sentence = connection.prepareStatement(sql);
        ResultSet result = sentence.executeQuery();
        while (result.next()) {
            Car car = new Car();
            car.setId(result.getInt(1));
            car.setLicense(result.getString(2));
            car.setBrand(result.getString(3));
            car.setModel(result.getString(4));
            car.setType(result.getString(5));

            carList.add(car);
        }

        return carList;
    }

    public boolean existsCar(String license) throws SQLException {
        String sql = "SELECT * FROM cars WHERE license = ?";
        PreparedStatement sentence = connection.prepareStatement(sql);
        sentence.setString(1, license);
        ResultSet result = sentence.executeQuery();

        return result.next();
    }
}
