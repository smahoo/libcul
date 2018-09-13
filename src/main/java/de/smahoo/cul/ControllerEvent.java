package de.smahoo.cul;

public class ControllerEvent
{
  public static final int ERROR = -1;
  public static final int ERROR_CONNECTION_LOST = -301;
  public static final int CONNECTING = 300;
  public static final int CONNECTED = 301;
  public static final int DISCONNECTING = 302;
  public static final int DISCONNECTED = 303;
  int eventType = -1;
  String msg;
  
  public ControllerEvent(int paramInt, String paramString)
  {
    this.msg = paramString;
    this.eventType = paramInt;
  }
  
  public String getMessage()
  {
    return this.msg;
  }
  
  public int getEventType()
  {
    return this.eventType;
  }
  
  public String toString()
  {
    return this.msg;
  }
}
