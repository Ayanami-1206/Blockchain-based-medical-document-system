import numpy as np
data=np.loadtxt("times_renzheng",dtype=np.dtype('i8'))
print("认证操作时间统计分析")
print("Number of samples: %d. Average time: %.4f seconds."%(data.size,data.mean()/1e9))
data=np.loadtxt("times_shouquan",dtype=np.dtype('i8'))
print("授权操作时间统计分析")
print("Number of samples: %d. Average time: %.4f seconds."%(data.size,data.mean()/1e9))
