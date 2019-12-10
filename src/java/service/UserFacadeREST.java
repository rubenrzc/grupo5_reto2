/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entitiesJPA.User;
import java.util.List;
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
@Stateless
@Path("entitiesjpa.user")
public class UserFacadeREST extends AbstractFacade<User> {

    @PersistenceContext(unitName = "grupo5_ServerPU")
    private EntityManager em;

    public UserFacadeREST() {
        super(User.class);
    }
    
    @GET
    @Path("{id}") //PARA LOGIN
    @Produces({MediaType.APPLICATION_XML})
    public User find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override  //PARA LISTA
    @Produces({MediaType.APPLICATION_XML})
    public List<User> findAll() {
        return super.findAll();
    }

    /**
     *
     * @param email
     * @return
     */
    @GET
    @Path("{email}") //buscar contraseña por email apra enviar por correo
    @Produces(MediaType.TEXT_PLAIN)
    @Override
    public String recoverPassword(@PathParam("email") String email) {
        return super.recoverPassword(email);
    }
    
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(User entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Integer id, User entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
