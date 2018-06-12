
# 考试前准备
请大家提前下载和安装以下软件：<br/>
1、JDK 1.8+ https://java.com/zh_CN/download/<br/>
2、IntelliJ (推荐)https://www.jetbrains.com/idea/download/ （不推荐Eclipse）<br/>
3、Git Bash命令行 （https://msysgit.github.io/ 或 https://www.sourcetreeapp.com/）<br/>
4、Maven https://maven.apache.org/download.cgi<br/>
5、将该工程fork到自己Github下<br/>
6、下载到自己的IDE里面，保证环境运行没有问题<br/>

# 考试提醒
* 编程前请仔细阅读需求文档,理解需求文档预计需要花费10~30分钟来理解,请务必看清楚要求再动手
* 编程时建议经常保持能编译运行，确保可以获得已经实现功能的得分
* 除题目明确要求需要考虑的异常情况、输入校验外，其他异常情况和输入校验一律不需要考虑。
* 请修改提供的测试用例，以符合自己的测试数据。
* 关于代码提交提交到你自己的github从该工程fork过去的工程下。<br/>
  比如路径为https://github.com/migu999/Demo.git   其中migu999为gitHub Username<br/>
  考试结束截止时间考试程序会根据各位提供的GitHub Username去拉取代码，后进行自动的单元测试<br/>

# 题目
从输入文件读取字符串，使用多线程进行排序，然后写入输出文件
* 构造N个线程将数据切割成N组分别使用qsort算法排序
* 主线程等待所有子线程排好序，归并子线程的结果
* 将结果写入输出文件
