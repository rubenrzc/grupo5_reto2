/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import exceptions.updateException;
import entitiesJPA.User;
import exceptions.createException;
import exceptions.deleteException;
import exceptions.getCollectionException;
import exceptions.loginException;
import exceptions.recoverPasswordException;
import java.util.Collection;

/**
 *
 * @author 2dam
 */
public interface EJBUsers {
    
    public void createUser(User user) throws createException;
    
    public void updateUser(User user) throws updateException;
    
    public User login(User user) throws loginException;
    
    public Collection<User> getUserList() throws getCollectionException;
    
    public Boolean recoverPassword(User user) throws recoverPasswordException;
    
    public void deleteUser(User user) throws deleteException;
    
}
