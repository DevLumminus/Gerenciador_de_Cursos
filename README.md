# Gerenciador_de_Cursos
Gerenciador de cursos criado em java com integração com o banco de dados MySQL, seguindo 3/9 padrões GRASP

### DECISÕES ESTRATEGICAS

Não colocar exibição no model de email (somente simula um multidado) <br>

### O QUE APRENDI?

String no java pode receber NULL <br>
TINYINT (no java se torna boolean) <br>
Default não é representado no model <br>
Na exibição para decisão se usa ? (ex: ex != 1 ? "Mantér" ou (ex ? "Ativo" : "Desativado") para tipo boolean) <br>
