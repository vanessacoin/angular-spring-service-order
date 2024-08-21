package com.vanessa.config;

//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;

import javax.sql.DataSource;

//@Configuration
public class DatabaseConfig {

    //@Autowired
    private DataSource dataSource;

    //@Bean
    /*public CommandLineRunner initDatabase() {
        return args -> {
            // Aqui você pode adicionar comandos SQL para criar tabelas ou verificar a conexão
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
    } */
}
