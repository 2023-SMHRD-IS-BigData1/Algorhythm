package com.smhrd.algo.model.dto;

import com.smhrd.algo.model.dto.LatLonRequest.LatlonList.Latlon;
import com.smhrd.algo.model.dto.LatLonRequest.LatlonList.LatlonStation;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter @Setter
@Builder @AllArgsConstructor
@NoArgsConstructor
public class LatLonRequest {
    private LatlonList latlonList;

    public String getLatLonStationString() {
        return IntStream.range(0, latlonList.getLatlonStations().size())
                .mapToObj(index -> {
                    LatlonStation latlonStation = latlonList.getLatlonStations().get(index);
                    return latlonStation.getLon() + "," + latlonStation.getLat();
                })
                .collect(Collectors.joining("_"));
    }

    @Getter @Setter
    @Builder @AllArgsConstructor
    @NoArgsConstructor
    public static class LatlonList {
        private List<Latlon> latlon;
        private List<LatlonStation> latlonStations;

        @Getter @Setter
        @Builder @AllArgsConstructor
        @NoArgsConstructor
        public static class Latlon {
            private String type;
            private Double lat;
            private Double lon;
        }

        @Getter @Setter
        @Builder @AllArgsConstructor
        @NoArgsConstructor
        public static  class LatlonStation {
            private String type;
            private Double lat;
            private Double lon;
        }
    }
}
