package br.com.joandersong.inkwithin.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource dataSource() throws Exception {
        // Conecta ao Secrets Manager
        SecretsManagerClient client = SecretsManagerClient.builder()
                .region(Region.US_EAST_1) // ajuste a região
                .build();

        // Busca o segredo
        GetSecretValueRequest request = GetSecretValueRequest.builder()
                .secretId("iw-admin-access") // nome ou ARN do segredo
                .build();

        GetSecretValueResponse response = client.getSecretValue(request);

        // Converte JSON para Map
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> secret = mapper.readValue(response.secretString(), Map.class);

        // Monta DataSource
        String host = secret.get("host");
        String port = String.valueOf(secret.get("port"));
        String dbname = secret.get("dbname") != null ? secret.get("dbname") : "postgres";
        return DataSourceBuilder.create()
                .url("jdbc:postgresql://" + host + ":" + port + "/" + dbname)
                .username(secret.get("username"))
                .password(secret.get("password"))
                .driverClassName("org.postgresql.Driver")
                .build();
    }

}
