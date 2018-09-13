package de.smahoo.cul;

import java.io.PrintStream;

public class DataEncoder
{
  public DeviceMsg evaluateLine(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    if (paramString.length() < 3) {
      return null;
    }
    int i = paramString.charAt(0);
    switch (i)
    {
    case 84: 
      if (paramString.length() == 13) {
        return evaluateFHT(paramString);
      }
      if (paramString.length() == 11) {
        return evaluateFHTTK(paramString);
      }
      break;
    case 75: 
      if (paramString.length() == 11) {
        return evaluateWS(paramString);
      }
      break;
    case 72: 
      return evaluateHMS(paramString);
    case 70: 
      return evaluateFS20(paramString);
    case 69: 
      return evaluateEm(paramString);
    }
    return null;
  }
  
  protected DeviceMsg evaluateFS20_Sensor_Motion(String paramString)
  {
    String str1 = "";
    String str2 = "";
    String str3 = "";
    int i = -1;
    int j = -1;
    try
    {
      str1 = paramString.substring(1, 5);
      str2 = String.valueOf(paramString.charAt(5)) + String.valueOf(paramString.charAt(6));
      str3 = String.valueOf(paramString.charAt(7)) + String.valueOf(paramString.charAt(8));
      i = Integer.parseInt(str2, 16);
      if (i > 128) {
        i = 192 - i;
      }
      j = Integer.parseInt(str3, 16);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return new Fs20sMdDeviceMsg(paramString, str1, i, j);
  }
  
  protected DeviceMsg evaluateEm(String paramString)
  {
    String str1 = paramString;
    String str2 = "";
    int i = 0;
    int j = 0;
    int k = 0;
    int m = 0;
    try
    {
      String str3 = String.valueOf(paramString.charAt(1)) + String.valueOf(paramString.charAt(2));
      i = Integer.parseInt(str3);
      str2 = String.valueOf(paramString.charAt(3)) + String.valueOf(paramString.charAt(4));
      str3 = String.valueOf(paramString.charAt(9)) + String.valueOf(paramString.charAt(10)) + String.valueOf(paramString.charAt(7)) + String.valueOf(paramString.charAt(8));
      j = Integer.parseInt(str3, 16);
      str3 = String.valueOf(paramString.charAt(13)) + String.valueOf(paramString.charAt(14)) + String.valueOf(paramString.charAt(11)) + String.valueOf(paramString.charAt(12));
      k = Integer.parseInt(str3, 16);
      str3 = String.valueOf(paramString.charAt(17)) + String.valueOf(paramString.charAt(18)) + String.valueOf(paramString.charAt(15)) + String.valueOf(paramString.charAt(16));
      m = Integer.parseInt(str3, 16);
    }
    catch (Exception localException)
    {
      System.out.println("Unable to interpret '" + paramString + "'");
      localException.printStackTrace();
    }
    EmDeviceMessage localEmDeviceMessage = new EmDeviceMessage(str1, str2, i, j, k, m);
    return localEmDeviceMessage;
  }
  
  protected DeviceMsg evaluateFS20(String paramString)
  {
    if (paramString.length() == 13) {
      return evaluateFS20_Sensor_Motion(paramString);
    }
    String str1 = "";
    String str2 = "";
    String str3 = "";
    int i = -1;
    int j = -1;
    try
    {
      str1 = paramString.substring(1, 5);
      str2 = String.valueOf(paramString.charAt(5)) + String.valueOf(paramString.charAt(6));
      str3 = String.valueOf(paramString.charAt(7)) + String.valueOf(paramString.charAt(8));
      i = Integer.parseInt(str2, 16);
      if (i > 128) {
        i = 192 - i;
      }
      j = Integer.parseInt(str3);
    }
    catch (Exception localException)
    {
      System.out.println("Unable to evaluate Line '" + paramString + "'");
      localException.printStackTrace();
    }
    return new FS20SDeviceMsg(paramString, str1, i, j);
  }
  
  protected DeviceMsg evaluateHMS(String paramString)
  {
    String str1 = "";
    String str2 = "";
    String str3 = "";
    try
    {
      str1 = paramString.substring(1, 5);
      str2 = String.valueOf(paramString.charAt(10)) + String.valueOf(paramString.charAt(7)) + "." + String.valueOf(paramString.charAt(8));
      str3 = String.valueOf(paramString.charAt(11)) + String.valueOf(paramString.charAt(12)) + "." + String.valueOf(paramString.charAt(9));
    }
    catch (Exception localException1)
    {
      System.out.println("Unable to evaluate HMS DataLine (" + paramString + ")");
      return null;
    }
    double d1 = 0.0D;
    double d2 = 0.0D;
    boolean bool = false;
    try
    {
      bool = paramString.charAt(6) != '2';
      d1 = Double.parseDouble(str2);
      d2 = Double.parseDouble(str3);
      if (paramString.charAt(5) == '8') {
        d1 = -1.0D * d1;
      }
    }
    catch (Exception localException2)
    {
      localException2.printStackTrace();
    }
    return new HmsDeviceMsg(paramString, str1, 4001, d1, d2, bool);
  }
  
  protected DeviceMsg evaluateFHT(String paramString)
  {
    String str1 = "";
    String str2 = "";
    String str3 = "";
    String str4 = "";
    try
    {
      str1 = String.valueOf(paramString.charAt(1)) + String.valueOf(paramString.charAt(2)) + String.valueOf(paramString.charAt(3)) + String.valueOf(paramString.charAt(4));
      str2 = String.valueOf(paramString.charAt(5)) + String.valueOf(paramString.charAt(6));
      str3 = String.valueOf(paramString.charAt(7)) + String.valueOf(paramString.charAt(8));
      str4 = String.valueOf(paramString.charAt(9)) + String.valueOf(paramString.charAt(10));
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      return null;
    }
    int i = -1;
    if (str3.equals("69")) {
      i = Integer.parseInt(str2, 16);
    } else if (str2.toLowerCase().equals("00")) {
      if (str3.toLowerCase().equals("2c")) {
        i = 2;
      } else {
        i = 1;
      }
    }
    int j;
    try
    {
      j = Integer.parseInt(str4, 16);
    }
    catch (Exception localException2)
    {
      localException2.printStackTrace();
      return null;
    }
    FhtDeviceMsg localFhtDeviceMsg = new FhtDeviceMsg(paramString, str1, 3001, new DeviceMsgParameter(i, j));
    return localFhtDeviceMsg;
  }
  
  protected DeviceMsg evaluateFHTTK(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    if (paramString.length() < 8) {
      return null;
    }
    String str = "";
    str = paramString.substring(1, 7);
    boolean bool = paramString.charAt(8) == '1';
    return new FhttkDeviceMsg(paramString, str, bool);
  }
  
  protected DeviceMsg evaluateWS(String paramString)
  {
    DeviceMsg localDeviceMsg = null;
    int i = 0;
    try
    {
      i = Integer.parseInt(String.valueOf(paramString.charAt(2)));
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return null;
    }
    switch (i)
    {
    case 1: 
      localDeviceMsg = evaluateWS_S300TH(paramString);
    }
    return localDeviceMsg;
  }
  
  private DeviceMsg evaluateWS_S300TH(String paramString)
  {
    double d1 = 0.0D;
    double d2 = 0.0D;
    int i = -1;
    try
    {
      i = Integer.parseInt(String.valueOf(paramString.charAt(1)), 16) + 1;
      String str = String.valueOf(paramString.charAt(6)) + String.valueOf(paramString.charAt(3)) + "." + String.valueOf(paramString.charAt(4));
      d1 = Double.parseDouble(str);
      if (i > 8)
      {
        d1 *= -1.0D;
        i -= 8;
      }
      str = String.valueOf(paramString.charAt(7)) + String.valueOf(paramString.charAt(8)) + "." + String.valueOf(paramString.charAt(5));
      d2 = Double.parseDouble(str);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return new WsDeviceMsg(paramString, "" + i, 1002, d1, d2);
  }
}
