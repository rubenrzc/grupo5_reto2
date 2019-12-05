/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitiesJPA;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.Past;
import javax.xml.bind.annotation.XmlRootElement;

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

    @ManyToOne
    private Company company;

    @OneToMany(mappedBy = "user")
    private Collection<Document> documents;

    private String login;

    private String email;

    private String fullname;

    private UserStatus status;

    private UserPrivilege privilege;

    private String password;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Past
    private Date lastAccess;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Past
    private Date lastPassWordChange;

    private byte[] photo;

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Collection<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(Collection<Document> documents) {
        this.documents = documents;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public UserPrivilege getPrivilege() {
        return privilege;
    }

    public void setPrivilege(UserPrivilege privilege) {
        this.privilege = privilege;
    }

    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLastAccess() {
        return lastAccess;
    }

    public void setLastAccess(Date lastAccess) {
        this.lastAccess = lastAccess;
    }

    public Date getLastPassWordChange() {
        return lastPassWordChange;
    }

    public void setLastPassWordChange(Date lastPassWordChange) {
        this.lastPassWordChange = lastPassWordChange;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        return hash;
    }

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

    @Override
    public String toString() {
        return "entitiesJPA.User[ id=" + id + " ]";
    }

}
