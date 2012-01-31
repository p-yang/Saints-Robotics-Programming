// Copyright (c) National Instruments 2009.  All Rights Reserved.
// Do Not Edit... this file is generated!

package edu.wpi.first.wpilibj.fpga;

import com.ni.rio.*;

public class tAnalogTrigger extends tSystem
{

   public tAnalogTrigger(final int sys_index)
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

   public static final int kNumSystems = 8;
   public final int m_SystemIndex;



//////////////////////////////////////////////////////////////////////////////////////////////////
// Accessors for SourceSelect
//////////////////////////////////////////////////////////////////////////////////////////////////
   private static final int kSourceSelect_Channel_BitfieldMask = 0x00007000;
   private static final int kSourceSelect_Channel_BitfieldOffset = 12;
   private static final int kSourceSelect_Module_BitfieldMask = 0x00000800;
   private static final int kSourceSelect_Module_BitfieldOffset = 11;
   private static final int kSourceSelect_Averaged_BitfieldMask = 0x00000400;
   private static final int kSourceSelect_Averaged_BitfieldOffset = 10;
   private static final int kSourceSelect_Filter_BitfieldMask = 0x00000200;
   private static final int kSourceSelect_Filter_BitfieldOffset = 9;
   private static final int kSourceSelect_FloatingRollover_BitfieldMask = 0x00000100;
   private static final int kSourceSelect_FloatingRollover_BitfieldOffset = 8;
   private static final int kSourceSelect_RolloverLimit_BitfieldMask = 0x000000FF;
   private static final int kSourceSelect_RolloverLimit_BitfieldOffset = 0;
   private static final int kSourceSelect_RolloverLimit_FixedPointIntegerShift = 4;
   private static final int kAnalogTrigger0_SourceSelect_Address = 0x83C8;
   private static final int kAnalogTrigger1_SourceSelect_Address = 0x83BC;
   private static final int kAnalogTrigger2_SourceSelect_Address = 0x83B0;
   private static final int kAnalogTrigger3_SourceSelect_Address = 0x83A4;
   private static final int kAnalogTrigger4_SourceSelect_Address = 0x8398;
   private static final int kAnalogTrigger5_SourceSelect_Address = 0x838C;
   private static final int kAnalogTrigger6_SourceSelect_Address = 0x8380;
   private static final int kAnalogTrigger7_SourceSelect_Address = 0x8374;
   private static final int kSourceSelect_Addresses [] =
   {
      kAnalogTrigger0_SourceSelect_Address,
      kAnalogTrigger1_SourceSelect_Address,
      kAnalogTrigger2_SourceSelect_Address,
      kAnalogTrigger3_SourceSelect_Address,
      kAnalogTrigger4_SourceSelect_Address,
      kAnalogTrigger5_SourceSelect_Address,
      kAnalogTrigger6_SourceSelect_Address,
      kAnalogTrigger7_SourceSelect_Address,
   };

