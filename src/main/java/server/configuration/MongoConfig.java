package server.configuration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

@Configuration
@PropertySource(value = "classpath:mongo.properties", ignoreResourceNotFound = true)
public class MongoConfig extends AbstractMongoClientConfiguration {
    private MongoClient mongoClient;
    private MongoDatabase database;

    @Override
    public MongoClient mongoClient() {
        return mongoClient;
    }

    @Override
    protected String getDatabaseName() {
        return database.getName();
    }

    public MongoConfig(@Value("${mongo.address:#{environment.MONGO_ADDRESS}}") String url) {
        this.mongoClient = MongoClients.create(url);
        database = mongoClient.getDatabase("voipServerDB");
    }
}
