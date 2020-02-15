package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.UserDAO;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.SignUpRestrictedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserDAO userDAO;

    @Transactional
    public UserEntity signupUser(UserEntity userEntity) throws SignUpRestrictedException {

        String usernameToBeSignedUp = userEntity.getUsername();
        String emaiIdToBeSignedUp = userEntity.getEmail();
        List<UserEntity> existingUsers = userDAO.getUsersList();
        boolean usernameExists = false;
        boolean emaiIdExists = false;

        for (UserEntity u : existingUsers){
            if(usernameToBeSignedUp.equalsIgnoreCase(u.getUsername())){
                usernameExists = true;
                break;
            }
        }
        for (UserEntity u : existingUsers){
            if(emaiIdToBeSignedUp.equalsIgnoreCase(u.getEmail())){
                emaiIdExists = true;
                break;
            }
        }


        if(usernameExists == false && emaiIdExists ==false){
            return userDAO.createUser(userEntity);
        }
        else if(usernameExists == true && emaiIdExists == true)
        {
            throw new SignUpRestrictedException("SGR-001","Try any other Username, this Username has already been taken");
        }
        else {
            throw new SignUpRestrictedException("SGR-002","This user has already been registered, try with any other emailId");
        }

    }
}
