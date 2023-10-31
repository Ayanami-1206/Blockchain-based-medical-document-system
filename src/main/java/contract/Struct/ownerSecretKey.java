package contract.Struct;

public class ownerSecretKey {
    //定义一次性密钥ID
    String pubKey;
    String priKey;

    public ownerSecretKey(String pubKey, String priKey) {
        this.pubKey = pubKey;
        this.priKey = priKey;
    }

    public String getPubKey() {
        return pubKey;
    }

    public void setPubKey(String pubKey) {
        this.pubKey = pubKey;
    }

    public String getPriKey() {
        return priKey;
    }

    public void setPriKey(String priKey) {
        this.priKey = priKey;
    }

    @Override
    public String toString() {
        return pubKey +";" + priKey;
    }
}