   public void writeSourceSelect(final int value)
   {
      NiRioSrv.poke32(m_DeviceHandle, kSourceSelect_Addresses[m_SystemIndex], value, status);
   }
   public void writeSourceSelect_Channel(final int value)
   {
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kSourceSelect_Addresses[m_SystemIndex], status);
      regValue &= ~kSourceSelect_Channel_BitfieldMask;
      regValue |= ((value) << kSourceSelect_Channel_BitfieldOffset) & kSourceSelect_Channel_BitfieldMask;
      NiRioSrv.poke32(m_DeviceHandle, kSourceSelect_Addresses[m_SystemIndex], regValue, status);
   }
   public void writeSourceSelect_Module(final int value)
   {
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kSourceSelect_Addresses[m_SystemIndex], status);
      regValue &= ~kSourceSelect_Module_BitfieldMask;
      regValue |= ((value) << kSourceSelect_Module_BitfieldOffset) & kSourceSelect_Module_BitfieldMask;
      NiRioSrv.poke32(m_DeviceHandle, kSourceSelect_Addresses[m_SystemIndex], regValue, status);
   }
   public void writeSourceSelect_Averaged(final boolean value)
   {
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kSourceSelect_Addresses[m_SystemIndex], status);
      regValue &= ~kSourceSelect_Averaged_BitfieldMask;
      regValue |= (((value ? 1 : 0)) << kSourceSelect_Averaged_BitfieldOffset) & kSourceSelect_Averaged_BitfieldMask;
      NiRioSrv.poke32(m_DeviceHandle, kSourceSelect_Addresses[m_SystemIndex], regValue, status);
   }
   public void writeSourceSelect_Filter(final boolean value)
   {
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kSourceSelect_Addresses[m_SystemIndex], status);
      regValue &= ~kSourceSelect_Filter_BitfieldMask;
      regValue |= (((value ? 1 : 0)) << kSourceSelect_Filter_BitfieldOffset) & kSourceSelect_Filter_BitfieldMask;
      NiRioSrv.poke32(m_DeviceHandle, kSourceSelect_Addresses[m_SystemIndex], regValue, status);
   }
   public void writeSourceSelect_FloatingRollover(final boolean value)
   {
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kSourceSelect_Addresses[m_SystemIndex], status);
      regValue &= ~kSourceSelect_FloatingRollover_BitfieldMask;
      regValue |= (((value ? 1 : 0)) << kSourceSelect_FloatingRollover_BitfieldOffset) & kSourceSelect_FloatingRollover_BitfieldMask;
      NiRioSrv.poke32(m_DeviceHandle, kSourceSelect_Addresses[m_SystemIndex], regValue, status);
   }
   public void writeSourceSelect_RolloverLimit(final int value)
   {
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kSourceSelect_Addresses[m_SystemIndex], status);
      regValue &= ~kSourceSelect_RolloverLimit_BitfieldMask;
      regValue |= ((value >>> kSourceSelect_RolloverLimit_FixedPointIntegerShift) << kSourceSelect_RolloverLimit_BitfieldOffset) & kSourceSelect_RolloverLimit_BitfieldMask;
      NiRioSrv.poke32(m_DeviceHandle, kSourceSelect_Addresses[m_SystemIndex], regValue, status);
   }
   public int readSourceSelect()
   {
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kSourceSelect_Addresses[m_SystemIndex], status);
      return (int)(regValue);
   }
   public byte readSourceSelect_Channel()
   {
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kSourceSelect_Addresses[m_SystemIndex], status);
      int bitfieldValue = ((regValue & kSourceSelect_Channel_BitfieldMask) >>> kSourceSelect_Channel_BitfieldOffset);
      return (byte)((bitfieldValue) & 0x00000007);
   }
   public byte readSourceSelect_Module()
   {
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kSourceSelect_Addresses[m_SystemIndex], status);
      int bitfieldValue = ((regValue & kSourceSelect_Module_BitfieldMask) >>> kSourceSelect_Module_BitfieldOffset);
      return (byte)((bitfieldValue) & 0x00000001);
   }
   public boolean readSourceSelect_Averaged()
   {
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kSourceSelect_Addresses[m_SystemIndex], status);
      int bitfieldValue = ((regValue & kSourceSelect_Averaged_BitfieldMask) >>> kSourceSelect_Averaged_BitfieldOffset);
      return ((bitfieldValue) != 0 ? true : false);
   }
   public boolean readSourceSelect_Filter()
   {
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kSourceSelect_Addresses[m_SystemIndex], status);
      int bitfieldValue = ((regValue & kSourceSelect_Filter_BitfieldMask) >>> kSourceSelect_Filter_BitfieldOffset);
      return ((bitfieldValue) != 0 ? true : false);
   }
   public boolean readSourceSelect_FloatingRollover()
   {
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kSourceSelect_Addresses[m_SystemIndex], status);
      int bitfieldValue = ((regValue & kSourceSelect_FloatingRollover_BitfieldMask) >>> kSourceSelect_FloatingRollover_BitfieldOffset);
      return ((bitfieldValue) != 0 ? true : false);
   }
   public short readSourceSelect_RolloverLimit()
   {
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kSourceSelect_Addresses[m_SystemIndex], status);
      int bitfieldValue = ((regValue & kSourceSelect_RolloverLimit_BitfieldMask) >>> kSourceSelect_RolloverLimit_BitfieldOffset) << kSourceSelect_RolloverLimit_FixedPointIntegerShift;
      // Sign extension
      bitfieldValue <<= 20;
      bitfieldValue >>= 20;
      return (short)(bitfieldValue);
   }

