/**
 * Copyright 2011 Wouter Hisschem�ller
 *
 * This file is part of Euclidean Pattern Generator.
 *
 * Euclidean Pattern Generator is free software: you can redistribute
 * it and/or modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * Euclidean Pattern Generator is distributed in the hope that
 * it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Euclidean Pattern Generator.  If not,
 * see <http://www.gnu.org/licenses/>.
 */

package com.hisschemoller.sequencer.view.components;

import com.hisschemoller.sequencer.model.vo.PatternVO;
import com.hisschemoller.sequencer.model.vo.SettingsVO;
import com.hisschemoller.sequencer.util.EPGSwingEngine;
import com.hisschemoller.sequencer.util.SequencerEnums.Quantization;
import com.hisschemoller.sequencer.view.events.IViewEventListener;
import com.hisschemoller.sequencer.view.events.ViewEvent;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.UUID;
import java.util.Vector;

public class PatternSettings implements ActionListener, ChangeListener {
	public static final long serialVersionUID = -1L;
	private UUID _patternID;
	private JSlider _stepsSlider;
	private JLabel _stepsLabel;
	private JSlider _fillsSlider;
	private JLabel _fillsLabel;
	private JSlider _rotationSlider;
	private JLabel _rotationLabel;
	private JSlider _channelSlider;
	private JLabel _channelLabel;
	private JSlider _pitchSlider;
	private JLabel _pitchLabel;
	private JSlider _velocitySlider;
	private JLabel _velocityLabel;
	private JSlider _lengthSlider;
	private JLabel _lengthLabel;
	private JTextField _addressTextField;
	private JCheckBox _muteCheckBox;
	private JCheckBox _soloCheckBox;
	private JButton _deleteButton;
	private ButtonGroup _stepTimeButtonGroup;
	private JTextField _nameTextField;
	private Vector<IViewEventListener> _viewEventListeners = new Vector<IViewEventListener>();
	private Boolean _isUpdating = false;
	private Boolean _isEnabled = true;

