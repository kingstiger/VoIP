package server.configuration;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import server.utility.EnvironmentalVariables;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    private MongoClient mongoClient = MongoClients.create(new ConnectionString(EnvironmentalVariables.getMongoConnector()));
    private MongoDatabase database = mongoClient.getDatabase("voipServerDB");

    @Override
    public MongoClient mongoClient() {
        return mongoClient;
    }

    @Override
    protected String getDatabaseName() {
        return database.getName();
    }
}
