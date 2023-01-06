/*
 * *****************************************************************************
 * Copyright (C) 2014-2023 Dennis Sheirer
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

package io.github.dsheirer.gui.power;

import io.github.dsheirer.controller.channel.Channel;
import io.github.dsheirer.dsp.squelch.ISquelchConfiguration;
import io.github.dsheirer.gui.control.DbPowerMeter;
import io.github.dsheirer.module.ProcessingChain;
import io.github.dsheirer.playlist.PlaylistManager;
import io.github.dsheirer.sample.Listener;
import io.github.dsheirer.sample.complex.ComplexSamplesToNativeBufferModule;
import io.github.dsheirer.settings.SettingsManager;
import io.github.dsheirer.source.SourceEvent;
import io.github.dsheirer.spectrum.ComplexDftProcessor;
import io.github.dsheirer.spectrum.SpectrumPanel;
import io.github.dsheirer.spectrum.converter.ComplexDecibelConverter;
import io.github.dsheirer.spectrum.converter.DFTResultsConverter;
import java.awt.EventQueue;
import java.text.DecimalFormat;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;
import net.miginfocom.swing.MigLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

/**
 * Display for channel power and squelch details
 */
public class ChannelPowerPanel extends JPanel implements Listener<ProcessingChain>
{
    private static final Logger mLog = LoggerFactory.getLogger(ChannelPowerPanel.class);
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.0");
    private static final String NOT_AVAILABLE = "Not Available";
    private PlaylistManager mPlaylistManager;
    private ProcessingChain mProcessingChain;
    private ComplexSamplesToNativeBufferModule mSampleStreamTapModule = new ComplexSamplesToNativeBufferModule();
    private ComplexDftProcessor mComplexDftProcessor = new ComplexDftProcessor();
    private DFTResultsConverter mDFTResultsConverter = new ComplexDecibelConverter();
    private SpectrumPanel mSpectrumPanel;
    private DbPowerMeter mPowerMeter = new DbPowerMeter();
    private PeakMonitor mPeakMonitor = new PeakMonitor(DbPowerMeter.DEFAULT_MINIMUM_POWER);
    private SourceEventProcessor mSourceEventProcessor = new SourceEventProcessor();
    private JLabel mPowerLabel;
    private JLabel mPeakLabel;
    private JLabel mSquelchLabel;
    private JLabel mSquelchValueLabel;
    private JButton mSquelchUpButton;
    private JButton mSquelchDownButton;
    private double mSquelchThreshold;
    private boolean mPanelVisible = false;
    private boolean mDftProcessing = false;

    /**
     * Constructs an instance.
     */
    public ChannelPowerPanel(PlaylistManager playlistManager, SettingsManager settingsManager)
    {
        mPlaylistManager = playlistManager;

        setLayout(new MigLayout("", "[][][][grow,fill]", "[grow,fill]"));
        mPowerMeter.setPeakVisible(true);
        mPowerMeter.setSquelchThresholdVisible(true);
        add(mPowerMeter);

        JPanel valuePanel = new JPanel();
        valuePanel.setLayout(new MigLayout("", "[right][left][][]", ""));

        mPeakLabel = new JLabel("0");
        valuePanel.add(new JLabel("Peak:"));
        valuePanel.add(mPeakLabel, "wrap");

        mPowerLabel = new JLabel("0");
        valuePanel.add(new JLabel("Power:"));
        valuePanel.add(mPowerLabel, "wrap");

        mSquelchLabel = new JLabel("Squelch:");
        mSquelchLabel.setEnabled(false);
        valuePanel.add(mSquelchLabel);
        mSquelchValueLabel = new JLabel(NOT_AVAILABLE);
        mSquelchValueLabel.setEnabled(false);
        valuePanel.add(mSquelchValueLabel, "wrap");

        IconFontSwing.register(FontAwesome.getIconFont());
        Icon iconUp = IconFontSwing.buildIcon(FontAwesome.ANGLE_UP, 12);
        mSquelchUpButton = new JButton(iconUp);
        mSquelchUpButton.setEnabled(false);
        mSquelchUpButton.addActionListener(e -> broadcast(SourceEvent.requestSquelchThreshold(null, mSquelchThreshold + 1)));
        valuePanel.add(mSquelchUpButton);

        Icon iconDown = IconFontSwing.buildIcon(FontAwesome.ANGLE_DOWN, 12);
        mSquelchDownButton = new JButton(iconDown);
        mSquelchDownButton.setEnabled(false);
        mSquelchDownButton.addActionListener(e -> broadcast(SourceEvent.requestSquelchThreshold(null, mSquelchThreshold - 1)));
        valuePanel.add(mSquelchDownButton);

        add(valuePanel);
        add(new JSeparator(JSeparator.VERTICAL));

        JPanel fftPanel = new JPanel();
        fftPanel.setLayout(new MigLayout("", "[grow,fill]", "[][grow,fill]"));
        fftPanel.add(new JLabel("Channel Spectrum"), "wrap");
        mSpectrumPanel = new SpectrumPanel(settingsManager);
        fftPanel.add(mSpectrumPanel);
        add(fftPanel);

        mSampleStreamTapModule.setListener(mComplexDftProcessor);
        mComplexDftProcessor.addConverter(mDFTResultsConverter);
        mDFTResultsConverter.addListener(mSpectrumPanel);
        mSpectrumPanel.clearSpectrum();
    }

