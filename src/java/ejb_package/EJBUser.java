/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb_package;

import entitiesJPA.User;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.GetCollectionException;
import exceptions.LoginException;
import exceptions.LoginPasswordException;
import exceptions.RecoverPasswordException;
import exceptions.UpdateException;
import utils.MailSender;
import interfaces.EJBUserInterface;
import java.util.Collection;
import java.util.Random;
import java.util.ResourceBundle;
import javax.persistence.EntityManager;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import utils.EncryptionClass;

/**
 *
 * @author Fran
 */
@Stateless
public class EJBUser implements EJBUserInterface {

    @PersistenceContext(unitName = "grupo5_ServerPU")
    private EntityManager em;

    @Override
    public void updateUser(User user) throws UpdateException {
        EncryptionClass hash = new EncryptionClass();
        String passwordHashDB = hash.hashingText(user.getPassword());
        user.setPassword(passwordHashDB);
        em.merge(user);
    }

    public User findUserById(int id) {
        return (User) em.createNamedQuery("findById").setParameter("id", id).getSingleResult();
    }

    @Override
    public User login(User user) throws LoginException, LoginPasswordException {
        User ret = new User();
        EncryptionClass hash = new EncryptionClass();
        try {
            ret = (User) em.createNamedQuery("findByLogin").setParameter("login", user.getLogin()).getSingleResult();
        } catch (NoResultException e) {
            throw new LoginException();
        }
        String passwordHashDB = hash.hashingText(user.getPassword());
        user.setPassword(passwordHashDB);
        
        ret = (User) em.createNamedQuery("findByLoginAndPassword").setParameter("login", user.getLogin()).setParameter("password", user.getPassword()).getSingleResult();
        if (ret == null) {
            throw new LoginPasswordException();
        }
        return ret;
    }

    @Override
    public Collection<User> getUserList() throws GetCollectionException {
        try {
            return em.createNamedQuery("findAllUsers").getResultList();
        } catch (Exception ex) {
            throw new GetCollectionException(ex.getMessage());
        }
    }

    @Override
    public void recoverPassword(User user) throws RecoverPasswordException {
        try {
            EncryptionClass hash = new EncryptionClass();
            String passwordHashDB = (String) em.createNamedQuery("recoverPassword")
                    .setParameter("email", user.getEmail()).getSingleResult();
            //generamos nueva contraseña
            String notEncodedNew = new Random().ints(10, 33, 122).collect(StringBuilder::new,
                    StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();

            passwordHashDB = hash.hashingText(notEncodedNew);
            user.setPassword(passwordHashDB);

            em.merge(user);//actualizamos en la base de datos
            //ENVIAR CORREO
            MailSender emailService = new MailSender(ResourceBundle.getBundle("files.MailSenderConfig").getString("SenderName"),
                    ResourceBundle.getBundle("files.MailSenderConfig").getString("SenderPassword"), null, null);
            try {
                emailService.sendMail(ResourceBundle.getBundle("files.MailSenderConfig").getString("SenderEmail"),
                        user.getEmail(),
                        ResourceBundle.getBundle("files.MailSenderConfig").getString("MessageSubject"),
                        ResourceBundle.getBundle("files.MailSenderConfig").getString("MessageEmail1")
                        + notEncodedNew
                        + ResourceBundle.getBundle("files.MailSenderConfig").getString("MessageEmail2"));
                System.out.println("Ok, mail sent!");
            } catch (MessagingException e) {
                System.out.println("Doh!");
                e.printStackTrace();
            }

        } catch (NoResultException e) {
            throw new RecoverPasswordException();
        }
    }

    @Override
    public void deleteUser(User user) throws DeleteException {
        em.remove(em.merge(user));
    }

    @Override
    public void createUser(User user) throws CreateException {
        EncryptionClass hash = new EncryptionClass();
        String hashPassword = user.getPassword();
        hashPassword = hash.hashingText(hashPassword);
        user.setPassword(hashPassword);
        em.persist(user);
    }

}
