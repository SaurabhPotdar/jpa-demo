package com.cg.repository;

import com.cg.constants.Constants;
import com.cg.dto.Employee;
import com.cg.dto.EmployeeView;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>, JpaSpecificationExecutor<Employee> {

    List<EmployeeView> findByFirstName(final String firstName);

    class FiltersUtils {
        private FiltersUtils() {
            throw new UnsupportedOperationException();
        }

        public static Specification<Employee> findBy(final Map<String, String> filters) {
            return (root, query, criteriaBuilder) -> {
                final List<Predicate> predicates = new ArrayList<>();
                //Like
                if (filters.containsKey(Constants.FIRST_NAME) && filters.get(Constants.FIRST_NAME) != null) {
                    predicates.add(criteriaBuilder.like(root.get(Constants.FIRST_NAME), filters.get(Constants.FIRST_NAME)));
                }
                //Equal
                if (filters.get(Constants.DESIGNATION) != null) {
                    //Starting from second, we need to add criteriaBuilder.and() to chain the predicates
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Constants.DESIGNATION), filters.get(Constants.DESIGNATION))));
                }
                //Not Equal
                if (filters.get(Constants.DESIGNATION_NOT_EQUAL_TO) != null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.notEqual(root.get(Constants.DESIGNATION), filters.get(Constants.DESIGNATION_NOT_EQUAL_TO))));
                }
                //Greater Than
                if (filters.get(Constants.SALARY) != null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get(Constants.SALARY), filters.get(Constants.SALARY))));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            };
        }
    }

}