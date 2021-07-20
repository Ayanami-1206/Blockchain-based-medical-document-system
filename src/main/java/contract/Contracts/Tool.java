package contract.Contracts;



import LevelDB.Constant;
import LevelDB.LevelDbUtil;
import contract.Struct.ApplyMessage;
import contract.Struct.Equip;
import contract.Struct.MiscCommand;
import contract.Struct.Resource;
import contract.Struct.User;
import Communication.SHA256RSA;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import bftsmart.tom.ServiceProxy;


public class Tool {
	public static ServiceProxy serviceProxy=null;
    //获取初始化资源库的资源信息
    public static ArrayList<Resource> getResources( ){
        LevelDbUtil res = new LevelDbUtil();
        res.initLevelDB(Constant.RESOURCES);
        //获取当前所有的资源
        ArrayList<Resource> resources = new ArrayList<>();
        List<String> keys = res.getKeys();
        System.out.println(keys);
        for (int i =0;i<keys.size();i++){
            if (res.get(keys.get(i))!=null){
                String[] resStr = res.get(keys.get(i)).split(";");
                resources.add(new Resource(resStr[0],Integer.parseInt(resStr[1]),resStr[2]));
            }

        }
        res.closeDB();
        return resources;
    }
    //获取初始化用户库的用户信息
    public static ArrayList<User> getUser( ){
        LevelDbUtil res = new LevelDbUtil();
        res.initLevelDB(Constant.USERS);
        //获取当前所有的资源
        ArrayList<User> users = new ArrayList<>();
        List<String> keys = res.getKeys();
        System.out.println(keys);
        for (int i =0;i<keys.size();i++){
            if (res.get(keys.get(i))!=null){
                String[] resStr = res.get(keys.get(i)).split(";");
                users.add(new User(resStr[0], resStr[1], resStr[2], resStr[3], Integer.parseInt(resStr[4]), resStr[5], resStr[6]));

            }

        }
        res.closeDB();
        return users;
    }
    //获取初始化设备库的设备信息
    public static ArrayList<Equip> getEquip( ){
        LevelDbUtil res = new LevelDbUtil();
        res.initLevelDB(Constant.EQUIPS);
        //获取当前所有的设备
        ArrayList<Equip> equips = new ArrayList<>();
        List<String> keys = res.getKeys();
        for (int i =0;i<keys.size();i++){
            if (res.get(keys.get(i))!=null) {
                String[] resStr = res.get(keys.get(i)).split(";");
                equips.add(new Equip(resStr[0], resStr[1], resStr[2]));
            }
        }
        res.closeDB();
        return equips;
    }
    //获取已经注册的用户的数据
    public static ArrayList<User> getUsereds( ){
        LevelDbUtil res = new LevelDbUtil();
        res.initLevelDB(Constant.USEREDS);
        //获取当前所有的资源
        ArrayList<User> users = new ArrayList<>();
        List<String> keys = res.getKeys();
        for (int i =0;i<keys.size();i++){
            if (res.get(keys.get(i))!=null) {
                String[] resStr = res.get(keys.get(i)).split(";");
                users.add(new User(resStr[0], resStr[1], resStr[2], resStr[3], Integer.parseInt(resStr[4]), resStr[5], resStr[6]));
            }
        }
        res.closeDB();
        return users;
    }
    //获取申请的列表
    public static ArrayList<ApplyMessage> getApplyMessages( ){
        LevelDbUtil res = new LevelDbUtil();
        res.initLevelDB(Constant.APPLYMESSAGES);
        //获取当前所有的资源
        ArrayList<ApplyMessage> applyMessages = new ArrayList<>();
        List<String> keys = res.getKeys();
        for (int i =0;i<keys.size();i++){
            if (res.get(keys.get(i))!=null) {
                String[] resStr = res.get(keys.get(i)).split(";");
                applyMessages.add(new ApplyMessage(resStr[0], resStr[1], resStr[2], resStr[3], resStr[4]));
            }
        }
        res.closeDB();
        return applyMessages;
    }
    //获取指定的LevelDb数据库
    public static  LevelDbUtil getLevelDB(String s){
        LevelDbUtil levelDbUtil = new LevelDbUtil();
        levelDbUtil.initLevelDB(s);
        return levelDbUtil;
    }
    public static final int FROMCLIENT=1;
    public static int nodeID=0;
    public static int getBlockHeightFromRemote(){
        try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutput objOut = new ObjectOutputStream(byteOut);) {
            objOut.writeObject(MiscCommand.getBlockHeight);
            objOut.flush();
            byteOut.flush();
            byte[] reply = serviceProxy.invokeOrdered(byteOut.toByteArray());
            if (reply.length == 0)
                return -1;
            try (ByteArrayInputStream byteIn = new ByteArrayInputStream(reply);
                    ObjectInput objIn = new ObjectInputStream(byteIn)) {
                return (int)objIn.readObject();
                }     
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Exception putting value into map: " + e.getMessage());
        }
        return -1;
    }
    public static String sendRawCommandToServer(String[] s){
        String className=new Exception().getStackTrace()[1].getClassName();
        String methodName=new Exception().getStackTrace()[1].getMethodName();
        try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutput objOut = new ObjectOutputStream(byteOut);) {

            objOut.writeObject(MiscCommand.smartContract);
            objOut.writeObject(className);
            objOut.writeObject(methodName);
            objOut.writeObject(s); // method parameters
            objOut.flush();
            byteOut.flush();
            String signature=SHA256RSA.signatureSHA256RSAWithByteArrayInput(byteOut.toByteArray(),User_Contract.realPK);
            objOut.writeObject(signature);
            objOut.flush();
            byteOut.flush();            
        
            byte[] reply = serviceProxy.invokeOrdered(byteOut.toByteArray());
            if (reply.length == 0)
                return null;
            try (ByteArrayInputStream byteIn = new ByteArrayInputStream(reply);
                    ObjectInput objIn = new ObjectInputStream(byteIn)) {
                return (String)objIn.readObject();
                }     
        } catch (Exception e) {
            System.out.println("Exception putting value into map: " + e.getMessage());
        }
        return null;
    }
    static public void getchar(){
        Scanner scanner=new Scanner(System.in);
        scanner.nextLine();
        // scanner.close();
    }
}