    /**
     * Signals this panel to indicate if this panel is visible to turn on the FFT processor when the panel is visible
     * and turn off the FFT processor when it's not.
     *
     * Note: this method is intended to be called by the Swing event thread to ensure that only a single thread is
     * invoking either this method, or the receive() method, since there is no thread synchronization between these
     * two methods and they each depend on stable access to the mPanelVisible variable.
     *
     * @param visible true to indicate that this panel is showing/visible.
     */
    public void setPanelVisible(boolean visible)
    {
        mPanelVisible = visible;
        updateFFTProcessing();
    }

    /**
     * Updates processing state for the DFT processor.  Turns on DFT processing when we have a processing chain and
     * when the user has this tab selected and visible.  Otherwise, turns off DFT processing.
     */
    private void updateFFTProcessing()
    {
        if(mPanelVisible && mProcessingChain != null)
        {
            startDftProcessing();
        }
        else
        {
            stopDftProcessing();
        }
    }

    /**
     * Starts DFT processing
     */
    private void startDftProcessing()
    {
        if(!mDftProcessing)
        {
            mDftProcessing = true;
            mSampleStreamTapModule.setListener(mComplexDftProcessor);
            mComplexDftProcessor.start();
        }
    }

    /**
     * Stops DFT processing
     */
    private void stopDftProcessing()
    {
        if(mDftProcessing)
        {
            mSampleStreamTapModule.removeListener();
            mComplexDftProcessor.stop();
            mSpectrumPanel.clearSpectrum();
            mDftProcessing = false;
        }
    }

    /**
     * Updates the channel's decode configuration with a new squelch threshold value
     */
    private void setConfigSquelchThreshold(int threshold)
    {
        if(mProcessingChain != null)
        {
            Channel channel = mPlaylistManager.getChannelProcessingManager().getChannel(mProcessingChain);

            if(channel != null && channel.getDecodeConfiguration() instanceof ISquelchConfiguration)
            {
                ISquelchConfiguration configuration = (ISquelchConfiguration)channel.getDecodeConfiguration();
                configuration.setSquelchThreshold(threshold);
                mPlaylistManager.schedulePlaylistSave();
            }
        }
    }

    private void broadcast(SourceEvent sourceEvent)
    {
        if(mProcessingChain != null)
        {
            mProcessingChain.broadcast(sourceEvent);
        }
    }

    /**
     * Resets controls when changing processing chain source.  Note: this must be called on the Swing
     * dispatch thread because it directly invokes swing components.
     */
    private void reset()
    {
        mPeakMonitor.reset();
        mPowerMeter.reset();

        mPeakLabel.setText("0");
        mPowerLabel.setText("0");

        mSquelchLabel.setEnabled(false);
        mSquelchValueLabel.setText("Not Available");
        mSquelchValueLabel.setEnabled(false);
        mSquelchUpButton.setEnabled(false);
        mSquelchDownButton.setEnabled(false);
    }

    /**
     * Receive notifications of request to provide display of processing chain details.
     */
    @Override
    public void receive(ProcessingChain processingChain)
    {
        //Disconnect the FFT panel
        if(mProcessingChain != null)
        {
            mProcessingChain.removeSourceEventListener(mSourceEventProcessor);
            mProcessingChain.removeModule(mSampleStreamTapModule);
        }

        //Invoking reset - we're on the Swing dispatch thread here
        reset();

        mProcessingChain = processingChain;

        if(mProcessingChain != null)
        {
            mProcessingChain.addSourceEventListener(mSourceEventProcessor);
            mProcessingChain.addModule(mSampleStreamTapModule);
        }

        updateFFTProcessing();

        broadcast(SourceEvent.requestCurrentSquelchThreshold(null));
    }


    /**
     * Processor for source event stream to capture power level and squelch related source events.
     */
    private class SourceEventProcessor implements Listener<SourceEvent>
    {
        @Override
        public void receive(SourceEvent sourceEvent)
        {
            switch(sourceEvent.getEvent())
            {
                case NOTIFICATION_CHANNEL_POWER ->
                    {
                        final double power = sourceEvent.getValue().doubleValue();
                        final double peak = mPeakMonitor.process(power);

                        EventQueue.invokeLater(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                mPowerMeter.setPower(power);
                                mPowerLabel.setText(DECIMAL_FORMAT.format(power));

                                mPowerMeter.setPeak(peak);
                                mPeakLabel.setText(DECIMAL_FORMAT.format(peak));
                            }
                        });

                    }
                case NOTIFICATION_SQUELCH_THRESHOLD ->
                        {
                            final double threshold = sourceEvent.getValue().doubleValue();
                            mSquelchThreshold = threshold;
                            setConfigSquelchThreshold((int)threshold);

                            EventQueue.invokeLater(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    mPowerMeter.setSquelchThreshold(threshold);
                                    mSquelchLabel.setEnabled(true);
                                    mSquelchValueLabel.setEnabled(true);
                                    mSquelchValueLabel.setText(DECIMAL_FORMAT.format(threshold));
                                    mSquelchDownButton.setEnabled(true);
                                    mSquelchUpButton.setEnabled(true);
                                }
                            });
                        }
            }
        }
    }
}
