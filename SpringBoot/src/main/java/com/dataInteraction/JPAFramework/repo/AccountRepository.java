package com.dataInteraction.JPAFramework.repo;

import com.dataInteraction.JPAFramework.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    List<Account> findAccountByNameLike(String str);

    Optional<Account> findByIdAndName(Integer id, String name);

    boolean existsAccountById(Integer id);

}
