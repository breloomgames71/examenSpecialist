package com.prueba.nter.modules.products.infrastructure.repository;

import com.prueba.nter.modules.products.domain.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for ProductEntity.
 */
@Repository
public interface ProductRepository  extends JpaRepository<ProductEntity, Long> {

    @Query("select p from ProductEntity p where p.name = ?1")
    ProductEntity findByName(String name);
    @Query("select count(p) from ProductEntity p where p.category = ?1")
    Long countByCategory(String category);
    @Query("select p from ProductEntity p where p.category = ?1 and p.name = ?2")
    ProductEntity findByCategoryAndName(String category, String name);
    @Query("select p from ProductEntity p where p.name = ?1 order by p.price asc")
    List<ProductEntity> findPricesByNameOrderByPriceAsc(String name);
    @Query("select p from ProductEntity p where p.name = ?1 and p.price = ( select min(p2.price) from ProductEntity p2 where p2.name = ?1)")
    ProductEntity findCheapestProductByName(String name);
    boolean existsByNameIgnoreCase(String name);

}
