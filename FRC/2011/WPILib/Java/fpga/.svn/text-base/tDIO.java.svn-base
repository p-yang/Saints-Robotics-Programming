// Copyright (c) National Instruments 2009.  All Rights Reserved.
// Do Not Edit... this file is generated!

package edu.wpi.first.wpilibj.fpga;

import com.ni.rio.*;

public class tDIO extends tSystem
{

   public tDIO(final int sys_index)
   {
      super();
      m_SystemIndex = sys_index;
      if (status.isNotFatal() && m_SystemIndex >= kNumSystems)
      {
         status.setStatus(NiRioStatus.kRIOStatusBadSelector);
      }
   }

   protected void finalize()
   {
      super.finalize();
   }

   public int getSystemIndex()
   {
      return m_SystemIndex;
   }

   public static final int kNumSystems = 2;
   public final int m_SystemIndex;



//////////////////////////////////////////////////////////////////////////////////////////////////
// Accessors for DO
//////////////////////////////////////////////////////////////////////////////////////////////////
   private static final int kDIO0_DO_Address = 0x8360;
   private static final int kDIO1_DO_Address = 0x82F4;
   private static final int kDO_Addresses [] =
   {
      kDIO0_DO_Address,
      kDIO1_DO_Address,
   };

   public void writeDO(final int value)
   {
      NiRioSrv.poke32(m_DeviceHandle, kDO_Addresses[m_SystemIndex], value, status);
   }
   public int readDO()
   {
      return (int)((NiRioSrv.peek32(m_DeviceHandle, kDO_Addresses[m_SystemIndex], status)) & 0x0000FFFF);
   }

//////////////////////////////////////////////////////////////////////////////////////////////////
// Accessors for I2CDataToSend
//////////////////////////////////////////////////////////////////////////////////////////////////
   private static final int kDIO0_I2CDataToSend_Address = 0x8328;
   private static final int kDIO1_I2CDataToSend_Address = 0x82BC;
   private static final int kI2CDataToSend_Addresses [] =
   {
      kDIO0_I2CDataToSend_Address,
      kDIO1_I2CDataToSend_Address,
   };

   public void writeI2CDataToSend(final long value)
   {
      NiRioSrv.poke32(m_DeviceHandle, kI2CDataToSend_Addresses[m_SystemIndex], (int)(value), status);
   }
   public long readI2CDataToSend()
   {
      return (long)((NiRioSrv.peek32(m_DeviceHandle, kI2CDataToSend_Addresses[m_SystemIndex], status)) & 0xFFFFFFFFl);
   }

//////////////////////////////////////////////////////////////////////////////////////////////////
// Accessors for FilterSelect
//////////////////////////////////////////////////////////////////////////////////////////////////
   public static final int kFilterSelect_NumElements = 16;
   public static final int kFilterSelect_ElementSize = 2;
   public static final int kFilterSelect_ElementMask = 0x3;
   private static final int kDIO0_FilterSelect_Address = 0x8300;
   private static final int kDIO1_FilterSelect_Address = 0x8294;
   private static final int kFilterSelect_Addresses [] =
   {
      kDIO0_FilterSelect_Address,
      kDIO1_FilterSelect_Address,
   };

   public void writeFilterSelect(final int bitfield_index, final int value)
   {
      if (status.isNotFatal() && bitfield_index >= kFilterSelect_NumElements)
      {
         status.setStatus(NiRioStatus.kRIOStatusBadSelector);
      }
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kFilterSelect_Addresses[m_SystemIndex], status);
      regValue &= ~(kFilterSelect_ElementMask << ((kFilterSelect_NumElements - 1 - bitfield_index) * kFilterSelect_ElementSize));
      regValue |= ((value & kFilterSelect_ElementMask) << ((kFilterSelect_NumElements - 1 - bitfield_index) * kFilterSelect_ElementSize));
      NiRioSrv.poke32(m_DeviceHandle, kFilterSelect_Addresses[m_SystemIndex], regValue, status);
   }
   public byte readFilterSelect(final int bitfield_index)
   {
      if (status.isNotFatal() && bitfield_index >= kFilterSelect_NumElements)
      {
         status.setStatus(NiRioStatus.kRIOStatusBadSelector);
      }
      int arrayElementValue = ((NiRioSrv.peek32(m_DeviceHandle, kFilterSelect_Addresses[m_SystemIndex], status))
          >>> ((kFilterSelect_NumElements - 1 - bitfield_index) * kFilterSelect_ElementSize)) & kFilterSelect_ElementMask;
      return (byte)((arrayElementValue) & 0x00000003);
   }

