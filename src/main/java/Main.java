import LevelDB.LevelDbUtil;
import contract.Contracts.Initialization_Contract;
import contract.Contracts.Tool;
import contract.Contracts.User_Contract;
import contract.Struct.ApplyMessage;
import contract.Struct.Resource;
import contract.Struct.User;
import contract.UI.UserRegisterUI;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;

import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) throws UnsupportedEncodingException {
        if(true){
            // System.out.println("defaultcharset: "+Charset.defaultCharset().displayName());
            // System.out.println("阿达放大");
            // String[] strArr = {"1","1","1","1","1"};
            // String  returnStr = User_Contract.userRegister(strArr,false);
            // return;
        }else{

        }
        //初始化
        try{
            UIManager.setLookAndFeel(
            UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception e){
            e.printStackTrace();
        }
        Initialization_Contract.InitialzationRes();
        Initialization_Contract.InitialzationEquip();
        Initialization_Contract.InitialzationUser();
        InitialzationApply();
        new UserRegisterUI();

    }
    public static  void InitialzationApply(){
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
    }


}
