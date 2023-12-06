// 주요 필드
  let map;
  var markerArr = [];
  var resultdrawArr = [];
  var dataInfo;
  var startLatData;
  var startLonData;
  var endLatData;
  var endLonData;

function initTmap() {
  // 맵 객체 생성
  map = new Tmapv2.Map(
    "map", // "map" id 값과 맞추기
    {
      center: new Tmapv2.LatLng(36.5040736, 127.2494855),
      width: "100%",
      height: "1500px",
      zoom: 17,
      zoomControl: true,
      scrollwheel: true,
    }
  );

  // 정류장 지도 위에 표기하기
  $.ajax({
    url: "/getstation",
    success: function (response) {
      for (var i = 0; i < response.length; i++) {
        var lat = response[i].lat;
        var lon = response[i].lng;
        var name = response[i].stationName;

        var markerPosition = new Tmapv2.LatLng(lat, lon);

        marker = new Tmapv2.Marker({
          position: markerPosition,
          icon: "/img/tmap/stationMarker.png",
          iconSize: new Tmapv2.Size(15, 15),
          title: name,
          map: map,
        });
      }
    },
  });

    $(".bus-button").click(function () {
      $("#destination-toggle").addClass("hidden");
      $("#bike-toggle").addClass("hidden");
      $("#bus-toggle").removeClass("hidden");
      $(".bus-button").addClass("active-tab");
      $(".bike-button").removeClass("active-tab");
    });
    // 자전거 버튼
    $(".bike-button").click(function () {
      $("#destination-toggle").addClass("hidden");
      $("#bike-toggle").removeClass("hidden");
      $("#bus-toggle").addClass("hidden");
      $(".bike-button").addClass("active-tab");
      $(".bus-button").removeClass("active-tab");
    });
    // 출발지 버튼
    $("#search1").click(function () {
      $("#destination-toggle").removeClass("hidden");
      $("#bike-toggle").addClass("hidden");
      $("#search-field1").removeClass("hidden");
      $("#bus-toggle").addClass("hidden");
      $(".bus-button").removeClass("active-tab");
      $(".bike-button").removeClass("active-tab");
    });
    // 도착지 버튼
    $("#search2").click(function () {
      $("#destination-toggle").removeClass("hidden");
      $("#bike-toggle").addClass("hidden");
      $("#search-field").removeClass("hidden");
      $("#bus-toggle").addClass("hidden");
      $(".bus-button").removeClass("active-tab");
      $(".bike-button").removeClass("active-tab");
    });
}
  // 검색 기능 함수 아래 존재
function poi1(){
    dataInfo = "start";
    console.log(dataInfo)
  $("#search1").click(function () {
    var inputValue = $("#search_keyword").val();
    poiSearch(inputValue, 1);
  });
}

function poi2(){
    dataInfo = "end";
    console.log(dataInfo)
  $("#search2").click(function () {
    var inputValue = $("#search_keyword1").val();
    poiSearch(inputValue, 2);
  });
}
function person(){
  // 길찾기 기능 함수 아래 존재
  $("#navi").click(function () {
    naviPerson();
  });
}

function transport(){
  $("#bus-navi").click(function () {
    naviTransport();
  });
}

// 대중교통 기반 길찾기 서비스
function naviTransport() {
  var transportList;
  var selectRoute;

    // latLonRequest 제작하기
    var latLonRequest = {};

    latLonRequest.latlonList = { latlon: [] };

    latLonRequest.latlonList.latlon.push({
    type: "start",
    lat: startLatData,
    lon: startLonData,
    });
    latLonRequest.latlonList.latlon.push({
    type: "end",
    lat: endLatData,
    lon: endLonData,
    });

  $.ajax({
    url: "/navitransport",
    type: "POST",
        contentType: "application/json",
    data: JSON.stringify(latLonRequest),
    success: function (response) {
      console.log(response);

      // 기존 마커 제거
      if (markerArr.length > 0) {
        for (var i in markerArr) {
          markerArr[i].setMap(null);
        }
        markerArr = [];
      }

      // 기존 경로 제거
      if (resultdrawArr.length > 0) {
        for (var i in resultdrawArr) {
          resultdrawArr[i].setMap(null);
        }
        resultdrawArr = [];
      }

      transportList = response.metaData.plan.itineraries;
      selectRoute = transportList[0];

      drawRoute(selectRoute);
    },
    error: function () {
      alert("JSON 출력 실패");
    },
  });
} // function[E]

