export default {
  attribute: {
    chartTitle: "",               //主标题文本
    titleLeft: "center",          //title 组件离容器左侧的距离
    titleTop: 0,                  //title 组件离容器上侧的距离
    titleTextColor: "#1590ff",    //标题color
    titleFontSize: 14,            //标题字体大小
    legendShow:false,             //是否显示图例
    xAxisShow: true,
    yAxisShow: true,
    xLineShow: true,              //是否显示x坐标轴轴线
    yLineShow: false,              //是否显示y坐标轴轴线
    yAxisName:"异常数据（条）",
    xLabelColor: "#fff",       //x刻度标签颜色
    xLineColor: "#ccc",           //x轴线颜色
    yLabelColor: "#fff",       //y刻度标签颜色
    yLineColor: "#ccc",           //y轴线颜色
    yGridLineShow: true,         //是否显示网格线
    yTickShow: false,              //是否显示y坐标刻度
    xTickShow: false,              //是否显示x坐标刻度
    barBgShow: false,             //是否显示柱条的背景色
    barBorderRadius: 10,          //设置圆角半径
    barLabelShow: false,          //图形上的文本标签
    barLabelColor: "#ff0",        //图形上的文本标签颜色
    barLabelSize: 10,
    gradualColor: true,
    barColor: "#F8A16F",          //图像颜色
    barColor1: "#F8A16F",
    barColor2: "#FC5389",
    barColor3: "#FC5389",
    barWidth: 18,
    barType: "bar",               //图像类型
    xFontRotate: 0,               //x轴坐标刻度倾斜角度
    barPath: "path://M0,10 L10,10 C5.5,10 5.5,5 5,0 C4.5,5 4.5,10 0,10 z",
    dataZoom: 
      {
        type: 'slider',
        show: true,
        xAxisIndex: [0],
        start: 1,
        end: 55
      },
  },
  cptDataForm: {
    xData: "广州市,深圳市,东莞市,茂名市,珠海市,江门市,汕头市,云浮市,韶关市,湛江市,阳江市",
    yData: [
      [120, 200, 150, 80, 70, 110, 130],
      [120, 200, 150, 80, 70, 110, 130],
    ],
    yDataName: ["差旅酒店", "非协议酒店"],
  },
};