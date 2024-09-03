package com.vanessa.serviceorder.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.cdimascio.dotenv.Dotenv;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.jdbc.DataSourceBuilder;

import java.sql.SQLException;
import java.sql.Connection;
import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Bean
    public DataSource dataSource() {
        // Carrega as variáveis de ambiente do arquivo .env
        Dotenv dotenv = Dotenv.load();

        return DataSourceBuilder.create()
                .url(dotenv.get("DB_URL"))
                .username(dotenv.get("DB_USERNAME"))
                .password(dotenv.get("DB_PASSWORD"))
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();
    }

    @Bean
    public CommandLineRunner initDatabase(DataSource dataSource) {
        return args -> {
            // criar tabelas ou verificar a conexão
            try (Connection connection = dataSource.getConnection()) {
                System.out.println("Conectado ao banco de dados: " + connection.getCatalog());
                // Exemplo de criação de tabela
                String sql = "CREATE TABLE IF NOT EXISTS example_table (id BIGINT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255) NOT NULL)";
                connection.createStatement().execute(sql);
                System.out.println("Tabela 'example_table' verificada/criada.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        };
    } 
}
