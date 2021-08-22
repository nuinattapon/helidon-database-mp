package me.nui.mp.database;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("temps")
public class TempResource {

    @PersistenceContext(unitName = "test")
    private EntityManager entityManager;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Temp> get() {
        return entityManager.createNamedQuery("getTemps", Temp.class).getResultList();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Temp getById(@PathParam("id") String id) {
        Temp entity = entityManager.find(Temp.class, id);
        if (entity == null) {
            throw new NotFoundException("Unable to find with ID " + id);
        }
        return entity;
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional(Transactional.TxType.REQUIRED)
    public Response deleteById(@PathParam("id") String id) {
        Temp entity = getById(id);

        try {
            entityManager.remove(entity);
            return Response.ok(entity).build();
        } catch (Exception e) {
            throw new BadRequestException("Unable to delete with ID " + entity.getId());
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional(Transactional.TxType.REQUIRED)
    public Response update(Temp temp) {
        System.out.println("Update entity to " + temp);

        if (temp.getId().isEmpty()) {
            throw new BadRequestException("ID not provided - " + temp.getId());
        }
        Temp entity = entityManager.find(Temp.class, temp.getId());
        if (entity == null) {
            System.out.println("ID not found");
            throw new BadRequestException("ID not found - " + temp.getId());
        }
        try {
//            Query query = entityManager.createNamedQuery("updateTempName");
//            query.setParameter("name", entity.getName())
//                    .setParameter("id", entity.getId())
//                    .executeUpdate();
            entity.setName(temp.getName());
            entityManager.merge(entity);
        } catch (Exception e) {
            System.out.println("Unable to update with ID");
            e.printStackTrace();
            throw new BadRequestException("Unable to update with ID " + entity.getId());
        }
        return Response.ok(entity).build();

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional(Transactional.TxType.REQUIRED)
    public Response create(Temp entity) {
        TypedQuery<Temp> query = entityManager.createNamedQuery("getTempByName", Temp.class);
        List<Temp> list = query.setParameter("name", entity.getName()).getResultList();
        if (!list.isEmpty()) {
            throw new BadRequestException("Duplicated name - " + entity.getName());
        }
        try {
            entityManager.persist(entity);
        } catch (Exception e) {
            throw new BadRequestException("Unable to create with ID " + entity.getId());
        }
        return Response.ok(entity).build();
    }

    @GET
    @Path("name/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Temp getByName(@PathParam("name") String name) {
        TypedQuery<Temp> query = entityManager.createNamedQuery("getTempByName", Temp.class);
        List<Temp> list = query.setParameter("name", name).getResultList();
        if (list.isEmpty()) {
            throw new NotFoundException("Unable to find name " + name);
        }
        return list.get(0);
    }

}
