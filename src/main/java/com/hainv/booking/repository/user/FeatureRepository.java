package com.hainv.booking.repository.user;

import com.hainv.booking.entity.user.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, String> {

    @Query("""
        SELECT DISTINCT f
        FROM AccountPermissionGroup apg
        JOIN GroupFeature gf ON gf.group.codeId = apg.group.codeId
        JOIN Feature f ON f.codeId = gf.feature.codeId
        WHERE apg.id.accountId = :accountId
    """)
    List<Feature> findFeaturesByAccountId(@Param("accountId") String accountId);
}

