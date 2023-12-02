
document.addEventListener("DOMContentLoaded", () => {
    echarts.init(document.querySelector("#chart-day")).setOption({
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
    xAxis: {
        type: 'category',
        data: ['월', '화', '수', '목', '금', '토', '일']
    },
    yAxis: {
          type: 'value'
    },
    series: [{
        data: [52, 47, 60, 24, 32, 55, 63],
        color: '#fcc82a',
        stack: 'Total',
        label: {
            fontSize: 20,
            show: true,
            position: 'top'
        },
        type: 'line',
        smooth: false
      }]
    });
  });