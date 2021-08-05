#include<cstdio>
#include<cstdlib>
#include<iostream>
#include<cstring>
#include<unistd.h>
#include<fcntl.h>
#include<signal.h>
#include<algorithm>
#include<set>
using namespace std;
int hostid; // for ip 10.0.0.1, hostid=1, BFT id=0 
char hostname[6];
char ipaddr[20];
void cd(int x){
    char tmp[20];
    sprintf(tmp,"env/%d/",x);
    chdir(tmp);
}
char guicmd[]=R"(java -D"java.security.properties"="./config/java.security" -D"logback.configurationFile"="./config/logback.xml" -cp "bin/*:lib/*:./*:../../target/BlockChainDemo2-1.0-SNAPSHOT-jar-with-dependencies.jar" Main gui)";
int main(){
    char  ifconfigOuputTemplate[]=".ifconfigXXXXXX";
    mktemp(ifconfigOuputTemplate);
    char  systemCmd[200];
    sprintf(systemCmd,"ifconfig > %s",ifconfigOuputTemplate);
    system(systemCmd);
    FILE* ifconfigFile=fopen(ifconfigOuputTemplate,"r");
    int c;
    int i=0;
    while((c=fgetc(ifconfigFile))!='-'){
        if(!i&&c!='h'){
            printf("error!!!!!");
            return 1;
        }
        hostname[i++]=c;
    }
    hostname[i]='\0';
    sscanf(hostname,"h%d",&hostid);
    while((c=fgetc(ifconfigFile))!='\n');
    for(i=0;i<12;i++){
        fgetc(ifconfigFile);
    }
    fscanf(ifconfigFile,"%s",ipaddr);
    // printf("hostname=%s, ipaddr=%s\n",hostname,ipaddr);
    fclose(ifconfigFile);
    sprintf(systemCmd,"rm %s",ifconfigOuputTemplate);
    system(systemCmd);
    cd(hostid-1);
    char tmp[300];
    sprintf(tmp,"%s %d",guicmd,hostid-1);
    system(tmp);
    return 0;
}