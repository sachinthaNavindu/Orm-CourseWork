package edu.ijse.therapycenter.dao.custom;

import edu.ijse.therapycenter.dao.CrudDAO;
import edu.ijse.therapycenter.entity.Patient;
import edu.ijse.therapycenter.entity.User;

public interface UserDAO extends CrudDAO<User,String> {
    boolean cheackUser(String email);
    User getSelectUser(String userName);
}
