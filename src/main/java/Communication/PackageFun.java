package Communication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
public class PackageFun {
   //将数据进行打包
    public static String Pack(String document, String strPrivateKey) throws Exception {
        String s1 =signatureSHA256RSA(document,strPrivateKey);
        //数据打包的格式：数字签名后的数据+原始数据（合约名,合约函数；输入；输出；）
        String  packData =s1+"%%"+document;
//        System.out.println("0_____p"+packData);
        return packData;

    }
    //数据实现数字签名
     public static String signatureSHA256RSA(String document, String strPrivateKey) throws Exception {
        return signatureSHA256RSAWithByteArrayInput(document.getBytes(),strPrivateKey);
    }
    public static String signatureSHA256RSAWithByteArrayInput(byte[] document, String strPrivateKey) throws Exception {
        String realPK = strPrivateKey.replaceAll("-----END PRIVATE KEY-----", "")
                .replaceAll("-----BEGIN PRIVATE KEY-----", "")
                .replaceAll("\n", "");
        //strPk is PKCS#8 format PEM private key
        byte[] b1 = Base64.getDecoder().decode(realPK);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(b1);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        Signature privateSignature = Signature.getInstance("SHA256withRSA");
        privateSignature.initSign(kf.generatePrivate(spec));
        privateSignature.update(document);
        byte[] s = privateSignature.sign();
        return Base64.getEncoder().encodeToString(s);
    }
    //将内容打包到文件
    public static void PackToFile(String s) throws IOException {
        String filePath1 = new File("Contract\\src\\event.txt").getAbsolutePath();
        System.out.println(filePath1);
        //创建集合
        ArrayList<String> as = new ArrayList<>();
        //创建字符缓冲输入流对象
        BufferedReader br = new BufferedReader(new FileReader(filePath1));
        //从文件中读数据，并添加到集合中
        String line;
        while ((line = br.readLine())!=null){
            as.add(line);
        }
        br.close();
        //遍历集合
        String returnS=String.join("",as);
        System.out.println("re"+returnS);
    }
}
