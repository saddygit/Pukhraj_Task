
package com.prizy.pricer.service.repository;

import com.prizy.pricer.domain.ProductSurvey;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import java.math.BigDecimal;
import java.util.List;

@RestResource(path = "productSurvey")
public interface ProductSurveyRepository extends CrudRepository<ProductSurvey, Long> {

    @Query("select pl.price from ProductSurvey pl where pl.product.barcode = ?1")
    List<BigDecimal> getPrice(String barcode);
    

}