// 대중교통 지도에 실제로 그려주는 부분
function drawRoute(selectRoute) {
  var latlonList = [];
  var route = selectRoute.legs;

  // 메소드 호출시 맵 객체가 누수 되는 오류 발생, 맵 재할당 하여 해결
  if (resultdrawArr.length > 0) {
    if (resultdrawArr[0].getMap() == null) {
      for (var i in resultdrawArr) {
        resultdrawArr[i].setMap(map);
      }
    }
  }

  // 지도 크기 조절 및 이동을 위한 객체 생성
  var positionBounds = new Tmapv2.LatLngBounds();

  for (let checkRoute of route) {
    if (checkRoute.mode == "WALK") {
      // 보행자 걷는 거리 받아와서 비교후 자전거로 안내하기
      for (let latlon of checkRoute.latlons) {
        var pass = new Tmapv2.LatLng(latlon.lat, latlon.lon);

        latlonList.push(pass);
        positionBounds.extend(pass);
      }

      // latLonRequest 제작하기
      var latLonRequest = {};

      latLonRequest.latlonList = { latlon: [] };

      latLonRequest.latlonList.latlon.push({
        type: checkRoute.start.name,
        lat: checkRoute.start.lat,
        lon: checkRoute.start.lon,
      });
      latLonRequest.latlonList.latlon.push({
        type: checkRoute.end.name,
        lat: checkRoute.end.lat,
        lon: checkRoute.end.lon,
      });

      // latlonRequest 만들기
      $.ajax({
        url: "/naviperson",
        data: JSON.stringify(latLonRequest),
        contentType: "application/json",
        method: "POST",
        success: function (response) {
          console.log(response);

          var lastMarker = "walk";

          var resultData = response.features;

          drawBikeInfoArr = [];
          walkInfoList = [];
          drawWalkInfoArr = [];

          for (var i in resultData) {
            //for문 [S]
            var geometry = resultData[i].geometry;
            var properties = resultData[i].properties;
            var polyline_;

            if (properties.pointType == "PP1" || properties.pointType == "EP") {
              lastMarker = "bike";

              // 걷는 부분만 따로 저장
              walkInfoList.push(drawWalkInfoArr);
              drawWalkInfoArr = [];
            } else if (properties.pointType == "PP2") {
              lastMarker = "walk";
            }

            if (geometry.type == "LineString") {
              for (var j in geometry.coordinates) {
                if (lastMarker == "bike") {
                  // 포인트객체의 정보로 좌표값 변환 객체로 저장
                  var convertChange = new Tmapv2.LatLng(
                    geometry.coordinates[j][1],
                    geometry.coordinates[j][0]
                  );

                  // 배열에 담기
                  drawBikeInfoArr.push(convertChange);
                } else {
                  var convertChange = new Tmapv2.LatLng(
                    geometry.coordinates[j][1],
                    geometry.coordinates[j][0]
                  );
                  // 배열에 담기
                  drawWalkInfoArr.push(convertChange);
                }
              }
            } else {
              var markerImg = "";
              var pType = "";
              var size;

              if (properties.pointType == "S") {
                //출발지 마커
                markerImg = "/upload/tmap/marker/pin_r_m_s.png";
                pType = "S";
                size = new Tmapv2.Size(24, 38);
              } else if (properties.pointType == "E") {
                //도착지 마커
                markerImg = "/upload/tmap/marker/pin_r_m_e.png";
                pType = "E";
                size = new Tmapv2.Size(24, 38);
              } else {
                //각 포인트 마커
                markerImg = "http://topopen.tmap.co.kr/imgs/point.png";
                pType = "P";
                size = new Tmapv2.Size(8, 8);
              }

              // 경로들의 결과값들을 포인트 객체로 변환
              var latlon = new Tmapv2.Point(
                geometry.coordinates[0],
                geometry.coordinates[1]
              );

              // 줌 아웃을 위한 포인트 객체 저장
              var lonlat = new Tmapv2.LatLng(
                geometry.coordinates[1],
                geometry.coordinates[0]
              );

              positionBounds.extend(lonlat);

              var routeInfoObj = {
                markerImage: markerImg,
                lng: geometry.coordinates[0],
                lat: geometry.coordinates[1],
                pointType: pType,
              };

              // Marker 추가
              marker_p = new Tmapv2.Marker({
                position: new Tmapv2.LatLng(routeInfoObj.lat, routeInfoObj.lng),
                icon: routeInfoObj.markerImage,
                iconSize: size,
                map: map,
              });

              markerArr.push(marker_p);
            }
          } //for문 [E]

          drawLine(drawBikeInfoArr, walkInfoList, map, resultdrawArr);
        },
      });

      // <!--                    // 선 스타일 제어하는 부분-->
      // <!--                    polyline_ = new Tmapv2.Polyline({-->
      // <!--                        path : latlonList,-->
      // <!--                        strokeColor : "#000000",-->
      // <!--                        strokeWeight : 5,-->
      // <!--                        map : map-->
      // <!--                    });-->

      // <!--                    resultdrawArr.push(polyline_)-->
      latlonList = [];
    } else {
      for (let latlon of checkRoute.latlons) {
        var pass = new Tmapv2.LatLng(latlon.lat, latlon.lon);

        latlonList.push(pass);
        positionBounds.extend(pass);
      }

      var strokeColor = "#" + checkRoute.routeColor;

      // 선 스타일 제어하는 부분
      polyline_ = new Tmapv2.Polyline({
        path: latlonList,
        strokeColor: strokeColor,
        strokeWeight: 5,
        map: map,
      });

      resultdrawArr.push(polyline_);
      latlonList = [];
    }

    map.panToBounds(positionBounds);
  }
} // function[E}

