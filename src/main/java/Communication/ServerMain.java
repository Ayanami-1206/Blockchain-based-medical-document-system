package Communication;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.Base64;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;

import bftsmart.tom.ServiceProxy;
import contract.Contracts.ContractServer;
import contract.Contracts.Initialization_Contract;
import contract.Contracts.Tool;
import contract.Struct.MiscCommand;

public class ServerMain {
    public static void main(String[] args) {
        Tool.nodeID=Integer.parseInt(args[0]);
        String destination = Tool.blockPath; 
        // if there is no,block data file, init the block chain
        // otherwise, no init operations
        if(!Files.exists(Paths.get(destination))){
            try(PrintStream ps = new PrintStream(new FileOutputStream(destination, false))){
                ps.printf("Block data\n\n\n");
            }
            catch(Exception e){
                e.printStackTrace();
            }
            System.out.println("#################clear db!!!!!!!!!!!!!");
            Tool.levelDBClear();
            Tool.storeVar();
        }
        else{
            Tool.loadVar();
        }
        ContractServer contractServer=new ContractServer(Tool.nodeID);
        Tool.initSnapshot=contractServer.getSnapshot__();
        ServiceProxy c=new ServiceProxy(Tool.nodeID);
        try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutput objOut = new ObjectOutputStream(byteOut);) {
            System.out.println("Wait servers to be ready.....");
            Thread.sleep(10*1000);
            System.out.println("Wait done......");
            objOut.writeObject(MiscCommand.getState);
            objOut.flush();
            byteOut.flush();
            byte[] reply = c.invokeOrdered(byteOut.toByteArray());
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(reply);
            System.out.printf("Returned hash: %s\n",Base64.getEncoder().encodeToString(md.digest()));
            contractServer.installSnapshot__(reply);
            c.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
