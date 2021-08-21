package me.nui.mp.database;

import javax.persistence.*;


@Entity(name = "Product")
@Table(name = "product", uniqueConstraints = {@UniqueConstraint(name = "name_unique", columnNames = {"name"})})
@NamedQueries({
        @NamedQuery(name = "getProducts",
                query = "SELECT t FROM Product t"),
        @NamedQuery(name = "getProductTypeById",
                query = "SELECT t FROM Product t WHERE t.id = :id")
})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(length = 36, nullable = false, updatable = false)
    private int id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false)
    private double price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}
