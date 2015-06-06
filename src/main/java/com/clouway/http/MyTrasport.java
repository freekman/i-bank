package com.clouway.http;

import com.clouway.core.SessionRegister;
import com.google.gson.Gson;
import com.google.inject.TypeLiteral;
import com.google.sitebricks.client.Transport;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Set;

/**
 * Created byivan.genchev1989@gmail.com.
 */

public class MyTrasport implements Transport {
  public MyTrasport() {
    System.out.println("I was called");
  }

  @Override
  public <T> T in(InputStream in, Class<T> type) throws IOException {
    String object = "";
    object = getString(in, object);
    System.out.println(object);
    Gson gson = new Gson();
    T t = gson.fromJson(object, type);
    System.out.println();

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    Set<ConstraintViolation<T>> errors;
    errors = validator.validate(t);

    System.out.println();

    return t;
  }

  @Override
  public <T> T in(InputStream in, TypeLiteral<T> type) throws IOException {
    System.out.println("_______2________");

    return null;
  }

  @Override
  public <T> void out(OutputStream out, Class<T> type, T data) throws IOException {
    System.out.println("________3_______");


  }

  private String getString(InputStream in, String object) throws IOException {
    InputStreamReader is = new InputStreamReader(in);
    StringBuilder sb = new StringBuilder();
    BufferedReader br = new BufferedReader(is);
    String read = br.readLine();
    while (read != null) {
      object = read;
      sb.append(read);
      read = br.readLine();
    }
    return object;
  }

  @Override
  public String contentType() {

    return null;
  }
}
