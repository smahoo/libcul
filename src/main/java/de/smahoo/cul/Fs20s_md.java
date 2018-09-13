package de.smahoo.cul;

import java.util.Timer;
import java.util.TimerTask;

public class Fs20s_md
  extends Device
{
  public static final int CHANNELSTATE_UNKNOWN = -1;
  public static final int CHANNELSTATE_MOTION = 58;
  protected static final int MAX_CHANNELS = 36;
  protected int channelCnt = 2;
  protected int[] channelStates = new int[36];
  protected TimerTask timerTask = null;
  protected Timer timer = null;
  
  public Fs20s_md(String paramString1, String paramString2)
  {
    super(paramString1, paramString2);
    for (int i = 0; i < 36; i++) {
      this.channelStates[i] = -1;
    }
    this.timer = new Timer();
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
    Fs20sMdDeviceMsg localFs20sMdDeviceMsg = (Fs20sMdDeviceMsg)paramDeviceMsg;
    j = localFs20sMdDeviceMsg.getCommand();
    i = localFs20sMdDeviceMsg.getChannel();
    setChannelState(0, j);
    if (this.timerTask != null) {
      try
      {
        this.timerTask.cancel();
      }
      catch (Exception localException) {}
    }
    this.timerTask = new TimerTask()
    {
      public void run()
      {
        Fs20s_md.this.setChannelState(0, 0);
      }
    };
    this.timer.schedule(this.timerTask, 30000L);
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
    case 58: 
      return "Motion detected";
    }
    return "Unknown State";
  }
  
  protected void setChannels(int paramInt)
  {
    this.channelCnt = paramInt;
  }
  
  public int getDeviceType()
  {
    return 5130;
  }
  
  public int getDeviceFamily()
  {
    return 5000;
  }
}

