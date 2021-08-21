
package me.nui.mp.database;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

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
        Product product = entityManager.find(Product.class, Long.valueOf(id));
        if (product == null) {
            throw new NotFoundException("Unable to find product with ID " + id);
        }
        return product;
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional(Transactional.TxType.REQUIRED)
    public void deleteProduct(@PathParam("id") String id) {
        Product product = getProductById(id);
        entityManager.remove(product);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional(Transactional.TxType.REQUIRED)
    public void createProduct(Product product) {
        TypedQuery<Product> query = entityManager.createNamedQuery("getProductByName", Product.class);
        List<Product> list = query.setParameter("name", product.getName()).getResultList();
        if (!list.isEmpty()) {
            throw new BadRequestException("Duplicated product name - " + product.getName());
        }
        try {
            entityManager.persist(product);
        } catch (Exception e) {
            throw new BadRequestException("Unable to create product with ID " + product.getId());
        }
    }

    @GET
    @Path("name/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Product getPokemonByName(@PathParam("name") String name) {
        TypedQuery<Product> query = entityManager.createNamedQuery("getProductByName", Product.class);
        List<Product> list = query.setParameter("name", name).getResultList();
        if (list.isEmpty()) {
            throw new NotFoundException("Unable to find product with name " + name);
        }
        return list.get(0);
    }

}
