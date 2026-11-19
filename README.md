# Sistema de Gerenciamento de Estoque — Manutenção

# Como executar o projeto

Este guia mostra como configurar o banco de dados e executar o sistema usando **XAMPP e VS Code no Windows**.

### 1. Inicie o banco de dados

1. Instale e abra o **XAMPP**.
2. Clique em **Start** ao lado de **MySQL**.
3. Acesse `http://localhost/phpmyadmin`.
4. Crie um banco de dados chamado `fs`.
5. Importe o arquivo `.sql` do projeto para criar as tabelas.

### 2. Crie um usuário para o sistema

No phpMyAdmin, selecione a aba SQL e execute:

```sql
CREATE USER 'stock_app'@'localhost'
IDENTIFIED BY 'MinhaSenha123!';

GRANT ALL PRIVILEGES ON fs.*
TO 'stock_app'@'localhost';
```

Isso cria um usuário chamado `stock_app`, com acesso ao banco `fs`.

*A senha acima é apenas um exemplo. Você pode escolher outra.*

### 3. Configure as variáveis de ambiente

No Windows:

1. Pesquise por **Editar as variáveis de ambiente do sistema**.
2. Clique em **Variáveis de Ambiente**.
3. Na seção **Variáveis de usuário**, clique em **Novo**.
4. Crie estas duas variáveis:

| Nome da variável | Valor |
|---|---|
| `DB_USER` | `stock_app` |
| `DB_PASSWORD` | `MinhaSenha123!` |

Clique em **OK** para salvar.

**Importante:** feche e abra novamente o VS Code para que ele reconheça as variáveis.

### 4. Execute no VS Code

1. Instale o **JDK 25** e a extensão **Extension Pack for Java** no VS Code.
2. Abra a pasta `StockManagementSystem` no VS Code.
3. Verifique se as bibliotecas JDBC do projeto estão configuradas.
4. Abra `MainFiles/Login.java`.
5. Clique em **Run Java** para iniciar a aplicação.

Pronto! O sistema utilizará automaticamente as credenciais configuradas nas variáveis de ambiente.

**Observação:** o MySQL do XAMPP deve permanecer ligado enquanto o sistema estiver em execução. As variáveis criadas no Windows permanecem configuradas após reiniciar o computador.

Quando estiver usando o sistema!

Perfil	    Login	             Senha
Retailer	  admin	             123
Supplier	  supplier	         123


