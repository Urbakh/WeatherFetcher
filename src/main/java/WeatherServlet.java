import com.fasterxml.jackson.databind.ObjectMapper;
import entities.OpenWeather;

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

    private static final String OPENWEATHER_API_HOST = "https://api.openweathermap.org";

    private static final String OPENWEATHER_API_KEY = "d25cf7d16e56c63f3804fe830fc6eda3";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String city = req.getParameter("city");
        resp.setContentType("text/plain");

        PrintWriter out = resp.getWriter();

        if (city != null) {
            OpenWeather data = parseWeatherData(httpGet(OPENWEATHER_API_HOST + "/data/2.5/weather?q=" + city + "&units=metric&appid=" + OPENWEATHER_API_KEY));

            if (data == null) {
                out.print("City incorrect or parsing failed");
            } else {
                out.print("Current temperature in city " + city + ": " + data.getMain().getTemp().toString());
            }
        } else {
            out.print("No query parameter 'city' defined" );
        }
        out.close();
    }

    private OpenWeather parseWeatherData(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            return mapper.readValue(json, OpenWeather.class);

        } catch (IllegalArgumentException | IOException e) {
            e.printStackTrace();
        }
        return null;
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