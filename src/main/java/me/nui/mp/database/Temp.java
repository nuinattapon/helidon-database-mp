package me.nui.mp.database;

import javax.persistence.*;
import java.util.UUID;


@Entity(name = "Temp")
@Table(name = "temp", uniqueConstraints = {@UniqueConstraint(name = "name_unique", columnNames = {"name"})})
@NamedQueries({
        @NamedQuery(name = "getTemps",
                query = "SELECT t FROM Temp t ORDER BY t.name"),
        @NamedQuery(name = "getTempById",
                query = "SELECT t FROM Temp t WHERE t.id = :id"),
        @NamedQuery(name = "getTempByName",
                query = "SELECT t FROM Temp t WHERE t.name = :name")
})
@Access(value = AccessType.FIELD)
public class Temp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private long id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false)
    private double price;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
