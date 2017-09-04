package com.mechanitis.mongodb.gettingstarted;

import com.mechanitis.mongodb.gettingstarted.person.Address;
import com.mechanitis.mongodb.gettingstarted.person.Person;
import com.mechanitis.mongodb.gettingstarted.person.PersonAdaptor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.UnknownHostException;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@SuppressWarnings("unchecked")
public class Exercise4RetrieveTest {
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    @Before
    public void setUp() throws UnknownHostException {
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        database = mongoClient.getDatabase("Examples");
        collection = database.getCollection("people");
    }

    @Test
    public void shouldRetrieveBobFromTheDatabaseWhenHeIsTheOnlyOneInThere() {
        // Given
        Person bob = new Person("bob", "Bob The Amazing", new Address("123 Fake St", "LondonTown", 1234567890), asList(27464, 747854));
        collection.insertOne(PersonAdaptor.toDocument(bob));

        // When
        // TODO: get this from querying the collection.  Hint: you can find just one
        Document result = null;

        // Then
        assertThat((String) result.get("_id"), is("bob"));
    }

    @Test
    public void shouldRetrieveEverythingFromTheDatabase() {
        // Given
        Person charlie = new Person("charlie", "Charles", new Address("74 That Place", "LondonTown", 1234567890), asList(1, 74));
        collection.insertOne(PersonAdaptor.toDocument(charlie));

        Person bob = new Person("bob", "Bob The Amazing", new Address("123 Fake St", "LondonTown", 1234567890), asList(27464, 747854));
        collection.insertOne(PersonAdaptor.toDocument(bob));

        // When
        // TODO: get a cursor with everything in the database
        MongoCursor<Document> cursor = null;

        // Then
        assertThat(collection.count(), is(2L));
        // they should come back in the same order they were put in
        assertThat((String) cursor.next().get("_id"), is("charlie"));
        assertThat((String) cursor.next().get("_id"), is("bob"));
    }

    @Test
    public void shouldSearchForAndReturnOnlyBobFromTheDatabaseWhenMorePeopleExist() {
        // Given
        Person charlie = new Person("charlie", "Charles", new Address("74 That Place", "LondonTown", 1234567890), asList(1, 74));
        collection.insertOne(PersonAdaptor.toDocument(charlie));

        Person bob = new Person("bob", "Bob The Amazing", new Address("123 Fake St", "LondonTown", 1234567890), asList(27464, 747854));
        collection.insertOne(PersonAdaptor.toDocument(bob));

        // When
        // TODO create the query document
        Document query = null;
        FindIterable<Document> cursor = collection.find(query);

        // Then
        assertThat(collection.count(new Document("_id", "bob")), is(1L));
        assertThat((String) cursor.first().get("name"), is("Bob The Amazing"));
    }

    @After
    public void tearDown() {
        database.drop();
    }
}
