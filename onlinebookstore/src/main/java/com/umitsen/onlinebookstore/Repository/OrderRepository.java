package com.umitsen.onlinebookstore.Repository;

import com.umitsen.onlinebookstore.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long>
{ List<Order> findAllByUserIdOrderByUpdatedAtDesc(int userId);
}
