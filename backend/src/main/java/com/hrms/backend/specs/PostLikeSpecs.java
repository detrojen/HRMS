package com.hrms.backend.specs;

import com.hrms.backend.entities.EmployeeEntities.Employee;
import com.hrms.backend.entities.PostEntities.Post;
import com.hrms.backend.entities.PostEntities.PostLike;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class PostLikeSpecs {
    public static Specification<PostLike> hasEmployeeLikedPostId(Long employeeId,Long postId){
        return ((root, query, criteriaBuilder) -> {
            Join<PostLike, Post> postJoin= root.join("post");
            Join<PostLike, Employee> employeeJoin = root.join("likedBy");
            return criteriaBuilder.and(
                    criteriaBuilder.equal(postJoin.get("id"),postId),
                    criteriaBuilder.equal(employeeJoin.get("id"),employeeId)
            );
        });
    }
}
