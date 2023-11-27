package com.smhrd.algo.model.dto;

import com.smhrd.algo.model.dto.LatLonRequest.LatlonList.Latlon;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter @Setter
public class LatLonRequest {
    private LatlonList latlonList;

    public String getLatLonString() {
        return IntStream.range(0, latlonList.getLatlon().size())
                .filter(index -> index > 1) // 0번과 1번 인덱스를 건너띄기
                .mapToObj(index -> {
                    Latlon latlon = latlonList.getLatlon().get(index);
                    return latlon.getLon() + "," + latlon.getLat();
                })
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
