# testeWefin
Desenvolvimento de um API proposta pela empresa.

1 - Para o desenvolvimento, foi utilizado o banco oracle, com usuario e senha "wefin". Essas configurações, a String de conexão e outras mais estão presente no aquivo "application.properties".
2 - O endpoit proposto no método POST está no caminho "/pessoa/save".
3 - A documentação do endpoint está no caminho /swagger-ui/.
4 - Além do proposto no teste, foi adicionado um método get, que retorna as persistencias presente na tabela "PESSOA".
5 - Algumas validações a mais foram adicionadas, como a validação de um mesmo cpf ou cnpj ja persistidos na base de dados.