//////////////////////////////////////////////////////////////////////////////////////////////////
// Accessors for FilterPeriod
//////////////////////////////////////////////////////////////////////////////////////////////////
   public static final int kFilterPeriod_NumElements = 3;
   public static final int kFilterPeriod_ElementSize = 8;
   public static final int kFilterPeriod_ElementMask = 0xFF;
   private static final int kDIO0_FilterPeriod_Address = 0x8304;
   private static final int kDIO1_FilterPeriod_Address = 0x8298;
   private static final int kFilterPeriod_Addresses [] =
   {
      kDIO0_FilterPeriod_Address,
      kDIO1_FilterPeriod_Address,
   };

   public void writeFilterPeriod(final int bitfield_index, final int value)
   {
      if (status.isNotFatal() && bitfield_index >= kFilterPeriod_NumElements)
      {
         status.setStatus(NiRioStatus.kRIOStatusBadSelector);
      }
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kFilterPeriod_Addresses[m_SystemIndex], status);
      regValue &= ~(kFilterPeriod_ElementMask << ((kFilterPeriod_NumElements - 1 - bitfield_index) * kFilterPeriod_ElementSize));
      regValue |= ((value & kFilterPeriod_ElementMask) << ((kFilterPeriod_NumElements - 1 - bitfield_index) * kFilterPeriod_ElementSize));
      NiRioSrv.poke32(m_DeviceHandle, kFilterPeriod_Addresses[m_SystemIndex], regValue, status);
   }
   public short readFilterPeriod(final int bitfield_index)
   {
      if (status.isNotFatal() && bitfield_index >= kFilterPeriod_NumElements)
      {
         status.setStatus(NiRioStatus.kRIOStatusBadSelector);
      }
      int arrayElementValue = ((NiRioSrv.peek32(m_DeviceHandle, kFilterPeriod_Addresses[m_SystemIndex], status))
          >>> ((kFilterPeriod_NumElements - 1 - bitfield_index) * kFilterPeriod_ElementSize)) & kFilterPeriod_ElementMask;
      return (short)((arrayElementValue) & 0x000000FF);
   }

//////////////////////////////////////////////////////////////////////////////////////////////////
// Accessors for OutputEnable
//////////////////////////////////////////////////////////////////////////////////////////////////
   private static final int kDIO0_OutputEnable_Address = 0x8358;
   private static final int kDIO1_OutputEnable_Address = 0x82EC;
   private static final int kOutputEnable_Addresses [] =
   {
      kDIO0_OutputEnable_Address,
      kDIO1_OutputEnable_Address,
   };

   public void writeOutputEnable(final int value)
   {
      NiRioSrv.poke32(m_DeviceHandle, kOutputEnable_Addresses[m_SystemIndex], value, status);
   }
   public int readOutputEnable()
   {
      return (int)((NiRioSrv.peek32(m_DeviceHandle, kOutputEnable_Addresses[m_SystemIndex], status)) & 0x0000FFFF);
   }

//////////////////////////////////////////////////////////////////////////////////////////////////
// Accessors for DI
//////////////////////////////////////////////////////////////////////////////////////////////////
   private static final int kDIO0_DI_Address = 0x835C;
   private static final int kDIO1_DI_Address = 0x82F0;
   private static final int kDI_Addresses [] =
   {
      kDIO0_DI_Address,
      kDIO1_DI_Address,
   };

   public int readDI()
   {
      return (int)((NiRioSrv.peek32(m_DeviceHandle, kDI_Addresses[m_SystemIndex], status)) & 0x0000FFFF);
   }

//////////////////////////////////////////////////////////////////////////////////////////////////
// Accessors for I2CDataReceived
//////////////////////////////////////////////////////////////////////////////////////////////////
   private static final int kDIO0_I2CDataReceived_Address = 0x831C;
   private static final int kDIO1_I2CDataReceived_Address = 0x82B0;
   private static final int kI2CDataReceived_Addresses [] =
   {
      kDIO0_I2CDataReceived_Address,
      kDIO1_I2CDataReceived_Address,
   };

   public long readI2CDataReceived()
   {
      return (long)((NiRioSrv.peek32(m_DeviceHandle, kI2CDataReceived_Addresses[m_SystemIndex], status)) & 0xFFFFFFFFl);
   }

//////////////////////////////////////////////////////////////////////////////////////////////////
// Accessors for I2CStatus
//////////////////////////////////////////////////////////////////////////////////////////////////
   private static final int kI2CStatus_Transaction_BitfieldMask = 0x04000000;
   private static final int kI2CStatus_Transaction_BitfieldOffset = 26;
   private static final int kI2CStatus_Done_BitfieldMask = 0x02000000;
   private static final int kI2CStatus_Done_BitfieldOffset = 25;
   private static final int kI2CStatus_Aborted_BitfieldMask = 0x01000000;
   private static final int kI2CStatus_Aborted_BitfieldOffset = 24;
   private static final int kI2CStatus_DataReceivedHigh_BitfieldMask = 0x00FFFFFF;
   private static final int kI2CStatus_DataReceivedHigh_BitfieldOffset = 0;
   private static final int kDIO0_I2CStatus_Address = 0x8318;
   private static final int kDIO1_I2CStatus_Address = 0x82AC;
   private static final int kI2CStatus_Addresses [] =
   {
      kDIO0_I2CStatus_Address,
      kDIO1_I2CStatus_Address,
   };

   public int readI2CStatus()
   {
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kI2CStatus_Addresses[m_SystemIndex], status);
      return (int)(regValue);
   }
   public byte readI2CStatus_Transaction()
   {
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kI2CStatus_Addresses[m_SystemIndex], status);
      int bitfieldValue = ((regValue & kI2CStatus_Transaction_BitfieldMask) >>> kI2CStatus_Transaction_BitfieldOffset);
      return (byte)((bitfieldValue) & 0x00000001);
   }
   public boolean readI2CStatus_Done()
   {
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kI2CStatus_Addresses[m_SystemIndex], status);
      int bitfieldValue = ((regValue & kI2CStatus_Done_BitfieldMask) >>> kI2CStatus_Done_BitfieldOffset);
      return ((bitfieldValue) != 0 ? true : false);
   }
   public boolean readI2CStatus_Aborted()
   {
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kI2CStatus_Addresses[m_SystemIndex], status);
      int bitfieldValue = ((regValue & kI2CStatus_Aborted_BitfieldMask) >>> kI2CStatus_Aborted_BitfieldOffset);
      return ((bitfieldValue) != 0 ? true : false);
   }
   public int readI2CStatus_DataReceivedHigh()
   {
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kI2CStatus_Addresses[m_SystemIndex], status);
      int bitfieldValue = ((regValue & kI2CStatus_DataReceivedHigh_BitfieldMask) >>> kI2CStatus_DataReceivedHigh_BitfieldOffset);
      return (int)((bitfieldValue) & 0x00FFFFFF);
   }

