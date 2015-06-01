package com.clouway.db.adapter.mongodb.persistent;
import com.github.fakemongo.Fongo;
import com.mongodb.client.MongoDatabase;

/**
 * Created byivan.genchev1989@gmail.com.
 */
public class FakeBankMongoDatabase {
  private final MongoDatabase db = new Fongo("Bank database").getDatabase("bank");
  public MongoDatabase get() {
    return db;
  }
}
