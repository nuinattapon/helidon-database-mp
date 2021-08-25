
package me.nui.mp.database;

import javax.persistence.*;
import java.util.List;

/**
 * A Pokemon Type entity. A type is represented by an ID and a name.
 *
 * Pokémon, and Pokémon character names are trademarks of Nintendo.
 */
@Entity
@Table(name = "pokemontype", uniqueConstraints = {@UniqueConstraint(name="name_unique",columnNames = {"name"})})
@Access(AccessType.FIELD)
@NamedQueries({
        @NamedQuery(name = "getPokemonTypes",
                    query = "SELECT t FROM PokemonType t ORDER BY t.id"),
        @NamedQuery(name = "getPokemonTypeById",
                    query = "SELECT t FROM PokemonType t WHERE t.id = :id")
})
public class PokemonType {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private int id;

    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "pokemonType", orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Pokemon> pokemons;

    public PokemonType() {
    }

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

    public List<Pokemon> getPokemons() {
        return pokemons;
    }

    public void setPokemons(List<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }
}
