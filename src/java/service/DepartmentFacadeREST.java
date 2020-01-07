/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entitiesJPA.Department;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.GetCollectionException;
import exceptions.UpdateException;
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
import interfaces.EJBDepartmentInterface;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 2dam
 */
@Path("entitiesjpa.department")
public class DepartmentFacadeREST {

    @EJB(beanName="EJBDepartment")
    private EJBDepartmentInterface ejb;

    @POST
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Department entity) {
        try {
            ejb.createDepartment(entity);
        } catch (CreateException ex) {
            Logger.getLogger(DepartmentFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Integer id, Department entity) {
        try {
            ejb.updateDepartment(entity);
        } catch (UpdateException ex) {
            Logger.getLogger(DepartmentFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Department depart) {
        try {
            ejb.deleteDepartment(depart);
        } catch (DeleteException ex) {
            Logger.getLogger(DepartmentFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_XML})
    public Collection<Department> FindAllDepartment() {
        Collection<Department> ret = null;
        try {
            ret = ejb.getDepartmentList();
        } catch (GetCollectionException ex) {
            Logger.getLogger(DepartmentFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }


}
