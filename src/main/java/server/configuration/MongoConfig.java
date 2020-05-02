package server.configuration;

import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import server.utility.EnvironmentalVariables;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    private MongoClientURI uri = new MongoClientURI(EnvironmentalVariables.getMongoConnector());

    private MongoClient mongoClient = MongoClients.create(uri.getURI());
    private MongoDatabase database = mongoClient.getDatabase(uri.getDatabase());

    @Override
    public MongoClient mongoClient() {
        return mongoClient;
    }

    @Override
    protected String getDatabaseName() {
        return database.getName();
    }

}
