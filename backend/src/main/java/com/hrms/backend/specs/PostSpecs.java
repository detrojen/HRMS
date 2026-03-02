package com.hrms.backend.specs;

import com.hrms.backend.entities.PostEntities.Post;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class PostSpecs {
    private PostSpecs(){}
    public static Specification<Post> postedBefore(Date date){
        return ((root, query, criteriaBuilder) ->criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"),date));
    }
    public static Specification<Post> postedAfter(Date date){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"),date));
    }
}
