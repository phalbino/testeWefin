package com.TesteWefin.Repository;

import com.TesteWefin.Model.PessoaModel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PessoaDao extends CrudRepository<PessoaModel, String> {
    List<PessoaModel> findByIdentificadorContains(String identificador);

}
