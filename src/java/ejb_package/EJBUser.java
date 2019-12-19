/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb_package;

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
import javax.persistence.EntityManager;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author 2dam
 */
@Stateless
public class EJBUser implements EJBUserInterface{
    
    @PersistenceContext(unitName = "grupo5_ServerPU")
    private EntityManager em;

    @Override
    public void updateUser(User user) throws UpdateException {
        em.merge(user);
    }
    
    public User findUserById(int id){
        return (User) em.createNamedQuery("findById").setParameter("id", id).getSingleResult();
    }

    @Override
    public User login(User user) throws LoginException,LoginPasswordException {
        User ret = new User();
        try {
            ret = (User) em.createNamedQuery("findByLogin").setParameter("login", user.getLogin()).getSingleResult();
        } catch (NoResultException e) {
            throw new LoginException(); 
        }
        ret = (User) em.createNamedQuery("findByLoginAndPassword").setParameter("login", user.getLogin()).setParameter("password", user.getPassword()).getSingleResult();
        if(ret==null){
            throw new LoginPasswordException();
        }
        return ret;
    }

    @Override
    public Collection<User> getUserList() throws GetCollectionException {
        try {
            return em.createNamedQuery("findAllUsers").getResultList();
        } catch (Exception ex) {
            throw new GetCollectionException(ex.getMessage());
        }
    }

    @Override
    public void recoverPassword(User user) throws RecoverPasswordException {
        try {
            String password = (String) em.createNamedQuery("recoverPassword").setParameter("email", user.getEmail()).getSingleResult();
            //ENVIAR CORREO
        } catch (NoResultException e) {
            throw new RecoverPasswordException(); 
        }
    }

    @Override
    public void deleteUser(User user) throws DeleteException {
        em.remove(em.merge(user));
    }

    @Override
    public void createUser(User user) throws CreateException {
        em.persist(user);
    }
    
}
