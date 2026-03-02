package model.entities;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Department implements Serializable {
    @Serial
    private static final long serialVersionUID = 8763655018983570959L;
    private Integer depId;
    private String depName;

    public Department() {
    }

    public Department(Integer id, String name) {
        this.depId = id;
        this.depName = name;
    }

    public Integer getId() {
        return depId;
    }

    public void setId(Integer id) {
        this.depId = id;
    }

    public String getName() {
        return depName;
    }

    public void setName(String name) {
        this.depName = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return Objects.equals(depId, that.depId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(depId);
    }

    @Override
    public String toString() {
        return depName;
    }
}
