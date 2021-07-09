import contract.Contracts.ContractServer;
import contract.Contracts.Initialization_Contract;
import contract.Contracts.Tool;
import contract.Struct.Resource;
import contract.Struct.User;
import contract.UI.UserRegisterUI;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Scanner;

import javax.swing.UIManager;

import bftsmart.tom.ServiceProxy;

class miscTest{
    public static void main(String[] args) {
        System.out.println("line 1");
        Tool.getchar();
        System.out.println("Line 2");
        Tool.getchar();
        System.out.println("Line 3");
        Tool.getchar();
    }
}

public class Main {
    
    public static void main(String[] args) throws UnsupportedEncodingException {
        if(true){

        }else{

        }
        if (args.length < 1) {
			System.out.println("Usage: Main <server id>");
			System.exit(-1);
		}
        Tool.nodeID=Integer.parseInt(args[0]);
        ContractServer contractServer=new ContractServer(Tool.nodeID);
        Tool.getchar();
        if(Tool.nodeID!=1){//test: one client
            return;
        }
        Tool.serviceProxy=new ServiceProxy(Tool.nodeID);
        try{
            UIManager.setLookAndFeel(
            UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception e){
            e.printStackTrace();
        }
        Initialization_Contract.InitialzationRes(Tool.FROMCLIENT);
        Initialization_Contract.InitialzationEquip(Tool.FROMCLIENT);
        Initialization_Contract.InitialzationUser(Tool.FROMCLIENT);
        Initialization_Contract.InitialzationApply(Tool.FROMCLIENT);
        new UserRegisterUI();

    }


}
