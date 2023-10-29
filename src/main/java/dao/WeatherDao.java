package dao;

import entities.Weather;

import java.util.List;

public interface WeatherDao {

    void save(Weather weather);

    List<Weather> getAll();
}
