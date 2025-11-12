# Gerenciador_de_Cursos
Gerenciador de cursos criado em java com integração com o banco de dados MySQL, seguindo 3/9 padrões GRASP

### COMO ACESSAR

Faz o clone do projeto<br>
No config você coloca a senha do seu MySQL<br>
Se estiver usando um JDK anterior da versão 11:<br>
  &emsp;Acesse o pom.xml<br>
  &emsp;Nos trechos no parentese coloque o numero 11 no lugar do 17 (source/target/maven.compiler.source/maven.compiler.target)<br>

### DECISÕES ESTRATEGICAS

Não colocar exibição no model de email (somente simula um multidado) <br>
Criei as interfaces para evitar o auto acoplamento (A mais importante é a IEmailDAO por causa que Aluno é altamente dependente de Email por causa da FK)<br>
Por causa da baixa agragação aos padrões GRASP, foi decidido a retirada as interfaces de controller (manter a coesão e melhorar a eficiencia) - segue o modelo que levamos em conta (KISS)<br>

### O QUE APRENDI?

String no java pode receber NULL <br>
TINYINT (no java se torna boolean) <br>
Default não é representado no model <br>
Na exibição para decisão se usa ? (ex: ex != 1 ? "Mantér" ou (ex ? "Ativo" : "Desativado") para tipo boolean) <br>

