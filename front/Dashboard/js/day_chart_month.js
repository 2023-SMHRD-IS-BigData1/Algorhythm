var dom = document.getElementById('chart-container');
var myChart = echarts.init(dom, null, {
  renderer: 'canvas',
  useDirtyRect: false
});
var app = {};

var option;

option = {
  title: {
  },
  tooltip: {
    trigger: 'axis'
  },
  legend: {
    data: ['월별 이용현황']
  },
  grid: {
    left: '3%',
    right: '4%',
    bottom: '3%',
    containLabel: true
  },
  toolbox: {
  },
  xAxis: {
    type: 'category',
    boundaryGap: false,
    data: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월']
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      name: '월별 이용현황',
      type: 'line',
      stack: 'Total',
      color: '#444eff',
      label: {
        fontSize: 20,
        show: true,
        position: 'top'
      },
      data: [430, 325, 564, 185, 156, 550, 297, 374, 359, 589, 415, 216]
    },
  ]
};

if (option && typeof option === 'object') {
  myChart.setOption(option);
}

window.addEventListener('resize', myChart.resize);