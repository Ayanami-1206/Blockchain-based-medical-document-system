package contract.Struct;

import java.io.Serializable;

//定义医疗文件信息存储的结构
public class FileInfo implements Serializable {
    String doc_number; // 上传文件的医生
    String user_number; // 文件所属用户
    String file_name; // 文件简介
    String file_hash; // 文件摘要

    public FileInfo() {
    }

    public FileInfo(String doc_number, String User_number, String file_name, String file_hash) {
        this.doc_number = doc_number;
        this.user_number = User_number;
        this.file_name = file_name;
        this.file_hash = file_hash;
    }

    public String getDoc_number() {
        return doc_number;
    }

    public void setDoc_number(String doc_number) {
        this.doc_number = doc_number;
    }

    public String getUser_number() {
        return user_number;
    }

    public void setUser_number(String User_number) {
        this.user_number = FileInfo.this.user_number;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getFile_hash() {
        return file_hash;
    }

    public void setFile_hash(String file_hash) {
        this.file_hash = file_hash;
    }

    @Override
    public String toString() {
        return doc_number + ";" + user_number + ";" + file_name + ";" + file_hash;
    }

}