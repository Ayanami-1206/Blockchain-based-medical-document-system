* state hash: 使之更加像默克尔树？
* 除了state root，还要增加操作树？
* ~~block header增加设备ID~~
* 非法设备直接推出系统，非法用户提示信息有误
* 第一类用户测试流程和测试数据只读。第二类用户对测试流程只读，对测试数据可读删，第三类用户对测试流程可读写，对测试数据只读（检查代码，看看是GUI干的还是合约干的）
* 对照《详细设计》，用户资源的权限关系/映射关系
* 使用设备的物理地址？
* 新增设备？GUI/合约实现了多少？
* 数据回溯
* 签名：RSA/EC
* 验证：网络消息字段的合法性，签名。。。
* 认证授权时间的测量，认证授权时间是否会受到业务量的影响？时间指标与设备硬件是否有关系，还是只和协议有关系？
* 身份认证2s，授权3s
* 三级节点与一次性密钥
* returnStr =Ti+"&"+temp_name+"*" + "在ip地址为" + Ei + "上申请注销，于" + Ti + "注销失败,失败原因是密码错误"+"*注销"; 实际是身份证号id number错误
* mininet网速限制