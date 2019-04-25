package com.ecommerce.order.order;

import static com.ecommerce.order.order.model.OrderId.orderId;

import com.ecommerce.order.common.ddd.ApplicationService;
import com.ecommerce.order.order.command.CreateOrderCommand;
import com.ecommerce.order.order.model.Order;
import com.ecommerce.order.order.model.OrderId;
import com.ecommerce.order.order.representation.OrderRepresentation;
import com.ecommerce.order.order.representation.OrderRepresentationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class OrderApplicationService implements ApplicationService {

  private final OrderRepresentationService representationService;
  private final OrderRepository repository;
  private final OrderCreateService orderCreateService;

  @Autowired
  public OrderApplicationService(OrderRepresentationService representationService,
      OrderRepository repository, OrderCreateService orderCreateService) {
    this.representationService = representationService;
    this.repository = repository;
    this.orderCreateService = orderCreateService;
  }

  @Transactional(readOnly = true)
  public OrderRepresentation byId(String id) {
    Order order = repository.byId(orderId(id));
    return representationService.toRepresentation(order);
  }

  @Transactional
  public OrderId createOrder(CreateOrderCommand command) {
    Order order = orderCreateService.create(command);
    repository.save(order);
    return order.getId();
  }
}
