package me.nui.mp.database;

import javax.persistence.*;


@Entity
@Table(name = "product", uniqueConstraints = {@UniqueConstraint(name = "name_unique", columnNames = {"name"})})
@NamedQueries({
        @NamedQuery(name = "getProducts",
                query = "SELECT t FROM Product t ORDER BY t.name"),
        @NamedQuery(name = "getProductById",
                query = "SELECT t FROM Product t WHERE t.id = :id"),
        @NamedQuery(name = "getProductByName",
                query = "SELECT t FROM Product t WHERE t.name = :name")
})
@Access(value = AccessType.FIELD)
public class Product {

    @Id
    @SequenceGenerator(name = "product_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @Column(length = 36, nullable = false, updatable = false)
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
