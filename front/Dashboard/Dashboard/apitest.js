function initTmap() {
    // 주요 필드
    var map;
    var markerArr=[];

    // 맵 객체 생성
    map = new Tmapv2.Map("map", // "map" id 값과 맞추기
         {
             center : new Tmapv2.LatLng(35.1595454, 126.8526012),
             width : "80%",
             height : "650px",
             zoom : 14,
             zoomControl : true,
             scrollwheel : true
         });

    // 검색 기능
    $('#search').click(function() {
        var inputValue = $('#searchValue').val();
        poiSearch(map, inputValue, markerArr)
    });
}

// 검색 기능 function
function poiSearch(map, searchKeyword, markerArr) {

    var marker;
    var markerImg = "/img/tmap/testMarker.png";
    var positionBounds = new Tmapv2.LatLngBounds();

    $.ajax({
        url : "/poisearch",
        data : {"searchKeyword":searchKeyword},
        success : function(response){

            var data = response.searchPoiInfo.pois.poi;
            console.log(data);
            // 기존 마커 제거
            if (markerArr.length > 0) {
                for (var i in markerArr) {
                    markerArr[i].setMap(null);
                }
            }
            
            // 마커 생성
            for (var k=0; k<data.length; k++) { // for[s]
                var noorLat = data[k].noorLat; // 위도
                var noorLon = data[k].noorLon; // 경도

                // 마커 생성
                var lonlat = new Tmapv2.LatLng(noorLat, noorLon);
                var iconSize = new Tmapv2.Size(20,20);
                marker = new Tmapv2.Marker(
                {
                    position : lonlat,
                    icon : markerImg,
                    iconSize : iconSize,
                    map : map
                });
                markerArr.push(marker);
                positionBounds.extend(lonlat);
            } // for[e]

            // 마커들이 보이도록 지도 조정
            map.panToBounds(positionBounds);
            map.zoomOut();
        }
    });
}

