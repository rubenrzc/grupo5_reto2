/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entitiesJPA.Area;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.GetCollectionException;
import exceptions.UpdateException;
import java.util.Collection;
import javax.ejb.Local;

/**
 *
 * @author Andoni
 */
@Local
public interface EJBAreaInterface {
    
    public void createArea(Area department) throws CreateException;

    public void updateArea(Area department) throws UpdateException;
    
    public void deleteArea(Area department)throws DeleteException;

    public Collection<Area> getAreaList() throws GetCollectionException;
}
