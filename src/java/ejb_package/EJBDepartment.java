/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb_package;

import entitiesJPA.Company;
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
    public void createDepartment(Department depart) throws CreateException {
        em.persist(depart);
    }

    /**
     *
     * @param depart
     */
    public void updateDepartment(Department depart) throws UpdateException {
        em.merge(depart);
    }

    /**
     *
     * @param depart
     */
    public void deleteDepartment(int id) throws DeleteException {
        try {
            em.remove(em.find(Department.class, id));
        } catch (Exception ex) {
            throw new DeleteException(ex.getMessage());
        }
    }

    /**
     *
     * @return @throws exceptions.GetCollectionException
     */
    public Collection<Department> getDepartmentList() throws GetCollectionException {
        try {
            return em.createNamedQuery("findAllDepartments").getResultList();
        } catch (Exception ex) {
            throw new GetCollectionException(ex.getMessage());

        }
    }

}