//////////////////////////////////////////////////////////////////////////////////////////////////
// Accessors for SlowValue
//////////////////////////////////////////////////////////////////////////////////////////////////
   private static final int kSlowValue_RelayFwd_BitfieldMask = 0x000FF000;
   private static final int kSlowValue_RelayFwd_BitfieldOffset = 12;
   private static final int kSlowValue_RelayRev_BitfieldMask = 0x00000FF0;
   private static final int kSlowValue_RelayRev_BitfieldOffset = 4;
   private static final int kSlowValue_I2CHeader_BitfieldMask = 0x0000000F;
   private static final int kSlowValue_I2CHeader_BitfieldOffset = 0;
   private static final int kDIO0_SlowValue_Address = 0x8314;
   private static final int kDIO1_SlowValue_Address = 0x82A8;
   private static final int kSlowValue_Addresses [] =
   {
      kDIO0_SlowValue_Address,
      kDIO1_SlowValue_Address,
   };

   public void writeSlowValue(final int value)
   {
      NiRioSrv.poke32(m_DeviceHandle, kSlowValue_Addresses[m_SystemIndex], value, status);
   }
   public void writeSlowValue_RelayFwd(final int value)
   {
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kSlowValue_Addresses[m_SystemIndex], status);
      regValue &= ~kSlowValue_RelayFwd_BitfieldMask;
      regValue |= ((value) << kSlowValue_RelayFwd_BitfieldOffset) & kSlowValue_RelayFwd_BitfieldMask;
      NiRioSrv.poke32(m_DeviceHandle, kSlowValue_Addresses[m_SystemIndex], regValue, status);
   }
   public void writeSlowValue_RelayRev(final int value)
   {
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kSlowValue_Addresses[m_SystemIndex], status);
      regValue &= ~kSlowValue_RelayRev_BitfieldMask;
      regValue |= ((value) << kSlowValue_RelayRev_BitfieldOffset) & kSlowValue_RelayRev_BitfieldMask;
      NiRioSrv.poke32(m_DeviceHandle, kSlowValue_Addresses[m_SystemIndex], regValue, status);
   }
   public void writeSlowValue_I2CHeader(final int value)
   {
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kSlowValue_Addresses[m_SystemIndex], status);
      regValue &= ~kSlowValue_I2CHeader_BitfieldMask;
      regValue |= ((value) << kSlowValue_I2CHeader_BitfieldOffset) & kSlowValue_I2CHeader_BitfieldMask;
      NiRioSrv.poke32(m_DeviceHandle, kSlowValue_Addresses[m_SystemIndex], regValue, status);
   }
   public int readSlowValue()
   {
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kSlowValue_Addresses[m_SystemIndex], status);
      return (int)(regValue);
   }
   public short readSlowValue_RelayFwd()
   {
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kSlowValue_Addresses[m_SystemIndex], status);
      int bitfieldValue = ((regValue & kSlowValue_RelayFwd_BitfieldMask) >>> kSlowValue_RelayFwd_BitfieldOffset);
      return (short)((bitfieldValue) & 0x000000FF);
   }
   public short readSlowValue_RelayRev()
   {
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kSlowValue_Addresses[m_SystemIndex], status);
      int bitfieldValue = ((regValue & kSlowValue_RelayRev_BitfieldMask) >>> kSlowValue_RelayRev_BitfieldOffset);
      return (short)((bitfieldValue) & 0x000000FF);
   }
   public byte readSlowValue_I2CHeader()
   {
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kSlowValue_Addresses[m_SystemIndex], status);
      int bitfieldValue = ((regValue & kSlowValue_I2CHeader_BitfieldMask) >>> kSlowValue_I2CHeader_BitfieldOffset);
      return (byte)((bitfieldValue) & 0x0000000F);
   }

