import contract.Contracts.ContractServer;
import contract.Contracts.Initialization_Contract;
import contract.Contracts.Tool;
import contract.Contracts.User_Contract;
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
public class bench {
    
    public static void main(String[] args) throws Exception {
        Tool.nodeID=0;
        Tool.serviceProxy=new ServiceProxy(Tool.nodeID);
        Initialization_Contract.InitialzationUser(Tool.FROMCLIENT);
        Initialization_Contract.InitialzationRes(Tool.FROMCLIENT);
        Initialization_Contract.InitialzationEquip(Tool.FROMCLIENT);
        Initialization_Contract.InitialzationApply(Tool.FROMCLIENT);
        for(int i=0;i<30;i++){
            String[] strArr = {"typeone","1","14","1","123"};
            String  returnStr = User_Contract.userRegister(strArr,Tool.FROMCLIENT);
            System.out.println(returnStr);
            strArr = new String[] {"typeone"+i,"1","14","1","123"};
            returnStr = User_Contract.userRegister(strArr,Tool.FROMCLIENT);
            System.out.println(returnStr);
            strArr = new String[] {"typeone","14","123"};
            returnStr = User_Contract.userLogin(strArr,Tool.FROMCLIENT);
            System.out.println(returnStr);
            strArr = new String[] {"typeone","14","123"+i};
            returnStr = User_Contract.userLogin(strArr,Tool.FROMCLIENT);
            System.out.println(returnStr);
            strArr = new String[] {"typeone","1"};
            returnStr = User_Contract.userout(strArr,Tool.FROMCLIENT);
            System.out.println(returnStr);   
        }
    }

}

