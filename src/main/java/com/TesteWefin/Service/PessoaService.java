package com.TesteWefin.Service;

import com.TesteWefin.Model.PessoaModel;
import com.TesteWefin.Repository.PessoaDao;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@NoArgsConstructor
@Service
public class PessoaService {

    @Autowired
    private PessoaDao pessoa;
    @Autowired
    private PessoaDao pessoaRepository;

    public Boolean verificaIdentificadorNuloVazio(String identificador) {
        return identificador == null || identificador.length() == 0;
    }

    public String verificaIdentificador(String identificador) {

        if (identificador.length() == 11) {
            return "CPF";
        } else if (identificador.length() == 14) {
            return "CNPJ";
        } else
            return "Identificador invalido";
    }

    public void savePessoa(final PessoaModel pessoaModel) {
        pessoaModel.setTipoIdentificador(verificaIdentificador(pessoaModel.getIdentificador()));
        if (!pessoaModel.getTipoIdentificador().equals("Identificador invalido")) {
            pessoa.save(pessoaModel);
        }
    }

    public Boolean pessoaPersistida(PessoaModel pessoaModel) {
        String identificador = pessoaModel.getIdentificador();

        identificador = identificador.replace(".", "").trim();
        identificador = identificador.replace("-", "").trim();
        identificador = identificador.replace("/", "").trim();
        pessoaModel.setIdentificador(identificador);

        return pessoaRepository.findByIdentificadorContains(identificador).isEmpty();
    }
}
