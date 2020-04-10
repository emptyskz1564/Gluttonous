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
            'data': {
              'img': "@image('88x31','#d0d0d0')"
            } 
        })
        // 输出结果
       // console.log(JSON.stringify(res, null, 2))
        fn(res);
    }
}
module.exports = {
    ajax: ajax
}