//////////////////////////////////////////////////////////////////////////////////////////////////
// Accessors for PWMPeriodScale
//////////////////////////////////////////////////////////////////////////////////////////////////
   public static final int kPWMPeriodScale_NumElements = 10;
   public static final int kPWMPeriodScale_ElementSize = 2;
   public static final int kPWMPeriodScale_ElementMask = 0x3;
   private static final int kDIO0_PWMPeriodScale_Address = 0x832C;
   private static final int kDIO1_PWMPeriodScale_Address = 0x82C0;
   private static final int kPWMPeriodScale_Addresses [] =
   {
      kDIO0_PWMPeriodScale_Address,
      kDIO1_PWMPeriodScale_Address,
   };

   public void writePWMPeriodScale(final int bitfield_index, final int value)
   {
      if (status.isNotFatal() && bitfield_index >= kPWMPeriodScale_NumElements)
      {
         status.setStatus(NiRioStatus.kRIOStatusBadSelector);
      }
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kPWMPeriodScale_Addresses[m_SystemIndex], status);
      regValue &= ~(kPWMPeriodScale_ElementMask << ((kPWMPeriodScale_NumElements - 1 - bitfield_index) * kPWMPeriodScale_ElementSize));
      regValue |= ((value & kPWMPeriodScale_ElementMask) << ((kPWMPeriodScale_NumElements - 1 - bitfield_index) * kPWMPeriodScale_ElementSize));
      NiRioSrv.poke32(m_DeviceHandle, kPWMPeriodScale_Addresses[m_SystemIndex], regValue, status);
   }
   public byte readPWMPeriodScale(final int bitfield_index)
   {
      if (status.isNotFatal() && bitfield_index >= kPWMPeriodScale_NumElements)
      {
         status.setStatus(NiRioStatus.kRIOStatusBadSelector);
      }
      int arrayElementValue = ((NiRioSrv.peek32(m_DeviceHandle, kPWMPeriodScale_Addresses[m_SystemIndex], status))
          >>> ((kPWMPeriodScale_NumElements - 1 - bitfield_index) * kPWMPeriodScale_ElementSize)) & kPWMPeriodScale_ElementMask;
      return (byte)((arrayElementValue) & 0x00000003);
   }

//////////////////////////////////////////////////////////////////////////////////////////////////
// Accessors for Pulse
//////////////////////////////////////////////////////////////////////////////////////////////////
   private static final int kDIO0_Pulse_Address = 0x830C;
   private static final int kDIO1_Pulse_Address = 0x82A0;
   private static final int kPulse_Addresses [] =
   {
      kDIO0_Pulse_Address,
      kDIO1_Pulse_Address,
   };

   public void writePulse(final int value)
   {
      NiRioSrv.poke32(m_DeviceHandle, kPulse_Addresses[m_SystemIndex], value, status);
   }
   public int readPulse()
   {
      return (int)((NiRioSrv.peek32(m_DeviceHandle, kPulse_Addresses[m_SystemIndex], status)) & 0x0000FFFF);
   }

//////////////////////////////////////////////////////////////////////////////////////////////////
// Accessors for I2CStart
//////////////////////////////////////////////////////////////////////////////////////////////////
   private static final int kDIO0_I2CStart_Address = 0x8320;
   private static final int kDIO1_I2CStart_Address = 0x82B4;
   private static final int kI2CStart_Addresses [] =
   {
      kDIO0_I2CStart_Address,
      kDIO1_I2CStart_Address,
   };

   public void strobeI2CStart()
   {
      NiRioSrv.poke32(m_DeviceHandle, kI2CStart_Addresses[m_SystemIndex], 1, status);
   }

//////////////////////////////////////////////////////////////////////////////////////////////////
// Accessors for BFL
//////////////////////////////////////////////////////////////////////////////////////////////////
   private static final int kDIO0_BFL_Address = 0x8310;
   private static final int kDIO1_BFL_Address = 0x82A4;
   private static final int kBFL_Addresses [] =
   {
      kDIO0_BFL_Address,
      kDIO1_BFL_Address,
   };

   public void writeBFL(final boolean value)
   {
      NiRioSrv.poke32(m_DeviceHandle, kBFL_Addresses[m_SystemIndex], (value ? 1 : 0), status);
   }
   public boolean readBFL()
   {
      return ((NiRioSrv.peek32(m_DeviceHandle, kBFL_Addresses[m_SystemIndex], status)) != 0 ? true : false);
   }

//////////////////////////////////////////////////////////////////////////////////////////////////
// Accessors for DO_PWMDutyCycle
//////////////////////////////////////////////////////////////////////////////////////////////////
   public static final int kDO_PWMDutyCycle_NumElements = 4;
   public static final int kDO_PWMDutyCycle_ElementSize = 8;
   public static final int kDO_PWMDutyCycle_ElementMask = 0xFF;
   private static final int kDIO0_DO_PWMDutyCycle_Address = 0x82FC;
   private static final int kDIO1_DO_PWMDutyCycle_Address = 0x8290;
   private static final int kDO_PWMDutyCycle_Addresses [] =
   {
      kDIO0_DO_PWMDutyCycle_Address,
      kDIO1_DO_PWMDutyCycle_Address,
   };

   public void writeDO_PWMDutyCycle(final int bitfield_index, final int value)
   {
      if (status.isNotFatal() && bitfield_index >= kDO_PWMDutyCycle_NumElements)
      {
         status.setStatus(NiRioStatus.kRIOStatusBadSelector);
      }
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kDO_PWMDutyCycle_Addresses[m_SystemIndex], status);
      regValue &= ~(kDO_PWMDutyCycle_ElementMask << ((kDO_PWMDutyCycle_NumElements - 1 - bitfield_index) * kDO_PWMDutyCycle_ElementSize));
      regValue |= ((value & kDO_PWMDutyCycle_ElementMask) << ((kDO_PWMDutyCycle_NumElements - 1 - bitfield_index) * kDO_PWMDutyCycle_ElementSize));
      NiRioSrv.poke32(m_DeviceHandle, kDO_PWMDutyCycle_Addresses[m_SystemIndex], regValue, status);
   }
   public short readDO_PWMDutyCycle(final int bitfield_index)
   {
      if (status.isNotFatal() && bitfield_index >= kDO_PWMDutyCycle_NumElements)
      {
         status.setStatus(NiRioStatus.kRIOStatusBadSelector);
      }
      int arrayElementValue = ((NiRioSrv.peek32(m_DeviceHandle, kDO_PWMDutyCycle_Addresses[m_SystemIndex], status))
          >>> ((kDO_PWMDutyCycle_NumElements - 1 - bitfield_index) * kDO_PWMDutyCycle_ElementSize)) & kDO_PWMDutyCycle_ElementMask;
      return (short)((arrayElementValue) & 0x000000FF);
   }

