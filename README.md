#httpclient学习
简介
    httpClient 是Apache Jakarta Common 下的子项目，可以用来提供高效的、最新的、功能丰富的支持 HTTP 协议的客户端编程工具包，
    并且它支持 HTTP 协议最新的版本和建议。简而言之：就是模拟浏览器请求url获取响应、获取网页数据。
编码
    1.引入jar包
    2.使用httpclient 用来发送get或者post请求
问题：
    请求一些网站，被感知非浏览器为httpclient发出的请求。
模拟ie请求
    1.设置请求头消息 user-agent 模拟浏览器
        打开浏览器，开发者模式。请求消息头中。
    2.获取响应内容类型 content-type
    3.获取响应状态 status
        200正常 403拒绝 500服务器报错 404未找到
README.md
使用代理ip
    1.有的网站会反爬虫，对频繁访问、有规则的访问，采集ip，限制访问
    2.代理ip  
        1.透明代理，可以查到ip
        2.匿名代理，查不到ip，但是知道使用了代理。
        3.混淆代理，与匿名代理一样，别人会得到一个假的ip
        4.高匿代理，无法发现使用了代理
超时
    1.连接超时
        httpClient发送请求开始到连接到目标url主机的时间
            默认时间是1分钟，设置成10秒合理
    2.读取时间
        httpClient连接到目标url主机到读取数据花费的时间
            比如设置10秒