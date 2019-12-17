/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;
import entitiesJPA.Department;
import exceptions.getCollectionException;
import interfaces.DepartmentManagerLocal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author 2dam
 */
@Path("entitiesjpa.department")
public class DepartmentFacadeREST {

    @EJB
    private DepartmentManagerLocal ejb;

    @POST
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Department entity) {
        ejb.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Integer id, Department entity) {
        ejb.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        ejb.remove(ejb.find(id).getId());
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Department find(@PathParam("id") Long id) {
        return ejb.find(id);
    }

    @GET
    @Produces({MediaType.APPLICATION_XML})
    public List<Department> FindAllDepartment() {
        List<Department> ret = null;
        try {
            ret = ejb.FindAll();
        } catch (getCollectionException ex) {
            LOGGER.severe(ex.getMessage());
        }
        return ret;
    }
    @GET
    @Path("name/{name}")
    @Produces({MediaType.APPLICATION_XML})
    public Department FindDepartmentByName(@PathParam("name") String name){
        Department depart= null;
        depart= ejb.FindDepartmentByName(name);
        return depart;
    }

}