	public PatternSettings( EPGSwingEngine swingEngine ) {
		_stepsSlider = (JSlider) swingEngine.find( "SETTINGS_EUCLID_LENGTH_SLIDER" );
		_stepsSlider.addChangeListener( this );
		_stepsLabel = (JLabel) swingEngine.find( "SETTINGS_EUCLID_LENGTH_TEXT" );

		_fillsSlider = (JSlider) swingEngine.find( "SETTINGS_EUCLID_NOTES_SLIDER" );
		_fillsSlider.addChangeListener( this );
		_fillsLabel = (JLabel) swingEngine.find( "SETTINGS_EUCLID_NOTES_TEXT" );

		_rotationSlider = (JSlider) swingEngine.find( "SETTINGS_EUCLID_ROTATE_SLIDER" );
		_rotationSlider.addChangeListener( this );
		_rotationLabel = (JLabel) swingEngine.find( "SETTINGS_EUCLID_ROTATE_TEXT" );

		_channelSlider = (JSlider) swingEngine.find( "SETTINGS_MIDI_CHANNEL_SLIDER" );
		_channelSlider.addChangeListener( this );
		_channelLabel = (JLabel) swingEngine.find( "SETTINGS_MIDI_CHANNEL_TEXT" );

		_addressTextField = (JTextField) swingEngine.find( "SETTINGS_OSC_ADDRESS" );
		_addressTextField.getDocument().addDocumentListener( new DocumentListener() {
			public void removeUpdate( DocumentEvent event ) {
				PatternSettings.this.dispatchViewEventLater( _addressTextField, ViewEvent.OSC_ADDRESS_CHANGE );
			}

			public void insertUpdate( DocumentEvent event ) {
				PatternSettings.this.dispatchViewEventLater( _addressTextField, ViewEvent.OSC_ADDRESS_CHANGE );
			}

			public void changedUpdate( DocumentEvent event ) {
			}
		} );

		_pitchSlider = (JSlider) swingEngine.find( "SETTINGS_MIDI_PITCH_SLIDER" );
		_pitchSlider.addChangeListener( this );
		_pitchLabel = (JLabel) swingEngine.find( "SETTINGS_MIDI_PITCH_TEXT" );

		_velocitySlider = (JSlider) swingEngine.find( "SETTINGS_MIDI_VELOCITY_SLIDER" );
		_velocitySlider.addChangeListener( this );
		_velocityLabel = (JLabel) swingEngine.find( "SETTINGS_MIDI_VELOCITY_TEXT" );

		_lengthSlider = (JSlider) swingEngine.find( "SETTINGS_NOTE_LENGTH_SLIDER" );
		_lengthSlider.addChangeListener( this );
		_lengthLabel = (JLabel) swingEngine.find( "SETTINGS_NOTE_LENGTH_TEXT" );

		_nameTextField = (JTextField) swingEngine.find( "SETTINGS_PATTERN_NAME" );
		_nameTextField.getDocument().addDocumentListener( new DocumentListener() {
			public void removeUpdate( DocumentEvent event ) {
				PatternSettings.this.dispatchViewEventLater( _nameTextField, ViewEvent.NAME_CHANGE );
			}

			public void insertUpdate( DocumentEvent event ) {
				PatternSettings.this.dispatchViewEventLater( _nameTextField, ViewEvent.NAME_CHANGE );
			}

			public void changedUpdate( DocumentEvent event ) {
			}
		} );

		_muteCheckBox = (JCheckBox) swingEngine.find( "SETTINGS_MUTE_CHECKBOX" );
		_muteCheckBox.addActionListener( this );

		_soloCheckBox = (JCheckBox) swingEngine.find( "SETTINGS_SOLO_CHECKBOX" );
		_soloCheckBox.addActionListener( this );

		_deleteButton = (JButton) swingEngine.find( "SETTINGS_DELETE_BUTTON" );
		_deleteButton.addActionListener( this );

		_stepTimeButtonGroup = new ButtonGroup();

		JRadioButton radioButton = (JRadioButton) swingEngine.find( "SETTINGS_STEP_LENGTH_1" );
		radioButton.setActionCommand( Quantization.Q1.name() );
		radioButton.addActionListener( this );
		_stepTimeButtonGroup.add( radioButton );
		radioButton = (JRadioButton) swingEngine.find( "SETTINGS_STEP_LENGTH_1_2" );
		radioButton.setActionCommand( Quantization.Q2.name() );
		_stepTimeButtonGroup.add( radioButton );
		radioButton.addActionListener( this );
		radioButton = (JRadioButton) swingEngine.find( "SETTINGS_STEP_LENGTH_1_4" );
		radioButton.setActionCommand( Quantization.Q4.name() );
		radioButton.addActionListener( this );
		_stepTimeButtonGroup.add( radioButton );
		radioButton = (JRadioButton) swingEngine.find( "SETTINGS_STEP_LENGTH_1_8" );
		radioButton.setActionCommand( Quantization.Q8.name() );
		radioButton.addActionListener( this );
		_stepTimeButtonGroup.add( radioButton );
		radioButton = (JRadioButton) swingEngine.find( "SETTINGS_STEP_LENGTH_1_16" );
		radioButton.setActionCommand( Quantization.Q16.name() );
		radioButton.addActionListener( this );
		_stepTimeButtonGroup.add( radioButton );
		radioButton = (JRadioButton) swingEngine.find( "SETTINGS_STEP_LENGTH_1_32" );
		radioButton.setActionCommand( Quantization.Q32.name() );
		radioButton.addActionListener( this );
		_stepTimeButtonGroup.add( radioButton );
	}

	public void updatePattern( PatternVO patternVO ) {
		if( patternVO != null ) {
			setSettingsEnabled( true );
			if( patternVO.id != _patternID ) {
				_patternID = patternVO.id;
				setValues( patternVO );
			}
		} else {
			setSettingsEnabled( false );
			_patternID = null;
		}
	}

	public void updateSettings( PatternVO patternVO ) {
		if( patternVO.id == _patternID ) {
			setValues( patternVO );
		}
	}

