package contract.Struct;

import java.util.ArrayList;

public class DbStore {

    ArrayList<User> users = new ArrayList<>();//用来保存初始化用户的列表
    ArrayList<Equip> equips = new ArrayList<>();//用来保存初始化设备的列表
    ArrayList<Resource> resources = new ArrayList<>();//用来保存初始化资源的列表
    ArrayList<User> usereds = new ArrayList<>();//用来保存注册用户的列表
    ArrayList<Equip> equipeds = new ArrayList<>();//用来保存注册用设备的列表
    ArrayList<ApplyMessage> applyMessages = new ArrayList<>();//保存未处理的申请角色权限的列表
    ArrayList<FileInfo> fileInfos = new ArrayList<>();

    //数据库key value
    //添加用户
    public boolean put(String key, User value){
        boolean flag = false;
        if (key.equals("用户注册")){
            usereds.add(value);
            flag = true;
        }
        else if (key.equals("初始化用户")){
            users.add(value);
            flag = true;
        }
        return flag;

    };
    //添加设备
    public boolean put(String key, Equip value){
        boolean flag = false;
        if (key.equals("设备注册")){
            equips.add(value);
            flag = true;
        }
        else if (key.equals("初始化设备")){
            equips.add(value);
            flag = true;
        }
        return flag;
    };
    //初始化资源
    public boolean put(String key, Resource value){
        boolean flag = false;
        if (key.equals("初始化资源")){
            resources.add(value);
            flag = true;
        }
        return flag;
    };
    //add fileInfos
 //   public boolean put(String key, FileInfo value){
 //       boolean flag = false;
 //       if (key.equals("file upload")){
 //           fileInfos.add(value);
 //           flag = true;
 //       }
 //       else if (key.equals("file init")){
 //           fileInfos.add(value);
 //           flag = true;
 //       }
 //       flag = true;
 //   }
 //   //添加操作数据
    public void put(String key, String value){

    };
    //添加新的申请信息
    public boolean put(String key, ApplyMessage value){
        applyMessages.add(value);
        System.out.println("applyMessages"+applyMessages.size());
        return true;
    }

    //获取相关信息
    public  String get(String key){
        return "";
    };
    public ArrayList<User> getUsers(String key){
        ArrayList<User> u= new ArrayList<>();
        if(key.equals("注册用户")){
           u=usereds;
        }
        else if (key.equals("初始化用户")){
            u = users;
        }
        return u;
    };
    public ArrayList<Equip> getEquips(String key){
        ArrayList<Equip> e= new ArrayList<>();
        if(key.equals("设备")){
            e=equips;
        }
        return e;
    };
    public ArrayList<Resource> getResources(String key){
        return resources;
    };
    //获取申请角色权限的列表
    public ArrayList<ApplyMessage> getApply(String key){
        return applyMessages;
    }
    //用户申请权限的保存
    public boolean ApplyRole(ApplyMessage applyMessage){
        applyMessages.add(applyMessage);
        return true;
    }
//    public boolean fileUpload(FileInfo fileInfo){
//        fileInfos.add(fileInfo);
//    }
    //获取资源
    public ArrayList<Resource> getResources(){
        return resources;
    }
    //删除相关信息
    public void remove(String key){

    };
}
