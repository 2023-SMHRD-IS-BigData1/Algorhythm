package com.smhrd.algo.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter @Setter
public class WeatherResponse {
    private Response response;

    @Getter @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Response {

        // Body
        private Body body;

        @Getter @Setter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Body {

            private Items items;

            @Getter @Setter
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Items {

                private List<Item> item;

                @Getter @Setter
                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class Item {

                    private String baseDate;
                    private String baseTime;
                    private String category;
                    private double obsrValue;
                }
            }
        }
    }
}
