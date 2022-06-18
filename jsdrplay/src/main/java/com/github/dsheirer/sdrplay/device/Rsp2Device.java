/*
 * *****************************************************************************
 * Copyright (C) 2014-2022 Dennis Sheirer
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 * ****************************************************************************
 */

package com.github.dsheirer.sdrplay.device;

import com.github.dsheirer.sdrplay.SDRPlayException;
import com.github.dsheirer.sdrplay.SDRplay;
import com.github.dsheirer.sdrplay.parameter.composite.Rsp2CompositeParameters;

/**
 * RSP2 Device
 */
public class Rsp2Device extends Device<Rsp2CompositeParameters, Rsp2Tuner>
{
    private Rsp2Tuner mTuner1;

    /**
     * Constructs an SDRPlay RSP2 device from the foreign memory segment
     *
     * @param sdrPlay api instance that created this device
     * @param deviceStruct parser
     */
    Rsp2Device(SDRplay sdrPlay, IDeviceStruct deviceStruct)
    {
        super(sdrPlay, deviceStruct);
    }

    @Override
    public Rsp2Tuner getTuner() throws SDRPlayException
    {
        if(!isSelected())
        {
            throw new SDRPlayException("Device must be selected before accessing the tuner");
        }

        if(mTuner1 == null)
        {
            mTuner1 = new Rsp2Tuner(Rsp2Device.this, getAPI(), getTunerSelect(),
                    getCompositeParameters().getDeviceParameters(), getCompositeParameters().getTunerAParameters(),
                    getCompositeParameters().getControlAParameters());
        }

        return mTuner1;
    }
}
