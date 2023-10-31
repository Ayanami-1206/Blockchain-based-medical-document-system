package LevelDB;

import org.iq80.leveldb.DB;
import org.iq80.leveldb.DBFactory;
import org.iq80.leveldb.DBIterator;
import org.iq80.leveldb.Options;
import org.iq80.leveldb.impl.Iq80DBFactory;

import contract.Contracts.Tool;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.iq80.leveldb.impl.Iq80DBFactory.bytes;

public class LevelDbUtilLocal {
    private DB db;
    private String dbFolder;
    private String charset = "utf-16";
    /**
     * 初始化LevelDB
     * 每次使用levelDB前都要调用此方法，无论db是否存在
     */
    public DB initLevelDB(String s) {
        dbFolder="src/main/java/LevelDBLocalData/"+s;
        DBFactory factory = new Iq80DBFactory();
        Options options = new Options();
        options.createIfMissing(true);
        try {
            this.db = factory.open(new File(dbFolder), options);
        } catch (IOException e) {
            System.out.println("levelDB启动异常");
            e.printStackTrace();
        }
        return this.db;
    }
    /**
     * 存放数据
     * @param key
     * @param val
     */
    public void put(String key, String val) throws IOException {
        this.db.put(bytes(key), bytes(val));
    }
    /**
     * 根据key获取数据
     * @param key
     * @return
     */
    public String get(String key) {
        byte[] val = null;
        try {
            val = db.get(bytes(key));
        } catch (Exception e) {
            System.out.println("levelDB get error");
            e.printStackTrace();
            return null;
        }
        if (val == null) {
            return null;
        }
        return new String(val);
    }

    /**
     * 根据key删除数据
     * @param key
     */
    public void delete(String key) {

        try {
            db.delete(bytes(key));
        } catch (Exception e) {
            System.out.println("levelDB delete error");
            e.printStackTrace();
        }
    }
    /**
     * 关闭数据库连接
     * 每次只要调用了initDB方法，就要在最后调用此方法
     */
    public void closeDB() {
        if (db != null) {
            try {
                db.close();
            } catch (IOException e) {
                System.out.println("levelDB 关闭异常");
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取所有key
     * @return
     */
    public List<String> getKeys() {

        List<String> list = new ArrayList<>();
        DBIterator iterator = null;
        try {
            iterator = db.iterator();
            while (iterator.hasNext()) {
                Map.Entry<byte[], byte[]> item = iterator.next();
                String key = new String(item.getKey());
                list.add(key);
            }
        } catch (Exception e) {
            System.out.println("遍历发生异常");
            e.printStackTrace();
        } finally {
            if (iterator != null) {
                try {
                    iterator.close();
                } catch (IOException e) {
                    System.out.println("遍历发生异常");
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    public static void main(String[] args) throws IOException {
        LevelDbUtilLocal levelDbUtil=new LevelDbUtilLocal();
        // DB db =levelDbUtil.initLevelDB("test");
        levelDbUtil.initLevelDB("test");
        levelDbUtil.put("name","keer");
        levelDbUtil.put("hello","world");
        levelDbUtil.put("userInfo","aa");
        System.out.println("获得数据库中的所有key" + levelDbUtil.getKeys().toString());
        System.out.println("数据库中key：name，value："+levelDbUtil.get("hello").toString());
        System.out.println("数据库中key：userInfo，value："+levelDbUtil.get("userInfo").toString());
        levelDbUtil.closeDB();
    }
}
