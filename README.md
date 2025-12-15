# Calculadora de IMC Avançada

Este é um aplicativo Android nativo desenvolvido como parte do trabalho da disciplina de Programação para Dispositivos Móveis. O projeto vai além de uma simples calculadora de IMC, incorporando funcionalidades de saúde adicionais, persistência de dados, uma arquitetura moderna e uma interface de usuário reativa construída com Jetpack Compose.

## Funcionalidades

- **Cálculo de IMC:** Cálculo padrão do Índice de Massa Corporal com classificação textual (Abaixo do peso, Normal, etc.).
- **Cálculos Adicionais de Saúde:**
  - **Taxa Metabólica Basal (TMB):** Estimativa de calorias queimadas em repouso (Fórmula de Mifflin-St Jeor).
  - **Peso Ideal:** Estimativa de uma faixa de peso saudável (Fórmula de Devine).
  - **Necessidade Calórica Diária:** Estimativa de calorias diárias necessárias com base na TMB e no nível de atividade.
- **Histórico de Medições:**
  - Salva automaticamente cada medição em um banco de dados local.
  - Tela de histórico para visualizar todas as medições passadas, ordenadas por data.
  - Tela de detalhes para cada registro, mostrando todos os indicadores calculados.
- **Visualização Gráfica:** Gráfico na tela de histórico que mostra a evolução do IMC ao longo do tempo.
- **Interface Moderna e Reativa:** UI construída inteiramente com Jetpack Compose, com validação de entrada em tempo real.
- **Página de Ajuda:** Uma tela dedicada que explica cada métrica de saúde e as fórmulas utilizadas.

## Telas

| Tela Principal (Entrada) | Tela Principal (Resultado) | Tela de Histórico |
| :---: | :---: | :---: |
| *(Adicione um screenshot aqui)* | *(Adicione um screenshot aqui)* | *(Adicione um screenshot aqui)* |

| Tela de Detalhes | Tela de Ajuda |
| :---: | :---: |
| *(Adicione um screenshot aqui)* | *(Adicione um screenshot aqui)* |

## Arquitetura e Tecnologias

O projeto foi estruturado seguindo as melhores práticas do desenvolvimento Android moderno, com foco na separação de responsabilidades e na testabilidade.

- **Arquitetura:** MVVM (Model-View-ViewModel).
  - **View:** Camada de apresentação reativa, construída 100% com **Jetpack Compose**.
  - **ViewModel:** Gerencia o estado da UI e serve como intermediário entre a View e a camada de dados.
  - **Model:** Representa a camada de dados, consistindo no Repositório e nas fontes de dados.

- **Gerenciamento de Estado:** Utiliza `StateFlow` e `mutableStateOf` do Compose para garantir que a UI sempre reflita o estado atual dos dados.

- **Persistência de Dados:** **Room**, a biblioteca de persistência recomendada pelo Google, para criar e gerenciar o banco de dados SQLite local.

- **Navegação:** **Jetpack Navigation para Compose** para gerenciar o fluxo de navegação entre as telas de forma declarativa.

- **Injeção de Dependência:** Uma abordagem simplificada usando a classe `Application` como um contêiner de dependências para fornecer instâncias únicas do `Repository` e do `Database`.

- **Visualização de Dados:** **Vico**, uma biblioteca de gráficos moderna e flexível para Jetpack Compose.

- **Linguagem:** **Kotlin**, utilizando funcionalidades como Coroutines e Flow para operações assíncronas.

## Como Executar o Projeto

1.  **Clone o repositório:**
    ```bash
    git clone [URL do seu repositório GitHub aqui]
    ```
2.  **Abra no Android Studio:**
    - Abra o Android Studio (versão Hedgehog ou mais recente).
    - Selecione "Open an Existing Project" e navegue até a pasta do projeto clonado.
3.  **Sincronize o Gradle:** O Android Studio deve sincronizar o projeto automaticamente. Se não, clique em "Sync Project with Gradle Files".
4.  **Execute o aplicativo:**
    - Selecione um emulador ou conecte um dispositivo físico.
    - Clique em "Run 'app'".
