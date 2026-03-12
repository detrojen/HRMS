package com.hrms.backend.repositories.PostRepositories;

import com.hrms.backend.dtos.responseDtos.post.PostWithCommentsAndLikesDto;
import com.hrms.backend.entities.PostEntities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> , JpaSpecificationExecutor<Post> {

    List<Post> findPostsByIsDeleted(Boolean isDeleted);
    @Query(value = "execute sp_birthdayAndOrkAniversary",nativeQuery = true)
    int sp_birthdayAndOrkAniversary();

    @Query("select new com.hrms.backend.dtos.responseDtos.post.PostWithCommentsAndLikesDto(p.id,p.title,p.body,p.attachmentPath,p.tags,l.id,p.createdBy,p.likeCount,p.commentCount) from Post p left join p.likes l on l.likedBy.id=:empId left join p.createdBy where p.isDeleted=false and p.isDeletedByHr=false and (:tagLike is null or p.tags like :tagLike) and (:dateFrom is null or p.createdAt >= :dateFrom) and (:dateTo is null or p.createdAt <= :dateTo)")
    Page<PostWithCommentsAndLikesDto> getAll(Long empId, String tagLike, Date dateFrom, Date dateTo, Pageable pageable);
}
