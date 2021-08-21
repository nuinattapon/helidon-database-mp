
package me.nui.mp.database;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * This class implements a REST endpoint to retrieve Pokemon types.
 *
 * <ul>
 * <li>GET /type: Retrieve list of all pokemon types</li>
 * </ul>
 *
 * Pokémon, and Pokémon character names are trademarks of Nintendo.
 */
@Path("products")
public class ProductResource {

    @PersistenceContext(unitName = "test")
    private EntityManager entityManager;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> getProducts() {
        return entityManager.createNamedQuery("getProducts", Product.class).getResultList();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Product getProductById(@PathParam("id") String id) {
        Product product = entityManager.find(Product.class, Integer.valueOf(id));
        if (product == null) {
            throw new NotFoundException("Unable to find pokemon with ID " + id);
        }
        return product;
    }
}
