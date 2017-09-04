package com.mechanitis.mongodb.gettingstarted.person;

import com.mongodb.DBObject;
import org.bson.Document;

/**
 * This Adaptor allows us to separate our domain object, Person, from our library-specific classes, in this case the MongoDB-specific
 * DBObject.
 */
public final class PersonAdaptor {
    /**
     * @deprecated use {@link #toDocument(Person)} instead
     */
    public static final DBObject toDBObject(Person person) {
        throw new UnsupportedOperationException("You need to implement this");
    }

    public static final Document toDocument(Person person) {
        Document address = new Document().append("street", person.getAddress().getStreet())
                .append("city", person.getAddress().getTown())
                .append("phone", person.getAddress().getPhone());
        return new Document().append("_id", person.getId())
                .append("name", person.getName())
                .append("address", address)
                .append("books", person.getBookIds());
    }
}
