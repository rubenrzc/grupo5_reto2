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
import exceptions.SelectException;
import exceptions.UpdateException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import interfaces.EJBDepartmentInterface;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.NoResultException;

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
            em.flush();
        } catch (Exception ex) {
            throw new DeleteException(ex.getMessage());
        }
    }

    /**
     *
     * @return @throws exceptions.GetCollectionException
     */
    public Set<Department> getDepartmentList() throws GetCollectionException {
        List<Department> listDepartment = null;
        try {
            listDepartment = em.createNamedQuery("findAllDepartments").getResultList();
        } catch (Exception ex) {
            throw new GetCollectionException(ex.getMessage());
        }
        Set<Department> ret = new HashSet<Department>(listDepartment);
        return ret;
    }

    @Override
    public Department getDepartmentProfile(int id) throws SelectException {
        try {
            return em.find(Department.class, id);
        } catch (NoResultException ex) {
            throw new SelectException(ex.getMessage());
        }
    }

}
