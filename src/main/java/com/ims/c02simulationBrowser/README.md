#httpclient学习

问题：
    请求一些网站，被感知非浏览器为httpclient发出的请求。
模拟ie请求
    httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0");
    请求头消息设置 user-agent 模拟浏览器
    请求头消息来源：
        打开浏览器，开发者模式。请求消息头中。
    响应内容类型 content-type
    响应状态 status
        200正常 403拒绝 500服务器报错 404未找到