//////////////////////////////////////////////////////////////////////////////////////////////////
// Accessors for PulseLength
//////////////////////////////////////////////////////////////////////////////////////////////////
   private static final int kDIO0_PulseLength_Address = 0x8308;
   private static final int kDIO1_PulseLength_Address = 0x829C;
   private static final int kPulseLength_Addresses [] =
   {
      kDIO0_PulseLength_Address,
      kDIO1_PulseLength_Address,
   };

   public void writePulseLength(final int value)
   {
      NiRioSrv.poke32(m_DeviceHandle, kPulseLength_Addresses[m_SystemIndex], value, status);
   }
   public short readPulseLength()
   {
      return (short)((NiRioSrv.peek32(m_DeviceHandle, kPulseLength_Addresses[m_SystemIndex], status)) & 0x000000FF);
   }

//////////////////////////////////////////////////////////////////////////////////////////////////
// Accessors for DO_PWMConfig
//////////////////////////////////////////////////////////////////////////////////////////////////
   private static final int kDO_PWMConfig_PeriodPower_BitfieldMask = 0x000F0000;
   private static final int kDO_PWMConfig_PeriodPower_BitfieldOffset = 16;
   private static final int kDO_PWMConfig_OutputSelect_0_BitfieldMask = 0x0000F000;
   private static final int kDO_PWMConfig_OutputSelect_0_BitfieldOffset = 12;
   private static final int kDO_PWMConfig_OutputSelect_1_BitfieldMask = 0x00000F00;
   private static final int kDO_PWMConfig_OutputSelect_1_BitfieldOffset = 8;
   private static final int kDO_PWMConfig_OutputSelect_2_BitfieldMask = 0x000000F0;
   private static final int kDO_PWMConfig_OutputSelect_2_BitfieldOffset = 4;
   private static final int kDO_PWMConfig_OutputSelect_3_BitfieldMask = 0x0000000F;
   private static final int kDO_PWMConfig_OutputSelect_3_BitfieldOffset = 0;
   private static final int kDIO0_DO_PWMConfig_Address = 0x82F8;
   private static final int kDIO1_DO_PWMConfig_Address = 0x828C;
   private static final int kDO_PWMConfig_Addresses [] =
   {
      kDIO0_DO_PWMConfig_Address,
      kDIO1_DO_PWMConfig_Address,
   };

   public void writeDO_PWMConfig(final int value)
   {
      NiRioSrv.poke32(m_DeviceHandle, kDO_PWMConfig_Addresses[m_SystemIndex], value, status);
   }
   public void writeDO_PWMConfig_PeriodPower(final int value)
   {
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kDO_PWMConfig_Addresses[m_SystemIndex], status);
      regValue &= ~kDO_PWMConfig_PeriodPower_BitfieldMask;
      regValue |= ((value) << kDO_PWMConfig_PeriodPower_BitfieldOffset) & kDO_PWMConfig_PeriodPower_BitfieldMask;
      NiRioSrv.poke32(m_DeviceHandle, kDO_PWMConfig_Addresses[m_SystemIndex], regValue, status);
   }
   public void writeDO_PWMConfig_OutputSelect_0(final int value)
   {
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kDO_PWMConfig_Addresses[m_SystemIndex], status);
      regValue &= ~kDO_PWMConfig_OutputSelect_0_BitfieldMask;
      regValue |= ((value) << kDO_PWMConfig_OutputSelect_0_BitfieldOffset) & kDO_PWMConfig_OutputSelect_0_BitfieldMask;
      NiRioSrv.poke32(m_DeviceHandle, kDO_PWMConfig_Addresses[m_SystemIndex], regValue, status);
   }
   public void writeDO_PWMConfig_OutputSelect_1(final int value)
   {
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kDO_PWMConfig_Addresses[m_SystemIndex], status);
      regValue &= ~kDO_PWMConfig_OutputSelect_1_BitfieldMask;
      regValue |= ((value) << kDO_PWMConfig_OutputSelect_1_BitfieldOffset) & kDO_PWMConfig_OutputSelect_1_BitfieldMask;
      NiRioSrv.poke32(m_DeviceHandle, kDO_PWMConfig_Addresses[m_SystemIndex], regValue, status);
   }
   public void writeDO_PWMConfig_OutputSelect_2(final int value)
   {
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kDO_PWMConfig_Addresses[m_SystemIndex], status);
      regValue &= ~kDO_PWMConfig_OutputSelect_2_BitfieldMask;
      regValue |= ((value) << kDO_PWMConfig_OutputSelect_2_BitfieldOffset) & kDO_PWMConfig_OutputSelect_2_BitfieldMask;
      NiRioSrv.poke32(m_DeviceHandle, kDO_PWMConfig_Addresses[m_SystemIndex], regValue, status);
   }
   public void writeDO_PWMConfig_OutputSelect_3(final int value)
   {
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kDO_PWMConfig_Addresses[m_SystemIndex], status);
      regValue &= ~kDO_PWMConfig_OutputSelect_3_BitfieldMask;
      regValue |= ((value) << kDO_PWMConfig_OutputSelect_3_BitfieldOffset) & kDO_PWMConfig_OutputSelect_3_BitfieldMask;
      NiRioSrv.poke32(m_DeviceHandle, kDO_PWMConfig_Addresses[m_SystemIndex], regValue, status);
   }
   public int readDO_PWMConfig()
   {
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kDO_PWMConfig_Addresses[m_SystemIndex], status);
      return (int)(regValue);
   }
   public byte readDO_PWMConfig_PeriodPower()
   {
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kDO_PWMConfig_Addresses[m_SystemIndex], status);
      int bitfieldValue = ((regValue & kDO_PWMConfig_PeriodPower_BitfieldMask) >>> kDO_PWMConfig_PeriodPower_BitfieldOffset);
      return (byte)((bitfieldValue) & 0x0000000F);
   }
   public byte readDO_PWMConfig_OutputSelect_0()
   {
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kDO_PWMConfig_Addresses[m_SystemIndex], status);
      int bitfieldValue = ((regValue & kDO_PWMConfig_OutputSelect_0_BitfieldMask) >>> kDO_PWMConfig_OutputSelect_0_BitfieldOffset);
      return (byte)((bitfieldValue) & 0x0000000F);
   }
   public byte readDO_PWMConfig_OutputSelect_1()
   {
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kDO_PWMConfig_Addresses[m_SystemIndex], status);
      int bitfieldValue = ((regValue & kDO_PWMConfig_OutputSelect_1_BitfieldMask) >>> kDO_PWMConfig_OutputSelect_1_BitfieldOffset);
      return (byte)((bitfieldValue) & 0x0000000F);
   }
   public byte readDO_PWMConfig_OutputSelect_2()
   {
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kDO_PWMConfig_Addresses[m_SystemIndex], status);
      int bitfieldValue = ((regValue & kDO_PWMConfig_OutputSelect_2_BitfieldMask) >>> kDO_PWMConfig_OutputSelect_2_BitfieldOffset);
      return (byte)((bitfieldValue) & 0x0000000F);
   }
   public byte readDO_PWMConfig_OutputSelect_3()
   {
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kDO_PWMConfig_Addresses[m_SystemIndex], status);
      int bitfieldValue = ((regValue & kDO_PWMConfig_OutputSelect_3_BitfieldMask) >>> kDO_PWMConfig_OutputSelect_3_BitfieldOffset);
      return (byte)((bitfieldValue) & 0x0000000F);
   }

