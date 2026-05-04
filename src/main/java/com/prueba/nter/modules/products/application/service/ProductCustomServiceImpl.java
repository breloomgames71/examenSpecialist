package com.prueba.nter.modules.products.application.service;


import com.prueba.nter.modules.products.application.service.port.ProductCustomService;
import com.prueba.nter.modules.products.application.service.port.ProductService;
import com.prueba.nter.modules.products.domain.entity.ProductEntity;
import com.prueba.nter.modules.products.infrastructure.dto.input.ProductInputDto;
import com.prueba.nter.modules.products.infrastructure.dto.ouput.ProductOutputDto;
import com.prueba.nter.modules.products.infrastructure.mapper.ProductMapper;
import com.prueba.nter.modules.products.infrastructure.repository.ProductRepository;
import com.prueba.nter.modules.users.domain.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductCustomServiceImpl implements ProductCustomService {
    private final ProductMapper productMapper;
    private final EntityManager entityManager;


    /**
     * Search in the database
     * the product by the name and/or
     * category passed
     *
     * @param category category of the product
     * @param name     name of the product
     */
    @Override
    public List<ProductOutputDto> findProductByCategoryAndOrName(String category, String name) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProductEntity> query = cb.createQuery(ProductEntity.class);
        Root<ProductEntity> product = query.from(ProductEntity.class);

        List<Predicate> predicates = new ArrayList<>();

        if (category != null && !category.isBlank()) {
            predicates.add(cb.equal(product.get("category"), category));
        }

        if (name != null && !name.isBlank()) {
            predicates.add(cb.equal(product.get("name"), name));
        }

        query.where(predicates.toArray(new Predicate[0]));

        List<ProductEntity> products = entityManager.createQuery(query).getResultList();

        return productMapper.toListProductOutputDto(products);
    }

    /**
     * Search in the database
     * the products where the price is
     * smaller than the one passed
     *
     * @param price price of the product
     */
    @Override
    public List<ProductOutputDto> findProductsLessThanPrice(Double price) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProductEntity> query = cb.createQuery(ProductEntity.class);
        Root<ProductEntity> product = query.from(ProductEntity.class);

        query.where(cb.lessThan(product.get("price"), price));

        List<ProductEntity> products = entityManager.createQuery(query).getResultList();

        return productMapper.toListProductOutputDto(products);
    }

    /**
     * Search in the database
     * the products where the expirationDate
     * is between two params
     *
     * @param startDate starting expirationDate
     * @param endDate   ending expirationDate
     */
    @Override
    public List<ProductOutputDto> findProductsByDate(LocalDate startDate, LocalDate endDate) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProductEntity> query = cb.createQuery(ProductEntity.class);
        Root<ProductEntity> product = query.from(ProductEntity.class);

        query.where(cb.between(product.get("expirationDate"), startDate, endDate));
        query.orderBy(cb.desc(product.get("id")));

        List<ProductEntity> products = entityManager.createQuery(query).getResultList();

        return productMapper.toListProductOutputDto(products);
    }

    /**
     * Search in the database
     * the product by the category passed
     * and the lowest price
     *
     * @param category category of the product
     */
    @Override
    public ProductOutputDto findProductsByCategoryAndLowestPrice(String category) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProductEntity> query = cb.createQuery(ProductEntity.class);
        Root<ProductEntity> product = query.from(ProductEntity.class);

        Subquery<Double> subquery = query.subquery(Double.class);
        Root<ProductEntity> subProduct = subquery.from(ProductEntity.class);

        subquery.select(cb.min(subProduct.get("price")));
        subquery.where(cb.equal(subProduct.get("category"), category));

        query.where(
                cb.and(
                        cb.equal(product.get("category"), category),
                        cb.equal(product.get("price"), subquery)
                )
        );

        ProductEntity result = entityManager.createQuery(query).getSingleResult();

        return productMapper.toProductOutputDto(result);
    }

    /**
     * Retrieves products associated with a specific user email.
     * Optionally filters the results by category and brand.
     *
     * @param email user email used to filter products
     * @param category optional category filter (can be null or blank)
     * @param brand optional brand filter (can be null or blank)
     * @return list of products that match the given criteria
     */
    @Override
    public List<ProductOutputDto> findProductsByUserEmail(String email, String category, String brand) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProductEntity> query = cb.createQuery(ProductEntity.class);
        Root<ProductEntity> product = query.from(ProductEntity.class);

        Join<ProductEntity, UserEntity> user = product.join("user");

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.equal(user.get("email"), email));

        if (category != null && !category.isBlank()) {
            predicates.add(cb.equal(product.get("category"), category));
        }

        if (brand != null && !brand.isBlank()) {
            predicates.add(cb.equal(product.get("brand"), brand));
        }

        query.where(predicates.toArray(new Predicate[0]));

        List<ProductEntity> products = entityManager.createQuery(query).getResultList();

        return productMapper.toListProductOutputDto(products);
    }

    /**
     * Retrieves products that belong to the oldest users in the system.
     * The oldest users are determined based on their identifier (ascending order).
     *
     * @param numberOfUsers number of oldest users to consider
     * @return list of products associated with the selected users
     */
    @Override
    public List<ProductOutputDto> findProductsFromOldestUsers(Integer numberOfUsers) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<UserEntity> userQuery = cb.createQuery(UserEntity.class);
        Root<UserEntity> userRoot = userQuery.from(UserEntity.class);

        userQuery.orderBy(cb.asc(userRoot.get("id")));

        List<UserEntity> oldestUsers = entityManager
                .createQuery(userQuery)
                .setMaxResults(numberOfUsers)
                .getResultList();

        CriteriaQuery<ProductEntity> productQuery = cb.createQuery(ProductEntity.class);
        Root<ProductEntity> productRoot = productQuery.from(ProductEntity.class);

        productQuery.where(productRoot.get("user").in(oldestUsers));

        List<ProductEntity> products = entityManager.createQuery(productQuery).getResultList();

        return productMapper.toListProductOutputDto(products);
    }


    /**
     * Performs an advanced product search using dynamic filters,
     * pagination, and sorting.
     *
     * Filters are optional and only applied when provided.
     *
     * @param name optional product name filter (partial match, case insensitive)
     * @param category optional category filter
     * @param brand optional brand filter
     * @param minPrice optional minimum price filter
     * @param maxPrice optional maximum price filter
     * @param startDate optional start date for expiration filtering
     * @param endDate optional end date for expiration filtering
     * @param providerId optional provider identifier filter
     * @param page page number (starting from 0)
     * @param size number of elements per page
     * @param sortBy field used for sorting
     * @param direction sorting direction (asc or desc)
     * @return paginated result containing products that match the filters
     */
    @Override
    public Page<ProductOutputDto> advancedSearch(
            String name,
            String category,
            String brand,
            Double minPrice,
            Double maxPrice,
            LocalDate startDate,
            LocalDate endDate,
            Long providerId,
            int page,
            int size,
            String sortBy,
            String direction) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<ProductEntity> query = cb.createQuery(ProductEntity.class);
        Root<ProductEntity> product = query.from(ProductEntity.class);

        List<Predicate> predicates = new ArrayList<>();

        if (name != null && !name.isBlank()) {
            predicates.add(cb.like(cb.lower(product.get("name")), "%" + name.toLowerCase() + "%"));
        }

        if (category != null && !category.isBlank()) {
            predicates.add(cb.equal(product.get("category"), category));
        }

        if (brand != null && !brand.isBlank()) {
            predicates.add(cb.equal(product.get("brand"), brand));
        }

        if (minPrice != null) {
            predicates.add(cb.greaterThanOrEqualTo(product.get("price"), minPrice));
        }

        if (maxPrice != null) {
            predicates.add(cb.lessThanOrEqualTo(product.get("price"), maxPrice));
        }

        if (startDate != null && endDate != null) {
            predicates.add(cb.between(product.get("expirationDate"), startDate, endDate));
        }

        if (providerId != null) {
            predicates.add(cb.equal(product.get("provider").get("id"), providerId));
        }

        query.where(predicates.toArray(new Predicate[0]));

        if ("desc".equalsIgnoreCase(direction)) {
            query.orderBy(cb.desc(product.get(sortBy)));
        } else {
            query.orderBy(cb.asc(product.get(sortBy)));
        }

        List<ProductEntity> products = entityManager
                .createQuery(query)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<ProductEntity> countRoot = countQuery.from(ProductEntity.class);

        List<Predicate> countPredicates = new ArrayList<>();

        if (name != null && !name.isBlank()) {
            countPredicates.add(cb.like(cb.lower(countRoot.get("name")), "%" + name.toLowerCase() + "%"));
        }

        if (category != null && !category.isBlank()) {
            countPredicates.add(cb.equal(countRoot.get("category"), category));
        }

        if (brand != null && !brand.isBlank()) {
            countPredicates.add(cb.equal(countRoot.get("brand"), brand));
        }

        if (minPrice != null) {
            countPredicates.add(cb.greaterThanOrEqualTo(countRoot.get("price"), minPrice));
        }

        if (maxPrice != null) {
            countPredicates.add(cb.lessThanOrEqualTo(countRoot.get("price"), maxPrice));
        }

        if (startDate != null && endDate != null) {
            countPredicates.add(cb.between(countRoot.get("expirationDate"), startDate, endDate));
        }

        if (providerId != null) {
            countPredicates.add(cb.equal(countRoot.get("provider").get("id"), providerId));
        }

        countQuery.select(cb.count(countRoot));
        countQuery.where(countPredicates.toArray(new Predicate[0]));

        Long total = entityManager.createQuery(countQuery).getSingleResult();

        Pageable pageable = PageRequest.of(page, size);

        List<ProductOutputDto> productDtos = productMapper.toListProductOutputDto(products);

        return new PageImpl<>(productDtos, pageable, total);
    }
}
