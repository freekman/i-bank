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

  private  FileInputStream in;

  public PropertyReader() {
    System.out.println("FileReader is called and input stream is"+Main.prop);
    in=Main.prop;
  }

  private Properties properties = new Properties();

  public String getStringProperty(String propertyName) {
    if (in == null) {
      System.out.println("FIle input stream is null!~ String");
      setDefaultInputStream();
    }
    try {
      properties.load(in);
      return properties.getProperty(propertyName);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public int getIntProperty(String propertyName) {
    if (in == null) {
      System.out.println("FIle input stream is null!~Int");
      setDefaultInputStream();
    }
    try {
      properties.load(in);
      return Integer.parseInt(properties.getProperty(propertyName));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return 0;
  }

  private void setDefaultInputStream() {
    try {
      in = new FileInputStream("configuration.properties");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
}