//////////////////////////////////////////////////////////////////////////////////////////////////
// Accessors for I2CConfig
//////////////////////////////////////////////////////////////////////////////////////////////////
   private static final int kI2CConfig_Address_BitfieldMask = 0x7F800000;
   private static final int kI2CConfig_Address_BitfieldOffset = 23;
   private static final int kI2CConfig_BytesToRead_BitfieldMask = 0x00700000;
   private static final int kI2CConfig_BytesToRead_BitfieldOffset = 20;
   private static final int kI2CConfig_BytesToWrite_BitfieldMask = 0x000E0000;
   private static final int kI2CConfig_BytesToWrite_BitfieldOffset = 17;
   private static final int kI2CConfig_DataToSendHigh_BitfieldMask = 0x0001FFFE;
   private static final int kI2CConfig_DataToSendHigh_BitfieldOffset = 1;
   private static final int kI2CConfig_BitwiseHandshake_BitfieldMask = 0x00000001;
   private static final int kI2CConfig_BitwiseHandshake_BitfieldOffset = 0;
   private static final int kDIO0_I2CConfig_Address = 0x8324;
   private static final int kDIO1_I2CConfig_Address = 0x82B8;
   private static final int kI2CConfig_Addresses [] =
   {
      kDIO0_I2CConfig_Address,
      kDIO1_I2CConfig_Address,
   };

   public void writeI2CConfig(final int value)
   {
      NiRioSrv.poke32(m_DeviceHandle, kI2CConfig_Addresses[m_SystemIndex], value, status);
   }
   public void writeI2CConfig_Address(final int value)
   {
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kI2CConfig_Addresses[m_SystemIndex], status);
      regValue &= ~kI2CConfig_Address_BitfieldMask;
      regValue |= ((value) << kI2CConfig_Address_BitfieldOffset) & kI2CConfig_Address_BitfieldMask;
      NiRioSrv.poke32(m_DeviceHandle, kI2CConfig_Addresses[m_SystemIndex], regValue, status);
   }
   public void writeI2CConfig_BytesToRead(final int value)
   {
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kI2CConfig_Addresses[m_SystemIndex], status);
      regValue &= ~kI2CConfig_BytesToRead_BitfieldMask;
      regValue |= ((value) << kI2CConfig_BytesToRead_BitfieldOffset) & kI2CConfig_BytesToRead_BitfieldMask;
      NiRioSrv.poke32(m_DeviceHandle, kI2CConfig_Addresses[m_SystemIndex], regValue, status);
   }
   public void writeI2CConfig_BytesToWrite(final int value)
   {
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kI2CConfig_Addresses[m_SystemIndex], status);
      regValue &= ~kI2CConfig_BytesToWrite_BitfieldMask;
      regValue |= ((value) << kI2CConfig_BytesToWrite_BitfieldOffset) & kI2CConfig_BytesToWrite_BitfieldMask;
      NiRioSrv.poke32(m_DeviceHandle, kI2CConfig_Addresses[m_SystemIndex], regValue, status);
   }
   public void writeI2CConfig_DataToSendHigh(final int value)
   {
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kI2CConfig_Addresses[m_SystemIndex], status);
      regValue &= ~kI2CConfig_DataToSendHigh_BitfieldMask;
      regValue |= ((value) << kI2CConfig_DataToSendHigh_BitfieldOffset) & kI2CConfig_DataToSendHigh_BitfieldMask;
      NiRioSrv.poke32(m_DeviceHandle, kI2CConfig_Addresses[m_SystemIndex], regValue, status);
   }
   public void writeI2CConfig_BitwiseHandshake(final boolean value)
   {
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kI2CConfig_Addresses[m_SystemIndex], status);
      regValue &= ~kI2CConfig_BitwiseHandshake_BitfieldMask;
      regValue |= (((value ? 1 : 0)) << kI2CConfig_BitwiseHandshake_BitfieldOffset) & kI2CConfig_BitwiseHandshake_BitfieldMask;
      NiRioSrv.poke32(m_DeviceHandle, kI2CConfig_Addresses[m_SystemIndex], regValue, status);
   }
   public int readI2CConfig()
   {
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kI2CConfig_Addresses[m_SystemIndex], status);
      return (int)(regValue);
   }
   public short readI2CConfig_Address()
   {
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kI2CConfig_Addresses[m_SystemIndex], status);
      int bitfieldValue = ((regValue & kI2CConfig_Address_BitfieldMask) >>> kI2CConfig_Address_BitfieldOffset);
      return (short)((bitfieldValue) & 0x000000FF);
   }
   public byte readI2CConfig_BytesToRead()
   {
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kI2CConfig_Addresses[m_SystemIndex], status);
      int bitfieldValue = ((regValue & kI2CConfig_BytesToRead_BitfieldMask) >>> kI2CConfig_BytesToRead_BitfieldOffset);
      return (byte)((bitfieldValue) & 0x00000007);
   }
   public byte readI2CConfig_BytesToWrite()
   {
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kI2CConfig_Addresses[m_SystemIndex], status);
      int bitfieldValue = ((regValue & kI2CConfig_BytesToWrite_BitfieldMask) >>> kI2CConfig_BytesToWrite_BitfieldOffset);
      return (byte)((bitfieldValue) & 0x00000007);
   }
   public int readI2CConfig_DataToSendHigh()
   {
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kI2CConfig_Addresses[m_SystemIndex], status);
      int bitfieldValue = ((regValue & kI2CConfig_DataToSendHigh_BitfieldMask) >>> kI2CConfig_DataToSendHigh_BitfieldOffset);
      return (int)((bitfieldValue) & 0x0000FFFF);
   }
   public boolean readI2CConfig_BitwiseHandshake()
   {
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kI2CConfig_Addresses[m_SystemIndex], status);
      int bitfieldValue = ((regValue & kI2CConfig_BitwiseHandshake_BitfieldMask) >>> kI2CConfig_BitwiseHandshake_BitfieldOffset);
      return ((bitfieldValue) != 0 ? true : false);
   }


