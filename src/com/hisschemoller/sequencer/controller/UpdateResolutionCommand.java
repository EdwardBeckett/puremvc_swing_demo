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

package com.hisschemoller.sequencer.controller;

import com.hisschemoller.sequencer.model.SequencerProxy;
import com.hisschemoller.sequencer.notification.SeqNotifications;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

public class UpdateResolutionCommand extends SimpleCommand {
	/**
	 * Change sequencer resolution in PPQN.
	 */
	@Override
	public final void execute( final INotification notification ) {
		String ppqnString = notification.getBody().toString();
		int ppqn = Integer.parseInt( ppqnString );
		SequencerProxy sequencerProxy = (SequencerProxy) getFacade().retrieveProxy( SequencerProxy.NAME );
		sequencerProxy.setPulsesPerQuarterNote( ppqn );

		sendNotification( SeqNotifications.RESOLUTION_UPDATED, ppqn );
	}
}
