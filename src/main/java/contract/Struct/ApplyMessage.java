package contract.Struct;

import java.io.Serializable;
import java.util.Objects;

public class ApplyMessage implements Serializable{
    String apply_name;//申请人
    String apply_worknumber;//申请人工号
    String old_role;//申请人当前的角色权限
    String new_role;//申请的新角色权限
    String apply_time;//申请时间

    public ApplyMessage(String s, int i, String s1) {
    }

    public ApplyMessage(String apply_name, String apply_worknumber, String old_role, String new_role, String apply_time) {
        this.apply_name = apply_name;
        this.apply_worknumber = apply_worknumber;
        this.old_role = old_role;
        this.new_role = new_role;
        this.apply_time = apply_time;
    }

    public String getApply_name() {
        return apply_name;
    }

    public void setApply_name(String apply_name) {
        this.apply_name = apply_name;
    }

    public String getApply_worknumber() {
        return apply_worknumber;
    }

    public void setApply_worknumber(String apply_worknumber) {
        this.apply_worknumber = apply_worknumber;
    }

    public String getOld_role() {
        return old_role;
    }

    public void setOld_role(String old_role) {
        this.old_role = old_role;
    }

    public String getNew_role() {
        return new_role;
    }

    public void setNew_role(String new_role) {
        this.new_role = new_role;
    }

    public String getApply_time() {
        return apply_time;
    }

    public void setApply_time(String apply_time) {
        this.apply_time = apply_time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplyMessage that = (ApplyMessage) o;
        return Objects.equals(apply_name, that.apply_name) &&
                Objects.equals(apply_worknumber, that.apply_worknumber) &&
                Objects.equals(old_role, that.old_role) &&
                Objects.equals(new_role, that.new_role) &&
                Objects.equals(apply_time, that.apply_time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(apply_name, apply_worknumber, old_role, new_role, apply_time);
    }

    @Override
    public String toString() {
        return apply_name+";" +  apply_worknumber +";"+old_role +";"+ new_role +";"+apply_time;
    }
}