//////////////////////////////////////////////////////////////////////////////////////////////////
// Accessors for UpperLimit
//////////////////////////////////////////////////////////////////////////////////////////////////
   private static final int kAnalogTrigger0_UpperLimit_Address = 0x83C4;
   private static final int kAnalogTrigger1_UpperLimit_Address = 0x83B8;
   private static final int kAnalogTrigger2_UpperLimit_Address = 0x83AC;
   private static final int kAnalogTrigger3_UpperLimit_Address = 0x83A0;
   private static final int kAnalogTrigger4_UpperLimit_Address = 0x8394;
   private static final int kAnalogTrigger5_UpperLimit_Address = 0x837C;
   private static final int kAnalogTrigger6_UpperLimit_Address = 0x8388;
   private static final int kAnalogTrigger7_UpperLimit_Address = 0x8370;
   private static final int kUpperLimit_Addresses [] =
   {
      kAnalogTrigger0_UpperLimit_Address,
      kAnalogTrigger1_UpperLimit_Address,
      kAnalogTrigger2_UpperLimit_Address,
      kAnalogTrigger3_UpperLimit_Address,
      kAnalogTrigger4_UpperLimit_Address,
      kAnalogTrigger5_UpperLimit_Address,
      kAnalogTrigger6_UpperLimit_Address,
      kAnalogTrigger7_UpperLimit_Address,
   };

   public void writeUpperLimit(final int value)
   {
      NiRioSrv.poke32(m_DeviceHandle, kUpperLimit_Addresses[m_SystemIndex], value, status);
   }
   public int readUpperLimit()
   {
      return (int)(NiRioSrv.peek32(m_DeviceHandle, kUpperLimit_Addresses[m_SystemIndex], status));
   }

//////////////////////////////////////////////////////////////////////////////////////////////////
// Accessors for LowerLimit
//////////////////////////////////////////////////////////////////////////////////////////////////
   private static final int kAnalogTrigger0_LowerLimit_Address = 0x83C0;
   private static final int kAnalogTrigger1_LowerLimit_Address = 0x83B4;
   private static final int kAnalogTrigger2_LowerLimit_Address = 0x83A8;
   private static final int kAnalogTrigger3_LowerLimit_Address = 0x839C;
   private static final int kAnalogTrigger4_LowerLimit_Address = 0x8390;
   private static final int kAnalogTrigger5_LowerLimit_Address = 0x8378;
   private static final int kAnalogTrigger6_LowerLimit_Address = 0x8384;
   private static final int kAnalogTrigger7_LowerLimit_Address = 0x836C;
   private static final int kLowerLimit_Addresses [] =
   {
      kAnalogTrigger0_LowerLimit_Address,
      kAnalogTrigger1_LowerLimit_Address,
      kAnalogTrigger2_LowerLimit_Address,
      kAnalogTrigger3_LowerLimit_Address,
      kAnalogTrigger4_LowerLimit_Address,
      kAnalogTrigger5_LowerLimit_Address,
      kAnalogTrigger6_LowerLimit_Address,
      kAnalogTrigger7_LowerLimit_Address,
   };

   public void writeLowerLimit(final int value)
   {
      NiRioSrv.poke32(m_DeviceHandle, kLowerLimit_Addresses[m_SystemIndex], value, status);
   }
   public int readLowerLimit()
   {
      return (int)(NiRioSrv.peek32(m_DeviceHandle, kLowerLimit_Addresses[m_SystemIndex], status));
   }


