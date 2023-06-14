package com.TesteWefin.Controller;

import com.TesteWefin.Api.response.GenericResponse;
import com.TesteWefin.Api.response.Request;
import com.TesteWefin.Model.PessoaModel;
import com.TesteWefin.Repository.PessoaDao;
import com.TesteWefin.Service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
public class PessoaController {

    @Autowired
    private PessoaDao pessoaRepository;

    @Autowired
    private PessoaService service;

    @PostMapping("/pessoa/save")
    public ResponseEntity<GenericResponse> save(@RequestBody final PessoaModel pessoaModel, @RequestHeader final MultiValueMap<String, Object> httpHeaders) {
        String detalhes = "";
        final String SUCESSO = "Pessoa Criada com Sucesso.";
        final String ERROIDENTIFICADOR = "Identificador nulo ou vazio";
        final GenericResponse response = new GenericResponse();
        final Request request = new Request();

        if (service.verificaIdentificadorNuloVazio(pessoaModel.getIdentificador()) == true) {
            respostaChamada(ERROIDENTIFICADOR, request, response, pessoaModel, detalhes);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } else {
            if (!service.pessoaPersistida(pessoaModel))
                detalhes = "Usuario ja persistido, foi feita atualização no cadastro";
            service.savePessoa(pessoaModel);
            if (pessoaModel.getTipoIdentificador().equals("Identificador invalido")) {
                respostaChamada("Identificador invalido", request, response, pessoaModel, detalhes);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }

            respostaChamada(SUCESSO, request, response, pessoaModel, detalhes);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

    }

    public void respostaChamada(String Menssagem, Request request, GenericResponse response, PessoaModel pessoaModel, String detalhes) {
        request.getRequestObjects().put("pessoa", pessoaModel);
        response.setRequest(request);
        response.setMessage(Menssagem);
        response.setStatus(HttpStatus.CREATED.value());
        response.setTimestamp(new Timestamp(System.currentTimeMillis()));
        response.setDetails(detalhes);

    }

    @GetMapping(value = "pessoa/get/all")
    public ResponseEntity<Iterable<PessoaModel>> get() {
        return ResponseEntity.status(HttpStatus.OK).body(pessoaRepository.findAll());
    }


}
