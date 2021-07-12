package contract.Contracts;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;

import Communication.ReflectNode;
import bftsmart.tom.MessageContext;
import bftsmart.tom.ServiceReplica;
import bftsmart.tom.server.defaultservices.DefaultSingleRecoverable;
import contract.Struct.MiscCommand;

import bftsmart.demo.counter.CounterServer;

// public class ContractServer extends DefaultSingleRecoverable{
    
//     private int counter = 0;
//     private int iterations = 0;
    
//     public ContractServer(int id) {
//     	new ServiceReplica(id, this, this);
//     }
            
//     @Override
//     public byte[] appExecuteUnordered(byte[] command, MessageContext msgCtx) {   
//         // return new byte[0];      
//         return null;
//         // iterations++;
//         // System.out.println("(" + iterations + ") Counter current value: " + counter);
//         // try {
//         //     ByteArrayOutputStream out = new ByteArrayOutputStream(4);
//         //     new DataOutputStream(out).writeInt(counter);
//         //     return out.toByteArray();
//         // } catch (IOException ex) {
//         //     System.err.println("Invalid request received!");
//         //     return new byte[0];
//         // }
//     }
  
//     @Override
//     public byte[] appExecuteOrdered(byte[] command, MessageContext msgCtx) {
//         return new byte[0];  
//         // iterations++;
//         // try {
//         //     int increment = new DataInputStream(new ByteArrayInputStream(command)).readInt();
//         //     counter += increment;
            
//         //     System.out.println("(" + iterations + ") Counter was incremented. Current value = " + counter);
            
//         //     ByteArrayOutputStream out = new ByteArrayOutputStream(4);
//         //     new DataOutputStream(out).writeInt(counter);
//         //     return out.toByteArray();
//         // } catch (IOException ex) {
//         //     System.err.println("Invalid request received!");
//         //     return new byte[0];
//         // }
//     }

//     public static void main(String[] args){
//         if(args.length < 1) {
//             System.out.println("Use: java CounterServer <processId>");
//             System.exit(-1);
//         }      
//         new CounterServer(Integer.parseInt(args[0]));
//     }

    
//     @SuppressWarnings("unchecked")
//     @Override
//     public void installSnapshot(byte[] state) {
//         // try {
//         //     ByteArrayInputStream bis = new ByteArrayInputStream(state);
//         //     ObjectInput in = new ObjectInputStream(bis);
//         //     counter = in.readInt();
//         //     in.close();
//         //     bis.close();
//         // } catch (IOException e) {
//         //     System.err.println("[ERROR] Error deserializing state: "
//         //             + e.getMessage());
//         // }
//     }

//     @Override
//     public byte[] getSnapshot() {
//         // return new byte[0];
//         return null;  
//     //     try {
//     //         ByteArrayOutputStream bos = new ByteArrayOutputStream();
//     //         ObjectOutput out = new ObjectOutputStream(bos);
//     //         out.writeInt(counter);
//     //         out.flush();
//     //         bos.flush();
//     //         out.close();
//     //         bos.close();
//     //         return bos.toByteArray();
//     //     } catch (IOException ioe) {
//     //         System.err.println("[ERROR] Error serializing state: "
//     //                 + ioe.getMessage());
//     //         return "ERROR".getBytes();
//     //     }
//     }
// }

final public class ContractServer extends DefaultSingleRecoverable{

    public ContractServer(int id) {
        new ServiceReplica(id, this, this);
    }

    @Override
    public byte[] appExecuteOrdered(byte[] command, MessageContext msgCtx) {
        byte[] reply = null;
        String className=null;
        String methodName=null;
        String[] strArr=null;
		boolean hasReply = false;
        String result=null;
		try (ByteArrayInputStream byteIn = new ByteArrayInputStream(command);
				ObjectInput objIn = new ObjectInputStream(byteIn);
				ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
				ObjectOutput objOut = new ObjectOutputStream(byteOut);) {
            MiscCommand cmd=(MiscCommand)objIn.readObject();
            switch(cmd){
                case getBlockHeight:
                    objOut.writeObject(0);
                break;
                case smartContract:
                    className = (String)objIn.readObject();
                    methodName=(String)objIn.readObject();
                    strArr=(String[])objIn.readObject();
                    Class<?> c = Class.forName(className);
                    // System.out.println("will invode");
                    if(className.equals("contract.Contracts.Initialization_Contract")){
                        Method m=c.getMethod(methodName, String[].class);
                        result=(String)m.invoke(null, (Object)strArr);
                    }
                    else{
                        Method m=c.getMethod(methodName, String[].class,Boolean.class);
                        result=(String)m.invoke(null, (Object)strArr,false);
                    }
                    // System.out.println("did invoke");
                    // MethodHandles.Lookup lookup = MethodHandles.lookup();
                    // MethodType mt = MethodType.methodType(String[].class);
                    // MethodHandle mh = lookup.findVirtual(c, methodName, mt);;
                    // result=(String)mh.invoke(strArr);
                    // Method Fun = ReflectNode.getMethod(className,methodName);
                    // result = (String) Fun.invoke(null,strArr,false);
                    System.out.println(result);
                    objOut.writeObject(result);
                break;
                // case default:
                // break;
            }
            objOut.flush();
            byteOut.flush();
            reply=byteOut.toByteArray();
            return reply;
        }
        catch(Exception e){
            e.printStackTrace();
        } catch (Throwable e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new byte[0];
    }

    @Override
    public byte[] appExecuteUnordered(byte[] arg0, MessageContext arg1) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public byte[] getSnapshot() {
        // TODO Auto-generated method stub
        return new byte[0];
    }

    @Override
    public void installSnapshot(byte[] arg0) {
        // TODO Auto-generated method stub
        
    }
    
}
