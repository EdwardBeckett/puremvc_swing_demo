/**
 * Copyright 2011 Wouter Hisschem嗟ler
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

package com.hisschemoller.sequencer.controller.pattern;

import com.hisschemoller.sequencer.model.SequencerProxy;
import com.hisschemoller.sequencer.model.vo.PatternVO;
import com.hisschemoller.sequencer.model.vo.SettingsVO;
import com.hisschemoller.sequencer.notification.SeqNotifications;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

public final class UpdateOscSettingsCommand extends SimpleCommand {
	/**
	 * Update OSC settings.
	 */
	@Override
	public final void execute( final INotification notification ) {
		SettingsVO settingsVO = (SettingsVO) notification.getBody();

		SequencerProxy sequencerProxy = (SequencerProxy) getFacade().retrieveProxy( SequencerProxy.NAME );
		PatternVO patternVO = sequencerProxy.getPatternByID( settingsVO.patternID );

		if( !patternVO.address.equals( settingsVO.address ) ) {
			patternVO.address = settingsVO.address;
			sendNotification( SeqNotifications.OSC_SETTINGS_UPDATED, patternVO );
		}
	}
}