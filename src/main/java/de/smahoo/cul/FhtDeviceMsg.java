package de.smahoo.cul;

public class FhtDeviceMsg
  extends DeviceMsg
{
  public static final int DMSGTYPE_ACTUATOR = 1;
  public static final int DMSGTYPE_SYNC = 2;
  public static final int DMSGTYPE_OPERATIONMODE = 62;
  public static final int DMSGTYPE_VACATION_ENDTIME = 63;
  public static final int DMSGTYPE_VACATION_ENDDAY = 64;
  public static final int DMSGTYPE_DESIREDTEMP = 65;
  public static final int DMSGTYPE_TEMPERATURE_LOW = 66;
  public static final int DMSGTYPE_TEMPERATURE_HIGH = 67;
  public static final int DMSGTYPE_BATT_WINDOW = 68;
  public static final int DMSGTYPE_DESIREDTEMP_DAY = 130;
  public static final int DMSGTYPE_DESIREDTEMP_NIGHT = 132;
  public static final int DMSGTYPE_DESIREDTEMP_WINDOW = 138;
  
  public FhtDeviceMsg(String paramString1, String paramString2, int paramInt, DeviceMsgParameter paramDeviceMsgParameter)
  {
    super(paramString1, paramString2, paramInt);
    this.deviceFamily = 3000;
    setMsgParameter(paramDeviceMsgParameter);
  }
  
  public String toString()
  {
    if (getDeviceMsgParameter() == null) {
      return "damn";
    }
    String str = "FHT_" + this.deviceCode;
    int i = getDeviceMsgParameter().getParamType();
    int j = getDeviceMsgParameter().getParam();
    switch (i)
    {
    case 1: 
      str = str + " Actuator = " + 100.0D * (j / 255.0D) + " %";
      break;
    case 2: 
      str = str + " Syncmode";
      break;
    case 62: 
      str = str + " Operation Mode ";
      switch (j)
      {
      case 0: 
        str = str + " auto";
        break;
      case 1: 
        str = str + " manual";
        break;
      case 2: 
        str = str + " vacation";
      }
      break;
    case 63: 
      str = str + " Vacation End Time " + j;
      break;
    case 64: 
      str = str + " Vacation End Day " + j;
      break;
    case 65: 
      str = str + " Target Temperature " + 0.5D * j + " °C";
      break;
    case 66: 
      str = str + " Temperature Low " + j;
      break;
    case 67: 
      str = str + " temperature High" + j;
      break;
    case 68: 
      str = str + " Window ";
      if ((j == 33) || (j == 32)) {
        str = str + "open";
      } else {
        str = str + "closed";
      }
      str = str + " | Battery ";
      if ((j == 1) || (j == 33)) {
        str = str + "low";
      } else {
        str = str + "OK";
      }
      break;
    case 130: 
      str = str + " Target Temperature Day " + j * 0.5D + " °C";
      break;
    case 132: 
      str = str + " Target Temperature Night " + j * 0.5D + " °C";
      break;
    case 138: 
      str = str + " Target Temperature Window " + j * 0.5D + " °C";
    }
    return str;
  }
}
