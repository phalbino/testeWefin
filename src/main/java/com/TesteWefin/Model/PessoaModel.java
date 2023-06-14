package com.TesteWefin.Model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@NoArgsConstructor
@Data
@Entity
@Table(name = "Pessoa")
public class PessoaModel {

    @Column
    private String nome;

    @Id
    @Column
    private String identificador;

    @Column
    private String tipoIdentificador;

}
