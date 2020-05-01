package server.configuration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    private MongoClientURI uri = new MongoClientURI(
            "mongodb://heroku_n6rx2mxn:21sjf30svfsn1sot8qeni9bpi1@ds211168.mlab.com:11168/heroku_n6rx2mxn");

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
