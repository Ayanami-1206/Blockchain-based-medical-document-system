package MinIO; 
  
import java.io.FileInputStream;  
import java.io.FileNotFoundException;  
import java.io.InputStream;  
import java.math.BigInteger;  
import java.security.MessageDigest;

  
public class FileHashUtil {  //MD5

    /*  
    public static void main(String[] args) {  
        System.out.println(compareHash("a04fdb995305992e86750cbe0de4c2f6","a04fdb995305992e86750cbe0de4c2f6")) 
        try {  
            //此处我测试的是我本机jdk源码文件的MD5值 
        	String filePath = "/home/bupt/Downloads/test.txt";
        	
            String md5Hashcode = md5HashCode(filePath);
            String md5Hashcode32 = md5HashCode32(filePath);  
            
            System.out.println(md5Hashcode + "：文件的md5值");  
            System.out.println(md5Hashcode32+"：文件32位的md5值"); 
            
            //System.out.println(-100 & 0xff);
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        }  
    }  
    */
    
    /**
     * 获取文件的md5值 ，有可能不是32位
     * @param filePath	文件路径
     * @return
     * @throws FileNotFoundException
     */
    public static String md5HashCode(String filePath) throws FileNotFoundException{  
        FileInputStream fis = new FileInputStream(filePath);  
        String outcome =  md5HashCode(fis);  
        return outcome;  
    }  
    
    /**
     * 保证文件的MD5值为32位
     * @param filePath	文件路径
     * @return
     * @throws FileNotFoundException
     */
    public static String md5HashCode32(String filePath) throws FileNotFoundException{  
    	FileInputStream fis = new FileInputStream(filePath);  
        String outcome =  md5HashCode32(fis);  
        //fis.close();
    	return outcome;  
    }  
    
    /**
     * java获取文件的md5值  
     * @param fis 输入流
     * @return
     */
    public static String md5HashCode(InputStream fis) {  
        try {  
        	//拿到一个MD5转换器,如果想使用SHA-1或SHA-256，则传入SHA-1,SHA-256  
            MessageDigest md = MessageDigest.getInstance("MD5"); 
            
            //分多次将一个文件读入，对于大型文件而言，比较推荐这种方式，占用内存比较少。
            byte[] buffer = new byte[1024];  
            int length = -1;  
            while ((length = fis.read(buffer, 0, 1024)) != -1) {  
                md.update(buffer, 0, length);  
            }  
            fis.close();
            //转换并返回包含16个元素字节数组,返回数值范围为-128到127
  			byte[] md5Bytes  = md.digest();
            BigInteger bigInt = new BigInteger(1, md5Bytes);//1代表绝对值 
            return bigInt.toString(16);//转换为16进制
        } catch (Exception e) {  
            e.printStackTrace();  
            return "";  
        }  
    }  
    
    /**
     * java计算文件32位md5值
     * @param fis 输入流
     * @return
     */
  	public static String md5HashCode32(InputStream fis) {
  		try {
  			//拿到一个MD5转换器,如果想使用SHA-1或SHA-256，则传入SHA-1,SHA-256  
  			MessageDigest md = MessageDigest.getInstance("MD5");
  			
  			//分多次将一个文件读入，对于大型文件而言，比较推荐这种方式，占用内存比较少。
  			byte[] buffer = new byte[1024];
  			int length = -1;
  			while ((length = fis.read(buffer, 0, 1024)) != -1) {
  				md.update(buffer, 0, length);
  			}
  			fis.close();
  			
  			//转换并返回包含16个元素字节数组,返回数值范围为-128到127
  			byte[] md5Bytes  = md.digest();
  			StringBuffer hexValue = new StringBuffer();
  			for (int i = 0; i < md5Bytes.length; i++) {
  				int val = ((int) md5Bytes[i]) & 0xff;//解释参见最下方
  				if (val < 16) {
  					/**
  					 * 如果小于16，那么val值的16进制形式必然为一位，
  					 * 因为十进制0,1...9,10,11,12,13,14,15 对应的 16进制为 0,1...9,a,b,c,d,e,f;
  					 * 此处高位补0。
  					 */
  					hexValue.append("0");
  				}
  				//这里借助了Integer类的方法实现16进制的转换 
  				hexValue.append(Integer.toHexString(val));
  			}
  			return hexValue.toString();
  		} catch (Exception e) {
  			e.printStackTrace();
  			return "";
  		}
  	}
    /**
     * 判断两个字符串是否相等
     * @param upHash doHash	//文件路径
     * @return
     * @throws 
     */
    public static boolean compareHash(String upHash, String doHash) {
        if(upHash.equals(doHash)){return true;}
        else{return false;}
    }

  
}  
