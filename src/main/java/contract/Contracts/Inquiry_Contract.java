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
}
