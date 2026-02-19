package com.hrms.backend.repositories.PostRepositories;

import com.hrms.backend.entities.PostEntities.PostComment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostCommentRepository extends JpaRepository<PostComment,Long> {
    List<PostComment> findAllByPost_Id(Long postId, Sort sort);
}
