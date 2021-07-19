package Communication;
import java.io.FileOutputStream;
import java.io.PrintStream;

import contract.Contracts.ContractServer;
import contract.Contracts.Tool;

public class ServerMain {
    public static void main(String[] args) {
        Tool.nodeID=Integer.parseInt(args[0]);
        String destination = "block_data";
        try(PrintStream ps = new PrintStream(new FileOutputStream(destination, false))){
            ps.printf("Block data\n\n\n");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        ContractServer contractServer=new ContractServer(Tool.nodeID);
    }
}
