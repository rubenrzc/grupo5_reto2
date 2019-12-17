/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb_package;

import entitiesJPA.Department;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.GetCollectionException;
import exceptions.UpdateException;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import interfaces.EJBDepartmentInterface;
import java.util.Collection;

/**
 *
 * @author Yeray
 */
@Stateless
public class EJBDepartment implements EJBDepartmentInterface {

  
    @PersistenceContext(unitName = "grupo5_ServerPU")
    private EntityManager em;
    /**
     * 
     * @param depart 
     */
    public void createDepartment(Department depart) {
        em.persist(depart);
    }
    /**
     * 
     * @param depart 
     */
    public void updateDepartment(Department depart) {
        em.merge(depart);
    }
    /**
     * 
     * @param depart 
     */
    public void deleteDepartment(Department depart ) {
        Query query = em.createQuery("DELETE FROM Department a WHERE a.id=:id");
        query.setParameter("id", depart.getId());
        query.executeUpdate();
    }
 
    /**
     * 
     * @return 
     * @throws exceptions.GetCollectionException 
     */
    
    public Collection<Department> getDepartmentList() throws GetCollectionException {
        return em.createNamedQuery("FindAllDepartment").getResultList();
    }
    
    public Department FindDepartmentByName(String name){
     
        Query query = em.createNamedQuery("FindDepartmentByName");
        query.setParameter("name", name);
        return (Department) query.getSingleResult();
    }



}
