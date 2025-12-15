package com.example.calculadoraimc

import android.app.Application
import com.example.calculadoraimc.data.local.AppDatabase
import com.example.calculadoraimc.data.repository.IMCHistoryRepository

/** Gemini - início
 * Prompt: "Crie uma classe Application para servir como um contêiner de dependências. Ela deve instanciar o banco de dados (AppDatabase) e o repositório (IMCHistoryRepository) usando 'lazy delegate' para garantir que sejam criados apenas uma vez, quando forem necessários."
 */
/**
 * Classe de Application customizada para o app Calculadora de IMC.
 *
 * Esta classe serve como um contêiner de injeção de dependência em nível de aplicativo.
 * Ela é responsável por inicializar e fornecer instâncias únicas (Singleton) do banco de dados
 * e do repositório, que podem ser acessadas por qualquer parte do aplicativo que tenha acesso
 * ao contexto da aplicação.
 *
 * O uso de `by lazy` garante que a criação do banco de dados (`AppDatabase`) e do repositório
 * (`IMCHistoryRepository`) seja adiada até o primeiro acesso, o que é uma otimização de performance
 * durante a inicialização do aplicativo.
 */
class IMCApplication : Application() {
    /**
     * Instância única do banco de dados Room para o aplicativo.
     * A inicialização é feita de forma "preguiçosa" (lazy), ou seja, o banco de dados só é criado
     * na primeira vez que esta propriedade é acessada.
     */
    val database by lazy { AppDatabase.getDatabase(this) }

    /**
     * Instância única do repositório de histórico de IMC.
     * Depende da instância do `database` e também é inicializado de forma "preguiçosa".
     * Este repositório serve como a única fonte de verdade para os dados do histórico de IMC.
     */
    val repository by lazy { IMCHistoryRepository(database.imcHistoryDao()) }
}
/** Gemini - final */
