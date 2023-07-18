package com.gestion.stagiaires.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IBaseRepository<T, ID>  extends CrudRepository<T, ID>{

	Optional<T> findByNumero(String numero);
}
