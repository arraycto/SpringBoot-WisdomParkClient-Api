package com.niit.soft.client.api.repository;

import com.niit.soft.client.api.domain.model.SysCard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName CardRepository
 * @Description TODO
 * @Author 田震
 * @Date 2020/5/26
 **/
public interface CardRepository extends JpaRepository<SysCard, Long> {
    /**
     * 自定义分页查询
     *
     * @param pageable
     * @return
     */
    @Query("select u from SysCard u")
    Page<SysCard> findALL(Pageable pageable);

    /**
     * 通过学号查询余额
     * @param JobNumber
     * @return
     */
    @Query(value = "select card_balance from sys_card as u where u.job_number=?1 ", nativeQuery = true)
    Double findCardBalanceByJobNumber(String JobNumber);

    /**
     * 校园卡充值
     * @param cardNumber
     * @param money
     * @return
     */
    @Transactional
    @Modifying
    @Query(value = "update sys_card as u set  u.card_balance = u.card_balance + ?2 where u.card_number=?1",
            nativeQuery = true)
    int insertCardBalance(String cardNumber, Double money);


}