package com.example.repository;

import com.example.entity.Movie;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface MovieRepository extends CrudRepository <Movie,Long>{
}
