h1.cmd("sudo -u bupt ./runp2p > .h1.log 2> .h1.log &")
time.sleep(2)
nNodes=200
template='cmd("sudo -u bupt ./runp2p &")'
template_log='h%d.cmd("sudo -u bupt ./runp2p foo bar h%d > .h%d.log 2> .h%d.log &")'
for i in range(2,nNodes+1):
    # exec("h%d.%s"%(i,template))
    exec(template_log%(i,i,i,i))

# h2.cmd("sudo -u bupt ./runp2p &")
# h3.cmd("sudo -u bupt ./runp2p &")
# h4.cmd("sudo -u bupt ./runp2p &")
# h5.cmd("sudo -u bupt ./runp2p &")
# h6.cmd("sudo -u bupt ./runp2p &")
# h7.cmd("sudo -u bupt ./runp2p &")
# h8.cmd("sudo -u bupt ./runp2p &")
# h9.cmd("sudo -u bupt ./runp2p &")
# h10.cmd("sudo -u bupt ./runp2p &")
# h4.cmd("./runp2p &")
# h5.cmd("./runp2p &")
# h6.cmd("./runp2p &")
# h7.cmd("./runp2p &")
# h8.cmd("./runp2p &")
# h1.cmd("java 2> h1.log")
