package com.clouway.core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created byivan.genchev1989@gmail.com.
 */
public class PropertyReader {

  private Properties properties = new Properties();
  public FileInputStream in = null;

  public String getStringProperty(String propertyName) {
    try {
      in = new FileInputStream("configuration.properties");
      properties.load(in);
      return properties.getProperty(propertyName);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
  public int getIntProperty(String propertyName){
    try {
      in = new FileInputStream("configuration.properties");
      properties.load(in);
      return Integer.parseInt(properties.getProperty(propertyName));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return 0;
  }
}
