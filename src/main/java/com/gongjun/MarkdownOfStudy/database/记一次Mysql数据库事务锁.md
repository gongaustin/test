###运行python程序执行多条update修改语句,update完成之后再commit,但是没有update没有执行完毕，IDE宕了，强制关闭IDE重启后，再次执行update,发现无论如何都无法执行成功，mysql报"lock wait timeout exceeded"的错误"
###原因:之前update语句并没有执行完毕，没有commit,
###解决办法，查询拿琐的线程并kill掉
select * from information_schema.innodb_trx