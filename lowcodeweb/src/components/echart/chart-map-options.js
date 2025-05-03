export default {
    cptDataForm: '[{"name":"广州市","value":1}]',
    attribute:{
        roam:false,titleText:'',titleLeft: 'center', titleTop: 10,
        subtext:'',titleFontSize:20,titleColor: '#ddd',
        subTitleColor:'#aaa',subTitleFontSize:13,
        seriesName:'确诊病例',geoLabelColor:'#555',geoLabelSize:12,
        piecesOptions: [
        {
            gte: 10,
            lte: 100,
            label: '10 - 100 人',
            color: '#ff5428'
        }, {
            gte: 1,
            lt: 10,
            label: '1 - 9 人',
            color: '#ff8c71'
        }, {
            gte: 0,
            lt: 0,
            label: '无',
            color: '#eee'
        }]
    }
}