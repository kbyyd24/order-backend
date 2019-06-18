package com.ecommerce.order.product.representation;

import static java.util.stream.Collectors.toList;

import com.ecommerce.order.common.utils.PagedResource;
import com.ecommerce.order.product.Product;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductRepresentationService {

  private ProductSummaryRepresentation toSummary(Product product) {
    return new ProductSummaryRepresentation(product.getId().toString(), product.getName(),
        product.getPrice());
  }

  public PagedResource<ProductSummaryRepresentation> toSummary(PagedResource<Product> products) {
    List<ProductSummaryRepresentation> productSummaryRepresentations = products.getResource()
        .stream()
        .map(this::toSummary)
        .collect(toList());
    return PagedResource
        .of(products.getTotalNumber(), products.getPageIndex(), productSummaryRepresentations);
  }

}
