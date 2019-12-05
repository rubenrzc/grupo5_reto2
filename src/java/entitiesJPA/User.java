/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitiesJPA;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Email;

/**
 *
 * @author Francisco Romero Alonso
 */
@Entity
@Table(name = "user", schema = "grupo5_database")
@XmlRootElement
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Size(min = 1, max = 40)
    @NotNull
    @Column(unique = true)
    private String login;

    @Email
    @NotNull
    @Column(unique = true)
    private String email;

    @Size(min = 1, max = 40)
    @NotNull
    private String fullname;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserPrivilege privilege;

    @Size(min = 8, max = 40)
    @NotNull
    private String password;
    
    @Type(type = "org.hibernate.type.BlobType")
    @Lob
    private byte[] photo;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Past
    private Date lastAccess;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Past
    private Date lastPassWordChange;
    
    @ManyToOne
    private Company company;

    @OneToMany(mappedBy = "user")
    private Collection<Document> documents;

    /**
     * 
     */
    public User() {
    }

    /**
     * 
     * @return 
     */
    public int getId() {
        return id;
    }
    
/**
 * 
 * @param id 
 */
    public void setId(int id) {
        this.id = id;
    }
    
/**
 * 
 * @return 
 */
    public String getLogin() {
        return login;
    }
    
/**
 * 
 * @param login 
 */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * 
     * @return 
     */
    public String getEmail() {
        return email;
    }

    /**
     * 
     * @param email 
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 
     * @return 
     */
    public String getFullname() {
        return fullname;
    }

    /**
     * 
     * @param fullname 
     */
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    /**
     * 
     * @return 
     */
    public Company getCompany() {
        return company;
    }

    /**
     * 
     * @param company 
     */
    public void setCompany(Company company) {
        this.company = company;
    }

    /**
     * 
     * @return 
     */
    @XmlTransient
    public Collection<Document> getDocuments() {
        return documents;
    }

    /**
     * 
     * @param documents 
     */
    public void setDocuments(Collection<Document> documents) {
        this.documents = documents;
    }

    /**
     * 
     * @return 
     */
    public UserStatus getStatus() {
        return status;
    }

    /**
     * 
     * @param status 
     */
    public void setStatus(UserStatus status) {
        this.status = status;
    }

    /**
     * 
     * @return 
     */
    public UserPrivilege getPrivilege() {
        return privilege;
    }

    /**
     * 
     * @param privilege 
     */
    public void setPrivilege(UserPrivilege privilege) {
        this.privilege = privilege;
    }

    /**
     * 
     * @return 
     */
    public String getPassword() {
        return password;
    }

    /**
     * 
     * @param password 
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 
     * @return 
     */
    public Date getLastAccess() {
        return lastAccess;
    }

    /**
     * 
     * @param lastAccess 
     */
    public void setLastAccess(Date lastAccess) {
        this.lastAccess = lastAccess;
    }

    /**
     * 
     * @return 
     */
    public Date getLastPassWordChange() {
        return lastPassWordChange;
    }

    /**
     * 
     * @param lastPassWordChange 
     */
    public void setLastPassWordChange(Date lastPassWordChange) {
        this.lastPassWordChange = lastPassWordChange;
    }

    /**
     * 
     * @return 
     */
    public byte[] getPhoto() {
        return photo;
    }

    /**
     * 
     * @param photo 
     */
    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    /**
     * 
     * @return 
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        return hash;
    }

    /**
     * 
     * @param object
     * @return 
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    /**
     * 
     * @return 
     */
    @Override
    public String toString() {
        return "entitiesJPA.User[ id=" + id + " ]";
    }

}
