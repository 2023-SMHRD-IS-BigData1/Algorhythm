package com.smhrd.algo.model.dto.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter @Setter
public class SevenDayWeather {
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

                    private int rnSt3Am;
                    private int rnSt3Pm;
                    private int rnSt4Am;
                    private int rnSt4Pm;
                    private int rnSt5Am;
                    private int rnSt5Pm;
                    private int rnSt6Am;
                    private int rnSt6Pm;
                    private int rnSt7Am;
                    private int rnSt7Pm;
                    private int rnSt8;
                    private int rnSt9;
                    private int rnSt10;
                }
            }
        }
    }
}
