/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitiesJPA;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.persistence.NamedQuery;

/**
 *
 * @author Yeray
 */

@NamedQuery(
        name = "FindAllDepartment",
        query = "SELECT a FROM Department a ")
        
@Entity
@Table(name = "department", schema = "grupo5_database")
@XmlRootElement
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * Department name
     */
    @Size(min = 1, max = 40)
    @Column(nullable=false,unique=true)
    private String name;

    /**
     * Companies collection
     */
    @ManyToMany
    @JoinTable(name = "depart_comp", schema = "grupo5_database")
    private Collection<Company> companies;

    /**
     * Areas collection
     */
    @ManyToMany
    @JoinTable(name = "depart_area", schema = "grupo5_database")
    private Collection<Area> areas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the companies
     */
    @XmlTransient
    public Collection<Company> getCompanies() {
        return companies;
    }

    /**
     * @param companies the companies to set
     */
    public void setCompanies(Collection<Company> companies) {
        this.companies = companies;
    }

    /**
     * @return the areas
     */
    @XmlTransient
    public Collection<Area> getAreas() {
        return areas;
    }

    /**
     * @param areas the areas to set
     */
    public void setAreas(Collection<Area> areas) {
        this.areas = areas;
    }
    /**
     * 
     * @return 
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (Long) id;
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
        if (!(object instanceof Department)) {
            return false;
        }
        Department other = (Department) object;
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
        return "entitiesJPA.NewEntity[ id=" + id + " ]";
    }

}
