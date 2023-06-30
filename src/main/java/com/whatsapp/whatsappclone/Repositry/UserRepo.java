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

    public Optional<User> findByEmail(String email);

    @Query("select u from User u where u.fullname Like %:emailOrname% or u.email Like %:emailOrPhone%")
    public List<User>searchUser(@Param("emailOrname") String emailOrname);





}
