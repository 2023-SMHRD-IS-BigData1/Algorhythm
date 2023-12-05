package com.smhrd.algo.model.dto.tmap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class PoiResponse {
    private SearchPoiInfo searchPoiInfo;

    @Getter @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SearchPoiInfo {
        private Pois pois;

        @Getter @Setter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Pois {
            private List<Poi> poi;

            @Getter @Setter
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Poi {
                private String name; // 장소명
                private String noorLat; // 중심좌표 위도
                private String noorLon; // 중심좌표 경도
                private String middleAddrName; // ~구
                private String lowerAddrName; // ~동
                private String zipCode; // 우편번호
                private NewAddressList newAddressList;

                @Getter @Setter
                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class NewAddressList {
                    private List<NewAddress> newAddress;

                    @Getter @Setter
                    @JsonIgnoreProperties(ignoreUnknown = true)
                    public static class NewAddress {
                        private String fullAddressRoad; // 도로명 주소
                    }
                }
            }
        }
    }
}
