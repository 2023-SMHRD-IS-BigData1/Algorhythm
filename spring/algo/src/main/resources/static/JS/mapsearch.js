// 주요 필드
let map;
var markerArr = [];
var resultdrawArr = [];
var dataInfo;
var startLatData;
var startLonData;
var endLatData;
var endLonData;
var stationMarker = [];

function initTmap() {
  // 맵 객체 생성
  map = new Tmapv2.Map(
    "map", // "map" id 값과 맞추기
    {
      center: new Tmapv2.LatLng(36.5040736, 127.2494855),
      width: "100%",
      height: "800px",
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

        console.log("맵 생성");

        var marker = new Tmapv2.Marker({
          position: markerPosition,
          icon: "/img/tmap/bike.png",
          iconSize: new Tmapv2.Size(15, 15),
          title: name,
          map: map,
        });

        stationMarker.push(marker)

      }
    },
  });
}

function stationShow(show) {
    console.log(stationMarker.length)
    if (show == "off") {
        for (let marker of stationMarker) {
            marker.setVisible(false);
        }
        console.log("off")
    } else if (show == "on"){
        for (let marker of stationMarker) {
            marker.setVisible(true);
        }
        console.log("on")
    }
}

// 검색 기능 함수 아래 존재
function poi1() {
  dataInfo = "start";
  console.log(dataInfo);
  var inputValue = $("#search_keyword").val();
  stationShow("on")
  poiSearch(inputValue);
}

function poi2() {
  dataInfo = "end";
  console.log(dataInfo);
  var inputValue = $("#search_keyword1").val();
  stationShow("on")
  poiSearch(inputValue);
}

function person() {
  naviPerson();
}

function transport() {
  naviTransport();
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

      stationShow("off")

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
      alert("출발지와 도착지를 입력해주세요");
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
              console.log(properties.pointType)
              if (properties.pointType == "SP") {
                //출발지 마커
                markerImg = "../img/tmap/start.png";
                pType = "S";
                visible = true;
                size = new Tmapv2.Size(24, 38);
              } else if (properties.pointType == "EP") {
                //도착지 마커
                markerImg = "../img/tmap/end.png";
                visible = true;
                pType = "E";
                size = new Tmapv2.Size(24, 38);
              } else if (properties.pointType == "PP1") {
                  //도착지 마커
                  markerImg = "../img/tmap/bike.png";
                  visible = true;
                  pType = "B";
                  size = new Tmapv2.Size(15, 15);
              } else if (properties.pointType == "PP2") {
                  //도착지 마커
                  markerImg = "../img/tmap/bike.png";
                  visible = true;
                  pType = "B";
                  size = new Tmapv2.Size(15, 15);
              } else {
                //각 포인트 마커
                markerImg = "";
                pType = "P";
                visible = false;
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

              if (properties.pointType != "SP" && properties.pointType != "EP"){
                  // Marker 추가
                  marker_p = new Tmapv2.Marker({
                    position: new Tmapv2.LatLng(routeInfoObj.lat, routeInfoObj.lng),
                    icon: routeInfoObj.markerImage,
                    iconSize: size,
                    visible : visible,
                    map: map
                  });

                  markerArr.push(marker_p);
              } else if (properties.pointType == "SP") {
              } else if (properties.pointType == "EP") {
              }
            }
          } //for문 [E]
          drawLine(drawBikeInfoArr, walkInfoList, map, resultdrawArr);
        },
      });

      latlonList = [];
    } else {
      for (let latlon of checkRoute.latlons) {
        var pass = new Tmapv2.LatLng(latlon.lat, latlon.lon);

        latlonList.push(pass);
        positionBounds.extend(pass);
      }
      var busName = checkRoute.route;
      $("#busName").text(busName)

      var strokeColor = "#" + checkRoute.routeColor;
      $("#busColor").css("background-color", strokeColor)

      // 선 스타일 제어하는 부분
      polyline_ = new Tmapv2.Polyline({
        path: latlonList,
        strokeColor: strokeColor,
        strokeWeight: 6,
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
      stationShow("off")
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

          // 여기서 자전거 마커 제어하기

          var routeInfoObj = {
            markerImage: markerImg,
            lng: geometry.coordinates[0],
            lat: geometry.coordinates[1],
            pointType: pType,
          };
          if (properties.pointType == "PP1" || properties.pointType == "PP2") {
              marker_p = new Tmapv2.Marker({
                  position: new Tmapv2.LatLng(routeInfoObj.lat, routeInfoObj.lng),
                  icon: "../img/tmap/bike.png",
                  iconSize: new Tmapv2.Size(15, 15),
                  map: map
              });
          } else {
              // Marker 제어하는 곳
              marker_p = new Tmapv2.Marker({
                position: new Tmapv2.LatLng(routeInfoObj.lat, routeInfoObj.lng),
                icon: routeInfoObj.markerImage,
                visible: false,
                iconSize: size,
                map: map,
              });
          }

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
    error: function () {
          alert("출발지와 도착지를 입력해주세요");
        },
  });
}

// 지도에 선 그리는 부분
function drawLine(arrBikePoint, arrWalkPointList) {
  var polyline_;

  // 선 스타일 제어하는 부분
  polyline_ = new Tmapv2.Polyline({
    path: arrBikePoint,
    strokeColor: "#8e00ff",
    strokeWeight: 6,
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
      strokeWeight: 6,
      map: map,
    });
    resultdrawArr.push(polyline_);
  }
  // 초기화를 위해 라인 담기
}

