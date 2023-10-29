package controllers;

import services.WeatherRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/history")
public class HistoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");

        PrintWriter out = resp.getWriter();
        out.println("History of requests: ");

        WeatherRepository weatherRepository = new WeatherRepository();
        weatherRepository.getAll().forEach(weather -> {
            out.println(weather.toString());
        });

        out.close();
    }
}
