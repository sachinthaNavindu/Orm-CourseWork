package edu.ijse.therapycenter.bo.custom;

import edu.ijse.therapycenter.bo.SuperBO;
import edu.ijse.therapycenter.dto.UserDTO;
import edu.ijse.therapycenter.entity.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserBO extends SuperBO {

    boolean save(UserDTO user);
    boolean update(UserDTO user);
    boolean deleteByPK(String pk) throws Exception;
    List<UserDTO> getAll();
    Optional<UserDTO> findByPK(String pk);
    Optional<String> getLastPK();
    boolean exist(String id) throws SQLException, ClassNotFoundException;

    boolean cheackUser(String userName);
    UserDTO cheackPassword(String userName);
}
