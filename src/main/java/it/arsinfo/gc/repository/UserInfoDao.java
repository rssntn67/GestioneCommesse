package it.arsinfo.gc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.arsinfo.gc.entity.UserInfo;


public interface UserInfoDao extends JpaRepository<UserInfo, Long>{
    UserInfo findByUsername(String username);
}
