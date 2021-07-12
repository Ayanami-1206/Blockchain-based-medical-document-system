package Communication;
import contract.Contracts.ContractServer;
import contract.Contracts.Tool;

public class ServerMain {
    public static void main(String[] args) {
        Tool.nodeID=Integer.parseInt(args[0]);
        ContractServer contractServer=new ContractServer(Tool.nodeID);
    }
}
