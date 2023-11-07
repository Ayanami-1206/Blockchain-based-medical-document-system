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
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Locale;

public class Upload_Contract{
    public static String fileUpload(String[] strArr, int fromClient) {
        return Tool.sendRawCommandToServer(strArr);
    }
   //文件上传记录：医生，患者ID，文件说明，文件摘要
    public static String fileUpload(String[] strArr, Boolean testflag) throws IOException{
        String returnStr = "";
        String doc_number = strArr[0];
        String temp_name = strArr[1];
        String temp_number = strArr[2];
        String file_name = strArr[3];
        String file_hash = strArr[4];

        int flag = 0;
        String Ei = Tool.getClientIP();
        ArrayList<User> usereds = Tool.getUsereds();
        for(int index = 0; index < usereds.size(); index ++){
            User currentuser = usereds.get(index);
            // System.out.println(currentuser.getWork_number());
            if(temp_number.equals(currentuser.getWork_number())){
                flag = 5;
                break;
            }
        }
        if(flag == 0){
             if(!testflag){
                 String Ti = Tool.getCurrentTime();
                 returnStr = Ti + "&" + doc_number + "*" + "在ip地址为" + Ei + "上为" + temp_number + "于" + Ti + "上传文件失败，用户未注册"
                     + "*文件上传";
             }
        }else{
             if(!testflag){
                 String Ti = Tool.getCurrentTime();
                 ArrayList<FileInfo> a = new ArrayList<>();
                 a.add(new FileInfo(doc_number,temp_number,file_name,file_hash));
                 Tool.writeFileInfosDB(a);
                 returnStr = Ti + "&" + doc_number + "*" + "在ip地址为" + Ei + "上为" + temp_number + "于" + Ti + "上传文件成功"
                     + "*文件上传";
             }
        }
        
       return returnStr + flag;
    }

}