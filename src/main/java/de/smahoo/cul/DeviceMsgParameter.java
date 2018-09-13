package de.smahoo.cul;

public class DeviceMsgParameter
{
  int paramType;
  int param;
  
  public DeviceMsgParameter(int paramInt1, int paramInt2)
  {
    this.paramType = paramInt1;
    this.param = paramInt2;
  }
  
  public int getParamType()
  {
    return this.paramType;
  }
  
  public int getParam()
  {
    return this.param;
  }
}
