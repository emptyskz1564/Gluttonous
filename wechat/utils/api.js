let API_HOST = "http://xxx.com/xxx";
let DEBUG = true;//切换数据入口
var Mock = require('mock.js')
function ajax(data = '', fn, method = "get", header = {}) {
    if (!DEBUG) {
        wx.request({
            url: config.API_HOST + data,
            method: method ? method : 'get',
            data: {},
            header: header ? header : { "Content-Type": "application/json" },
            success: function (res) {
                fn(res);
            }
        });
    } else {
        // 模拟数据
        var res = Mock.mock({
            'error_code': '',
            'error_msg': '',
            'data|10': [{
                'id|+1': 1,
                'img': "@image('200x100', '#4A7BF7','#fff','pic')",   //打卡图片
                'userName':'@ctitle(2,5)',    //用户名
                'cardContent': '@ctitle(16,25)',   //打卡内容
                'cardTitle':'@ctitle(8,10)',        //打卡标题
                'cardLabel|3':['@ctitle(3,5)'],   //打卡标签
                'city': "@county(true)",
                'restId': '@integer(0,100)',   //打卡餐厅id
                'restName':'@ctitle(4,7)',        //打卡餐厅名字
                'cardId': '@integer(0,100)',   //打卡id
                'cardLike': '@integer(0,30)',   //打卡点赞数
                'marketing_start': '@datetime()',
                'marketing_stop': '@now()',
                'price': '@integer(100,2000)',//现价，单位：分  
                'original_price': '@integer(100,3000)'
            }]  
        })
        // 输出结果
       // console.log(JSON.stringify(res, null, 2))
        fn(res);
    }
}
module.exports = {
    ajax: ajax
}