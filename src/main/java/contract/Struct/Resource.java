package contract.Struct;

import java.util.Objects;

//定义资源的结构
public class Resource {
    String resource_name; //资源名称
    int resource_level; //资源的权限
    String resource_version; //资源的版本

    public Resource() {
    }

    public Resource(String resource_name, int resource_level, String resource_version) {
        this.resource_name = resource_name;
        this.resource_level = resource_level;
        this.resource_version = resource_version;
    }

    public String getResource_name() {
        return resource_name;
    }

    public void setResource_name(String resource_name) {
        this.resource_name = resource_name;
    }

    public int getResource_level() {
        return resource_level;
    }

    public void setResource_level(int resource_level) {
        this.resource_level = resource_level;
    }

    public String getResource_version() {
        return resource_version;
    }

    public void setResource_version(String resource_version) {
        this.resource_version = resource_version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resource resource = (Resource) o;
        return resource_level == resource.resource_level &&
                Objects.equals(resource_name, resource.resource_name) &&
                Objects.equals(resource_version, resource.resource_version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resource_name, resource_level, resource_version);
    }

    @Override
    public String toString() {
        return resource_name+";"+resource_level+";"+resource_version;
    }

}
