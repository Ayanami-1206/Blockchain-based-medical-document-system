package contract.Contracts;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;

import org.springframework.boot.autoconfigure.web.ResourceProperties.Strategy;

import Communication.ReflectNode;
import bftsmart.tom.MessageContext;
import bftsmart.tom.ServiceReplica;
import bftsmart.tom.server.defaultservices.DefaultSingleRecoverable;

public class ContractServer extends DefaultSingleRecoverable{

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
			className = (String)objIn.readObject();
            methodName=(String)objIn.readObject();
            strArr=(String[])objIn.readObject();
            Method Fun = ReflectNode.getMethod(className,methodName);
            result = (String) Fun.invoke(null,strArr,false);
            objOut.writeObject(result);
            objOut.flush();
            byteOut.flush();
            reply=byteOut.toByteArray();
            return reply;
        }
        catch(Exception e){
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
        return null;
    }

    @Override
    public void installSnapshot(byte[] arg0) {
        // TODO Auto-generated method stub
        
    }
    
}
