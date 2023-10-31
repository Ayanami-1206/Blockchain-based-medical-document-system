import contract.Contracts.ContractServer;
import contract.Contracts.Initialization_Contract;
import contract.Contracts.Tool;
import contract.Contracts.User_Contract;
import contract.Contracts.Res_Contract;
import contract.Struct.Resource;
import contract.Struct.User;
import contract.UI.UserRegisterUI;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.Locale.Category;

import javax.swing.UIManager;

import bftsmart.tom.ServiceProxy;

import Communication.ServerMain;

public class shouquan_bench {
    public static void main(String[] args) throws Exception {
        Tool.nodeID=0;
        Tool.serviceProxy=new ServiceProxy(Tool.nodeID);
        Initialization_Contract.InitialzationUser(Tool.FROMCLIENT);
        Initialization_Contract.InitialzationRes(Tool.FROMCLIENT);
        Initialization_Contract.InitialzationEquip(Tool.FROMCLIENT);
        Initialization_Contract.InitialzationApply(Tool.FROMCLIENT);
        String userName="typeonetest";
        String[] strArr = {userName,"1","14","1","123"};
        String  returnStr = User_Contract.userRegister(strArr,Tool.FROMCLIENT);
        System.out.println(returnStr);   
        for(int i=0;i<300;i++){
            strArr = new String[] {"0","0","测试流程1"};
            returnStr = Res_Contract.operateResource(strArr,Tool.FROMCLIENT);
            System.out.println(returnStr);   
            strArr = new String[] {"0","1","测试数据"};
            returnStr = Res_Contract.operateResource(strArr,Tool.FROMCLIENT);
            System.out.println(returnStr);   
            String old_R = "测试流程1,0,1.0";
            String new_R = "测试流程1,0,1.1";
            String operator= "修改";
            strArr = new String[] {old_R,new_R,operator};
            returnStr = Res_Contract.changeResource(strArr,Tool.FROMCLIENT);
            System.out.println(returnStr);   
            old_R = "测试流程1,0,1.1";
            new_R = "测试流程1,0,1.0";
            operator= "修改";
            strArr = new String[] {old_R,new_R,operator};
            returnStr = Res_Contract.changeResource(strArr,Tool.FROMCLIENT);
            System.out.println(returnStr);   
        }
    }
}