//////////////////////////////////////////////////////////////////////////////////////////////////
// Accessors for LoopTiming
//////////////////////////////////////////////////////////////////////////////////////////////////
   private static final int kDIO_LoopTiming_Address = 0x8368;

   public static int readLoopTiming()
   {
      return (int)((NiRioSrv.peek32(m_DeviceHandle, kDIO_LoopTiming_Address, status)) & 0x0000FFFF);
   }

//////////////////////////////////////////////////////////////////////////////////////////////////
// Accessors for PWMConfig
//////////////////////////////////////////////////////////////////////////////////////////////////
   private static final int kPWMConfig_Period_BitfieldMask = 0xFFFF0000;
   private static final int kPWMConfig_Period_BitfieldOffset = 16;
   private static final int kPWMConfig_MinHigh_BitfieldMask = 0x0000FFFF;
   private static final int kPWMConfig_MinHigh_BitfieldOffset = 0;
   private static final int kDIO_PWMConfig_Address = 0x8364;

   public static void writePWMConfig(final int value)
   {
      NiRioSrv.poke32(m_DeviceHandle, kDIO_PWMConfig_Address, value, status);
   }
   public static void writePWMConfig_Period(final int value)
   {
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kDIO_PWMConfig_Address, status);
      regValue &= ~kPWMConfig_Period_BitfieldMask;
      regValue |= ((value) << kPWMConfig_Period_BitfieldOffset) & kPWMConfig_Period_BitfieldMask;
      NiRioSrv.poke32(m_DeviceHandle, kDIO_PWMConfig_Address, regValue, status);
   }
   public static void writePWMConfig_MinHigh(final int value)
   {
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kDIO_PWMConfig_Address, status);
      regValue &= ~kPWMConfig_MinHigh_BitfieldMask;
      regValue |= ((value) << kPWMConfig_MinHigh_BitfieldOffset) & kPWMConfig_MinHigh_BitfieldMask;
      NiRioSrv.poke32(m_DeviceHandle, kDIO_PWMConfig_Address, regValue, status);
   }
   public static int readPWMConfig()
   {
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kDIO_PWMConfig_Address, status);
      return (int)(regValue);
   }
   public static int readPWMConfig_Period()
   {
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kDIO_PWMConfig_Address, status);
      int bitfieldValue = ((regValue & kPWMConfig_Period_BitfieldMask) >>> kPWMConfig_Period_BitfieldOffset);
      return (int)((bitfieldValue) & 0x0000FFFF);
   }
   public static int readPWMConfig_MinHigh()
   {
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kDIO_PWMConfig_Address, status);
      int bitfieldValue = ((regValue & kPWMConfig_MinHigh_BitfieldMask) >>> kPWMConfig_MinHigh_BitfieldOffset);
      return (int)((bitfieldValue) & 0x0000FFFF);
   }


