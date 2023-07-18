package com.whatsapp.whatsappclone.Repositry;

import com.whatsapp.whatsappclone.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE u.fullname LIKE %:emailOrName% OR u.email LIKE %:emailOrName%")
    public List<User> searchUser(@Param("emailOrName") String emailOrName);





}
