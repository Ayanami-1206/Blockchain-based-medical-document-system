package Communication;

import contract.Contracts.User_Contract;

public class test {

    public static void main(String[] args) throws Exception {


     /*   //测试数字签名并打包
        String packData = Package.Pack("contract.Contracts.User_Contract,Test;ss,nn;ss",realPK);
        System.out.println("packData:"+packData);
        //解密
        Boolean result = verify.Verify(true,packData);
        System.out.println("result++++++++++"+result);*/

     //测试文件读取
        User_Contract.PackToFile("ss11a");



    }
}