// 검색 기능
function poiSearch(searchKeyword) {
  var marker;
  var positionBounds = new Tmapv2.LatLngBounds();

  $.ajax({
    url: "/poisearch",
    data: { searchKeyword: searchKeyword },
    success: function (response) {
      var data = response.searchPoiInfo.pois.poi;

      console.log(response);

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
      var listHtml = "";
      // 마커 생성
      for (var k = 0; k < data.length; k++) {
        // for[s]
        var noorLat = data[k].noorLat; // 위도
        var noorLon = data[k].noorLon; // 경도
        var markerImg = "../img/tmap/"+(k+1)+".png"
        // 마커 생성
        var lonlat = new Tmapv2.LatLng(noorLat, noorLon);
        var iconSize = new Tmapv2.Size(20, 37);
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
        var field = data[k];
        var name = field.name;
        var lowerAddrName = field.lowerAddrName;
        var fullAddressRoad =
          field.newAddressList.newAddress[0].fullAddressRoad;
        var zipCode = field.zipCode;

        // 출력 리스트 만들기
        listHtml =
        listHtml +
          `<div id='listNum${listCnt}' onclick="getLatLon(${listCnt})">`;
        listHtml = listHtml + `<hr>`;
        listHtml = listHtml + `<div>`;
        listHtml = listHtml + `<img src='../img/tmap/m${k + 1}@2x.png'>`;
        listHtml = listHtml + `<ul id='placeList${listCnt}'>`;
        listHtml = listHtml + `<li>${name}</li>`;
        listHtml = listHtml + `<li>${lowerAddrName}</li>`;
        listHtml = listHtml + `<li>${fullAddressRoad}</li>`;
        listHtml = listHtml + `<li>${zipCode}</li>`;
        listHtml =
          listHtml +
          `<div id='lat${listCnt}' style='display: none;'>${noorLat}</div>`;
        listHtml =
          listHtml +
          `<div id='lon${listCnt}' style='display: none;'>${noorLon}</div>`;
        listHtml = listHtml + `</ul>`;
        listHtml = listHtml + `</div>`;
        listHtml = listHtml + `</div>`;
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
// 클릭한 리스트의 명칭을 가져와서 해당 입력창에 출력
var name = $(`#placeList${listCnt} li:first-child`).text();
// 시작지와 도착지를 구분하여 적절한 입력창에 명칭을 출력
 if (dataInfo === "start") {
    $("#search_keyword").val(name);
     $("#search_keyword").css({
     "background-color":"rgba(0, 255, 106, 0.75)",
     "border-radius":"5px"
     });
  } else if (dataInfo === "end") {
    $("#search_keyword1").val(name);
    $("#search_keyword1").css({
    "background-color":"rgba(0, 255, 106, 0.75)",
    "border-radius":"5px"
    });
  }

  if (dataInfo == "start") {
    startLatData = parseFloat($(`#lat${listCnt}`).text());
    startLonData = parseFloat($(`#lon${listCnt}`).text());
    console.log("startData");
  } else {
    endLatData = parseFloat($(`#lat${listCnt}`).text());
    endLonData = parseFloat($(`#lon${listCnt}`).text());
    console.log("endData");
  }
}

// 대중교통, 자전거 버튼 클릭시 토글
 document.addEventListener("DOMContentLoaded", function () {
   var toggleButton = document.getElementById("toggleButton");
   var sidebar = document.querySelector(".sidebar");
   var searchListBox = document.querySelector(".search-list-box");

   toggleButton.addEventListener("click", function () {
     // 사이드바 및 검색 목록 상자에 open/close 클래스 토글
     sidebar.classList.toggle("open");
     searchListBox.classList.toggle("open");

     // 버튼 아이콘에 rotated 클래스 토글
     toggleButton.classList.toggle("rotated");
   });

   var busButton = document.getElementById("bus-navi");
   var bikeButton = document.getElementById("navi");
   var busListInfo = document.querySelector(".bus-list-info");

   busButton.addEventListener("click", function () {
     // 버스 버튼 클릭 시 사이드바 닫기
     sidebar.classList.remove("open");
     searchListBox.classList.remove("open");
     toggleButton.classList.remove("rotated");

     // 출발지와 도착지가 입력되었는지 확인
       if (startLatData && startLonData && endLatData && endLonData) {
         // 출발지와 도착지가 입력되었을 때의 로직
         busListInfo.style.display = "block";
         naviTransport(); // 길찾기 서비스 호출
       } else {
         // 출발지 또는 도착지가 입력되지 않았을 때의 로직
         busListInfo.style.display = "none"; // 입력이 안되어 있으면 숨기기
       }
    });

   bikeButton.addEventListener("click", function () {
     // 자전거 버튼 클릭 시 사이드바 닫기
     sidebar.classList.remove("open");
     searchListBox.classList.remove("open");
     toggleButton.classList.remove("rotated");
     busListInfo.style.display = "none";
   });
 });

//검색시 엔터기능
var input1 = document.getElementById("search_keyword");
var input2 = document.getElementById("search_keyword1");
var button1 = document.getElementById("search1");
var button2 = document.getElementById("search2");

function handleEnterKeyPress(input, button, nextInput) {
    return function(event) {
        if (event.keyCode === 13) {
            event.preventDefault();
            button.click();
            input.blur();
            nextInput.focus();
        }
    };
}
input1.addEventListener("keyup", handleEnterKeyPress(input1, button1, input2));
input2.addEventListener("keyup", handleEnterKeyPress(input2, button2));
