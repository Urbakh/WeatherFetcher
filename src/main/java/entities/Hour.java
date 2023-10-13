package entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Hour {

    @JsonProperty("time")
    String time;

    @JsonProperty("temp_c")
    Double temp;

    @JsonProperty("condition")
    Condition condition;

    @JsonProperty("wind_kph")
    Double windKph;

}
