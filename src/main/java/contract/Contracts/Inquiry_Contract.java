package contract.Contracts;

import LevelDB.Constant;
import LevelDB.LevelDbUtil;
import MinIO.FileHashUtil;
import MinIO.MinioUtil;
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



public class Inquiry_Contract {
    public static String fileInquiry(String[] strArr, int fromClient) {
        return Tool.sendRawCommandToServer(strArr);
    }

    public static String fileInquiry(String[] strArr, Boolean testflag)throws IOException{
        String fileinfos = "";
        String user_id = strArr[0];
        ArrayList<FileInfo> infos = Tool.getFileInfos(user_id);
        for(int i = 0;i < infos.size();i++){
            FileInfo m = infos.get(i);
            fileinfos+=m.getUser_number();
            fileinfos+=";";
            fileinfos+=m.getFile_name();
            fileinfos+=";";
            fileinfos+=m.getFile_hash();
            fileinfos+=";";    
        }
        return fileinfos;
    }

    public static String downloadFile(String[] strArr, int fromClient){
        return Tool.sendRawCommandToServer(strArr);
    }

    public static String downloadFile(String[] strArr, Boolean testflag)throws IOException{
        
        
        int flag = 1;
        String returnStr = "";
        String bucket_name = strArr[0];
        String file_name = strArr[1];
        String file_hash = strArr[2];

        
        MinioUtil miniotool = new MinioUtil();
        miniotool.init(bucket_name);
        String a = miniotool.downloadFile(file_name , "/home/bupt/Downloads/" + file_name, bucket_name, file_hash);

        String Ei = Tool.getClientIP();
        String Ti = Tool.getCurrentTime();

        
	    String doHash = FileHashUtil.md5HashCode32("/home/bupt/Downloads/" + file_name);
		
        returnStr = Ti + "&" + bucket_name + "*" + "在ip地址为" + Ei + "下载文件:" + file_name + "&hash:" + doHash +"&文件下载";
        return returnStr + flag;
    }
}
