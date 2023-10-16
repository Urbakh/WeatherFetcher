package dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class HourDto {

    @JsonProperty("time")
    String time;

    @JsonProperty("temp_c")
    Double temp;

    @JsonProperty("condition")
    ConditionDto condition;

    @JsonProperty("wind_kph")
    Double windKph;
}
