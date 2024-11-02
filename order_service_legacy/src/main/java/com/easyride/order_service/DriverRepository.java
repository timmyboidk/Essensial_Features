package com.easyride.order_service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
//DriverRepository 提供了对司机实体的数据库操作方法。
//定义了查询所有可用司机的方法。
@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {

    List<Driver> findByIsAvailableTrue();
}
