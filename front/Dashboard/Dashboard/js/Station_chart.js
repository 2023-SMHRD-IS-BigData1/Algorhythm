var dom = document.getElementById('chart-container');
var myChart = echarts.init(dom, null, {
  renderer: 'canvas',
  useDirtyRect: false
});
var app = {};

var option;

option = {
  tooltip: {
    trigger: 'axis',
    axisPointer: {
      // Use axis to trigger tooltip
      type: 'shadow' // 'shadow' as default; can also be 'line' or 'shadow'
    }
  },
  legend: {},
  grid: {
    left: '3%',
    right: '4%',
    bottom: '3%',
    containLabel: true
  },
  xAxis: {
    type: 'category',
    data: ['정거장1', '정거장2', '정거장3', '정거장4', '정거장5', '정거장6', '정거장7', '정거장8']
    
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      name: '대여',
      type: 'bar',
      stack: 'total',
      color: '#4b51fc',
      label: {
        show: true,
        fontSize : 20,
      },
      emphasis: {
        focus: 'series'
      },
      data: [320, 302, 301, 334, 390, 330, 320, 123]
    },
    {
      name: '반납',
      type: 'bar',
      stack: 'total',
      color: '#fc4b66',
      label: {
        show: true,
        fontSize : 20,
      },
      emphasis: {
        focus: 'series'
      },
      data: [120, 132, 101, 134, 90, 230, 210, 450]
    },
  ]
};

if (option && typeof option === 'object') {
  myChart.setOption(option);
}

window.addEventListener('resize', myChart.resize);



// 데이터베이스 코드

// const express = require('express');
// const cors = require('cors');
// const app = express();

// app.use(cors());

// // 가상의 정거장 데이터
// const stationData = ['정거장1', '정거장2', '정거장3', '정거장4', '정거장5', '정거장6', '정거장7'];

// app.get('/stations', (req, res) => {
//   res.json(stationData);
// });

// const PORT = process.env.PORT || 3000;
// app.listen(PORT, () => {
//   console.log(`Server is running on port ${PORT}`);
// });

// document.addEventListener('DOMContentLoaded', function () {
//     // ECharts 라이브러리가 로드되었고 유효한 컨테이너가 있다고 가정
//     let myChart = echarts.init(document.getElementById('chart-container'));
  
//     // 서버에서 정거장 데이터를 가져오는 함수
//     function fetchStationData() {
//       return fetch('http://localhost:3000/stations')
//         .then(response => response.json())
//         .catch(error => console.error('Error fetching station data:', error));
//     }
  
//     // 초기 option 설정
//     let option = {
//       xAxis: {
//         type: 'category',
//         data: []  // 초기에는 빈 배열로 설정
//       },
//       yAxis: {
//         type: 'value'
//       },
//       series: [
//         {
//           name: '대여',
//           type: 'bar',
//           stack: 'total',
//           color: '#1ece45',
//           label: {
//             show: true,
//             fontSize: 20,
//           },
//           emphasis: {
//             focus: 'series'
//           },
//           data: []
//         },
//         {
//           name: '반납',
//           type: 'bar',
//           stack: 'total',
//           color: '#f4f742',
//           label: {
//             show: true,
//             fontSize: 20,
//           },
//           emphasis: {
//             focus: 'series'
//           },
//           data: []  // 초기에는 빈 배열로 설정
//         },
//       ],
//     };
  
//     // 서버에서 정거장 데이터를 가져온 후 차트를 초기화
//     fetchStationData().then(data => {
//       option.xAxis.data = data;
//       option.series[0].data = generateRandomData();
//       option.series[1].data = generateRandomData();
//       myChart.setOption(option);
//     });
  
//     // 동적 업데이트를 위한 간격 설정
//     setInterval(function () {
//       // 데이터를 동적으로 업데이트
//       option.series[0].data = generateRandomData();
//       option.series[1].data = generateRandomData();
  
//       // 업데이트된 데이터로 차트 새로 고침
//       myChart.setOption(option, true);
//     }, 3000); // 필요에 따라 간격 조절
//   });