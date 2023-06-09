/* Copyright (C) 2017  Matteo Hausner
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

package de.bwravencl.controllerbuddy.gui.mumbleoverlay.message;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class OverlayMsgPid implements IMessagePart {

	public static final int SIZE = Integer.BYTES;

	public int pid;

	public OverlayMsgPid(final byte[] bytes) {
		setBytes(bytes);
	}

	public OverlayMsgPid(final int pid) {
		this.pid = pid;
	}

	@Override
	public byte[] getBytes() {
		final var bb = ByteBuffer.allocate(getSize());
		bb.order(ByteOrder.LITTLE_ENDIAN);

		bb.putInt(pid);

		return bb.array();
	}

	@Override
	public int getSize() {
		return SIZE;
	}

	@Override
	public void setBytes(final byte[] bytes) {
		final var bb = ByteBuffer.wrap(bytes);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		pid = bb.getInt();
	}
}
