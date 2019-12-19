/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb_package;

import entitiesJPA.Department;
import entitiesJPA.Document;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.GetCollectionException;
import exceptions.UpdateException;
import java.util.Collection;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import interfaces.EJBDocumentInterface;

/**
 *
 * @author stone
 */
@Stateless
public class EJBDocument implements EJBDocumentInterface {

    @PersistenceContext(unitName = "grupo5_ServerPU")
    private EntityManager em;

    public Collection<Document> getDocumentList() throws GetCollectionException {
        try {
            return em.createNamedQuery("findAllDocuments").getResultList();
        } catch (Exception ex) {
            throw new GetCollectionException(ex.getMessage());
        }
    }
    
    public Document findDocumentById(int id){
        return (Document) em.createNamedQuery("findDocumentById").setParameter("id", id).getSingleResult();
    }

    @Override
    public void createNewDocument(Document document) throws CreateException {
        try {
            em.persist(document);
        } catch (Exception ex) {
            throw new CreateException(ex.getMessage());
        }
    }

    public void updateDocument(Document document) throws UpdateException {
        try {
            em.merge(document);
            em.flush();// actualiza al momento base de datos

        } catch (Exception ex) {
            throw new UpdateException(ex.getMessage());
        }
    }

    public void deleteDocument(Document document) throws DeleteException {
        try {
            em.remove(document.getId());
        } catch (Exception ex) {
            throw new DeleteException(ex.getMessage());
        }
    }


}
