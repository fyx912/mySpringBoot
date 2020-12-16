Logstash 的安装包进行解压。然后在config文件夹下添加 test.conf 文件内容：

````
input{
        tcp {   
                #模式选择为server
                mode => "server"
                #ip和端口根据自己情况填写，端口默认4560,对应下文logback.xml里appender中的destination
                host => "0.0.0.0" #我这里是本地
                port => 4560 #开放这个端口进行采集
                codec => json # 编解码器
        }
}
filter {
  #过滤器，根据需要填写
}
output {
  elasticsearch {
    action => "index"
    #这里是es的地址，多个es要写成数组的形式
    hosts  => "192.168.0.21:9200"
    #用于kibana过滤，可以填项目名称
    index  => "%{[appname]}"
  }
}
````