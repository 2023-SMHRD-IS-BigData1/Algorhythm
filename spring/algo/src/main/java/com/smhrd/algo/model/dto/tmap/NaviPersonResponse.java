package com.smhrd.algo.model.dto.tmap;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class NaviPersonResponse {
    private String type;
    private List<Feature> features;

    @Getter @Setter
    public static class Feature {
        private String type;
        private Geometry geometry;
        private Properties properties;

        @Getter @Setter
        public static class Geometry {
            private String type;
            private Object coordinates;
        }

        @Getter @Setter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Properties {
            private Integer totalDistance;
            private Integer totalTime;
            private Integer index;
            private Integer pointIndex;
            private Integer time;
            private Integer distance;
            private String name;
            private String nearPoiName;
            private String nearPoiX;
            private String nearPoiY;
            private String pointType;
        }
    }
}
