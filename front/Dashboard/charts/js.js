var echarts = require('echarts');

var chartDom = document.getElementById('main');
var myChart = echarts.init(chartDom);
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
      data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
  
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        name: '대여',
        type: 'bar',
        stack: 'total',
        color: "#000000", // 막대 컬러색상
        label: {
          show: true,
          fontSize: 20, 
        },
        emphasis: {
          focus: 'series'
        },
        data: [320, 302, 301, 334, 390, 330, 320]
      },
      {
        name: '반납',
        type: 'bar',
        stack: 'total',
        label: {
          show: true
        },
        emphasis: {
          focus: 'series'
        },
        data: [120, 132, 101, 134, 90, 230, 210]
      },
      
    ]
  };