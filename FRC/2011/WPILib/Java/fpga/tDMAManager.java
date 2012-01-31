// Class for handling DMA transters.
// Copyright (c) National Instruments 2008.  All Rights Reserved.

package edu.wpi.first.wpilibj.fpga;

import com.ni.rio.*;
import com.sun.cldc.jna.*;
import com.sun.cldc.jna.ptr.IntByReference;

public class tDMAManager extends tSystem
{

   boolean m_started;
   int m_dmaChannel;
   int m_hostBufferSize;
   DMAChannelDescriptors.tDMAChannelDescriptor m_dmaChannelDescriptor;

   public tDMAManager(int dmaChannel, int hostBufferSize)
   {
      super();
      if (status.isFatal()) return;

      m_started = false;
      m_dmaChannel = dmaChannel;
      m_hostBufferSize = hostBufferSize;

      boolean found = false;
      for (int i=0; i<kNUM_DMA_CHANNELS; i++)
      {
         if(kDMAChannelDescriptors[i].channel == m_dmaChannel)
         {
            m_dmaChannelDescriptor = kDMAChannelDescriptors[i];
            found = true;
            break;
         }
      }

      if(!found)
      {
         // The DMA channel is not defined in the bitfile.
          status.setStatus(NiRioStatus.kRIOStatusBadSelector);
          return;
      }

      // Allocate the appropriate resources in the RIO driver.
      NiRioSrv.fifoConfig(m_DeviceHandle, m_dmaChannel, m_hostBufferSize, status);
   }

   protected void finalize()
   {
      stop();
      super.finalize();
   }

   public void start()
   {
      NiRioSrv.fifoStart(m_DeviceHandle, m_dmaChannel, status);
      m_started = true;
   }

   public void stop()
   {
      NiRioSrv.fifoStop(m_DeviceHandle, m_dmaChannel, status);
      m_started = false;
   }

   public int[] read(
      int       num,
      int       timeout)
   {
      // Ensure that the FPGA is writing so that the host can read
      if(m_dmaChannelDescriptor.write != 1)
      {
         status.setStatus(NiRioStatus.kRIOStatusInvalidFunction);
         return new int[0];
      }

		Pointer pBuffer = new Pointer(num * 4);
      IntByReference read = new IntByReference(0);
      IntByReference remaining = new IntByReference(0);

      NiRioSrv.fifoRead(m_DeviceHandle, m_dmaChannel, pBuffer, num, timeout, read, remaining, status);

      int[] data = new int[num];
		pBuffer.getInts(0, data, 0, num);
		pBuffer.free();

      m_started = true;

      return data;
   }

//   public void write(
//      tNIRIO_u32*      buf,
//      int       num,
//      int       timeout,
//      tNIRIO_u32*      remaining)
//   {
//      // Ensure that the FPGA is reading so that the host can write
//      if(m_dmaChannelDescriptor.write != 0)
//      {
//         status.setStatus(NiRioStatus.kRIOStatusInvalidFunction);
//         return;
//      }
//
//      NiRioSrv.fifoWrite(m_DeviceHandle, m_dmaChannel, buf, num, timeout, remaining, status);
//      m_started = true;
//   }
}
