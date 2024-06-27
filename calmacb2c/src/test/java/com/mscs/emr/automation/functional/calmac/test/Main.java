package com.mscs.emr.automation.functional.calmac.test;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
class SailingResponse {
    @JsonProperty("c_RDR_RouteDateTimeResponse")
    private RouteDateTimeResponse routeDateTimeResponse;

    // Getters and setters
    public RouteDateTimeResponse getRouteDateTimeResponse() {
        return routeDateTimeResponse;
    }

    public void setRouteDateTimeResponse(RouteDateTimeResponse routeDateTimeResponse) {
        this.routeDateTimeResponse = routeDateTimeResponse;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class RouteDateTimeResponse {
    @JsonProperty("m_ROUT_TravelRoute")
    private TravelRoute travelRoute;

    @JsonProperty("c_C276_SailingCode")
    private String sailingCode;

    // Getters and setters
    public TravelRoute getTravelRoute() {
        return travelRoute;
    }

    public void setTravelRoute(TravelRoute travelRoute) {
        this.travelRoute = travelRoute;
    }

    public String getSailingCode() {
        return sailingCode;
    }

    public void setSailingCode(String sailingCode) {
        this.sailingCode = sailingCode;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class TravelRoute {
    @JsonProperty("c_C081_IsAvailable")
    private boolean isAvailable;

    // Getters and setters
    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Root {
    @JsonProperty("sailingprice")
    private List<SailingResponse> sailingPrices;

    // Getters and setters
    public List<SailingResponse> getSailingPrices() {
        return sailingPrices;
    }

    public void setSailingPrices(List<SailingResponse> sailingPrices) {
        this.sailingPrices = sailingPrices;
    }
}

public class Main {
    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            // Replace "path/to/your/json/file.json" with the actual file path
            Root root = mapper.readValue(new File("path/to/your/json/file.json"), Root.class);

            for (SailingResponse response : root.getSailingPrices()) {
                RouteDateTimeResponse routeDateTimeResponse = response.getRouteDateTimeResponse();
                if (routeDateTimeResponse != null && routeDateTimeResponse.getTravelRoute() != null) {
                    if (routeDateTimeResponse.getTravelRoute().isAvailable()) {
                        System.out.println("Sailing Code: " + routeDateTimeResponse.getSailingCode());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
