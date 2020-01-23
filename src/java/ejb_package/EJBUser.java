/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb_package;

import entitiesJPA.User;
import entitiesJPA.UserStatus;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.DisabledUserException;
import exceptions.GetCollectionException;
import java.time.LocalDate;
import exceptions.LoginException;
import exceptions.LoginPasswordException;
import exceptions.RecoverPasswordException;
import exceptions.SelectException;
import exceptions.UpdateException;
import utils.MailSender;
import interfaces.EJBUserInterface;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import static java.time.temporal.TemporalQueries.localDate;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Random;
import java.util.ResourceBundle;
import javax.persistence.EntityManager;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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

    public User findUserById(int id) throws SelectException {
        User ret = null;
        try {
            ret = (User) em.createNamedQuery("findById").setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            throw new SelectException();
        }
        return ret;
    }

    @Override
    public User login(User user) throws LoginException, LoginPasswordException, DisabledUserException {
        User ret = new User();
        //EncryptionClass hash = new EncryptionClass();
        try {
            ret = (User) em.createNamedQuery("findByLogin").setParameter("login", user.getLogin()).getSingleResult();
        } catch (NoResultException e) {
            throw new LoginException();
        }
        UserStatus x = ret.getStatus();
        if (x == UserStatus.ENABLED) {
            ret = (User) em.createNamedQuery("findByLoginAndPassword").setParameter("login", user.getLogin()).setParameter("password", user.getPassword()).getSingleResult();
            if (ret == null) {
                throw new LoginPasswordException();
            }
        } else {
            throw new DisabledUserException();
        }
        Query q1 = em.createQuery("update User a set a.lastAccess=:dateNow where a.id=:user_id");
        LocalDateTime localDate = LocalDateTime.now();
        Date date = Date.from( localDate.atZone( ZoneId.systemDefault()).toInstant());
        
        q1.setParameter("dateNow",date);
        q1.setParameter("user_id", ret.getId());
        q1.executeUpdate();
        //String passwordHashDB = hash.hashingText(user.getPassword());
        //user.setPassword(passwordHashDB);
        return ret;
    }

    @Override
    public Set<User> getUserList() throws GetCollectionException {
        List<User> listUser = null;
        try {
            listUser = em.createNamedQuery("findAllUsers").getResultList();
        } catch (Exception ex) {
            throw new GetCollectionException(ex.getMessage());
        }
        Set<User> ret = new HashSet<User>(listUser);
        return ret;
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
        try {
            Query q1 = em.createNamedQuery("DeleteUser").setParameter("id", user.getId());
            q1.executeUpdate();
            em.flush();
        } catch (Exception ex) {
            throw new DeleteException(ex.getMessage());
        }
    }

    @Override
    public void createUser(User user) throws CreateException {
        EncryptionClass hash = new EncryptionClass();
        String hashPassword = user.getPassword();
        hashPassword = hash.hashingText(hashPassword);
        user.setPassword(hashPassword);
        em.persist(user);
    }

    public void disabledUserByCompany(int company_id) throws UpdateException {
        try {
            Query q1 = em.createQuery("update User a set a.status='DISABLED',a.company.id=NULL where a.company.id=:company_id");
            q1.setParameter("company_id", company_id);
            q1.executeUpdate();
        } catch (Exception ex) {
            throw new UpdateException(ex.getMessage());
        }
    }

}
