package contract.Contracts;

import contract.Struct.DbStore;
import contract.Struct.Equip;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Equip_Contract {
    //设备新增函数设备ip、设备的名称、设备的物理
    public static boolean new_qquip(String[] strArr,Boolean testFlag){
        String equipId =strArr[0];
        String equipName = strArr[1];
        String macAddressuserName =strArr[2];
         String chairperson = "chairperson";
         boolean flag = false;
        if(chairperson=="chairperson"){
            Equip newEquip = new Equip();
            newEquip.setEquip_id(equipId);
            newEquip.setEquip_name(equipName);
            newEquip.setMac_address(macAddressuserName);
            //存储进数据库以及本地
            DbStore db = new DbStore();
            ArrayList<Equip> equips = db.getEquips("设备");
            equips.add(newEquip);
            if (!testFlag){
                db.put("设备注册",equipName);
                String Ti = getCurrentTime();
                //调用数据库存储接口,用户Ui在设备Ei上申请注册，于时间Ti申请成功/失败；
                db.put("设备注册", "设备" +equipName  + "在ip地址为" + equipId + "上申请注册，于" + Ti + "注册成功");
            }

            flag = true;
        }
        return flag;

    }
    //获取当前时间的函数
    public static String getCurrentTime(){
        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        String Ti = dateFormat.format(calendar.getTime());
        return Ti;
    }


}
