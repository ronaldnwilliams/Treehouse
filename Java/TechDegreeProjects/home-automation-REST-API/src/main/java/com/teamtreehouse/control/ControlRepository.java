package com.teamtreehouse.control;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.security.access.prepost.PreAuthorize;

public interface ControlRepository extends PagingAndSortingRepository<Control, Long> {

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    <S extends Control> S save(S entity);

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    <S extends Control> Iterable<S> save(Iterable<S> entities);

}
