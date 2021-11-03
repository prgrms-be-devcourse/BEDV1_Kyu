package org.prgrms.kyu.service;

import javassist.NotFoundException;
import javax.naming.AuthenticationException;
import lombok.RequiredArgsConstructor;
import org.prgrms.kyu.dto.OrderRequest;
import org.prgrms.kyu.dto.StoreFindResponse;
import org.prgrms.kyu.entity.Order;
import org.prgrms.kyu.entity.Store;
import org.prgrms.kyu.entity.User;
import org.prgrms.kyu.repository.OrderRepository;
import org.prgrms.kyu.repository.StoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

  private final OrderRepository orderRepository;
  private final OrderFoodService orderFoodService;
  private final UserService userService;
  private final StoreService storeService;

  public Order getById(Long orderId) {
    return orderRepository.getById(orderId);
  }

  @Transactional
  public Long save(OrderRequest orderRequest, Long userId, Long storeId)
      throws AuthenticationException, NotFoundException {
    User user = userService.findById(userId);
    StoreFindResponse storeRequest = storeService.findById(storeId);
    Store store = storeRequest.convertToStore();
    Order order = orderRequest.convertToOrder(
        user,
        store
    );

    Long orderId = orderRepository.save(order).getId();

    orderFoodService.save(orderRequest.getOrderFood(), getById(orderId));
    return orderId;
  }



}
