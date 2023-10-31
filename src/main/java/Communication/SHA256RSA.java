package Communication;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;


public class SHA256RSA {
    public static void main(String[] args) throws Exception {
        String input = "sdlyyxy";
        String filename = "private.pem";
        byte[] tmpBytes=Files.readAllBytes(Paths.get(filename));
        // Not a real private key! Replace with your private key!
        String strPk = new String(tmpBytes);
         

    }
 

// Create base64 encoded signature using SHA256/RSA.
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

    public static boolean verifySHA256RSA(byte[] sig, String document, String strPubKey) throws Exception {
        return verifySHA256RSAWithByteArrayInput(sig, document.getBytes(), strPubKey);
    }

    public static boolean verifySHA256RSAWithByteArrayInput(byte[] sig, byte[] document, String strPubKey) throws Exception {
        String realPub = strPubKey.replaceAll("-----END PUBLIC KEY-----", "")
                                .replaceAll("-----BEGIN PUBLIC KEY-----", "")
                                .replaceAll("\n", "");
        //strPk is PKCS#8 format PEM public key   
        byte[] b2 = Base64.getDecoder().decode(realPub);
        X509EncodedKeySpec spec2 = new X509EncodedKeySpec(b2);                                              
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

