package com.prueba.nter.modules.products.application.service;


import com.prueba.nter.modules.products.application.service.port.ProductService;
import com.prueba.nter.modules.products.domain.entity.ProductEntity;
import com.prueba.nter.modules.products.infrastructure.dto.input.ProductInputDto;
import com.prueba.nter.modules.products.infrastructure.dto.ouput.ProductOutputDto;
import com.prueba.nter.modules.products.infrastructure.mapper.ProductMapper;
import com.prueba.nter.modules.products.infrastructure.repository.ProductRepository;
import com.prueba.nter.modules.users.domain.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    /**
     * Retrieves all products with pagination
     *
     * @return a list of all ProductEntity
     */
    @Override
    public List<ProductOutputDto> getAll(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return productMapper.toListProductOutputDto(productRepository.findAll(pageRequest).stream().toList());
    }

    /**
     * Create new products from a list
     *
     * @param products the product entities to create
     */
    @Override
    public void create(List<ProductInputDto> products) {
        productRepository.saveAll(productMapper.toListProductEntity(products));
    }

    /**
     * Calculates the total of the prices
     * from the products passed
     *
     * @param products
     * @return
     */
    @Override
    public Double calculatePrices(List<ProductInputDto> products) {
        Double totalPrice = 0.0;
        for (ProductInputDto p : products) {
            totalPrice += p.getPrice();
        }
        return totalPrice;
    }

    /**
     * Counts the total of products
     * by the passed category
     * @param products
     * @param category
     * @return
     */
    @Override
    public Long countByCategory(List<ProductInputDto> products, String category) {
        Long totalProductsByCategory = 0L;
        for (ProductInputDto p : products){
            if (p.getCategory().equalsIgnoreCase(category)){
                totalProductsByCategory ++;
            }
        }
        return totalProductsByCategory;
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
    public List<ProductEntity> findProductsByUserEmail(String email, String category, String brand) {

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

        return entityManager.createQuery(query).getResultList();
    }

    /**
     * Retrieves products that belong to the oldest users in the system.
     * The oldest users are determined based on their identifier (ascending order).
     *
     * @param numberOfUsers number of oldest users to consider
     * @return list of products associated with the selected users
     */
    public List<ProductEntity> findProductsFromOldestUsers(Integer numberOfUsers) {

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

        return entityManager.createQuery(productQuery).getResultList();
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
    public Page<ProductEntity> advancedSearch(
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

        List<ProductEntity> result = entityManager
                .createQuery(query)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();

        Long total = countAdvancedSearch(
                name,
                category,
                brand,
                minPrice,
                maxPrice,
                startDate,
                endDate,
                providerId
        );

        Pageable pageable = PageRequest.of(page, size);

        return new PageImpl<>(result, pageable, total);
    }

    /**
     * Counts the total number of products that match the same filters
     * used in the advanced search.
     *
     * This method is used to calculate pagination metadata.
     *
     * @param name optional product name filter
     * @param category optional category filter
     * @param brand optional brand filter
     * @param minPrice optional minimum price filter
     * @param maxPrice optional maximum price filter
     * @param startDate optional start date for expiration filtering
     * @param endDate optional end date for expiration filtering
     * @param providerId optional provider identifier filter
     * @return total number of products that match the filters
     */
    private Long countAdvancedSearch(
            String name,
            String category,
            String brand,
            Double minPrice,
            Double maxPrice,
            LocalDate startDate,
            LocalDate endDate,
            Long providerId) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
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

        query.select(cb.count(product));
        query.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(query).getSingleResult();
    }
}
