package contract.Contracts;

import LevelDB.Constant;
import LevelDB.LevelDbUtil;
import contract.Struct.DbStore;
import contract.Struct.Resource;

import java.io.IOException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Res_Contract {
    static String chairperson = "192.168.1.125";

    //资源查看：资源的类型
    public static ArrayList<Resource> getResource(int r_level){
        DbStore db = new DbStore();
        ArrayList<Resource> resources_temp = db.getResources("资源");
        ArrayList<Resource> resources = new ArrayList<>();
        if (resources_temp.size() != 0){
            for (int index = 0;index<resources_temp.size();index++){
                Resource rCurrent = resources_temp.get(index);
                if (rCurrent.getResource_level() == r_level){
                    resources.add(rCurrent);
                }
            }
        }
        return resources;
    }
    //用户访问指定资源：参数：用户的角色，资源的类型
    public static String operateResource(String[] strArr, Boolean testFlag) throws UnknownHostException {
        String returnStr = "";
        int user_role = Integer.parseInt(strArr[0]);
        int r_level= Integer.parseInt(strArr[1]);
        String r_name = strArr[2];
        int flag = 0;//0 可读写删反之 只可读
        int flag1 = 0;//作为测发控系统部署与否的标志
        /*if (r_level==1){
            //第二类数据永远只读
        }*/
        String Ti = getCurrentTime();
        returnStr = Ti + "&" + "资源" + r_name + "在" + Ti + "申请修改" + "资源";
        if (user_role==0&&r_level == 0){
            //测发控系统是否部署觉得是否可以读写
            flag = 1;
            if(!testFlag) {
                 Ti = getCurrentTime();
                returnStr = Ti + "&" + "资源" + r_name + "在" + Ti + "申请修改" + "资源";
            }
        }
        else if (user_role == 1 &&r_level==1){
            flag = 1;//可删
            if(!testFlag) {
                 Ti = getCurrentTime();
                returnStr = Ti + "&" + "资源" + r_name + "在" + Ti + "申请修改" + "资源";
            }
        }
        else if (user_role == 2 &&r_level==0){
            flag = 1;//可删
            if(!testFlag) {
                 Ti = getCurrentTime();
                returnStr = Ti + "&" + "资源" + r_name + "在" + Ti + "申请修改" + "资源";
            }
        }
        else if (user_role==3&&r_level == 0){
            flag = 1;
            if(!testFlag) {
                 Ti = getCurrentTime();
                returnStr = Ti + "&" + "资源" + r_name + "在" + Ti + "申请修改" + "资源";
            }
        }
        return returnStr+flag ;
    };
    //资源操作：资源的名称、资源的级别、操作
    public static String  changeResource(String[] strArr,Boolean testFlag) {
      String returnStr="";
        String[] old= strArr[0].split(",");
        Resource old_R = new Resource(old[0],Integer.parseInt(old[1]),old[2]);
        String[] new1 = strArr[1].split(",");
        Resource new_R = new Resource(new1[0],Integer.parseInt(new1[1]),new1[2]);
        String operator = strArr[2];
        int flag = 0;
        DbStore db = new DbStore();
        //从数据库获取原有资源的列表
        ArrayList<Resource> resources = Tool.getResources();
        System.out.println(resources);
        if (resources.size()!=0){
            for (int index = 0;index<resources.size();index++){
                Resource current_R = resources.get(index);
                if (current_R.equals(old_R)){
                    //找到对应的资源进行修改
                    LevelDbUtil Res = Tool.getLevelDB(Constant.RESOURCES);
                    if (operator.equals("修改")){
                        System.out.println(old_R);
                        //获取资源数据库，进行修改
                        try {
                            Res.delete(String.valueOf(old_R.hashCode()));
                            Res.put(String.valueOf(new_R.hashCode()),new_R.toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        flag = 1;
                    }
                    else if (operator.equals("删除")){
                        Res.delete(String.valueOf(old_R.hashCode()));
                        flag = 2;
                    }
                    Res.closeDB();
                    ArrayList<Resource> resources2 = Tool.getResources();
                    System.out.println("修改后");
                    System.out.println(resources2);
                }
            }
        }
        String Ti = getCurrentTime();
        returnStr = Ti + "&" + "资源" +strArr[0]  + "在" + Ti + "修改为"+strArr[1]+"失败" + "资源";
        if(!testFlag&&flag==1) {

            returnStr = Ti + "&" + "资源" +strArr[0]  + "在" + Ti + "修改为"+strArr[1] +"成功"+ "资源";
        }
        if(!testFlag&&flag==2) {

            returnStr = Ti + "&" + "资源" +strArr[0]  + "在" + Ti + "被删除" + "资源";
        }
        if(!testFlag&&flag==0) {

            returnStr = Ti + "&" + "资源" +strArr[0]  + "在" + Ti + "修改为"+strArr[1]+"失败" + "资源";
        }
        return returnStr+flag;
    }


    //获取当前时间的函数
    public static String getCurrentTime(){
        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        String Ti = dateFormat.format(calendar.getTime());
        return Ti;
    }



}


