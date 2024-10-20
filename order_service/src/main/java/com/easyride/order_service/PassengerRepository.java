package com.easyride.order_service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//PassengerRepository 提供了对乘客实体的数据库操作方法。
@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {
}

