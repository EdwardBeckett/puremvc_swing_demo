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

package com.hisschemoller.sequencer.model.vo;

import javax.sound.midi.MidiEvent;
import java.util.ArrayList;
import java.util.UUID;

public class PatternVO {
	public UUID id;
	public ArrayList<MidiEvent> events = new ArrayList<MidiEvent>();

	/** quantization, length and position in PPQN. */
	public int quantization;
	public int stepLength;
	public int patternLength;
	public int position;

	/** Pattern settings */
	public int steps;
	public int fills;
	public int rotation;

	/** MIDI settings */
	public int midiChannel;
	public int midiPitch;
	public int midiVelocity;
	public int noteLength;

	/** OSC settings */
	public String address = "";

	/** Other settings */
	public boolean mute = false;
	public boolean solo = false;
	public boolean mutedBySolo = false;
	public String name = "";

	/** Location. */
	public int viewX;
	public int viewY;

	public String toString() {
		String returnStr = "steps: " + steps + ", fills: " + fills + ", rot: " + rotation;
		returnStr += ", x: " + viewX + ", y: " + viewY + ", name: " + name;
		returnStr += ", quant: " + quantization + ", stepLength: " + stepLength + ", patternLength: " + patternLength + ", position: " + position;
		return returnStr;
	}
}