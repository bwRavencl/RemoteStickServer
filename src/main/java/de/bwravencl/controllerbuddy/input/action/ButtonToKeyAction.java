/* Copyright (C) 2018  Matteo Hausner
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package de.bwravencl.controllerbuddy.input.action;

import de.bwravencl.controllerbuddy.input.Input;

public class ButtonToKeyAction extends ToKeyAction implements IButtonToAction {

	private boolean longPress = DEFAULT_LONG_PRESS;
	private float activationValue = DEFAULT_ACTIVATION_VALUE;

	@Override
	public void doAction(final Input input, float value) {
		value = handleLongPress(input, value);

		if (!IButtonToAction.floatEquals(value, activationValue)) {
			if (downUp)
				wasUp = true;
			else
				input.getDownKeyStrokes().remove(keystroke);
		} else if (downUp) {
			if (wasUp) {
				input.getDownUpKeyStrokes().add(keystroke);
				wasUp = false;
			}
		} else
			input.getDownKeyStrokes().add(keystroke);
	}

	@Override
	public float getActivationValue() {
		return activationValue;
	}

	@Override
	public boolean isLongPress() {
		return longPress;
	}

	@Override
	public void setActivationValue(final float activationValue) {
		this.activationValue = activationValue;
	}

	@Override
	public void setLongPress(final boolean longPress) {
		this.longPress = longPress;
	}

}
