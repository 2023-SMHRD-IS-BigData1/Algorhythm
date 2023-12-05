package com.smhrd.algo.model.dto.tmap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.net.InterfaceAddress;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor @Builder
public class NaviTransportResponse {

    private MetaData metaData;

    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MetaData {
        private Plan plan;

        @Getter @Setter
        @NoArgsConstructor
        @AllArgsConstructor @Builder
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Plan {
            private List<Itineraries> itineraries;

            @Getter @Setter
            @NoArgsConstructor
            @AllArgsConstructor @Builder
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Itineraries {
                private List<Legs> legs;
                private double totalDistance;
                private int totalTime;
                private double totalWalkDistance;
                private int totalWalkTime;


                @Getter @Setter
                @NoArgsConstructor
                @AllArgsConstructor @Builder
                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class Legs {
                    private String mode;
                    private double distance;
                    private int sectionTime;
                    private Start start;
                    private End end;
                    private List<Steps> steps;
                    private List<Latlons> latlons;
                    private PassShape passShape;
                    private String route;
                    private String routeColor;

                    @Getter @Setter
                    @NoArgsConstructor
                    @AllArgsConstructor @Builder
                    @JsonIgnoreProperties(ignoreUnknown = true)
                    public static class PassShape {
                        private String linestring;
                    }

                    @Getter @Setter
                    @NoArgsConstructor
                    @AllArgsConstructor @Builder
                    @JsonIgnoreProperties(ignoreUnknown = true)
                    public static class Latlons {
                        private double lat;
                        private double lon;
                    }

                    @Getter @Setter
                    @NoArgsConstructor
                    @AllArgsConstructor @Builder
                    @JsonIgnoreProperties(ignoreUnknown = true)
                    public static class Steps {
                        private String streetName;
                        private double distance;
                        private String linestring;
                    }

                    @Getter @Setter
                    @NoArgsConstructor
                    @AllArgsConstructor @Builder
                    @JsonIgnoreProperties(ignoreUnknown = true)
                    public static class End {
                        private String name;
                        private double lon;
                        private double lat;
                    }

                    @Getter @Setter
                    @NoArgsConstructor
                    @AllArgsConstructor @Builder
                    @JsonIgnoreProperties(ignoreUnknown = true)
                    public static class Start {
                        private String name;
                        private double lon;
                        private double lat;
                    }
                }
            }
        }
    }
}
