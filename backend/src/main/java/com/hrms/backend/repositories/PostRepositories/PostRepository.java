package com.hrms.backend.repositories.PostRepositories;

import com.hrms.backend.entities.PostEntities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> , JpaSpecificationExecutor<Post> {

    List<Post> findPostsByIsDeleted(Boolean isDeleted);
    @Query(value = "execute sp_birthdayAndOrkAniversary",nativeQuery = true)
    int sp_birthdayAndOrkAniversary();
}
