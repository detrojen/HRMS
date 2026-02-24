package com.hrms.backend.specs;

import com.hrms.backend.entities.PostEntities.Post;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class PostSpecs {
    public static Specification<Post> postedBefore(Date date){
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"),date);
        });
    }
    public static Specification<Post> postedAfter(Date date){
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"),date);
        });
    }
}
