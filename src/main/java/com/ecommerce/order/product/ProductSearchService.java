package com.ecommerce.order.product;

import com.ecommerce.order.common.utils.PagedResource;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductSearchService {

  private final ProductRepository productRepository;

  @Autowired
  public ProductSearchService(
      ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public PagedResource<Product> pagedProducts(int pageIndex, int pageSize) {
    int offset = pageIndex * pageSize;
    List<Product> products = productRepository.pagedProducts(pageSize, offset);
    int totalSize = productRepository.totalSize();
    return PagedResource.of(totalSize, pageIndex, products);
  }

}
