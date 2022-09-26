/* Copyright (C) 2020  Matteo Hausner
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
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package de.bwravencl.controllerbuddy.input.action;

import java.text.MessageFormat;

import de.bwravencl.controllerbuddy.gui.Main;
import de.bwravencl.controllerbuddy.input.Input;
import de.bwravencl.controllerbuddy.input.action.annotation.ActionProperty;
import de.bwravencl.controllerbuddy.input.action.gui.CursorSensitivityEditorBuilder;
import de.bwravencl.controllerbuddy.input.action.gui.MouseAxisEditorBuilder;

public abstract class ToCursorAction<V extends Number> extends InvertableAction<V> {

	public enum MouseAxis {

		X("MOUSE_AXIS_X"), Y("MOUSE_AXIS_Y");

		private final String label;

		MouseAxis(final String labelKey) {
			label = Main.strings.getString(labelKey);
		}

		@Override
		public String toString() {
			return label;
		}
	}

	private static final int DEFAULT_CURSOR_SENSITIVITY = 2000;

	@ActionProperty(label = "MOUSE_AXIS", editorBuilder = MouseAxisEditorBuilder.class, order = 10)
	private MouseAxis axis = MouseAxis.X;

	@ActionProperty(label = "CURSOR_SENSITIVITY", editorBuilder = CursorSensitivityEditorBuilder.class, order = 11)
	int cursorSensitivity = DEFAULT_CURSOR_SENSITIVITY;

	transient float remainingD;

	public MouseAxis getAxis() {
		return axis;
	}

	public int getCursorSensitivity() {
		return cursorSensitivity;
	}

	@Override
	public String getDescription(final Input input) {
		if (!isDescriptionEmpty())
			return super.getDescription(input);

		return MessageFormat.format(Main.strings.getString("MOUSE_AXIS_DIR"), axis.toString());
	}

	void moveCursor(final Input input, float d) {
		d = invert ? -d : d;

		d += remainingD;

		if (d >= -1f && d <= 1f)
			remainingD = d;
		else {
			remainingD = 0f;

			final var intD = Math.round(d);

			if (axis == MouseAxis.X)
				input.setCursorDeltaX(input.getCursorDeltaX() + intD);
			else
				input.setCursorDeltaY(input.getCursorDeltaY() + intD);
		}
	}

	public void setAxis(final MouseAxis axis) {
		this.axis = axis;
	}

	public void setCursorSensitivity(final int cursorSensitivity) {
		this.cursorSensitivity = cursorSensitivity;
	}
}
