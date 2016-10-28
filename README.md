# Cas9

Cas9是一个根据[张峰实验室](http://crispr.mit.edu/about)的打分策略开发的一个离线的、分布式的、多线程的打分程序。
其打分策略完全来自[张峰实验室](http://crispr.mit.edu/about)。

## 特点：

    1. 快速，在人类基因组的测试下（30G以上的background database)，给一个序列打分，通常在0.6秒左右。
    2. 多线程，你可以在命令行通过-t参数来指定线程的数量
    3. 分布式，你可以将你的计算任务分开，放到多台服务器上运行
    4. 可以和pycas9结合，跑通整个流程

## 测试：
```shell
mvn test
```

## build:

```shell
mvn clean compile assembly:single
```

##  运行
```shell
java -jar -i cas9-demo.csv -c demo.conf -t 10
```
## 

[文档](tps://github.com/cangtu/cas9/wiki)
