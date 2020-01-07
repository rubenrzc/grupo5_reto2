/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entitiesJPA.Area;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.GetCollectionException;
import exceptions.UpdateException;
import interfaces.EJBAreaInterface;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
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
 * @author Andoni
 */
@Path("entitiesjpa.area")
public class AreaFacadeREST {

    @EJB(beanName="EJBArea")
    private EJBAreaInterface ejb;

    @POST
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Area entity) {
        try {
            ejb.createArea(entity);
        } catch (CreateException ex) {
            Logger.getLogger(DepartmentFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Integer id, Area entity) {
        try {
            ejb.updateArea(entity);
        } catch (UpdateException ex) {
            Logger.getLogger(DepartmentFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Area area) {
        try {
            ejb.deleteArea(area);
        } catch (DeleteException ex) {
            Logger.getLogger(DepartmentFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_XML})
    public Collection<Area> FindAllArea() {
        Collection<Area> ret = null;
        try {
            ret = ejb.getAreaList();
        } catch (GetCollectionException ex) {
            Logger.getLogger(DepartmentFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }
}
