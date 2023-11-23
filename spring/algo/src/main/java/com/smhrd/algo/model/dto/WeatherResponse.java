package com.smhrd.algo.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class WeatherResponse {
    private Response response;

    @Getter @Setter
    public static class Response {

        // Header
        private Header header;

        @Getter @Setter
        public static class Header {
            private String resultCode;
            private String resultMsg;
        }

        // Body
        private Body body;

        @Getter @Setter
        public static class Body {

            private String dataType;
            private Items items;

            @Getter @Setter
            public static class Items {

                private List<Item> item;

                @Getter @Setter
                public static class Item {

                    private String baseDate;
                    private String baseTime;
                    private String category;
                    private int nx;
                    private int ny;
                    private double obsrValue;
                }
            }
        }
    }
}