//////////////////////////////////////////////////////////////////////////////////////////////////
// Accessors for Output
//////////////////////////////////////////////////////////////////////////////////////////////////
   public static final int kOutput_NumElements = 8;
   public static final int kOutput_ElementSize = 4;
   public static final int kOutput_ElementMask = 0xF;
   private static final int kOutput_InHysteresis_BitfieldMask = 0x00000008;
   private static final int kOutput_InHysteresis_BitfieldOffset = 3;
   private static final int kOutput_OverLimit_BitfieldMask = 0x00000004;
   private static final int kOutput_OverLimit_BitfieldOffset = 2;
   private static final int kOutput_Rising_BitfieldMask = 0x00000002;
   private static final int kOutput_Rising_BitfieldOffset = 1;
   private static final int kOutput_Falling_BitfieldMask = 0x00000001;
   private static final int kOutput_Falling_BitfieldOffset = 0;
   private static final int kAnalogTrigger_Output_Address = 0x83CC;

   public static int readOutput(final int bitfield_index)
   {
      if (status.isNotFatal() && bitfield_index >= kOutput_NumElements)
      {
         status.setStatus(NiRioStatus.kRIOStatusBadSelector);
      }
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kAnalogTrigger_Output_Address, status) >>> ((kOutput_NumElements - 1 - bitfield_index) * kOutput_ElementSize);
      return (int)(regValue);
   }
   public static boolean readOutput_InHysteresis(final int bitfield_index)
   {
      if (status.isNotFatal() && bitfield_index >= kOutput_NumElements)
      {
         status.setStatus(NiRioStatus.kRIOStatusBadSelector);
      }
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kAnalogTrigger_Output_Address, status) >>> ((kOutput_NumElements - 1 - bitfield_index) * kOutput_ElementSize);
      int bitfieldValue = ((regValue & kOutput_InHysteresis_BitfieldMask) >>> kOutput_InHysteresis_BitfieldOffset);
      return ((bitfieldValue) != 0 ? true : false);
   }
   public static boolean readOutput_OverLimit(final int bitfield_index)
   {
      if (status.isNotFatal() && bitfield_index >= kOutput_NumElements)
      {
         status.setStatus(NiRioStatus.kRIOStatusBadSelector);
      }
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kAnalogTrigger_Output_Address, status) >>> ((kOutput_NumElements - 1 - bitfield_index) * kOutput_ElementSize);
      int bitfieldValue = ((regValue & kOutput_OverLimit_BitfieldMask) >>> kOutput_OverLimit_BitfieldOffset);
      return ((bitfieldValue) != 0 ? true : false);
   }
   public static boolean readOutput_Rising(final int bitfield_index)
   {
      if (status.isNotFatal() && bitfield_index >= kOutput_NumElements)
      {
         status.setStatus(NiRioStatus.kRIOStatusBadSelector);
      }
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kAnalogTrigger_Output_Address, status) >>> ((kOutput_NumElements - 1 - bitfield_index) * kOutput_ElementSize);
      int bitfieldValue = ((regValue & kOutput_Rising_BitfieldMask) >>> kOutput_Rising_BitfieldOffset);
      return ((bitfieldValue) != 0 ? true : false);
   }
   public static boolean readOutput_Falling(final int bitfield_index)
   {
      if (status.isNotFatal() && bitfield_index >= kOutput_NumElements)
      {
         status.setStatus(NiRioStatus.kRIOStatusBadSelector);
      }
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kAnalogTrigger_Output_Address, status) >>> ((kOutput_NumElements - 1 - bitfield_index) * kOutput_ElementSize);
      int bitfieldValue = ((regValue & kOutput_Falling_BitfieldMask) >>> kOutput_Falling_BitfieldOffset);
      return ((bitfieldValue) != 0 ? true : false);
   }




}
