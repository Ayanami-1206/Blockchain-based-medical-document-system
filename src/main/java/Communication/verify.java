package Communication;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class verify {
    public static void main(String[] args) throws Exception {
//        String verifyStr ="ac/Ug2OH0HT9wzYhilr/QSoGZ9RQ9I7fCOHKy2uRfHVHiZbsKA90Zj01vYceLhWriO6lI867i9RwAa1JInNl/jFd7nuPCPRohS/tNyUG+J4nnxlta0ywZLpsxxHJaeecXxilk7bmJmmHNHC7MwLBNpWVbJkdKDspRHZGIKHhKAewUd1o825g7UgJqXrY62gBjAVnO4Vcj2WbhXxMlrt5jojVliXYutdZ8H0Vb9CL361zOvK3CdTR4QdRdsb/XD0FejwgOAV6lqazLe8hN2isG+/HCez7iUprlZxdFR5XMLFbfOYhkVMM9W02B0AYBYPAtlVJiMh40BRq5p2kML7Rzg==%%contract.Contracts.Res_Contract,operateResource;3,1,测试数据;资源测试数据在2021-06-01 :07:57:24申请修改资源0";
       String verifyStr ="h1ks9pUOK1kXB6xYzRE/3JjApKW/iDtvSbPSCV521WixiW0ze0f22jSKWNALA9ABYJJy1IfpNOSfQD2KwiBP75AluSjz6uqJPzZHZbnxr4obVii3itenk5wKxzBxsCEP/8WjP9pRg/qBSLjKOHuH4GvDN/DgU2Sgqn7xppurg96z2smdr5u+RrHCIAwYlx/uZ6oQGPdBcHNZ0Uv4tT/xQS7HKMZ+VSlqTJHWdLibu041nfnVqyNqDcTh780d81Y7o4Fuw8KxlnQ1h8KqwDk7VNGQiiMYOndH2wZ0dYh1wHl+lnYh10eYJyWLXWZQKmlHV10XBlopx0SnJ27b0sQW2A==%%contract.Contracts.User_Contract,userLogin;管理节点,13,1;管理节点*在ip地址为192.168.32.1上申请登录，于2021-06-03 :12:18:00登录成功*登录3" ;
        Boolean result = Verify(true,verifyStr);
        System.out.println(result);
    }
//    用于验证是否是合法的智能合约的调用
    //VerifyFlag:用于标识是进行验证还是调动智能合约
    //VerifyStr:用于验证智能合约的输入格式:数字签名后的数据%%合约名,合约函数;输入;输出
    public static Boolean Verify(Boolean verifyFlag, String verifyStr) throws Exception {
        String strPubKey ="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwZJO7qu7KLteVCAJH4rB\n" +
                "VnhynhauUJirZPGntVUFx3QJfhHaRdxbk1KVxDCAAYhdUoF4l6uZ762Qjcld9AZE\n" +
                "3BW5CLHdotpfNuJNSJOcnS5RUzXB2jvn8FxAX/dfjG+6y3Og9aKnYygO7DGnSmND\n" +
                "JtNiaLraFHeb3H5IwUa/3AIOvv//iWEwntnGlUDjpajxkNB9auR4Y25fH2kQ1/NB\n" +
                "bJlSJITihUywSysW9OWPS16j+Dj3yu8QmkBzj57/qyU+FtsDbk70xx+B3NdTwWDl\n" +
                "6I0xyB657Nen+FSqxDqQFBIEDaJIIuMom4IZb8skm43JR7a9vW0DFkoP7XMvk0T0\n" +
                "nQIDAQAB";
        Boolean flag = false;
        //将字符串分为合约名、输入、输出
        String[] signatures = verifyStr.split("%%");
        //获取进行数字签名验证的部分
        String signature = signatures[0];
        System.out.println("s0"+signature);
        //进行数字签名前的数据
        String verifyData = signatures[1];
        System.out.println("verifyData:"+verifyData);
        String[] verifyArr = verifyData.split(";");
        System.out.println(verifyArr[0]);
        //获取合约的名称+合约函数
        String[] contract = verifyArr[0].split(",");
        System.out.println("cccccccccccc"+contract);
        //合约名
        String ContractNameStr = contract[0];
        //合约函数
        String ContractFunStr = contract[1];
        //获取输入
        String[] input = verifyArr[1].split(",");
        //获取输出
        String outPut = verifyArr[2];
        String outPutV = outPut.substring(outPut.length()-1,outPut.length());
        //测试合约
        Method Fun = ReflectNode.getMethod(ContractNameStr,ContractFunStr);
        String result="";
        try {
            //利用反射实现方法的调用
            Object obj =Class.forName(ContractNameStr).newInstance();
              result = (String) Fun.invoke(obj,input,verifyFlag);


        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        //进行数字签名的验证
        System.out.println("----------"+verifyData);
        byte[] bs = Base64.getDecoder().decode(signature);
        Boolean verifyResult = verifySHA256RSA(bs,verifyData,strPubKey);
        System.out.println("s1"+result);
        System.out.println("outputV"+outPutV);

        System.out.println("verifyResult"+verifyResult);
        System.out.println(result.equals(outPut));
        String resultV = result.substring(result.length()-1,result.length());
        System.out.println("verifyV"+resultV);
        if(resultV.equals(outPutV)&&verifyResult) {
            flag = true;
        }
        return flag;
    }

    //利用数字签名进行解密
    static boolean verifySHA256RSA(byte[] sig, String document, String strPubKey) throws Exception {
        return verifySHA256RSAWithByteArrayInput(sig, document.getBytes(), strPubKey);
    }

    static boolean verifySHA256RSAWithByteArrayInput(byte[] sig, byte[] document, String strPubKey) throws Exception {
        String realPub = strPubKey.replaceAll("-----END PUBLIC KEY-----", "")
                .replaceAll("-----BEGIN PUBLIC KEY-----", "")
                .replaceAll("\n", "");
        //strPk is PKCS#8 format PEM public key
        byte[] b2 = Base64.getDecoder().decode(realPub);
        X509EncodedKeySpec spec2 = new X509EncodedKeySpec(b2);
        System.out.println("ssss2222222"+spec2);
        PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(spec2);
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);
        signature.update(document);
        if(signature.verify(sig)){
            return true;
        }else{
            return false;
        }
    }
}
