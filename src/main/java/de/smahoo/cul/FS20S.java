package de.smahoo.cul;

public class FS20S
  extends Device
{
  public static final int CHANNELSTATE_UNKNOWN = -1;
  public static final int CHANNELSTATE_OFF = 0;
  public static final int CHANNELSTATE_ON = 1;
  public static final int CHANNELSTATE_DIMM_UP = 13;
  public static final int CHANNELSTATE_DIMM_DOWN = 14;
  public static final int CHANNELSTATE_PROGRAM = 16;
  public static final int CHANNELSTATE_SWITCH = 3;
  protected static final int MAX_CHANNELS = 36;
  protected int channelCnt = 2;
  protected int[] channelStates = new int[36];
  
  public FS20S(String paramString1, String paramString2)
  {
    super(paramString1, paramString2);
    for (int i = 0; i < 36; i++) {
      this.channelStates[i] = -1;
    }
  }
  
  public int getState(int paramInt)
  {
    if ((paramInt >= 0) && (paramInt < this.channelCnt)) {
      return this.channelStates[paramInt];
    }
    return -1;
  }
  
  public int getChannels()
  {
    return this.channelCnt;
  }
  
  protected boolean evaluateMsg(DeviceMsg paramDeviceMsg)
  {
    int i = -1;
    int j = -1;
    FS20SDeviceMsg localFS20SDeviceMsg = (FS20SDeviceMsg)paramDeviceMsg;
    j = localFS20SDeviceMsg.getCommand();
    i = localFS20SDeviceMsg.getChannel();
    setChannelState(i, j);
    return true;
  }
  
  protected void setChannelState(int paramInt1, int paramInt2)
  {
    if (paramInt1 > 36) {
      return;
    }
    if (paramInt1 < 0) {
      return;
    }
    if (paramInt1 >= this.channelCnt) {
      setChannels(paramInt1 + 1);
    }
    if (this.channelStates[paramInt1] != paramInt2)
    {
      this.channelStates[paramInt1] = paramInt2;
      dispatchDeviceEvent(new DeviceEvent(3, this));
    }
  }
  
  public static String getStateDescription(int paramInt)
  {
    switch (paramInt)
    {
    case -1: 
      return "Unkown";
    case 0: 
      return "Off";
    case 1: 
      return "On";
    case 13: 
      return "Dimm Up";
    case 14: 
      return "Dimm Down";
    case 3: 
      return "Switch";
    }
    return "Unknown State";
  }
  
  protected void setChannels(int paramInt)
  {
    this.channelCnt = paramInt;
  }
  
  public int getDeviceType()
  {
    return 5100;
  }
  
  public int getDeviceFamily()
  {
    return 5000;
  }
}
