package com.TesteWefin.Service;

import com.TesteWefin.Model.PessoaModel;
import com.TesteWefin.Repository.PessoaDao;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.InputMismatchException;

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

    public Boolean isCpf(PessoaModel pessoaModel) {

        Long.parseLong(pessoaModel.getIdentificador());
        int d1, d2;
        int digito1, digito2, resto;
        int digitoCPF;
        String nDigResult;

        d1 = d2 = 0;
        digito1 = digito2 = resto = 0;

        for (int nCount = 1; nCount < pessoaModel.getIdentificador().length() - 1; nCount++) {
            digitoCPF = Integer.valueOf(pessoaModel.getIdentificador().substring(nCount - 1, nCount)).intValue();
            d1 = d1 + (11 - nCount) * digitoCPF;
            d2 = d2 + (12 - nCount) * digitoCPF;
        }
        ;

        resto = (d1 % 11);

        if (resto < 2)
            digito1 = 0;
        else
            digito1 = 11 - resto;

        d2 += 2 * digito1;
        resto = (d2 % 11);

        if (resto < 2)
            digito2 = 0;
        else
            digito2 = 11 - resto;

        String nDigVerific = pessoaModel.getIdentificador().substring(pessoaModel.getIdentificador().length() - 2, pessoaModel.getIdentificador().length());

        nDigResult = String.valueOf(digito1) + String.valueOf(digito2);
        return nDigVerific.equals(nDigResult);
    }

    public Boolean isCNPJ(PessoaModel pessoaModel) {
        if (pessoaModel.getIdentificador().equals("00000000000000") || pessoaModel.getIdentificador().equals("11111111111111") ||
                pessoaModel.getIdentificador().equals("22222222222222") || pessoaModel.getIdentificador().equals("33333333333333") ||
                pessoaModel.getIdentificador().equals("44444444444444") || pessoaModel.getIdentificador().equals("55555555555555") ||
                pessoaModel.getIdentificador().equals("66666666666666") || pessoaModel.getIdentificador().equals("77777777777777") ||
                pessoaModel.getIdentificador().equals("88888888888888") || pessoaModel.getIdentificador().equals("99999999999999"))

            return (false);

        char dig13, dig14;
        int sm, i, r, num, peso;

        try {
            sm = 0;
            peso = 2;
            for (i = 11; i >= 0; i--) {
                num = (int) (pessoaModel.getIdentificador().charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10)
                    peso = 2;
            }

            r = sm % 11;
            if ((r == 0) || (r == 1))
                dig13 = '0';
            else dig13 = (char) ((11 - r) + 48);

            sm = 0;
            peso = 2;
            for (i = 12; i >= 0; i--) {
                num = (int) (pessoaModel.getIdentificador().charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10)
                    peso = 2;
            }

            r = sm % 11;
            if ((r == 0) || (r == 1))
                dig14 = '0';
            else dig14 = (char) ((11 - r) + 48);

            if ((dig13 == pessoaModel.getIdentificador().charAt(12)) && (dig14 == pessoaModel.getIdentificador().charAt(13)))
                return (true);
            else return (false);
        } catch (InputMismatchException erro) {
            return (false);
        }
    }

}
