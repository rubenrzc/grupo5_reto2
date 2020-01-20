/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entitiesJPA.User;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.GetCollectionException;
import exceptions.LoginException;
import exceptions.LoginPasswordException;
import exceptions.RecoverPasswordException;
import exceptions.UpdateException;
import interfaces.EJBUserInterface;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
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
@Path("user")
public class UserFacadeREST {

    @EJB(beanName="EJBUser")
    private EJBUserInterface ejb;
    
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public User find(@PathParam("id") int id) {   
        return ejb.findUserById(id);
    }
    
    /**
     * 
     * @param login
     * @param password
     * @return 
     */
    @GET
    @Path("{login}/{password}") //PARA LOGIN
    @Produces({MediaType.APPLICATION_JSON})
    public User login(@PathParam("login") String login,@PathParam("password") String password) {
        User ret = new User();
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        try {
            ret = ejb.login(user);
        } catch (LoginPasswordException ex) {
            throw new InternalServerErrorException("The login does not exist.");
        } catch (LoginException ex) {
            throw new InternalServerErrorException("The password is incorrect.");
        }
        return ret;
    }

    /**
     * 
     * @return 
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Collection<User> findAll() {
        Collection<User> collection=null;
        try {
            collection = ejb.getUserList();
        } catch (GetCollectionException ex) {
            Logger.getLogger(UserFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            throw new InternalServerErrorException(ex.getMessage());
        }
        return collection;
    }

    /**
     * 
     * @param email 
     */
    @PUT
    @Path("{email}") //buscar contraseña por email apra enviar por correo
    @Consumes({MediaType.APPLICATION_JSON})
    public void recoverPassword(User user) {
        try {
            ejb.recoverPassword(user);
        } catch (RecoverPasswordException ex) {
            Logger.getLogger(UserFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * 
     * @param user 
     */
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public void create(User user) {
        try {
            ejb.createUser(user);
        } catch (CreateException ex) {
            Logger.getLogger(UserFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 
     * @param user 
     */
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    public void edit(User user) {
        try {
            ejb.updateUser(user);
        } catch (UpdateException ex) {
            Logger.getLogger(UserFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id")Integer id) {
        User user = new User();
        user.setId(id);
        try {
            ejb.deleteUser(user);
        } catch (DeleteException ex) {
            Logger.getLogger(UserFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            throw new InternalServerErrorException(ex.getMessage());
        }
    }
    
     
    
}
