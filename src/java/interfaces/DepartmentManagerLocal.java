/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entitiesJPA.Department;
import exceptions.getCollectionException;
import java.util.List;
import javax.ejb.Local;


/**
 *
 * @author Yeray
 */
@Local
public interface DepartmentManagerLocal {

    public void create(Department depart);

    public void edit(Department depart);

    public void remove(Long id);

    public Department find(Long id);

    public List<Department> FindAll() throws getCollectionException;
    
    public Department FindDepartmentByName(String name);
}
