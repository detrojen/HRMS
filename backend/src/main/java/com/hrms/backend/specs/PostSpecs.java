package com.hrms.backend.specs;

import com.hrms.backend.entities.PostEntities.Post;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class PostSpecs {
    public static Specification<Post> postedBefore(LocalDate date){
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("createdAt"),date);
        });
    }
}
