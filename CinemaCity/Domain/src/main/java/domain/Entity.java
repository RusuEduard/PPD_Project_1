package domain;

import java.io.Serializable;
import java.sql.Timestamp;

public class Entity<ID> implements Serializable {
    public ID id;
    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "id=" + id +
                '}';
    }
}
