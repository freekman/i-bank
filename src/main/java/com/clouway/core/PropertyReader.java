package com.clouway.core;

import com.google.inject.Singleton;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created byivan.genchev1989@gmail.com.
 */
@Singleton
public class PropertyReader {
  private Properties properties = new Properties();
  private FileInputStream inputStream;

  public PropertyReader(FileInputStream inputStream) {

    this.inputStream = inputStream;
  }

  public String getStringProperty(String propertyName) {
    if (inputStream == null) {
      setDefaultInputStream();
    }
    try {
      properties.load(inputStream);
      return properties.getProperty(propertyName);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public int getIntProperty(String propertyName) {
    if (inputStream == null) {
      setDefaultInputStream();
    }
    try {
      properties.load(inputStream);
      return Integer.parseInt(properties.getProperty(propertyName));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return 0;
  }

  private void setDefaultInputStream() {
    try {
      inputStream = new FileInputStream("configuration.propertieszzzz");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
}