	public void updateQuantization( PatternVO patternVO ) {
		if( patternVO.id == _patternID ) {
			setValues( patternVO );
		}
	}

	public void soloPattern( PatternVO patternVO ) {
		_muteCheckBox.setEnabled( !patternVO.mutedBySolo && !patternVO.solo );
	}

	public void mutePattern( PatternVO patternVO ) {

	}

	public void updateResolution( int ppqn ) {
		_isUpdating = true;
		_lengthSlider.setMaximum( ppqn * 4 );
		_isUpdating = false;
	}

	private void setValues( PatternVO patternVO ) {
		_isUpdating = true;

		_stepsSlider.setValue( patternVO.steps );
		_stepsLabel.setText( Integer.toString( patternVO.steps ) );
		_fillsSlider.setMaximum( patternVO.steps );
		_fillsSlider.setValue( patternVO.fills );
		_fillsLabel.setText( Integer.toString( patternVO.fills ) );
		_rotationSlider.setMaximum( patternVO.steps - 1 );
		_rotationSlider.setValue( patternVO.rotation );
		_rotationLabel.setText( Integer.toString( patternVO.rotation ) );
		_channelSlider.setValue( patternVO.midiChannel + 1 );
		_channelLabel.setText( Integer.toString( patternVO.midiChannel + 1 ) );
		_pitchSlider.setValue( patternVO.midiPitch );
		_pitchLabel.setText( Integer.toString( patternVO.midiPitch ) );
		_velocitySlider.setValue( patternVO.midiVelocity );
		_velocityLabel.setText( Integer.toString( patternVO.midiVelocity ) );
		_lengthSlider.setValue( patternVO.noteLength );
		_addressTextField.setText( patternVO.address );
		_lengthLabel.setText( Integer.toString( patternVO.noteLength ) );
		_muteCheckBox.setEnabled( !patternVO.mutedBySolo && !patternVO.solo );
		_muteCheckBox.setSelected( patternVO.mute );
		_soloCheckBox.setSelected( patternVO.solo );
		_nameTextField.setText( patternVO.name );

		Enumeration<AbstractButton> radioButtons = _stepTimeButtonGroup.getElements();
		while( radioButtons.hasMoreElements() ) {
			JRadioButton radioButton = (JRadioButton) radioButtons.nextElement();
			String quantizationName = radioButton.getActionCommand();
			Quantization quantization = Enum.valueOf( Quantization.class, quantizationName );

			if( patternVO.quantization == quantization.getValue() ) {
				_stepTimeButtonGroup.setSelected( radioButton.getModel(), true );
			}
		}

		_isUpdating = false;
	}

	public void actionPerformed( ActionEvent event ) {
		if( event.getSource() == _deleteButton ) {
			dispatchViewEvent( _deleteButton, ViewEvent.DELETE_PATTERN );
		} else {
			if( event.getSource() == _muteCheckBox ) {
				dispatchViewEvent( _deleteButton, ViewEvent.MUTE_PATTERN );
			} else {
				if( event.getSource() == _soloCheckBox ) {
					dispatchViewEvent( _deleteButton, ViewEvent.SOLO_PATTERN );
				} else {
					if( event.getSource() == _addressTextField ) {
						dispatchViewEvent( _addressTextField, ViewEvent.OSC_ADDRESS_CHANGE );
					} else {
						if( event.getSource().getClass() == JRadioButton.class ) {
							dispatchViewEvent( _stepTimeButtonGroup, ViewEvent.QUANTIZATION );
						}
					}
				}
			}
		}
	}

