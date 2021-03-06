package de.smahoo.cul;

public class FS20SDeviceMsg
  extends DeviceMsg
{
  int channel;
  int command;
  
  public FS20SDeviceMsg(String paramString1, String paramString2, int paramInt1, int paramInt2)
  {
    super(paramString1, paramString2, 5100);
    this.deviceFamily = 5000;
    this.channel = paramInt1;
    this.command = paramInt2;
  }
  
  public String toString()
  {
    return "FS20_S_" + getDeviceCode() + " Channel " + this.channel + "  Command " + this.command;
  }
  
  public int getCommand()
  {
    return this.command;
  }
  
  public int getChannel()
  {
    return this.channel;
  }
}

