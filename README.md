* 第一类用户，第二类用户，第三类用户的user_role分别是0，1，2，管理节点user_role是3（三级节点）
* in mininet> py execfile('batchp2p.py')
* teamviewer: 8iedgej9
* 追溯：ShowMessageUI

## 编译

软件环境：
* Ubuntu 20.04
* Linux Kernel 5.4
* python 2.7.18
* python 3.8.10
* mininet 2.2
* numpy 1.21.1
* OpenJDK 8 u292
* Apache ant 1.10.7
* Apache maven 3.6.3
* g++ 9.3.0
* SFML 2.5.1
* GNU Make 4.2.1

获取代码后，可以看到项目整体上组织为三个文件夹，BlockChainDemo2，library和open-chord-code，三者分别是区块链认证授权主程序、BFT-smart模块和Open-chord模块。认证授权程序的运行依赖于BFT-smart和Open-chord，首先对这两部分进行编译。

进入open-chord-code目录，配置runp2p.cpp文件中的servers_num变量和servers_f变量，servers_num代表参与共识的节点数量，servers_f代表允许容错的节点数量，两者的关系要满足servers_num>=3*servers_f+1，servers_num的范围是4-200。配置完成后在命令行中输入make：

*附图*

编译生成P2P软件包。

进入library目录，输入ant：

*附图*

编译生成BFT共识软件包。

进入BlockChainDemo2目录，输入make build：

*附图*

注意第一次编译maven需要从互联网中下载项目所需依赖，耗费时间较长。编译后生成区块链认证授权程序。

## 运行

以下操作都在BlockChainDemo2目录下进行。

区块初始化，输入make blockinit：

*pic*

开启mininet网络，输入make netstart：

*pic*

在开启mininet网络这一步，用户可以修改Makefile文件中的sudo mn --topo single,201，对网络拓扑、网络速率进行自定义。

在mininet控制台里输入mininet> py execfile('batchp2p.py')，开启共识节点集群：

*pic*

通过batchp2p.py里面nNodes参数可以控制网络节点个数，大小范围是4-200。

运行batchp2p.py之后可以选定一台mininet中的虚拟主机运行认证授权程序。例如，假设要在管理节点（h1）上运行，首先输入ps aux|grep h1$

*pic*

找到该虚拟主机对应的进程号PID在输出的第二项，为180263。输入bash enter.sh 180263，即可进入该主机。可以通过ifconfig确认虚拟主机的网络环境。

输入make run_gui，即可在当前的虚拟主机里运行认证授权程序。注意运行make run_gui之前一定要进入一台虚拟主机环境，否则无法正常运行。

*pic*

*之前测试文档的内容*

## 结束运行

要结束整个系统的运行，输入make batchkill

*pic*

并退出mininet，关闭网络虚拟环境，在mininet控制台中输入exit并回车：

*pic*

此时区块信息是保留的，要同时清理区块，输入make blockinit

*pic*

此时系统完全恢复到运行前初始状态，可以再次重新配置、编译、运行。

## 时间指标分析

程序运行时会将每次认证授权所需时间以纳秒为单位存储在BlockChainDemo2目录下的times文档中。要分析时间指标，输入python3 time_analysis.py

*pic*

即可得出结果。