	public void stateChanged( ChangeEvent event ) {
		if( event.getSource() == _stepsSlider ) {
			dispatchViewEvent( _stepsSlider, ViewEvent.PATTERN_SETTINGS_CHANGE );
		} else {
			if( event.getSource() == _fillsSlider ) {
				dispatchViewEvent( _fillsSlider, ViewEvent.PATTERN_SETTINGS_CHANGE );
			} else {
				if( event.getSource() == _rotationSlider ) {
					dispatchViewEvent( _rotationSlider, ViewEvent.PATTERN_SETTINGS_CHANGE );
				} else {
					if( event.getSource() == _channelSlider ) {
						dispatchViewEvent( _channelSlider, ViewEvent.MIDI_SETTINGS_CHANGE );
					} else {
						if( event.getSource() == _pitchSlider ) {
							dispatchViewEvent( _pitchSlider, ViewEvent.MIDI_SETTINGS_CHANGE );
						} else {
							if( event.getSource() == _velocitySlider ) {
								dispatchViewEvent( _velocitySlider, ViewEvent.MIDI_SETTINGS_CHANGE );
							} else {
								if( event.getSource() == _lengthSlider ) {
									dispatchViewEvent( _lengthSlider, ViewEvent.MIDI_SETTINGS_CHANGE );
								}
							}
						}
					}
				}
			}
		}
	}

	public SettingsVO getSettings() {
		SettingsVO settingsVO = new SettingsVO();
		settingsVO.patternID = _patternID;
		settingsVO.midiChannel = _channelSlider.getValue() - 1;
		settingsVO.midiPitch = _pitchSlider.getValue();
		settingsVO.midiVelocity = _velocitySlider.getValue();
		settingsVO.noteLength = _lengthSlider.getValue();
		settingsVO.address = _addressTextField.getText();
		settingsVO.steps = _stepsSlider.getValue();
		settingsVO.fills = _fillsSlider.getValue();
		settingsVO.rotation = _rotationSlider.getValue();
		settingsVO.mute = _muteCheckBox.isSelected();
		settingsVO.solo = _soloCheckBox.isSelected();
		settingsVO.name = _nameTextField.getText();

		String quantizationName = _stepTimeButtonGroup.getSelection().getActionCommand();
		Quantization quantization = Enum.valueOf( Quantization.class, quantizationName );
		settingsVO.quantization = quantization.getValue();

		return settingsVO;
	}

	public synchronized void addViewEventListener( IViewEventListener listener ) {
		_viewEventListeners.addElement( listener );
	}

	public synchronized void removeViewEventListener( IViewEventListener listener ) {
		_viewEventListeners.removeElement( listener );
	}

	/**
	 * Dispatch custom ViewEvent .
	 */
	protected void dispatchViewEventLater( Object inSource, int inId ) {
		final Object source = inSource;
		final int id = inId;

		SwingUtilities.invokeLater( new Runnable() {
			public void run() {
				dispatchViewEvent( source, id );
			}
		} );
	}

	/**
	 * Dispatch custom ViewEvent.
	 */
	protected void dispatchViewEvent( Object source, int id ) {
		if( _isUpdating ) {
			return;
		}

		ViewEvent viewEvent = new ViewEvent( source, id );
		for(int i = 0; i < _viewEventListeners.size(); i++) {
			( (IViewEventListener) _viewEventListeners.elementAt( i ) ).viewEventHandler( viewEvent );
		}
	}

	private void setSettingsEnabled( boolean enabled ) {
		if( enabled != _isEnabled ) {
			_isEnabled = enabled;
			_stepsSlider.setEnabled( _isEnabled );
			_fillsSlider.setEnabled( _isEnabled );
			_rotationSlider.setEnabled( _isEnabled );
			_channelSlider.setEnabled( _isEnabled );
			_velocitySlider.setEnabled( _isEnabled );
			_pitchSlider.setEnabled( _isEnabled );
			_addressTextField.setEnabled( _isEnabled );
			_lengthSlider.setEnabled( _isEnabled );
			_muteCheckBox.setEnabled( _isEnabled );
			_soloCheckBox.setEnabled( _isEnabled );
			_nameTextField.setEnabled( _isEnabled );
			_deleteButton.setEnabled( _isEnabled );

			Enumeration<AbstractButton> radioButtons = _stepTimeButtonGroup.getElements();
			while( radioButtons.hasMoreElements() ) {
				radioButtons.nextElement().setEnabled( _isEnabled );
			}
		}
	}
}
