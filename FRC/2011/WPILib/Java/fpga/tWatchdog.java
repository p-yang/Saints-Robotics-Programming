// Copyright (c) National Instruments 2009.  All Rights Reserved.
// Do Not Edit... this file is generated!

package edu.wpi.first.wpilibj.fpga;

import com.ni.rio.*;

public class tWatchdog extends tSystem
{

   public tWatchdog()
   {
      super();
   }

   protected void finalize()
   {
      super.finalize();
   }

   public static final int kNumSystems = 1;




//////////////////////////////////////////////////////////////////////////////////////////////////
// Accessors for Status
//////////////////////////////////////////////////////////////////////////////////////////////////
   private static final int kStatus_SystemActive_BitfieldMask = 0x80000000;
   private static final int kStatus_SystemActive_BitfieldOffset = 31;
   private static final int kStatus_Alive_BitfieldMask = 0x40000000;
   private static final int kStatus_Alive_BitfieldOffset = 30;
   private static final int kStatus_SysDisableCount_BitfieldMask = 0x3FFF8000;
   private static final int kStatus_SysDisableCount_BitfieldOffset = 15;
   private static final int kStatus_DisableCount_BitfieldMask = 0x00007FFF;
   private static final int kStatus_DisableCount_BitfieldOffset = 0;
   private static final int kWatchdog_Status_Address = 0x8448;

   public static int readStatus()
   {
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kWatchdog_Status_Address, status);
      return (int)(regValue);
   }
   public static boolean readStatus_SystemActive()
   {
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kWatchdog_Status_Address, status);
      int bitfieldValue = ((regValue & kStatus_SystemActive_BitfieldMask) >>> kStatus_SystemActive_BitfieldOffset);
      return ((bitfieldValue) != 0 ? true : false);
   }
   public static boolean readStatus_Alive()
   {
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kWatchdog_Status_Address, status);
      int bitfieldValue = ((regValue & kStatus_Alive_BitfieldMask) >>> kStatus_Alive_BitfieldOffset);
      return ((bitfieldValue) != 0 ? true : false);
   }
   public static short readStatus_SysDisableCount()
   {
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kWatchdog_Status_Address, status);
      int bitfieldValue = ((regValue & kStatus_SysDisableCount_BitfieldMask) >>> kStatus_SysDisableCount_BitfieldOffset);
      return (short)((bitfieldValue) & 0x00007FFF);
   }
   public static short readStatus_DisableCount()
   {
      int regValue = NiRioSrv.peek32(m_DeviceHandle, kWatchdog_Status_Address, status);
      int bitfieldValue = ((regValue & kStatus_DisableCount_BitfieldMask) >>> kStatus_DisableCount_BitfieldOffset);
      return (short)((bitfieldValue) & 0x00007FFF);
   }

//////////////////////////////////////////////////////////////////////////////////////////////////
// Accessors for Feed
//////////////////////////////////////////////////////////////////////////////////////////////////
   private static final int kWatchdog_Feed_Address = 0x8444;

   public static void strobeFeed()
   {
      NiRioSrv.poke32(m_DeviceHandle, kWatchdog_Feed_Address, 1, status);
   }

//////////////////////////////////////////////////////////////////////////////////////////////////
// Accessors for Kill
//////////////////////////////////////////////////////////////////////////////////////////////////
   private static final int kWatchdog_Kill_Address = 0x8440;

   public static void strobeKill()
   {
      NiRioSrv.poke32(m_DeviceHandle, kWatchdog_Kill_Address, 1, status);
   }

//////////////////////////////////////////////////////////////////////////////////////////////////
// Accessors for Immortal
//////////////////////////////////////////////////////////////////////////////////////////////////
   private static final int kWatchdog_Immortal_Address = 0x8434;

   public static void writeImmortal(final boolean value)
   {
      NiRioSrv.poke32(m_DeviceHandle, kWatchdog_Immortal_Address, (value ? 1 : 0), status);
   }
   public static boolean readImmortal()
   {
      return ((NiRioSrv.peek32(m_DeviceHandle, kWatchdog_Immortal_Address, status)) != 0 ? true : false);
   }

//////////////////////////////////////////////////////////////////////////////////////////////////
// Accessors for Expiration
//////////////////////////////////////////////////////////////////////////////////////////////////
   private static final int kWatchdog_Expiration_Address = 0x8438;

   public static void writeExpiration(final long value)
   {
      NiRioSrv.poke32(m_DeviceHandle, kWatchdog_Expiration_Address, (int)(value), status);
   }
   public static long readExpiration()
   {
      return (long)((NiRioSrv.peek32(m_DeviceHandle, kWatchdog_Expiration_Address, status)) & 0xFFFFFFFFl);
   }

//////////////////////////////////////////////////////////////////////////////////////////////////
// Accessors for Timer
//////////////////////////////////////////////////////////////////////////////////////////////////
   private static final int kWatchdog_Timer_Address = 0x843C;

   public static long readTimer()
   {
      return (long)((NiRioSrv.peek32(m_DeviceHandle, kWatchdog_Timer_Address, status)) & 0xFFFFFFFFl);
   }




}
