package contract.Contracts;



import LevelDB.Constant;
import LevelDB.LevelDbUtil;
import contract.Struct.ApplyMessage;
import contract.Struct.Equip;
import contract.Struct.Resource;
import contract.Struct.User;

import java.util.ArrayList;
import java.util.List;

public class Tool {
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
    public static ArrayList<ApplyMessage> getApplayMessages( ){
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
}
