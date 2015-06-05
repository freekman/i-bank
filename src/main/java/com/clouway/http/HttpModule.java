package com.clouway.http;

import com.clouway.core.*;
import com.clouway.db.adapter.mongodb.persistent.PersistentSessionRepository;
import com.clouway.db.adapter.mongodb.persistent.PersistentUserRepository;
import com.clouway.validator.SimpleValidator;
import com.clouway.validator.TransactionValidator;
import com.clouway.validator.UserFormValidator;
import com.clouway.validator.Validator;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

/**
 * Created byivan.genchev1989@gmail.com.
 */
public class HttpModule extends ServletModule {

  private PropertyReader reader;

  public HttpModule(PropertyReader reader) {

    this.reader = reader;
  }

  @Override
  protected void configureServlets() {
    bind(CurrentUser.class).to(BankCurrentUser.class);
    bind(TransactionExecutor.class).to(BankTransactionExecutor.class);
    bind(SimpleValidator.class).to(TransactionValidator.class);
    bind(SidProvider.class).to(CurrentSidProvider.class);
    bind(SessionFinder.class).to(PersistentSessionRepository.class);
    bind(SessionRegister.class).to(PersistentSessionRepository.class);
    bind(UserAuthenticator.class).to(BankUserAuthenticator.class);
    bind(Validator.class).to(UserFormValidator.class);
    bind(UserFinder.class).to(PersistentUserRepository.class);
    bind(UserRegister.class).to(PersistentUserRepository.class);
    bind(AccountRegister.class).to(PersistentUserRepository.class);
    filter("/home", "/login", "/info", "/bank").through(SecurityFilter.class);
  }


  @Provides
  @Singleton
  MongoClient provideMongoClient() {
    MongoClient client = new MongoClient(reader.getStringProperty("db.host"), reader.getIntProperty("db.port"));
    return client;
  }

  @Provides
  MongoDatabase provideMongoDatabase(MongoClient client) {
    return client.getDatabase(reader.getStringProperty("db.name"));
  }
}
