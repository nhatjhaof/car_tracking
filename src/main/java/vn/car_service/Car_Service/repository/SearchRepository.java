package vn.car_service.Car_Service.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import vn.car_service.Car_Service.dto.response.PageResponse;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
public class SearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public PageResponse<?> getAllUserWithMultipleColumnsAndSearch(int pageNo, int pageSize,String keyword, String sortBy) {
        StringBuilder sqlQuery = new StringBuilder("SELECT new vn.car_service.Car_Service.dto.response.UserResponse(u.id, u.firstName, u.lastName, u.gender, u.birthday, u.username, u.email, u.phone) FROM User u WHERE 1=1");
        if(StringUtils.hasLength(keyword)){
            sqlQuery.append(" AND lower(u.firstName) LIKE lower(:firstName)");
            sqlQuery.append(" OR lower(u.lastName) LIKE lower(:lastName)");
            sqlQuery.append(" OR lower(u.email) LIKE lower(:email)");
        }
        //query ra list user

        if(StringUtils.hasLength(sortBy)){
            //kiem tra format field:asc|desc
            Pattern pattern = Pattern.compile("(\\w+?)(:)(.*)");
            Matcher matcher = pattern.matcher(sortBy);
            if (matcher.find()) {
                if (matcher.group(3).equalsIgnoreCase("asc")){
                    sqlQuery.append(String.format("ORDER BY u.%s %s" , matcher.group(1) , matcher.group(3)));
                }
            }
        }

        Query selectQuery = entityManager.createQuery(sqlQuery.toString());
        selectQuery.setFirstResult(pageNo);
        selectQuery.setMaxResults(pageSize);
        if(StringUtils.hasLength(keyword)){
            selectQuery.setParameter("firstName", String.format("%%%s%%", keyword));
            selectQuery.setParameter("lastName", String.format("%%%s%%", keyword));
            selectQuery.setParameter("email", String.format("%%%s%%", keyword));
        }
        List users = selectQuery.getResultList();
        System.out.println(users);

        //query ra so record
        StringBuilder sqlCountQuery = new StringBuilder("SELECT COUNT(*) FROM User u WHERE 1=1");
        if(StringUtils.hasLength(keyword)){
            sqlCountQuery.append(" AND lower(u.firstName) LIKE lower(?1)");
            sqlCountQuery.append(" OR lower(u.lastName) LIKE lower(?2)");
            sqlCountQuery.append(" OR lower(u.email) LIKE lower(?3)");
        }
        Query countQuery = entityManager.createQuery(sqlCountQuery.toString());
        if(StringUtils.hasLength(keyword)){
            countQuery.setParameter(1, String.format("%%%s%%", keyword));
            countQuery.setParameter(2, String.format("%%%s%%", keyword));
            countQuery.setParameter(3, String.format("%%%s%%", keyword));
        }
        Long totalElements = (Long) countQuery.getSingleResult();

        Page<?> page = new PageImpl<Object>(users, PageRequest.of(pageNo, pageSize), totalElements);
        return PageResponse.builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalElements(page.getTotalPages())
                .items(page.stream().toList())
                .build();
    }
}
