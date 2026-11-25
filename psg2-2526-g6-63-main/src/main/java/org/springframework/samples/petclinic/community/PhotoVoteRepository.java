package org.springframework.samples.petclinic.community;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoVoteRepository extends JpaRepository<PhotoVote, Integer> {

    boolean existsByPhotoIdAndUserId(Integer photoId, Integer userId);

    long countByPhotoId(Integer photoId);
}
