document.addEventListener('DOMContentLoaded', () => {
    // URL base da sua API Spring Boot (Rodando em 8080)
    const BASE_URL = 'http://localhost:8080/api';

    // Elementos de Autenticação
    const loginForm = document.getElementById('login-form');
    const loginMessage = document.getElementById('login-message');
    const loginArea = document.getElementById('login-area');
    const dataArea = document.getElementById('data-area');
    const logoutButton = document.getElementById('logout-button');

    // Elementos de Consulta e Resultado
    const resultsTableHead = document.querySelector('#results-table thead tr');
    const resultsTableBody = document.querySelector('#results-table tbody');
    const alunoIdInput = document.getElementById('alunoIdInput');
    const rankingAnoInput = document.getElementById('rankingAnoInput');

    let AUTH_TOKEN = null; // Variável para armazenar o token Base64 (Basic Auth)

    // --- Funções de Autenticação ---

    function setAuthHeader(credentials) {
        return {
            'Authorization': `Basic ${credentials}`,
            'Content-Type': 'application/json'
        };
    }

    function handleLogin(e) {
        e.preventDefault();
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;

        // 1. Codificar credenciais em Base64 (Requisito HTTP Basic)
        const credentials = btoa(`${username}:${password}`);
        
        // 2. Tentar chamar um endpoint protegido para validar o token
        // Usamos um endpoint simples para teste de autenticação
        fetch(`${BASE_URL}/alunos/1/historico`, {
            method: 'GET',
            headers: setAuthHeader(credentials)
        })
        .then(response => {
            if (response.ok || response.status === 200) {
                // Sucesso: Armazenar o token e mostrar a área de dados
                AUTH_TOKEN = credentials;
                loginArea.style.display = 'none';
                dataArea.style.display = 'block';
                loginMessage.textContent = 'Login realizado com sucesso.';
                loginMessage.style.color = 'green';
            } else if (response.status === 401 || response.status === 403) {
                // Falha de Autenticação
                loginMessage.textContent = 'Falha no login. Credenciais inválidas.';
                loginMessage.style.color = 'red';
            } else {
                // Outro erro de servidor (ex: 500)
                loginMessage.textContent = `Erro ${response.status} do servidor. Verifique o console.`;
                loginMessage.style.color = 'red';
            }
        })
        .catch(error => {
            loginMessage.textContent = `Erro de rede: O servidor pode estar offline.`;
            loginMessage.style.color = 'red';
            console.error('Erro de rede:', error);
        });
    }

    function handleLogout() {
        AUTH_TOKEN = null;
        loginArea.style.display = 'block';
        dataArea.style.display = 'none';
        loginForm.reset();
        resultsTableBody.innerHTML = '';
        resultsTableHead.innerHTML = '';
        loginMessage.textContent = 'Sessão encerrada.';
        loginMessage.style.color = 'black';
    }

    // --- Funções de Consulta (Onde a mágica acontece) ---

    // Função auxiliar para acessar propriedades aninhadas (ex.: "disciplina.nome")
    function getNestedValue(obj, path) {
        if (!path) return obj;
        return path.split('.').reduce((acc, key) => (acc && acc[key] !== undefined ? acc[key] : null), obj);
    }

    // Função principal para buscar e exibir dados genéricos em tabela HTML
    function fetchAndDisplay(endpoint, queryParams = {}, headerMapping = []) {
        if (!AUTH_TOKEN) {
            alert('Faça login primeiro.');
            return;
        }

        let url = BASE_URL + endpoint;
        const params = new URLSearchParams(queryParams);
        if (Object.keys(queryParams).length > 0) {
            url += '?' + params.toString();
        }

        // Limpa e exibe status de carregamento
        resultsTableHead.innerHTML = '';
        resultsTableBody.innerHTML = '<tr><td colspan="10">Buscando dados...</td></tr>';

        fetch(url, {
            method: 'GET',
            headers: setAuthHeader(AUTH_TOKEN)
        })
        .then(response => {
            if (response.status === 204) { // No Content
                resultsTableBody.innerHTML = '<tr><td colspan="10">Nenhum resultado encontrado (204 No Content).</td></tr>';
                return null;
            }
            if (!response.ok) {
                throw new Error(`Erro ${response.status}: Falha na consulta.`);
            }
            return response.json();
        })
        .then(data => {
            if (!data) return;

            // 1. Determinar Cabeçalhos
            let headers = headerMapping;
            if (data.length > 0 && typeof data[0] === 'object') {
                 // Usa as chaves do primeiro objeto para o cabeçalho
                 if (headers.length === 0) {
                    headers = Object.keys(data[0]);
                 }
            } else if (data.length > 0 && typeof data[0] !== 'object') {
                 // Caso de lista de strings (ex: Disciplinas Sem Turma)
                 headers = ['Resultado'];
                 data = data.map(item => ({ Resultado: item })); // Envolve em objeto
            }
            
            resultsTableHead.innerHTML = headers.map(h => `<th>${h}</th>`).join('');

            // 2. Preencher o corpo da tabela
            resultsTableBody.innerHTML = '';
                data.forEach(item => {
                const row = resultsTableBody.insertRow();
                headers.forEach(key => {
                    const value = getNestedValue(item, key);
                    // Formatação simples: arredonda números para 2 casas decimais
                    const displayValue = (typeof value === 'number' && key !== 'id') ? value.toFixed(2) : (value ?? 'N/A');
                    row.insertCell().textContent = displayValue;
                });
            });

        })
        .catch(error => {
            resultsTableBody.innerHTML = `<tr><td colspan="10" style="color: red;">Falha ao executar consulta: ${error.message}</td></tr>`;
            console.error('Erro de busca:', error);
        });
    }

    // --- Mapeamento de Eventos e Endpoints (10 Buscas) ---

    // 1. Histórico Detalhado (JOINs Múltiplas)
    document.getElementById('fetch-history-button').addEventListener('click', () => {
        const id = alunoIdInput.value;
        const mapping = ['nomeAluno', 'nomeDisciplina', 'nomeProfessor', 'periodo', 'notaFinal', 'frequencia', 'situacao'];
        fetchAndDisplay(`/alunos/${id}/historico`, {}, mapping);
    });

    // 2. Alunos Regulares (Subquery IN/HAVING)
    document.getElementById('fetch-regulares-button').addEventListener('click', () => {
        fetchAndDisplay(`/alunos/regulares`, {}, ['idAluno', 'nomeAluno']);
    });

    // 3. Média Geral por Turma (Agregada AVG/COUNT)
    document.getElementById('fetch-media-geral-button').addEventListener('click', () => {
        fetchAndDisplay(`/turmas/media-geral`, {}, ['idTurma', 'nomeDisciplina', 'mediaNotas', 'totalAlunos']);
    });
    
    // 4. Ranking de Professores (Agregada COUNT/ORDER BY)
    document.getElementById('fetch-ranking-button').addEventListener('click', () => {
        const ano = rankingAnoInput.value;
        fetchAndDisplay(`/professores/ranking`, { ano: ano }, ['nomeProfessor', 'totalTurmas']);
    });
    
    // 5. Disciplinas Sem Turma (Subquery NOT IN)
    document.getElementById('fetch-sem-turma-button').addEventListener('click', () => {
        fetchAndDisplay(`/disciplinas/sem-turma`); // Projeta lista de Strings
    });

    // 6. Busca por Nome Parcial (Strings LIKE)
    document.getElementById('fetch-busca-parcial-button').addEventListener('click', () => {
        const termo = document.getElementById('disciplinaTermoInput').value;
        // campo em JSON é "cargaHoraria" (atributo da entidade)
        fetchAndDisplay(`/disciplinas/buscar`, { termo: termo }, ['id', 'nome', 'cargaHoraria']);
    });

    // --- Mapeamento das Buscas CRUD Básicas (Exemplo de GET / findAll) ---
    
    // 7. Buscar todos os Professores (GET /professores)
    document.getElementById('fetch-professores-all-button').addEventListener('click', () => {
        fetchAndDisplay(`/professores`, {}, ['id', 'nome', 'cpf', 'departamento']);
    });

    // 8. Buscar todas as Disciplinas (GET /disciplinas)
    document.getElementById('fetch-disciplinas-all-button').addEventListener('click', () => {
        fetchAndDisplay(`/disciplinas`, {}, ['id', 'nome', 'carga']);
    });

    // 9. Buscar todos os Cursos (Implícito)
    document.getElementById('fetch-cursos-all-button').addEventListener('click', () => {
        // campo em JSON é "cargaHoraria" (atributo da entidade)
        fetchAndDisplay(`/cursos`, {}, ['id', 'nome', 'cargaHoraria']);
    });

    // 10. Buscar todas as Turmas (Implícito)
    document.getElementById('fetch-turmas-all-button').addEventListener('click', () => {
        // Exibe dados da turma com campos aninhados (disciplina.nome / professor.nome)
        fetchAndDisplay(`/turmas`, {}, ['id', 'disciplina.nome', 'professor.nome', 'periodo', 'turno']);
    });


    // --- Listener de Eventos ---
    loginForm.addEventListener('submit', handleLogin);
    logoutButton.addEventListener('click', handleLogout);
});