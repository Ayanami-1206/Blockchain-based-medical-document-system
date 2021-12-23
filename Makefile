build: runguibuild
	mvn clean compile assembly:single | egrep "INFO"
blockinit:
	rm -rf env/*/block_data
	rm -rf env/*/currentView
	rm -rf env/*/src
	rm -rf env/*/.h*.in
	rm -rf env/*/.h*.out
	rm -rf env/*/kill.sh
	rm -rf env/*/.storage
runguibuild: rungui.cpp Makefile
	g++ rungui.cpp -o rungui -g 2>/dev/null
run0:
	cd env/0 && java -Xms10m -Xmx20m -D"java.security.properties"="./config/java.security" -D"logback.configurationFile"="./config/logback.xml" -cp "bin/*:lib/*:./*:../../target/BlockChainDemo2-1.0-SNAPSHOT-jar-with-dependencies.jar" Main 0
run1:
	cd env/1 && java -D"java.security.properties"="./config/java.security" -D"logback.configurationFile"="./config/logback.xml" -cp "bin/*:lib/*:./*:../../target/BlockChainDemo2-1.0-SNAPSHOT-jar-with-dependencies.jar" Main 1
run2:
	cd env/2 && java -D"java.security.properties"="./config/java.security" -D"logback.configurationFile"="./config/logback.xml" -cp "bin/*:lib/*:./*:../../target/BlockChainDemo2-1.0-SNAPSHOT-jar-with-dependencies.jar" Main 2
run3:
	cd env/3 && java -D"java.security.properties"="./config/java.security" -D"logback.configurationFile"="./config/logback.xml" -classpath "bin/*:lib/*:./*:../../target/BlockChainDemo2-1.0-SNAPSHOT-jar-with-dependencies.jar" Main 3
runall:
	make run0 & make run1 & make run2 & make run3 &
killall:
	jobs -p | xargs -n1 pkill -SIGINT -g # not the same bash env, doestn't work
runp2pbuild:
	cd ../open-chord-code/&& make runp2p
antbuild:
	cd ../library && ant	
batchkill:
	killall -g -SIGINT runp2p || true
	killall runp2p || true
	rm -f *.log
	rm -f .*.log
netstart:
	sudo mn --custom MyTopo200.py --topo mytopo  # 201 for illegal devices
run_gui:
	@./rungui 1>/dev/null 2>/dev/null
renzheng_bench:
	cd env/0 && java -D"java.security.properties"="./config/java.security" -D"logback.configurationFile"="./config/logback.xml" -cp "bin/*:lib/*:./*:../../target/BlockChainDemo2-1.0-SNAPSHOT-jar-with-dependencies.jar" bench
shouquan_bench:
	cd env/0 && java -D"java.security.properties"="./config/java.security" -D"logback.configurationFile"="./config/logback.xml" -cp "bin/*:lib/*:./*:../../target/BlockChainDemo2-1.0-SNAPSHOT-jar-with-dependencies.jar" shouquan_bench


demo_init_gadget:
	make batchkill
	make blockinit	


init:
	@make demo_init_gadget > /dev/null 2>/dev/null &
	@bash bar.sh

set_quiet_run:
	@ln -s -f /home/bupt/sdlyyxy/BlockChainDemo2/env/0/config/quiet_run.xml /home/bupt/sdlyyxy/BlockChainDemo2/env/0/config/logback.xml

set_show_consensus:
	@ln -s -f /home/bupt/sdlyyxy/BlockChainDemo2/env/0/config/show_consensus.xml /home/bupt/sdlyyxy/BlockChainDemo2/env/0/config/logback.xml

view_consensus:
	@grep --color=always -e "type=.*," -e "deci.* " BFT.log

show_selected_nodes:
	@cat /home/bupt/sdlyyxy/BlockChainDemo2/env/0/config/hosts.config