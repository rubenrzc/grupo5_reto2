/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb_package;

import entitiesJPA.Area;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.GetCollectionException;
import exceptions.UpdateException;
import interfaces.EJBAreaInterface;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * EJB class that make the CRUD of the Area entity
 * @author Andoni
 */
@Stateless
public class EJBArea implements EJBAreaInterface{
    
    @PersistenceContext(unitName = "grupo5_ServerPU")
    private EntityManager em;
    Logger LOGGER;
    
    /**
     * Methot that create a new Area
     * @param area Area entity object type
     * @throws CreateException 
     */
    public void createArea(Area area) throws CreateException { 
        try{
            if(em.find(Area.class, area.getId())==null){
                LOGGER.info("Creating new Area");
                em.persist(area);
            }else{
                LOGGER.info("Area not created due to it exist");
            }
            
        }catch(Exception e){
            LOGGER.log(Level.WARNING, "IMPOSIBLE TO CREATE AREA");
            throw new CreateException(e.getMessage());
        }   
    }
    /**
     * Method that make the update of the area entity
     * @param area Area entity object type
     * @throws UpdateException 
     */
    public void updateArea(Area area) throws UpdateException{
        try{
            if(em.find(Area.class, area.getId())==null){
                LOGGER.info("Updating Area");
                em.merge(area);
                em.flush(); 
            }
        }catch(Exception e) {
            LOGGER.log(Level.WARNING, "IMPOSIBLE TO UPDATE AREA");
            throw new UpdateException(e.getMessage());
        }
    }
    /**
     * Methot that delete a specific Area
     * by the id
     * @param id int that contain the area id to delete
     * @throws DeleteException 
     */
    public void deleteArea(int id) throws DeleteException {
        try{
            LOGGER.info("Deleting Area");
            em.remove(id);
        }catch(Exception e){
            LOGGER.log(Level.WARNING, "IMPOSIBLE TO DELETE AREA");
            throw new DeleteException(e.getMessage());
        }
        
    }
    /**
     * Methot that return a area list collection
     * @return a area list collection
     * @throws GetCollectionException 
     */
    public Collection<Area> getAreaList() throws GetCollectionException {
        //try{
            //LOGGER.info("Geting area list");
            return em.createNamedQuery("FindAllAreas").getResultList();
       // }catch(Exception e){
           // LOGGER.log(Level.WARNING, "IMPOSIBLE GET AREA LIST");
            //throw new GetCollectionException(e.getMessage());
        //}       
    }  
}
