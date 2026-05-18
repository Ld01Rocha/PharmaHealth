# PharmaHealth 💊

## 📝 Sobre o Projeto
O PharmaHealth é um aplicativo móvel desenvolvido para auxiliar o gerenciamento de medicamentos, permitindo que usuários ou cuidadores de Pessoas com Deficiência (PcD) cadastrem remédios e agendem alertas sonoros. Uma solução inclusiva para garantir a adesão correta aos tratamentos de saúde.

---

## 🚀 Funcionalidades Principais
* **Cadastro de Medicamentos:** Organização por nome, categoria, dosagem e frequência.
* **Alertas Sonoros:** Notificações programadas para os horários de cada remédio.
* **Painel do Paciente:** Dashboard web integrado para monitoramento e histórico de medições.
* **Gestão de Dependentes:** Opção para cuidadores administrarem múltiplos perfis.

---

## 🛠️ Tecnologias Utilizadas
* **Mobile:** Android / iOS (Interface do Usuário)
* **Web (Dashboard):** PHP, HTML5, CSS3, JavaScript
* **Banco de Dados:** MySQL (Integração via `conexao.php`)

---

## 🔧 Como Executar a Versão Web (Dashboard)
1. Certifique-se de ter um servidor local (como XAMPP) ou uma hospedagem ativa (como InfinityFree).
2. Clone ou cole os arquivos do projeto no diretório do servidor (`htdocs` ou `public_html`).
3. Configure o arquivo `conexao.php` com as credenciais do seu banco de dados MySQL.
4. Importe a estrutura das tabelas (`tb_usuarios`, `tb_medicamentos`) no seu banco.
5. Acesse `index.php` pelo navegador para realizar o login e acessar o sistema.
