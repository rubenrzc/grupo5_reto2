/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb_package;

import entitiesJPA.Company;
import entitiesJPA.User;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.GetCollectionException;
import exceptions.SelectException;
import exceptions.UpdateException;
import interfaces.EJBCompanyInterface;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import static javax.ws.rs.client.Entity.entity;

/**
 *
 * @author Ruben
 */
@Stateless
public class EJBCompany implements EJBCompanyInterface {

    @PersistenceContext(unitName = "grupo5_ServerPU")
    private EntityManager em;

    @Override
    public void createCompany(Company company) throws CreateException {
        try {
            em.persist(company);
        } catch (Exception ex) {
            throw new CreateException(ex.getMessage());
        }
    }

    @Override
    public void updateCompany(Company company) throws UpdateException {

        try {
            em.merge(company);
            em.flush();// actualiza al momento base de datos

        } catch (Exception ex) {
            throw new UpdateException(ex.getMessage());
        }
    }

    @Override
    public void deleteCompany(int id) throws DeleteException {
        try {
            em.remove(em.find(Company.class, id));
        } catch (Exception ex) {
            throw new DeleteException(ex.getMessage());
        }
    }

    @Override
    public List<Company> getCompanyList() throws GetCollectionException {
        try {
            return em.createNamedQuery("findAllCompanies").getResultList();
        } catch (Exception ex) {
            throw new GetCollectionException(ex.getMessage());
        }
    }

    @Override
    public Company getCompanyProfile(int id) throws SelectException {
        try {
            return em.find(Company.class, id);
        } catch (Exception ex) {
            throw new SelectException(ex.getMessage());
        }
    }
}