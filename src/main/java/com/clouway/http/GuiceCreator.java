package com.clouway.http;

import com.clouway.core.*;
import com.clouway.db.adapter.mongodb.persistent.PersistentSessionRepository;
import com.clouway.db.adapter.mongodb.persistent.PersistentUserRepository;
import com.clouway.validator.SimpleValidator;
import com.clouway.validator.TransactionValidator;
import com.clouway.validator.UserFormValidator;
import com.clouway.validator.Validator;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.google.sitebricks.SitebricksModule;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

/**
 * Created byivan.genchev1989@gmail.com.
 */
public class GuiceCreator extends GuiceServletContextListener {
  @Override
  protected Injector getInjector() {
    return Guice.createInjector(new ServletModule() {
      @Override
      protected void configureServlets() {
        bind(BankUser.class).to(CurrentBankUser.class);
        bind(TransactionManager.class).to(BankTransactionManager.class);
        bind(SimpleValidator.class).to(TransactionValidator.class);
        bind(SidProvider.class).to(UserSidProvider.class);
        bind(SessionFinder.class).to(PersistentSessionRepository.class);
        bind(SessionRegister.class).to(PersistentSessionRepository.class);
        bind(Authenticator.class).to(UserAuthenticator.class);
        bind(Validator.class).to(UserFormValidator.class);
        bind(UserFinder.class).to(PersistentUserRepository.class);
        bind(UserRegister.class).to(PersistentUserRepository.class);
        filter("/home", "/login", "/info", "/bank").through(SecurityFilter.class);
      }

      @Provides
      @Singleton
      MongoClient provideMongoClient() {
        MongoClient client = new MongoClient();
        return client;
      }

      @Provides
      MongoDatabase provideMongoDatabase(MongoClient client) {
        return client.getDatabase("bank");
      }
    }, new SitebricksModule() {
      @Override
      protected void configureSitebricks() {
        at("/info").show(LoginPage.class);
        scan(RegistrationPage.class.getPackage());
      }
    });
  }

}
