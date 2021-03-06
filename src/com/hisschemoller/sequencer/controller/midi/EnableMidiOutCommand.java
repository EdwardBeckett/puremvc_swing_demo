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

package com.hisschemoller.sequencer.controller.midi;

import com.hisschemoller.sequencer.model.SequencerProxy;
import com.hisschemoller.sequencer.notification.SeqNotifications;
import com.hisschemoller.sequencer.util.EPGPreferences;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

public class EnableMidiOutCommand extends SimpleCommand {
	/**
	 * Enable MIDI out.
	 */
	@Override
	public final void execute( final INotification notification ) {
		boolean isEnabled = (Boolean) notification.getBody();
		SequencerProxy sequencerProxy = (SequencerProxy) getFacade().retrieveProxy( SequencerProxy.NAME );

		if( sequencerProxy.getMidiEnabled() != isEnabled ) {
			sequencerProxy.setMidiEnabled( isEnabled );
			sendNotification( SeqNotifications.MIDI_OUT_DEVICE_ENABLED, isEnabled );
			EPGPreferences.putBoolean( EPGPreferences.MIDI_OUT_ENABLED, isEnabled );
		}
	}
}
