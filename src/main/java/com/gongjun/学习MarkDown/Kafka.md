#消息队列

##模式
>1.点对点
>                   请求<---------
>>  Producer--Cluster(消息集群)<-->Customer
>                  ------->发送消息
>2.订阅模式
>
>>Producer--Cluster(消息集群)--> CustomerOne
>>                         --> CustomerTwo
>>                         --> CustomerThree