// 보행자 길찾기 서비스
function naviPerson() {
  // 지도 크기 조절 및 이동을 위한 객체 생성
  var positionBounds = new Tmapv2.LatLngBounds();

  // 메소드 호출시 맵 객체가 누수 되는 오류 발생, 맵 재할당 하여 해결
  if (resultdrawArr.length > 0) {
    if (resultdrawArr[0].getMap() == null) {
      for (var i in resultdrawArr) {
        resultdrawArr[i].setMap(map);
      }
    }
  }

  // passList 생성하기
  var latLonRequest = {};

  latLonRequest.latlonList = { latlon: [] };

  latLonRequest.latlonList.latlon.push({
    type: "start",
    lat: startLatData,
    lon: startLonData,
  });
  latLonRequest.latlonList.latlon.push({
    type: "end",
    lat: endLatData,
    lon: endLonData,
  });

  $.ajax({
    url: "/naviperson",
    data: JSON.stringify(latLonRequest),
    contentType: "application/json",
    method: "POST",
    success: function (response) {
      var lastMarker = "walk";

      console.log(response);

      // 기존 마커 제거
      if (markerArr.length > 0) {
        for (var i in markerArr) {
          markerArr[i].setMap(null);
        }
        markerArr = [];
      }

      var resultData = response.features;

      // 결과 출력
      var tDistance =
        "총 거리 : " +
        (resultData[0].properties.totalDistance / 1000).toFixed(1) +
        "km,";
      var tTime =
        " 총 시간 : " +
        (resultData[0].properties.totalTime / 60).toFixed(0) +
        "분";

      $("#result").text(tDistance + tTime);

      // 기존 경로 및 마커 제거
      if (resultdrawArr.length > 0) {
        for (var i in resultdrawArr) {
          resultdrawArr[i].setMap(null);
        }
        resultdrawArr = [];
      }
      drawBikeInfoArr = [];
      walkInfoList = [];
      drawWalkInfoArr = [];

      for (var i in resultData) {
        //for문 [S]
        var geometry = resultData[i].geometry;
        var properties = resultData[i].properties;
        var polyline_;

        if (properties.pointType == "PP1" || properties.pointType == "EP") {
          lastMarker = "bike";

          // 걷는 부분만 따로 저장
          walkInfoList.push(drawWalkInfoArr);
          drawWalkInfoArr = [];
        } else if (properties.pointType == "PP2") {
          lastMarker = "walk";
        }

        if (geometry.type == "LineString") {
          for (var j in geometry.coordinates) {
            if (lastMarker == "bike") {
              // 포인트객체의 정보로 좌표값 변환 객체로 저장
              var convertChange = new Tmapv2.LatLng(
                geometry.coordinates[j][1],
                geometry.coordinates[j][0]
              );

              // 배열에 담기
              drawBikeInfoArr.push(convertChange);
            } else {
              var convertChange = new Tmapv2.LatLng(
                geometry.coordinates[j][1],
                geometry.coordinates[j][0]
              );
              // 배열에 담기
              drawWalkInfoArr.push(convertChange);
            }
          }
        } else {
          var markerImg = "";
          var pType = "";
          var size;

          if (properties.pointType == "S") {
            //출발지 마커
            markerImg = "/upload/tmap/marker/pin_r_m_s.png";
            pType = "S";
            size = new Tmapv2.Size(24, 38);
          } else if (properties.pointType == "E") {
            //도착지 마커
            markerImg = "/upload/tmap/marker/pin_r_m_e.png";
            pType = "E";
            size = new Tmapv2.Size(24, 38);
          } else {
            //각 포인트 마커
            markerImg = "http://topopen.tmap.co.kr/imgs/point.png";
            pType = "P";
            size = new Tmapv2.Size(8, 8);
          }

          // 경로들의 결과값들을 포인트 객체로 변환
          var latlon = new Tmapv2.Point(
            geometry.coordinates[0],
            geometry.coordinates[1]
          );

          // 줌 아웃을 위한 포인트 객체 저장
          var lonlat = new Tmapv2.LatLng(
            geometry.coordinates[1],
            geometry.coordinates[0]
          );

          positionBounds.extend(lonlat);

          var routeInfoObj = {
            markerImage: markerImg,
            lng: geometry.coordinates[0],
            lat: geometry.coordinates[1],
            pointType: pType,
          };

          // Marker 추가
          marker_p = new Tmapv2.Marker({
            position: new Tmapv2.LatLng(routeInfoObj.lat, routeInfoObj.lng),
            icon: routeInfoObj.markerImage,
            iconSize: size,
            map: map,
          });

          markerArr.push(marker_p);
        }
      } //for문 [E]

      drawLine(drawBikeInfoArr, walkInfoList, map, resultdrawArr);

      // 마커들이 보이도록 지도 조정
      map.panToBounds(positionBounds);
    },
    error: function (request, status, error) {
      console.log(
        "code:" +
          request.status +
          "\n" +
          "message:" +
          request.responseText +
          "\n" +
          "error:" +
          error
      );
    },
  });
}

