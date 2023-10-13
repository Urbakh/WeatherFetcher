import com.fasterxml.jackson.databind.ObjectMapper;
import entities.ForecastDay;
import entities.WeatherApiObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

@WebServlet("/weather")
public class WeatherServlet extends HttpServlet {

    private static final String WEATHERAPI_HOST = "http://api.weatherapi.com";

    private static final String WEATHERAPI_KEY = "f1d5b8e828d3414a86f115208221401";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String city = req.getParameter("city");
        String days = req.getParameter("days");
        resp.setContentType("text/plain");

        PrintWriter out = resp.getWriter();

        if (city != null) {
            String weatherData = days == null ? getCurrentWeatherData(city) : getForecastWeatherData(days, city);

            WeatherApiObject data = parseWeatherData(weatherData);

            if (data == null) {
                out.print("City incorrect or parsing failed");
            } else {
                out.println("Current time in city " + city + ": " + data.getLocation().getLocaltime());
                out.println("Current temperature" + ": " + data.getCurrent().getTemp());
                out.println("Current weather condition" + ": " + data.getCurrent().getCondition().getText());
                out.println("Current speed of wind" + ": " + data.getCurrent().getWindKph());


                for (int i = 0; i < data.getForecast().getForecastDay().size(); i++) {
                    ForecastDay forecastDay = data.getForecast().getForecastDay().get(i);
                    for (int j = 0; j < forecastDay.getHours().size(); j++) {
                        out.println("Forecast weather" + ": " + forecastDay.getHours().get(j).getTime());
                    }
                }
            }
        } else {
            out.print("No query parameter 'city' defined" );
        }
        out.close();
    }

    private WeatherApiObject parseWeatherData(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            return mapper.readValue(json, WeatherApiObject.class);

        } catch (IllegalArgumentException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getCurrentWeatherData(String city) {

        return httpGet(WEATHERAPI_HOST + "/v1/current.json?key=" + WEATHERAPI_KEY + "&q=" + city);
    }

    private String getForecastWeatherData(String days, String city) {

        return httpGet(WEATHERAPI_HOST + "/v1/forecast.json?key=" + WEATHERAPI_KEY + "&q=" + city + "&days=" + days);
    }

    private String httpGet(String url) {
        try {
            URL apiUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(60000);

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                return response.toString();
            } else {
                System.out.println("Request failed with status code: ${responseCode}");
                return null;
            }
        } catch (Exception e) {
            System.out.println("An error occurred: ${e.message}");
            return null;
        }
    }
}