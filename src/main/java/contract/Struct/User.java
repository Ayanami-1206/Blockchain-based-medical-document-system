package contract.Struct;

import java.io.Serializable;
import java.util.Objects;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//定义用户结构
public class User implements Serializable{
    String user_name; //用户的名字
    String user_phone;//用户电话号码
    String work_number; //用户的ID
    String id_number; //用户的身份证号
    int user_role; //用户对应的角色权限
    String password;//用户登录密码
    String pubKey;

    private SecretKey secretKey;


    public User() {
    }


    public User(String user_name, String user_phone, String work_number, String id_number, int user_role, String password, String pubKey) {
        this.user_name = user_name;
        this.user_phone = user_phone;
        this.work_number = work_number;
        this.id_number = id_number;
        this.user_role = user_role;
        this.password = password;
        this.pubKey = pubKey;

        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);

            secretKey = keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return user_role == user.user_role &&
                Objects.equals(user_name, user.user_name) &&
                Objects.equals(user_phone, user.user_phone) &&
                Objects.equals(work_number, user.work_number) &&
                Objects.equals(id_number, user.id_number) &&
                Objects.equals(password, user.password) &&
                Objects.equals(pubKey, user.pubKey);
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getWork_number() {
        return work_number;
    }

    public void setWork_number(String work_number) {
        this.work_number = work_number;
    }

    public String getId_number() {
        return id_number;
    }

    public void setId_number(String id_number) {
        this.id_number = id_number;
    }

    public int getUser_role() {
        return user_role;
    }

    public void setUser_role(int user_role) {
        this.user_role = user_role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPubKey() {
        return pubKey;
    }

    public void setPubKey(String pubKey) {
        this.pubKey = pubKey;
    }

    public SecretKey getSecretKey() {
        return secretKey;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_name, user_phone, work_number, id_number, user_role, password, pubKey);
    }

    @Override
    public String toString() {
        return user_name + ";"+user_phone + ";"+work_number+ ";"+ id_number + ";"+ user_role+ ";" + password+";"+pubKey;
    }
}
