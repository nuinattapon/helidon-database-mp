package me.nui.mp.database;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


@Entity
@Table(name = "temp", uniqueConstraints = {@UniqueConstraint(name = "name_unique", columnNames = {"name"})})
@NamedQueries({
        @NamedQuery(name = "getTemps",
                query = "SELECT t FROM Temp t ORDER BY t.name"),
        @NamedQuery(name = "getTempById",
                query = "SELECT t FROM Temp t WHERE t.id = :id"),
        @NamedQuery(name = "getTempByName",
                query = "SELECT t FROM Temp t WHERE t.name = :name")
})
public class Temp {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false)
    private String id;
    @Column(nullable = false, length = 120)
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "Temp{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
