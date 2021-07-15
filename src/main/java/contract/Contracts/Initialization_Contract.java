package contract.Contracts;

import LevelDB.LevelDbUtil;
import contract.Struct.*;

import java.io.IOException;
import java.net.InetAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Initialization_Contract {
    static public void foo(){
        System.out.println("fooooo");
    }
    //规定合约的调用者为合约的部署者
    static String chairperson = "192.168.1.125";
    private User[] usereds;
    //定义存储已注册设备的数组
    private Equip[] equipeds;
    //定义一次性密钥的存储数组
    private ownerSecretKey[] oneTimeIDs;
    //定义用户初始化函数。参数：用户的姓名、用户的身份证、用户的工号
    public static String initializeUser(Boolean TestFlag){
        int flag = 0;
        String returnStrv ="";
        //获取当前用户的ip
        String Ei= getLocalIp();
        //管理者就是合约的部署者,只有合约的部署者可以进行初始化
        if (true | chairperson == Ei) {
            //初始化用户信息
            InitialzationUser(null);
            flag=1;
        }
        String Ti = getCurrentTime();
        if(flag==1){
            returnStrv = Ti+"&"+"管理节点"+"在"+Ti+"进行用户初始化成功"+"初始化";
        }else {
            returnStrv = Ti+"&"+"管理节点"+"在"+Ti+"进行用户初始化失败"+"初始化";
        }
        return returnStrv+flag;
    }
    //初始化设备函数。参数：设备ID、设备的名称、设备的物理
    public static String initializeEquip(Boolean TestFlag) {
        int flag = 0;
        String returnStrv ="";
        //获取当前用户的ip
        String Ei= getLocalIp();
        //管理者就是合约的部署者,只有合约的部署者可以进行初始化
        if (true | chairperson == Ei) {
            //初始化设备信息
            InitialzationEquip(null);
            flag=1;
        }
        String Ti = getCurrentTime();
        if(flag==1){
            returnStrv = Ti+"&"+"管理节点"+"在"+Ti+"进行设备初始化成功"+"初始化";
        }else {
            returnStrv = Ti+"&"+"管理节点"+"在"+Ti+"进行设备初始化失败"+"初始化";
        }
        return returnStrv+flag;
    }
    //初始化资源函数 参数：资源的名称、资源的级别、资源的版本
    public static String initializeResource(Boolean TestFlag){
        int flag = 0;
        String returnStrv ="";
        //获取当前用户的ip
        String Ei= getLocalIp();
        //管理者就是合约的部署者,只有合约的部署者可以进行初始化
        if (true | chairperson == Ei) {
            //初始化资源信息
            InitialzationRes(null);
            flag=1;
        }
        String Ti = getCurrentTime();
        if(flag==1){
            returnStrv = Ti+"&"+"管理节点"+"在"+Ti+"进行资源初始化成功"+"初始化";
        }else {
            returnStrv = Ti+"&"+"管理节点"+"在"+Ti+"进行资源初始化失败"+"初始化";
        }
        return returnStrv+flag;
    }
    //获取当前设备的ip地址
    public static String getLocalIp(){
        String ip = "";
        //获取当前设备ip
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ip;
    }
    //获取当前时间的函数
    public static String getCurrentTime(){
        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        String Ti = dateFormat.format(calendar.getTime());
        return Ti;
    }
    public static String pub = pub = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwZJO7qu7KLteVCAJH4rB\n" +
            "VnhynhauUJirZPGntVUFx3QJfhHaRdxbk1KVxDCAAYhdUoF4l6uZ762Qjcld9AZE\n" +
            "3BW5CLHdotpfNuJNSJOcnS5RUzXB2jvn8FxAX/dfjG+6y3Og9aKnYygO7DGnSmND\n" +
            "JtNiaLraFHeb3H5IwUa/3AIOvv//iWEwntnGlUDjpajxkNB9auR4Y25fH2kQ1/NB\n" +
            "bJlSJITihUywSysW9OWPS16j+Dj3yu8QmkBzj57/qyU+FtsDbk70xx+B3NdTwWDl\n" +
            "6I0xyB657Nen+FSqxDqQFBIEDaJIIuMom4IZb8skm43JR7a9vW0DFkoP7XMvk0T0\n" +
            "nQIDAQAB";
    public static String pri ="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDBkk7uq7sou15U\n" +
            "IAkfisFWeHKeFq5QmKtk8ae1VQXHdAl+EdpF3FuTUpXEMIABiF1SgXiXq5nvrZCN\n" +
            "yV30BkTcFbkIsd2i2l824k1Ik5ydLlFTNcHaO+fwXEBf91+Mb7rLc6D1oqdjKA7s\n" +
            "MadKY0Mm02JoutoUd5vcfkjBRr/cAg6+//+JYTCe2caVQOOlqPGQ0H1q5Hhjbl8f\n" +
            "aRDX80FsmVIkhOKFTLBLKxb05Y9LXqP4OPfK7xCaQHOPnv+rJT4W2wNuTvTHH4Hc\n" +
            "11PBYOXojTHIHrns16f4VKrEOpAUEgQNokgi4yibghlvyySbjclHtr29bQMWSg/t\n" +
            "cy+TRPSdAgMBAAECggEARq+i200i31DstVOtgTMuPuFlGY22wf++NvJ4B6OwpJEf\n" +
            "FkZ2qqQ5XKb/wdDkLasaSRK2csxbPkbnTp+GC6JuauAT3bT19o4/zs784c/llRBg\n" +
            "6j3rOEJABGnrqB+xaJs0Xy8uTATFk1QBfwbyzBtLXlY6zXvOP0MeS3piHxIy5afI\n" +
            "EjuViXp656kOglK8n/8p2sH/WQA/oIX35gxgtp3CC/toTtorpRgcmC/Z03mebaVo\n" +
            "pRtlvK1IZ71pw2DTsbS4D+rH6FHqSIOnvzetkJXm3FzpxEdWaNU1/X1Nf4TdKjzs\n" +
            "w/zHlaLAlMFShFSC7rD2L5fvmK8JbsxhWlSkv1F4EQKBgQDj98piLVa6nHE8D0vp\n" +
            "LACYHtJoe0Co5OybCpYtfHS6XbTxjZXwnzHNq8ORZiCGII4D+Vg1KrV7cO15tczX\n" +
            "nZibobSeaNszoxKQxBytEyrpKabV8vOihNw8r6wA6QBwU+iiWoC184b2lGBgAk5l\n" +
            "Aaxbk4B4beTrjzV4jiTb/BGyMwKBgQDZX8CBtQodVHnY1O4ZzzxVO0Szu5JCwaq1\n" +
            "nm08wIhnjDPJ3c5xM2HwGQC5QLAMpBPc8QOlDwNmz6n9kKA7n2isgYcuxp66e5Lo\n" +
            "bDbmFuA0FsnhyfIykx3KVGPqB4ew8IhLvWI6ZrPjC/JHw+3jNYtJf9wVH/dRW3XN\n" +
            "xP3PP4EN7wKBgQCKASW5jWzlh3NdQn1X6NVrQa1qpsKb/AnoSd5kJuWbNMMnP+k3\n" +
            "Q4go1ASObt7dk7OlYDsv5Gz9D34ToiUhmjwaaygmhZ+9gMjqbm5VsDbX8+kVuAAZ\n" +
            "sIiLDGX6HxOu7Hz2XAdzHrvjZit5s6u1pe+reH3badudZpKeKp7RtaUY1QKBgFu3\n" +
            "bv5LaM0fS5oVnbD+I7LCoyZCyl+oX2LqSaUMh4FJQsC1+dkBmR05L3j6tk1fHZJn\n" +
            "juYiA4lprYvzbeg6Rmwi6urtmyOL6Fxw7GkA1fCkfOHr12lHcZZhLRcdvj8F/jwW\n" +
            "2E9T7iqqa7ukC8eGXBLTBAVVy4BkWrxLldvQK5+5AoGAALSqIKxih448ip4wVsnQ\n" +
            "E3uSAwgJMquwjiLOk36+fU2qa0ts+q/7xE7+c5mJ9+f5HXpQEz9+DCvJWqHg9DP5\n" +
            "8pdDifE4lUqHpCr+OkVnDuPCOEU/8wsWzIg+RutLkdhom85R9jUnvq7mYoE26zct\n" +
            "L3iOO2o4xC1VC/feyk0bhJM=";
    public static String realPub = pub.replaceAll("-----END PUBLIC KEY-----", "")
            .replaceAll("-----BEGIN PUBLIC KEY-----", "")
            .replaceAll("\n", "");
    public static String realPK = pri.replaceAll("-----END PRIVATE KEY-----", "")
            .replaceAll("-----BEGIN PRIVATE KEY-----", "")
            .replaceAll("\n", "");

    public  static void InitialzationUser(int fromClient){
        Tool.sendRawCommandToServer(new String[0]);
    }

    static boolean userDone=false;
    //初始化用户
    public  static String InitialzationUser(String[] s){
        if(userDone){
            return null;
        }
        //初始化用户
        //初始化用户
        LevelDbUtil Users = new LevelDbUtil();
        Users.initLevelDB("Users");
        ArrayList<User> users = new ArrayList<>();//用来保存初始化用户的列表
        //初始化用户
        // User u1 = new User("一类用户", "1", "10", "1", 0, "", realPub);
        // User u2 = new User("二类用户", "1", "11", "1", 1, "", realPub);
        // User u3 = new User("三类用户", "1", "12", "1", 2, "", realPub);
        // User u4 = new User("管理节点", "1", "13", "1", 3, "", realPub);
        users.add(new User("一类用户", "1", "10", "1", 0, "", realPub));
        users.add(new User("typeone", "1", "14", "1", 0, "", realPub));
        users.add(new User("二类用户", "1", "11", "1", 1, "", realPub));
        users.add(new User("三类用户", "1", "12", "1", 2, "", realPub));
        users.add(new User("管理节点", "1", "13", "1", 3, "", realPub));
        users.add(new User("typethree", "1", "15", "1", 3, "", realPub));
        for (int i = 0; i < users.size(); i++) {
            String key = users.get(i).getWork_number();
            String val = users.get(i).toString();
            try {
                Users.put(key, val);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Users.closeDB();
        userDone=true;
        return null;
    }

    public static void InitialzationRes(int fromClient){
        Tool.sendRawCommandToServer(new String[0]);
    }

    static boolean resDone=false;
    //初始化资源
    public static String InitialzationRes(String[] s){
        if(resDone){
            return null;
        }
        //初始化资源
        LevelDbUtil Resources = new LevelDbUtil();
        Resources.initLevelDB("Resources");
        ArrayList<Equip> equips = new ArrayList<>();//用来保存初始化设备的列表
        ArrayList<Resource> resources = new ArrayList<>();//用来保存初始化资源的列表
        //用于初始化的资源
        Resource r1 = new Resource("测试流程1",0,"1.0");
        Resource r2 = new Resource("测试流程2",0,"2.0");
        Resource r3 = new Resource("测试流程3",0,"1.0.3");
        Resource r4 = new Resource("测试数据",1,"1.0");
        Resource r5 = new Resource("测试数据",1,"2.0");
        Resource r6 = new Resource("测试数据",1,"3.0");
        Resource r7 = new Resource("测试数据",1,"4.0");
        resources.add(r1);
        resources.add(r2);
        resources.add(r3);
        resources.add(r4);
        resources.add(r5);
        resources.add(r6);
        resources.add(r7);
        for (int i = 0; i < resources.size(); i++) {

            String key = String.valueOf(resources.get(i).hashCode());
            String val = resources.get(i).toString();
            try {
                Resources.put(key, val);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Resources.closeDB();
        resDone=true;
        return null;
    }

    public static void InitialzationEquip(int fromClient){
        Tool.sendRawCommandToServer(new String[0]);
    }
    static boolean equipDone=false;
    //初始化设备
    public static String InitialzationEquip(String[] s){
        if(equipDone){
            return null;
        }
        //初始化设备
        LevelDbUtil Equips = new LevelDbUtil();
        Equips.initLevelDB("Equips");
        //用于初始化的设备
        ArrayList<Equip> equips = new ArrayList<>();//用来保存初始化用户的列表
        // Equip e1 = new Equip("192.168.32.1","1","1");
        // Equip e2 = new Equip("10.28.129.146","1","1");
        // Equip e3 = new Equip("10.28.129.147","1","1");
        // equips.add(e1);
        // equips.add(e2);
        // equips.add(e3);
        equips.add(new Equip("192.168.32.1","1","1"));
        equips.add(new Equip("10.28.129.146","1","1"));
        equips.add(new Equip("10.28.129.147","1","1"));
        equips.add(new Equip("192.168.0.103","1","1"));
        equips.add(new Equip("192.168.0.101","1","1"));
        equips.add(new Equip("127.0.1.1","1","1"));
        equips.add(new Equip("127.0.0.1","1","1"));
        for (int i=0;i<equips.size();i++){
            String key = equips.get(i).getEquip_id();
            String val = equips.get(i).toString();
            try {
                Equips.put(key,val);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Equips.closeDB();
        equipDone=true;
        return null;
    }

    public static void InitialzationApply(int fromClient){
        Tool.sendRawCommandToServer(new String[0]);
    }
    static boolean applyDone=false;
    public static String InitialzationApply(String[] s){
        if(applyDone){
            return null;
        }
        //权限申请
        LevelDbUtil ApplyMessages = new LevelDbUtil();
        ApplyMessages.initLevelDB("ApplyMessages");
        //未处理的申请角色权限
        ArrayList<ApplyMessage> applyMessages = new ArrayList<>();//保存未处理的申请角色权限的列表
        ApplyMessage m1 = new ApplyMessage("李工","1","1","2","2021.02.09");
        ApplyMessage m2 = new ApplyMessage("白工","1","1","2","2021.02.09");
        ApplyMessage m3 = new ApplyMessage("王工","1","1","2","2021.02.09");
        applyMessages.add(m1);
        applyMessages.add(m2);
        applyMessages.add(m3);
        for (int i=0;i<applyMessages.size();i++){
            String key = String.valueOf(applyMessages.get(i).hashCode());
            String val = applyMessages.get(i).toString();
            try {
                ApplyMessages.put(key,val);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ApplyMessages.closeDB();
        applyDone=true;
        return null;
    }

}
