package com.example.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.entities.EmpleadoEntity;

@Repository
public interface EmpleadoRepository extends CrudRepository<EmpleadoEntity, String> {

}
