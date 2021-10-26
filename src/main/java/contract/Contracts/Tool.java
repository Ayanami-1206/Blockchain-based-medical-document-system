package contract.Contracts;



import LevelDB.Constant;
import LevelDB.LevelDbUtil;
import contract.Struct.ApplyMessage;
import contract.Struct.Equip;
import contract.Struct.MiscCommand;
import contract.Struct.Resource;
import contract.Struct.User;
import Communication.SHA256RSA;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
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
    public static final int maxClientCount=200;
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
        long t1;
        long t2;
        String className=new Exception().getStackTrace()[1].getClassName();
        String methodName=new Exception().getStackTrace()[1].getMethodName();
        try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutput objOut = new ObjectOutputStream(byteOut);) {

            objOut.writeObject(MiscCommand.smartContract);
            objOut.writeObject(className);
            objOut.writeObject(methodName);
            objOut.writeObject(s); // method parameters
            objOut.writeObject(Tool.getLocalTime());
            objOut.writeObject(Tool.currentUserName);
            objOut.flush();
            byteOut.flush();
            String signature=SHA256RSA.signatureSHA256RSAWithByteArrayInput(byteOut.toByteArray(),User_Contract.realPK);
            objOut.writeObject(signature);
            objOut.flush();
            byteOut.flush();            
            t1=System.nanoTime();
            byte[] reply = serviceProxy.invokeOrdered(byteOut.toByteArray());
            t2=System.nanoTime();
            long method_type=2;
            if(methodName.equals("userRegister")||methodName.equals("userLogin")||methodName.equals("userout")){
                method_type=1;
            }
            logTime(t2-t1,method_type);
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
    
    //x: nanoseconds
    static public void logTime(long x,long type){
        //type=1 认证
        //type=2 授权
        String filename="";
        if(type==1){
            filename="../../times_renzheng";
        }
        else{
            filename="../../times_shouquan";
        }
        try(PrintStream ps = new PrintStream(new FileOutputStream(filename, true))){
            ps.printf("%d\n",x); // %d works for all integer types, including int/long
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    static public void getchar(){
        Scanner scanner=new Scanner(System.in);
        scanner.nextLine();
        // scanner.close();
    }
    //获取当前设备的ip地址
    public static String getLocalIp(){
        Enumeration<NetworkInterface> n;
        try{
            n = NetworkInterface.getNetworkInterfaces();
            for (; n.hasMoreElements();){
                NetworkInterface e = n.nextElement();
                Enumeration<InetAddress> a = e.getInetAddresses();
                for (; a.hasMoreElements();){
                    InetAddress addr = a.nextElement();
                    String currentEquip = addr.getHostAddress();
                    boolean ipv6=false;
                    for(int i=0;i<currentEquip.length();i++){
                        if(currentEquip.charAt(i)==':'){
                            ipv6=true;
                            break;
                        }
                    }
                    if(ipv6)continue;
                    if((!currentEquip.substring(0,3).equals("127"))){
                        return currentEquip;
                    }
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return "";
    }
    public static void system(String s){
        Runtime rt = Runtime.getRuntime();
        try{
            Process p=rt.exec(s);
            BufferedReader stdInput = new BufferedReader(new
            InputStreamReader(p.getInputStream()));
            while ((s = stdInput.readLine()) != null) {
                // System.out.println(s);
                // make this command blocks
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void levelDBClear(){
        String dbFolder="src/main/java/LevelDBLocalData/";
        system("rm -rf "+dbFolder);
    }
    public static void writeApplyMessageDB(ArrayList<ApplyMessage> a){
        LevelDbUtil ApplyMessages = new LevelDbUtil();
        ApplyMessages.initLevelDB(Constant.APPLYMESSAGES);
        for (int i=0;i<a.size();i++){
            String key = String.valueOf(a.get(i).hashCode());
            String val = a.get(i).toString();
            try {
                ApplyMessages.put(key,val);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ApplyMessages.closeDB();
    }
    public static void writeEquipDB(ArrayList<Equip> a){
        LevelDbUtil Equips = new LevelDbUtil();
        Equips.initLevelDB(Constant.EQUIPS);
        for (int i=0;i<a.size();i++){
            String key = a.get(i).getEquip_id();
            String val = a.get(i).toString();
            try {
                Equips.put(key,val);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Equips.closeDB();
    }
    public static void writeResourceDB(ArrayList<Resource> a){
        LevelDbUtil Resources = new LevelDbUtil();
        Resources.initLevelDB(Constant.RESOURCES);
        for (int i = 0; i < a.size(); i++) {
            String key = String.valueOf(a.get(i).hashCode());
            String val = a.get(i).toString();
            try {
                Resources.put(key, val);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Resources.closeDB();
    }
    public static void writeUserDB(ArrayList<User> a){
        LevelDbUtil Users = new LevelDbUtil();
        Users.initLevelDB(Constant.USERS);
        for (int i = 0; i < a.size(); i++) {
            String key = a.get(i).getWork_number();
            String val = a.get(i).toString();
            try {
                Users.put(key, val);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Users.closeDB();
    }
    public static void writeUseredsDB(ArrayList<User> a){
        LevelDbUtil Users = new LevelDbUtil();
        Users.initLevelDB(Constant.USEREDS);
        for (int i = 0; i < a.size(); i++) {
            String key = a.get(i).getWork_number();
            String val = a.get(i).toString();
            try {
                Users.put(key, val);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Users.closeDB();
    }
    final public static String blockPath="block_data";
    public static void storeVar(){
        try{
            FileOutputStream fileOut = new FileOutputStream(".storage");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(ContractServer.blockHeight);
            out.writeObject(ContractServer.parentHash);
            out.writeObject(Initialization_Contract.applyDone);
            out.writeObject(Initialization_Contract.resDone);
            out.writeObject(Initialization_Contract.userDone);
            out.writeObject(Initialization_Contract.equipDone);
            out.close();
            fileOut.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void loadVar(){
        try{
            FileInputStream fileOut = new FileInputStream(".storage");
            ObjectInputStream out = new ObjectInputStream(fileOut);
            ContractServer.blockHeight=(int)out.readObject();
            ContractServer.parentHash=(byte[])out.readObject();
            Initialization_Contract.applyDone=(boolean)out.readObject();
            Initialization_Contract.resDone=(boolean)out.readObject();
            Initialization_Contract.userDone=(boolean)out.readObject();
            Initialization_Contract.equipDone=(boolean)out.readObject();
            out.close();
            fileOut.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public static int currentClientID=-1;
    public static String getClientIP(){
        return "10.0.0."+(currentClientID+1);
    }
    public static String clientTimeString="default timestamp";
    //获取当前时间的函数
    public static String getCurrentTime(){
        return clientTimeString;
        // Calendar calendar= Calendar.getInstance();
        // SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // String Ti = dateFormat.format(calendar.getTime());
        // return Ti;
    }
    public static String getLocalTime(){
        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String Ti = dateFormat.format(calendar.getTime());
        return Ti;
    }    
    public static byte[] initSnapshot=null;
    public static String currentUserName="null";
    public static String[] getBlockData(){
        try{
            byte[] data=Files.readAllBytes(Paths.get(blockPath));
            String all=new String(data,StandardCharsets.UTF_8);
            // System.out.println(all);
            return all.split("\n\n");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return new String[0];
    }
    public static String queryBlockData(String userName,int type){
        // ArrayList<String> res=new ArrayList<>();
        // System.out.printf("type=%d\n",type);
        String res="";
        String[] allBlocks=getBlockData();
        // System.out.println(allBlocks.length);
        for(int i=0;i<allBlocks.length;i++){
            String tmp=allBlocks[i];
            // System.out.println(tmp);
            String types[]={"userRegister","userLogin","operateResource",
                "applyRole","userout"};
            // if(tmp.contains("User name: "+userName)){
            String str1="User name: "+userName;
            String str2="Contract method: "+types[type];
            // System.out.printf("str2=%s\n",str2);
            if(tmp.contains(str1)&&tmp.contains(str2)){
            // if(tmp.contains("mgmt")){
                res=res+tmp+"\n\n";
            }
        }
        return res;
    }
}