// 지도에 선 그리는 부분
function drawLine(arrBikePoint, arrWalkPointList) {
  var polyline_;

  // 선 스타일 제어하는 부분
  polyline_ = new Tmapv2.Polyline({
    path: arrBikePoint,
    strokeColor: "#000000",
    strokeWeight: 5,
    map: map,
  });

  // 초기화를 위해 라인 담기
  resultdrawArr.push(polyline_);

  for (let arrWalkPoint of arrWalkPointList) {
    // 선 스타일 제어하는 부분
    polyline_ = new Tmapv2.Polyline({
      path: arrWalkPoint,
      strokeColor: "#FF0000",
      strokeStyle: "dot",
      strokeWeight: 5,
      map: map,
    });
    resultdrawArr.push(polyline_);
  }
  // 초기화를 위해 라인 담기
}

// 검색 기능
function poiSearch(searchKeyword, divNum) {
  var marker;
  var markerImg = "/img/tmap/testMarker.png";
  var positionBounds = new Tmapv2.LatLngBounds();

  $.ajax({
    url: "/poisearch",
    data: { searchKeyword: searchKeyword },
    success: function (response) {
      var data = response.searchPoiInfo.pois.poi;

      console.log(response)

      // 메소드 호출시 맵 객체가 누수 되는 오류 발생, 맵 재할당 하여 해결
      if (resultdrawArr.length > 0) {
        if (resultdrawArr[0].getMap() == null) {
          for (var i in resultdrawArr) {
            resultdrawArr[i].setMap(map);
          }
        }
      }

      // 기존 마커 제거
      if (markerArr.length > 0) {
        for (var i in markerArr) {
          markerArr[i].setMap(null);
        }
        markerArr = [];
      }

      // 기존 경로 제거
      if (resultdrawArr.length > 0) {
        for (var i in resultdrawArr) {
          resultdrawArr[i].setMap(null);
        }
        resultdrawArr = [];
      }

      var listCnt = 0;
      var listHtml;
      // 마커 생성
      for (var k = 0; k < data.length; k++) {
        // for[s]
        var noorLat = data[k].noorLat; // 위도
        var noorLon = data[k].noorLon; // 경도

        // 마커 생성
        var lonlat = new Tmapv2.LatLng(noorLat, noorLon);
        var iconSize = new Tmapv2.Size(20, 20);
        marker = new Tmapv2.Marker({
          position: lonlat,
          icon: markerImg,
          iconSize: iconSize,
          map: map,
        });
        markerArr.push(marker);
        positionBounds.extend(lonlat);

        // 출력 필드
        listCnt = listCnt + 1;
        var field = data[k]
        var name = field.name
        var lowerAddrName = field.lowerAddrName
        var fullAddressRoad = field.newAddressList.newAddress[0].fullAddressRoad
        var zipCode = field.zipCode

        // 출력 리스트 만들기
        listHtml = listHtml + `<div id='listNum${listCnt}' onclick="getLatLon(${listCnt})">`
        listHtml = listHtml + `<ul id='placeList${listCnt}'>`
        listHtml = listHtml + `<li>${name}</li>`
        listHtml = listHtml + `<li>${lowerAddrName}</li>`
        listHtml = listHtml + `<li>${fullAddressRoad}</li>`
        listHtml = listHtml + `<li>${zipCode}</li>`
        listHtml = listHtml + `<div id='lat${listCnt}' style='display: none;'>${noorLat}</div>`
        listHtml = listHtml + `<div id='lon${listCnt}' style='display: none;'>${noorLon}</div>`
        listHtml = listHtml + `</ul>`
        listHtml = listHtml + `<hr>`
        listHtml = listHtml + `</div>`

      } // for[e]

      $("#menu_wrap").html(listHtml);

      // 마커들이 보이도록 지도 조정
      map.panToBounds(positionBounds);
      map.zoomOut();
    },
    error: function () {
      // 여기서 검색 결과 실패시 모달 창이라도 띄워주세요.
      alert("검색결과가 없습니다");
    },
  });
}

