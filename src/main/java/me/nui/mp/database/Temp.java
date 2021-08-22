package me.nui.mp.database;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;


@Entity
@Table(name = "temp", uniqueConstraints = {@UniqueConstraint(name = "name_unique", columnNames = {"name"})})
@NamedQueries({
        @NamedQuery(name = "getTemps",
                query = "SELECT t FROM Temp t ORDER BY t.name"),
        @NamedQuery(name = "getTempById",
                query = "SELECT t FROM Temp t WHERE t.id = :id"),
        @NamedQuery(name = "getTempByName",
                query = "SELECT t FROM Temp t WHERE t.name = :name"),
        @NamedQuery(name = "updateTempName",
                query = "UPDATE Temp SET name = :name WHERE id = :id")
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

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }


    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = OffsetDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = OffsetDateTime.now();
    }

    @Override
    public String toString() {
        return "Temp{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
