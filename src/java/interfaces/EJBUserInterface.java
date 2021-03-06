/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entitiesJPA.User;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.GetCollectionException;
import exceptions.LoginException;
import exceptions.LoginPasswordException;
import exceptions.RecoverPasswordException;
import exceptions.UpdateException;
import java.util.Collection;
import javax.ejb.Local;

/**
 *
 * @author 2dam
 */
@Local
public interface EJBUserInterface {
   
    public void createUser(User user) throws CreateException;
    
    public void updateUser(User user) throws UpdateException;
    
    public User login(User user) throws LoginException,LoginPasswordException;
    
    public Collection<User> getUserList() throws GetCollectionException;
    
    public void recoverPassword(User user) throws RecoverPasswordException;
    
    public void deleteUser(User user) throws DeleteException;

    public User findUserById(int id);
    
}
