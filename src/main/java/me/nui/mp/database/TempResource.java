package me.nui.mp.database;

import org.eclipse.microprofile.openapi.annotations.Operation;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Path("temps")
public class TempResource {

    @PersistenceContext(unitName = "test")
    private EntityManager entityManager;

    @GET
    @Path("/now")
    @Produces(MediaType.TEXT_PLAIN)
    public String getCurrentDateTime() {

//        return DateTimeFormatter.RFC_1123_DATE_TIME.format(OffsetDateTime.now());
        return DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(OffsetDateTime.now());

    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Finds all Temp",
            description = "Find all Temp")
    public List<Temp> get() {
        return entityManager.createNamedQuery("getTemps", Temp.class).getResultList();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Finds Temp by id",
            description = "Find a Temp by a given id")
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
        } catch (Exception e) {
            throw new BadRequestException("Unable to delete with ID " + entity.getId());
//            return Response.status(Response.Status.BAD_REQUEST).entity(entity).build();
        }
        return Response.ok(entity).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional(Transactional.TxType.REQUIRED)
    public Response update(Temp temp) {
        //VALIDATE if id is provided
        if (temp.getId() == null || temp.getId().isEmpty()) {
            throw new BadRequestException("ID not provided - " + temp.getId());
        }

        // CHECK if the id exists in the database
        Temp entity = entityManager.find(Temp.class, temp.getId());
        if (entity == null) {
            System.out.println("ID not found");
            throw new BadRequestException("ID not found - " + temp.getId());
        }

        //UPDATE the database
        try {
            entity.setName(temp.getName());
            entityManager.merge(entity);
        } catch (Exception e) {
            System.out.println("Unable to update with ID");
            e.printStackTrace();
            throw new BadRequestException("Unable to update with ID " + entity.getId());
        }
        // RETURN ok response with the deleted entity
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
