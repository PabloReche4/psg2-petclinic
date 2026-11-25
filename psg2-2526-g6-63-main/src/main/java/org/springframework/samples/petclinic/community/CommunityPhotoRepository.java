package org.springframework.samples.petclinic.community;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommunityPhotoRepository extends JpaRepository<CommunityPhoto, Integer> {

    List<CommunityPhoto> findByStatusOrderByCreatedAtDesc(CommunityPhotoStatus status, Pageable pageable);

    List<CommunityPhoto> findByOwnerIdOrderByCreatedAtDesc(Integer ownerId);

    @Query("select cp as photo, count(v) as votes from CommunityPhoto cp left join PhotoVote v on v.photo = cp "
        + "where cp.status = :status and cp.createdAt between :start and :end "
        + "group by cp order by count(v) desc")
    List<Object[]> findTopByMonth(
        @Param("status") CommunityPhotoStatus status,
        @Param("start") LocalDateTime start,
        @Param("end") LocalDateTime end,
        Pageable pageable
    );
}
