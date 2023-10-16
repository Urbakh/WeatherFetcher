package dao;

import entities.Weather;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WeatherRepository {
    private Connection connection;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.ENGLISH);

    private final String USERNAME = "postgres";

    private final String PASSWORD = "root";

    public WeatherRepository() {
        try {
            Class.forName("org.postgresql.Driver");

            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/weatherapi", USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void save(Weather weather) {
        String insertSQL = "INSERT INTO weather (city, date, temp, speed, condition) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement insertStatement = connection.prepareStatement(insertSQL)) {
            insertStatement.setString(1, weather.getCity());
            insertStatement.setString(2, weather.getDate().format(formatter));
            insertStatement.setObject(3, weather.getTemp());
            insertStatement.setObject(4, weather.getSpeedOfWind());
            insertStatement.setString(5, weather.getCondition());

            insertStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Weather> getAll() {
        List<Weather> weathers = new ArrayList<>();
        String selectAllSQL = "SELECT * FROM weather";
        try (PreparedStatement selectAllStatement = connection.prepareStatement(selectAllSQL);
             ResultSet resultSet = selectAllStatement.executeQuery()) {
            while (resultSet.next()) {
                Weather weather = new Weather();
                weather.setId(resultSet.getLong("id"));
                weather.setDate(LocalDateTime.parse(resultSet.getString("date"), formatter));
                weather.setTemp(resultSet.getDouble("temp"));
                weather.setCondition(resultSet.getString("condition"));
                weather.setCity(resultSet.getString("city"));
                weather.setSpeedOfWind(resultSet.getDouble("speed"));
                weathers.add(weather);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return weathers;
    }
}