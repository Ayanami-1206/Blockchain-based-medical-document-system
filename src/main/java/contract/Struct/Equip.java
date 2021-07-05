package contract.Struct;
//定义设备的结构
public class Equip {
    String  equip_id; //设备的ID
    String equip_name; //设备的名称
    String  mac_address; //设备的物理地址

    public Equip() {
    }

    public Equip(String equip_id, String equip_name, String mac_address) {
        this.equip_id = equip_id;
        this.equip_name = equip_name;
        this.mac_address = mac_address;
    }

    public String getEquip_id() {
        return equip_id;
    }

    public void setEquip_id(String equip_id) {
        this.equip_id = equip_id;
    }

    public String getEquip_name() {
        return equip_name;
    }

    public void setEquip_name(String equip_name) {
        this.equip_name = equip_name;
    }

    public String getMac_address() {
        return mac_address;
    }

    public void setMac_address(String mac_address) {
        this.mac_address = mac_address;
    }

    @Override
    public String toString() {
        return equip_id+";" +equip_name +";" +mac_address;
    }
}
