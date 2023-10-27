//package com.dataInteraction.JPAFramework.repo;
//
//import com.dataInteraction.JPAFramework.entity.Account;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public interface AccountRepository extends JpaRepository<Account, Integer> {
//
//    List<Account> findAccountByNameLike(String str);
//
//    Optional<Account> findByIdAndName(Integer id, String name);
//
//    boolean existsAccountById(Integer id);
//
//    // ----------------------------------------------------------------------------------------
//
//    @Modifying
//    @Query("update Account set password = ?2 where id = ?1")
//    int updatePasswordById(Integer id, String newPassword);
//
//    @Modifying
//    @Query(value = "update db_user set password = :pwd where name = :name", nativeQuery = true)
//    int updatePasswordByUsername(@Param("name") String username,
//                                 @Param("pwd") String newPassword);
//
//}
