import numpy as np
data=np.loadtxt("times_renzheng",dtype=np.dtype('i8'))
print("认证操作时间统计分析")
print("Number of samples: %d. Average time: %.4f seconds."%(data.size,data.mean()/1e9))
data=np.loadtxt("times_shouquan",dtype=np.dtype('i8'))
print("授权操作时间统计分析")
print("Number of samples: %d. Average time: %.4f seconds."%(data.size,data.mean()/1e9))
data=np.loadtxt("times_renzheng",dtype=np.dtype('i8'))
print(data[0:-1:5].mean()/1e9)
print(data[1:-1:5].mean()/1e9)
print(data[2:-1:5].mean()/1e9)
print(data[3:-1:5].mean()/1e9)
print(data[4:-1:5].mean()/1e9)



# print(data[0:-1:4].mean()/1e9)
# print(data[1:-1:4].mean()/1e9)
# print(data[2:-1:4].mean()/1e9)
# print(data[3:-1:4].mean()/1e9)
