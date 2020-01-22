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
import exceptions.SelectException;
import exceptions.UpdateException;
import interfaces.EJBDocumentInterface;
import interfaces.EJBUserInterface;
import java.util.Set;
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
 * @author Fran
 */
@Path("user")
public class UserFacadeREST {

    @EJB
    private EJBUserInterface ejb;
    
    @EJB
    private EJBDocumentInterface ejbDoc;
    
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public User find(@PathParam("id") int id) throws InternalServerErrorException{   
        User ret = null;
        try {
            ret = ejb.findUserById(id);
        } catch (SelectException ex) {
            throw new InternalServerErrorException("No hay usuario con esa id en la base de datos.");
        }
        return ret;
    }
    
    /**
     * 
     * @param login
     * @param password
     * @return 
     */
    @GET
    @Path("{login}/{password}") //PARA LOGIN
    @Produces({MediaType.APPLICATION_XML})
    public User login(@PathParam("login") String login,@PathParam("password") String password) throws InternalServerErrorException{
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
    @Produces({MediaType.APPLICATION_XML})
    public Set<User> findAll() {
        Set<User> collection=null;
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
    @Consumes({MediaType.APPLICATION_XML})
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
    @Consumes({MediaType.APPLICATION_XML})
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
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(User user) {
        try {
            ejb.updateUser(user);
        } catch (UpdateException ex) {
            Logger.getLogger(UserFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id")Integer id) throws InternalServerErrorException{
        User user = new User();
        user.setId(id);
        try {
            ejbDoc.updateDocumentByUser(id);
            ejb.deleteUser(user);
        } catch (DeleteException ex) {
            Logger.getLogger(UserFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            throw new InternalServerErrorException(ex.getMessage());
        } catch (UpdateException ex) {
            Logger.getLogger(UserFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            throw new InternalServerErrorException(ex.getMessage());
        }
    }
    
     
    
}
