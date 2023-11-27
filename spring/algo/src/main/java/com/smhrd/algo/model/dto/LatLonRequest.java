package com.smhrd.algo.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
public class LatLonRequest {
    private LatlonList latlonList;

    public String getLatLonString() {
        return latlonList.getLatlon().stream()
                .map(latlon -> latlon.getLon() + "," + latlon.getLat())
                .collect(Collectors.joining("_"));
    }

    @Getter @Setter
    public static class LatlonList {
        private List<Latlon> latlon;

        @Getter @Setter
        public static  class Latlon {
            private String type;
            private Double lat;
            private Double lon;
        }
    }
}
