package contract.Contracts;

import LevelDB.Constant;
import LevelDB.LevelDbUtil;
import contract.Struct.*;

import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Locale;

public class User_Contract {

    //定义管理节点的ip地址

    //添加新的用户
    public static void addNewUser(){}
    //添加新的资源
    public static void addNewRes(){}
    //添加新的设备
    public static void addNewEquip(){}

    //用户注册。传入的参数：用户的姓名、用户电话号码、用户工号、用户的身份证号、用户密码
    public static String userRegister(String[] strArr,Boolean testFlag) throws UnsupportedEncodingException {
        String returnStr ="";
        String temp_name = strArr[0];
        String temp_phone = strArr[1];
        String temp_worknumber = strArr[2];
        String temp_idnumber = strArr[3];
        String psw =strArr[4];
        System.out.println("中文测试 in user_contract");
        System.out.printf("Did enter userRegister\ntemp_name=%s, temp_phone=%s, temp_worknumber=%s, temp_idnumber=%s, psw=%s\n", temp_name,temp_phone,temp_worknumber,temp_idnumber,psw);
        int flag = 0;
        //获取当前主机的ip地址
        String  Ei = getLocalIp();
        //获取已经初始化的用户列表
        ArrayList<User> users = Tool.getUser();
        ArrayList<Equip> equips = Tool.getEquip();
        ArrayList<User> usersed = Tool.getUsereds();
        //检测当前设备是否在已经合法
        if (isEquip(equips)) {
        //遍历已初始化的用户，检查是否存在匹配信息
        for (int index = 0; index < users.size(); index++) {
            User currentUser = users.get(index);
            if (temp_name.equals(currentUser.getUser_name())&&temp_idnumber.equals(currentUser.getId_number())&&temp_worknumber.equals(currentUser.getWork_number())) {
                //遍历是否已经注册
                for (int i =0;i<usersed.size();i++){
                    if (usersed.get(i).equals(currentUser)){
                        //用户已经注册
                        flag = 1;
                        break;
                    }
                }
                if (flag!=3){
                    currentUser.setUser_phone(temp_phone);
                    currentUser.setPassword(psw);
                    //获取当前的时间，将注册事件存储到数据库中
                    //不是验证环境，将数据出块，验证环境，不出块。
                    //获取已经注册的数据库，并将数据添加进数据库中
                    LevelDbUtil Usereds = Tool.getLevelDB(Constant.USEREDS);
                    try {
                        Usereds.put(currentUser.getWork_number(),currentUser.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }finally {
                        Usereds.closeDB();
                    }

                    if(!testFlag) {
                        //
                        String Ti = getCurrentTime();
                        //调用数据库存储接口,用户Ui在设备Ei上申请注册，于时间Ti申请成功/失败；
                        returnStr = Ti+"&"+currentUser.getUser_name()+"*" + "在ip地址为" + Ei + "上申请注册，于" + Ti + "注册成功"+"*注册";
                    }
                    flag = 2;
                    break;
                }
            }
        }
        if(flag==0){
            // no matched user in ArrayList<User> users = Tool.getUser();
            //将注册失败的信息存储到数据库中
            if(!testFlag) {
                // System.out.println("currentUser.getUser_name(): "+currentUser.getUser_name());
                // System.out.println("Ei: "+Ei);
                System.out.println("no matched user in ArrayList<User> users = Tool.getUser();");
                String Ti = getCurrentTime();
                returnStr = Ti+"&"+ temp_name+"*" + "在ip地址为" + Ei + "上申请注册，于" + Ti + "注册失败"+"*注册";
                System.out.println("returnStr: "+returnStr);
            }
        }
    }else{
            //isEquip(equips)==false
            //将注册失败的信息存储到数据库中
            if(!testFlag) {
                System.out.println("isEquip(equips)==false");
                String Ti = getCurrentTime();
                //将数据临时存储到
                returnStr = Ti+"&"+ temp_name +"*"+ "在ip地址为" + Ei + "上申请注册，于" + Ti + "注册失败"+"*注册";
            }
        }
        return returnStr+flag;
    }
    //用户登录函数：传入的参数：用户的名字 用户的身份证号 用户密码
    public static String userLogin(String[] strArr,Boolean testFlag) throws IOException {
        String returnStr ="";
        String temp_name = strArr[0];
        String temp_workNumber = strArr[1];
        String temp_psw=strArr[2];
        int flag = 7;
        //获取当前主机的ip地址
        String  Ei = getLocalIp();
        ArrayList<User> usereds = Tool.getUsereds();
        System.out.println(usereds);
        ArrayList<Equip> equips = Tool.getEquip();
        if (isEquip(equips)) {
            //与已经注册的用户数据进行匹配
            flag=5;
            for (int index = 0; index < usereds.size(); index++) {
                User currentUser = usereds.get(index);
                System.out.println(currentUser);
                System.out.println(temp_name);
                System.out.println(temp_workNumber);
                if(currentUser.getUser_name().equals(temp_name) && currentUser.getWork_number().equals(temp_workNumber)&&currentUser.getPassword().equals(temp_psw)){
                    if(!testFlag){
                        String Ti = getCurrentTime();
                        returnStr = Ti+"&"+currentUser.getUser_name()+"*" + "在ip地址为" + Ei + "上申请登录，于" + Ti + "登录成功"+"*登录";
                    }

                    //将角色权限的值作为返回值0-3
                    flag = currentUser.getUser_role();
                    System.out.println(flag);
                    break;
                }
                if(currentUser.getUser_name().equals(temp_name) && currentUser.getWork_number().equals(temp_workNumber)&&!currentUser.getPassword().equals(temp_psw)){
                    flag = 4;
                    //将登录失败的信息存储到数据库进入用户界面
                    if(!testFlag) {
                        String Ti = getCurrentTime();
                        returnStr=Ti+"&"+ currentUser.getUser_name()+"*" + "在ip地址为" + Ei + "上申请登录，于" + Ti + "登录失败,失败原因是密码错误"+"*登录";
                    }
                    break;
                }
            }
            if(flag==5){
                if(!testFlag) {
                    String Ti = getCurrentTime();
                    returnStr = Ti+"&"+temp_name +"*"+ "在ip地址为" + Ei + "上申请登录，于" + Ti + "登录失败,失败原因是用户信息有误"+"*登录";
                }
            }
        }else {
            //设备未注册
            flag = 6;
            if(!testFlag) {
                String Ti = getCurrentTime();
                //调用数据库存储接口,用户Ui在设备Ei上申请登录，于时间Ti申请成功/失败；
                returnStr = Ti+"&"+temp_name+"*" + "在ip地址为" + Ei + "上申请登录，于" + Ti + "登录失败,失败原因是非法设备"+"*登录";
            }
        }
        return returnStr+flag;
    }
    //用户注销函数：传入的参数：用户的名字 账户的身份证号 用户密码
    public static String userout(String[] strArr,Boolean testFlag) throws UnknownHostException {
        String returnStr ="";
        String temp_name = strArr[0];
        String temp_idnumber=strArr[1];
        int flag = 0;
       String Ei = getLocalIp();
        //与已经注册的用户数据进行匹配
        ArrayList<User> usereds =Tool.getUsereds();
        String Ti = getCurrentTime();
        //调用数据库存储接口,用户Ui在设备Ei上申请注销，于时间Ti申请成功/失败；
        returnStr = Ti+"&"+ temp_name+"*" + "在ip地址为" + Ei + "上申请注销，于" + Ti + "注销失败"+"*注销";
        for (int index = 0;index<usereds.size();index++){
            User currentUser = usereds.get(index);
            if (currentUser.getUser_name().equals(temp_name)&&currentUser.getId_number().equals(temp_idnumber)){
                    //将需要注销的用户从注册列表中删除
                //获取已经注册的用户库
                LevelDbUtil Usereds = Tool.getLevelDB(Constant.USEREDS);
                Usereds.delete(currentUser.getWork_number());
                Usereds.closeDB();
                if(!testFlag) {
                     Ti= getCurrentTime();
                    //调用数据库存储接口,用户Ui在设备Ei上申请注销，于时间Ti申请成功/失败；
                    returnStr = Ti+"&"+ currentUser.getUser_name()+"*" + "在ip地址为" + Ei + "上申请注销，于" + Ti + "注销成功"+"*注销";
                }
                    flag = 1;
                    break;
            }
        }
        if (flag==0){
            //将注销失败的信息存储到数据库进入用户界面
            if(!testFlag) {
                 Ti = getCurrentTime();
                //调用数据库存储接口,用户Ui在设备Ei上申请登录，于时间Ti申请成功/失败；
                returnStr =Ti+"&"+temp_name+"*" + "在ip地址为" + Ei + "上申请注销，于" + Ti + "注销失败,失败原因是密码错误"+"*注销";

            }
        }
        return returnStr+flag;
    }
    //普通用户权限申请函数：参数：用户的名字  用户的工号 旧权限 新权限
    public static String  applyRole(String[] strArr,Boolean testFlag) throws IOException {
        String returnStr ="";
        String temp_name = strArr[0];
        String temp_worknumber = strArr[1];
        String temp_old=strArr[2];
        String temp_new = strArr[3];
        //遍历已注册的节点 查看信息是否匹配
        int flag = 0;
        String  Ei = getLocalIp();
        ArrayList<ApplyMessage> applyMessages = Tool.getApplayMessages();
        System.out.println("applyMessage"+applyMessages);
        //遍历是否存在重复申请
        if (applyMessages.size()!=0){
            for (int index = 0;index<applyMessages.size();index++) {
                ApplyMessage currentApply = applyMessages.get(index);
                System.out.println("currentApply"+currentApply);
                if (currentApply.getApply_worknumber().equals(temp_worknumber)) {

                    flag = 2;
                    break;
                }
            }
            if(flag!=2){
                flag =1;
                String Ti = getCurrentTime();
                ApplyMessage applyMessage = new ApplyMessage(temp_name,temp_worknumber,temp_old,temp_new,Ti);
                LevelDbUtil ApplyMessages = Tool.getLevelDB(Constant.APPLYMESSAGES);
                ApplyMessages.put(String.valueOf(applyMessage.hashCode()),applyMessage.toString());
                ApplyMessages.closeDB();
            }
        }
        String Ti = getCurrentTime();
        returnStr = Ti+"&"+  temp_name + "*"+"在ip地址为" + Ei + "上申请权限变更为" + temp_new+"*权限";
        if (flag==1){
            if(!testFlag) {
                Ti = getCurrentTime();
                returnStr = Ti+"&"+  temp_name + "*"+"在ip地址为" + Ei + "上申请权限变更为" + temp_new+"成功"+"*权限";
            }
        }else{
            if(!testFlag) {
               Ti = getCurrentTime();
                returnStr = Ti+"&"+temp_name +"*" + "在ip地址为" + Ei + "上申请权限变更为" + temp_new+"失败"+"*权限";
                System.out.println("您还有申请未处理，请稍后申请");
            }
        }
        return returnStr+flag;
    }
    //管理节点管理权限 参数：用户的名字  用户的工号 新权限 是否变更
    public static  String changeRole(String[] strArr,Boolean testFlag){
        String returnStr = "";
        String temp_name = strArr[0];
        String temp_worknumber=strArr[1];
        String temp_old =strArr[2];
        String temp_new =strArr[3];
        String pass=strArr[4];
        int flag = 0;
        String  Ei = getLocalIp();
        //获取已经注册的用户
        ArrayList<User> usereds =Tool.getUsereds();
        ArrayList<ApplyMessage> applyMessages = Tool.getApplayMessages();
        //遍历数组找到对应用户
        if (pass.equals("1")){
            //遍历数组找到对应用户
            System.out.println("1");
            if (usereds.size()!=0){
                System.out.println("usssss"+usereds);
                for (int index=0;index<usereds.size();index++){
                    User currentUser = usereds.get(index);
                    if (currentUser.getUser_name().equals(temp_name)&&currentUser.getWork_number().equals(temp_worknumber)){
                        currentUser.setUser_role(Integer.parseInt(temp_new));
                        System.out.println("newUserRole"+currentUser);
                        LevelDbUtil Usereds = Tool.getLevelDB(Constant.USEREDS);
                        try {
                            Usereds.put(currentUser.getWork_number(),currentUser.toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Usereds.closeDB();
                        flag = 1;
                    }
                }
            }

        }
        System.out.println("sssB"+applyMessages);
        for (int index = 0;index<applyMessages.size();index++){
            ApplyMessage applyMessage = applyMessages.get(index);
            if (applyMessage.getApply_name().equals(temp_name)&&applyMessage.getApply_worknumber().equals(temp_worknumber)){
               //删除已经处理的申请
                LevelDbUtil ApplyMessages = Tool.getLevelDB(Constant.APPLYMESSAGES);
               ApplyMessages.delete(String.valueOf(applyMessage.hashCode()));
               ApplyMessages.closeDB();
            }
        }
        System.out.println("sssA"+applyMessages);
        String Ti = getCurrentTime();
        returnStr = Ti +"&"+temp_name+"*"+"将角色权限变为"+temp_new+"失败*权限";
        if (flag==1){
            if(!testFlag) {
                Ti = getCurrentTime();
                //上传权限变更的数据
                returnStr = Ti +"&"+temp_name+"*"+"将角色权限变为"+temp_new+"成功"+"*权限";
            }

        }else{
            if(!testFlag) {
                Ti = getCurrentTime();
                returnStr = Ti +"&"+temp_name+"*"+"将角色权限变为"+temp_new+"失败"+"*权限";
            }
        }
        return returnStr+flag;
    }
    //获取当前的用户信息，参数用户的名字 用户的身份证号 用户密码
    public static User getCurrentUser(String temp_name,String temp_worknumber,String temp_psw){
        User currentUser = new User();
        ArrayList<User> usereds = Tool.getUsereds();
        //遍历列表获取当前用户
        if (usereds.size()!=0){
            for (int index = 0;index<usereds.size();index++){
                User temp_User = usereds.get(index);
                if (temp_User.getUser_name().equals( temp_name)&&temp_User.getWork_number().equals(temp_worknumber)&&temp_User.getPassword().equals(temp_psw)){
                    currentUser = temp_User;
                    break;
                }
            }
        }
        return currentUser;
    }
    //客户端：进行数据发送
    //参数：发送的数据 服务器的ip和端口

    public static void client(String date,String ipAdsress,int port) throws IOException {
        Socket socket = null;
        OutputStream os = null;

        try {
            socket = new Socket(ipAdsress, port);
            os = socket.getOutputStream();
            os.write(date.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

    }
    //服务端：进行数据接收，并返回接收的数据
    //参数：端口

    public static String  server(String ipAdsress,int port) throws IOException {
        ServerSocket ss = null;
        Socket socket = null;
        InputStream is = null;
        try {
            ss = new ServerSocket(port);
            socket = ss.accept();
            is = socket.getInputStream();
        }
        catch (IOException e){
            e.printStackTrace();
        }finally {
            if (is!=null){
                try {
                    is.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            if (socket!=null){
                try {
                    socket.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            if (ss!=null){
                try {
                    ss.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return is.toString();
    }
    //获取当前时间的函数
    public static String getCurrentTime(){
        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        String Ti = dateFormat.format(calendar.getTime());
        return Ti;
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

    //根据已知文件名获取文件路径
    public static String getPath(String name){
       return "";
    }
    //判断当前设备是否已经注册：参数：已经注册的设备列表
    public static boolean isEquip(ArrayList<Equip> equips){
        boolean flag = false;
        //获取当前设备的ip地址
        Enumeration<NetworkInterface> n;
        try {
            n = NetworkInterface.getNetworkInterfaces();
            for (; n.hasMoreElements();)
            {
                NetworkInterface e = n.nextElement();
        
                Enumeration<InetAddress> a = e.getInetAddresses();
                for (; a.hasMoreElements();)
                {
                    InetAddress addr = a.nextElement();
                    String currentEquip = addr.getHostAddress();
                    for (int index = 0;index<equips.size();index++){
                        String temp_ip = equips.get(index).getEquip_id();
                        if (currentEquip.equals(temp_ip)){
                            flag = true;
                            break;
                        }
                    }
                    if(flag)break;
                }
            }
        } catch (SocketException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return  flag;
    }
    public  static String Test(String [] ss,Boolean flag){
        System.out.println("hello Contract");
        for (int i =0;i<ss.length;i++){
            System.out.println(ss[i]);
        }
        return "ss";
    }
    //将内容打包到文件
    public static void PackToFile(String s) throws IOException {
        String filePath1=new File("src/main/java/event.txt").getAbsolutePath();
        System.out.println(filePath1);
        FileWriter fw = null;
        try {
            fw = new FileWriter(filePath1);
            fw.write(s);
        }catch (IOException e) {
            e.printStackTrace();
        }
        if (fw!=null) {
            try {
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
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

    //初始化用户
    public  static void InitialzationUser(){
        //初始化用户
        //初始化用户
        LevelDbUtil Users = new LevelDbUtil();
        Users.initLevelDB("Users");
        ArrayList<User> users = new ArrayList<>();//用来保存初始化用户的列表
        //初始化用户
        User u1 = new User("一类用户", "1", "10", "1", 0, "", realPub);
        User u2 = new User("二类用户", "1", "11", "1", 1, "", realPub);
        User u3 = new User("三类用户", "1", "12", "1", 2, "", realPub);
        User u4 = new User("管理节点", "1", "13", "1", 3, "", realPub);
        users.add(u1);
        users.add(u2);
        users.add(u3);
        users.add(u4);
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
    }
    //初始化资源
    public static void InitialzationRse(){
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
    }
    //初始化设备
    public static void InitialzationEquip(){
        //初始化设备
        LevelDbUtil Equips = new LevelDbUtil();
        Equips.initLevelDB("Equips");
        //用于初始化的设备
        ArrayList<Equip> equips = new ArrayList<>();//用来保存初始化用户的列表
        Equip e1 = new Equip("192.168.32.1","1","1");
        Equip e2 = new Equip("10.28.129.146","1","1");
        Equip e3 = new Equip("10.28.129.147","1","1");
        equips.add(e1);
        equips.add(e2);
        equips.add(e3);
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
    }

}
