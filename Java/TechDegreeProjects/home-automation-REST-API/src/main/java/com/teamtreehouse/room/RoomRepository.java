package com.teamtreehouse.room;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface RoomRepository extends PagingAndSortingRepository<Room, Long> {

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    <S extends Room> S save(S entity);

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    <S extends Room> Iterable<S> save(Iterable<S> entities);

    @RestResource(rel = "name-contains", path = "containsName")
    Page<Room> findByNameContaining(@Param("name") String name, Pageable page);

    @RestResource(rel = "area-less-than", path = "lessThanArea")
    Page<Room> findByAreaLessThan(@Param("area") int area, Pageable page);

}