function getLatLon(listCnt) {
    if(dataInfo == "start") {
        startLatData = parseFloat($(`#lat${listCnt}`).text());
        startLonData = parseFloat($(`#lon${listCnt}`).text());
        console.log("startData")
    } else {
        endLatData = parseFloat($(`#lat${listCnt}`).text());
        endLonData = parseFloat($(`#lon${listCnt}`).text());
        console.log("endData")
    }
}

//사이드바 토글
document.addEventListener("DOMContentLoaded", function () {
  var toggleButton = document.getElementById("toggleButton");
  var sidebar = document.querySelector(".sidebar");
  var searchListBox = document.querySelector(".search-list-box");

  toggleButton.addEventListener("click", function () {
    // 사이드바 및 검색 목록 상자에 close 클래스 토글
    sidebar.classList.toggle("close");
    searchListBox.classList.toggle("close");

    // 버튼 아이콘에 rotated 클래스 토글
    toggleButton.classList.toggle("rotated");
  });
});

//// 클릭 기능
//$(document).ready(function () {
//  // 대중교통 버튼
//  $(".bus-button").click(function () {
//    $("#destination-toggle").addClass("hidden");
//    $("#bike-toggle").addClass("hidden");
//    $("#bus-toggle").removeClass("hidden");
//    $(".bus-button").addClass("active-tab");
//    $(".bike-button").removeClass("active-tab");
//  });
//  // 자전거 버튼
//  $(".bike-button").click(function () {
//    $("#destination-toggle").addClass("hidden");
//    $("#bike-toggle").removeClass("hidden");
//    $("#bus-toggle").addClass("hidden");
//    $(".bike-button").addClass("active-tab");
//    $(".bus-button").removeClass("active-tab");
//  });
//  // 출발지 버튼
//  $("#search1").click(function () {
//    $("#destination-toggle").removeClass("hidden");
//    $("#bike-toggle").addClass("hidden");
//    $("#search-field1").removeClass("hidden");
//    $("#bus-toggle").addClass("hidden");
//    $(".bus-button").removeClass("active-tab");
//    $(".bike-button").removeClass("active-tab");
//  });
//  // 도착지 버튼
//  $("#search2").click(function () {
//    $("#destination-toggle").removeClass("hidden");
//    $("#bike-toggle").addClass("hidden");
//    $("#search-field").removeClass("hidden");
//    $("#bus-toggle").addClass("hidden");
//    $(".bus-button").removeClass("active-tab");
//    $(".bike-button").removeClass("active-tab");
//  });
//});
