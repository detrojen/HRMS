package com.hrms.backend.repositories.PostRepositories;

import com.hrms.backend.entities.PostEntities.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Sort;

import java.util.List;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike,Long> , JpaSpecificationExecutor<PostLike> {
    PostLike findByLikedBy_IdAndPost_Id(Long employeeId,Long PostId);
    List<PostLike> findAllByPost_Id(Long postId, Sort sort);
}
