import controllers.WeatherServlet;
import dao.WeatherRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.when;

public class WeatherServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private  WeatherRepository weatherRepository;

    private WeatherServlet weatherServlet;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        weatherServlet = new WeatherServlet();
        weatherServlet.setWeatherRepository(weatherRepository);
    }

    @Test
    public void testDoGetWithoutCity() throws Exception {
        when(request.getParameter("days")).thenReturn("1");

        StringWriter writer = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(writer));

        weatherServlet.doGet(request, response);

        String result = writer.toString();
        Assert.assertEquals("No query parameter 'city' defined", result);
    }
}
