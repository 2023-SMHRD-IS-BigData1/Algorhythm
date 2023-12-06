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
    right: '3%',
    bottom: '5%',
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
        fontFamily: 'Ghanachocolate',
         
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
        fontFamily: 'Ghanachocolate',
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

