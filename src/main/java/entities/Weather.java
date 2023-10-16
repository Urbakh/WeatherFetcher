package entities;

import dto.WeatherApiObjectDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Weather {

    private Long id;

    private String city;

    private Double temp;

    private Double speedOfWind;

    private String condition;

    private LocalDateTime date;

    public static Weather fromDto(WeatherApiObjectDto dto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.ENGLISH);
        LocalDateTime date = LocalDateTime.parse(dto.getLocation().getLocalTime(), formatter);

        return new Weather(
                null,
                dto.getLocation().getName(),
                dto.getCurrent().getTemp(),
                dto.getCurrent().getWindKph(),
                dto.getCurrent().getCondition().getText(),
                date
        );
    }

    @Override
    public String toString() {
        return "id=" + id +
                "| city='" + city + '\'' +
                "| temp=" + temp +
                "| speedOfWind=" + speedOfWind +
                "| condition='" + condition + '\'' +
                "| date=" + date;
    }
}