//////////////////////////////////////////////////////////////////////////////////////////////////
// Accessors for PWMValue
//////////////////////////////////////////////////////////////////////////////////////////////////
   public static final int kPWMValue_NumRegisters = 10;
   private static final int kDIO0_PWMValue0_Address = 0x8354;
   private static final int kDIO0_PWMValue1_Address = 0x8350;
   private static final int kDIO0_PWMValue2_Address = 0x834C;
   private static final int kDIO0_PWMValue3_Address = 0x8348;
   private static final int kDIO0_PWMValue4_Address = 0x8344;
   private static final int kDIO0_PWMValue5_Address = 0x8340;
   private static final int kDIO0_PWMValue6_Address = 0x833C;
   private static final int kDIO0_PWMValue7_Address = 0x8338;
   private static final int kDIO0_PWMValue8_Address = 0x8334;
   private static final int kDIO0_PWMValue9_Address = 0x8330;
   private static final int kDIO1_PWMValue0_Address = 0x82E8;
   private static final int kDIO1_PWMValue1_Address = 0x82E4;
   private static final int kDIO1_PWMValue2_Address = 0x82E0;
   private static final int kDIO1_PWMValue3_Address = 0x82DC;
   private static final int kDIO1_PWMValue4_Address = 0x82D8;
   private static final int kDIO1_PWMValue5_Address = 0x82D4;
   private static final int kDIO1_PWMValue6_Address = 0x82D0;
   private static final int kDIO1_PWMValue7_Address = 0x82CC;
   private static final int kDIO1_PWMValue8_Address = 0x82C8;
   private static final int kDIO1_PWMValue9_Address = 0x82C4;
   private static final int kPWMValue_Addresses [] =
   {
      kDIO0_PWMValue0_Address,
      kDIO0_PWMValue1_Address,
      kDIO0_PWMValue2_Address,
      kDIO0_PWMValue3_Address,
      kDIO0_PWMValue4_Address,
      kDIO0_PWMValue5_Address,
      kDIO0_PWMValue6_Address,
      kDIO0_PWMValue7_Address,
      kDIO0_PWMValue8_Address,
      kDIO0_PWMValue9_Address,
      kDIO1_PWMValue0_Address,
      kDIO1_PWMValue1_Address,
      kDIO1_PWMValue2_Address,
      kDIO1_PWMValue3_Address,
      kDIO1_PWMValue4_Address,
      kDIO1_PWMValue5_Address,
      kDIO1_PWMValue6_Address,
      kDIO1_PWMValue7_Address,
      kDIO1_PWMValue8_Address,
      kDIO1_PWMValue9_Address,
   };

   public void writePWMValue(final int reg_index, final int value)
   {
      if (status.isNotFatal() && reg_index >= kPWMValue_NumRegisters)
      {
         status.setStatus(NiRioStatus.kRIOStatusBadSelector);
      }
      NiRioSrv.poke32(m_DeviceHandle, kPWMValue_Addresses[m_SystemIndex * kPWMValue_NumRegisters + reg_index], value, status);
   }
   public short readPWMValue(final int reg_index)
   {
      if (status.isNotFatal() && reg_index >= kPWMValue_NumRegisters)
      {
         status.setStatus(NiRioStatus.kRIOStatusBadSelector);
      }
      return (short)((NiRioSrv.peek32(m_DeviceHandle, kPWMValue_Addresses[m_SystemIndex * kPWMValue_NumRegisters + reg_index], status)) & 0x000000FF);
   }



}
