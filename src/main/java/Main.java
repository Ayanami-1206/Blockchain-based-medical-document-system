import contract.Contracts.ContractServer;
import contract.Contracts.Initialization_Contract;
import contract.Contracts.Tool;
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

import javax.swing.UIManager;

import bftsmart.tom.ServiceProxy;

import Communication.ServerMain;
enum T{
    a,b,c;
}
class TT{
    public int x;
    public int y;
}
class miscTest{
    public static void main(String[] args) {
        TT a=new TT();
        // a.seria
        // try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        // ObjectOutput objOut = new ObjectOutputStream(byteOut);) {
        //     String destination = "file1.txt";

        //     try(PrintStream ps = new PrintStream(destination)){
        //         ps.println("Stackoverflow documentation seems fun.");
        //         ps.println();
        //         ps.println("I love Java!");
        //         ps.flush();
        //     } catch (FileNotFoundException e) {
        //         e.printStackTrace();
        //     }
        //     System.out.println();
        // }
        // catch(Exception e){
        //     e.printStackTrace();
        // }
        // System.out.println("line 1");
        // Tool.getchar();
        // System.out.println("Line 2");
        // Tool.getchar();
        // System.out.println("Line 3");
        // Tool.getchar();
    }
}

public class Main {
    
    public static void main(String[] args) throws Exception {
        if(true){

        }else{

        }//绕过不可达代码检查？？
        if(args.length==1){
            ServerMain.main(args);
            return;
        }
        // if (args.length < 1) {
		// 	System.out.println("Usage: Main <server id>");
		// 	System.exit(-1);
		// }
        // Tool.getchar();
        // if(Tool.nodeID!=1){//test: one client
            // return;
        // }
        Tool.nodeID=0;
        Tool.serviceProxy=new ServiceProxy(Tool.nodeID);
        try{
            UIManager.setLookAndFeel(
            UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception e){
            e.printStackTrace();
        }
        Initialization_Contract.InitialzationUser(Tool.FROMCLIENT);
        Initialization_Contract.InitialzationRes(Tool.FROMCLIENT);
        Initialization_Contract.InitialzationEquip(Tool.FROMCLIENT);
        Initialization_Contract.InitialzationApply(Tool.FROMCLIENT);
        new UserRegisterUI();

    }


}
