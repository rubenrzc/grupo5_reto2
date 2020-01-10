/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entitiesJPA.Company;
import entitiesJPA.User;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.GetCollectionException;
import exceptions.SelectException;
import exceptions.UpdateException;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import interfaces.EJBCompanyInterface;
import java.util.Collection;
import java.util.logging.Level;

/**
 *
 * @author Ruben
 */
@Path("company")
public class CompanyFacadeREST {

    @EJB(beanName = "EJBCompany")
    private EJBCompanyInterface ejb;

    @POST
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Company company) {
        try {
            ejb.createCompany(company);
        } catch (CreateException ex) {
            Logger.getLogger(CompanyFacadeREST.class.getName()).severe(ex.getMessage());
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(Company company) {
        try {
            ejb.updateCompany(company);
        } catch (UpdateException ex) {
            Logger.getLogger(CompanyFacadeREST.class.getName()).severe(ex.getMessage());
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        try {
            ejb.deleteCompany(id);
        } catch (DeleteException ex) {
            Logger.getLogger(CompanyFacadeREST.class.getName()).severe(ex.getMessage());
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Company find(@PathParam("id") Integer id) {
        Company company = null;
        try {
            company = ejb.getCompanyProfile(id);
        } catch (SelectException ex) {
            Logger.getLogger(CompanyFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return company;
    }

    @GET
    @Produces({MediaType.APPLICATION_XML})
    public Collection<Company> findAll() {
        Collection<Company> companies = null;
        try {
            companies = ejb.getCompanyList();
        } catch (GetCollectionException ex) {
            Logger.getLogger(CompanyFacadeREST.class.getName()).severe(ex.getMessage());
            throw new InternalServerErrorException(ex.getMessage());
        }
        return companies;
    }


    
}
