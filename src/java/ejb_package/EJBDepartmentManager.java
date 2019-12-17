/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb_package;

import entitiesJPA.Department;
import exceptions.getCollectionException;
import interfaces.DepartmentManagerLocal;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Yeray
 */
@Stateless
public class EJBDepartmentManager implements DepartmentManagerLocal {

    private static final Logger LOGGER = Logger.getLogger(EJBUser.class.getPackage() + "." + EJBDepartmentManager.class.getName());

    @PersistenceContext(unitName = "grupo5_ServerPU")
    private EntityManager em;
    /**
     * 
     * @param depart 
     */
    public void create(Department depart) {
        em.persist(depart);
    }
    /**
     * 
     * @param depart 
     */
    public void edit(Department depart) {
        em.merge(depart);
    }
    /**
     * 
     * @param depart 
     */
    public void remove(Long id ) {
        Query query = em.createQuery("DELETE FROM Department a WHERE a.id=:id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
    /**
     * 
     * @param depart
     * @return 
     */
    public Department find(Long id) {
        return em.find(Department.class, id);
    }
    /**
     * 
     * @return 
     * @throws exceptions.getCollectionException 
     */
    @Override
    public List<Department> FindAll() throws getCollectionException {
        return em.createNamedQuery("FindAllDepartment").getResultList();
    }
    
    public Department FindDepartmentByName(String name){
     
        Query query = em.createNamedQuery("FindDepartmentByName");
        query.setParameter("name", name);
        return (Department) query.getSingleResult();
    }

}
