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

package de.bwravencl.controllerbuddy.runmode;

import de.bwravencl.controllerbuddy.gui.GuiUtils;
import de.bwravencl.controllerbuddy.gui.Main;
import de.bwravencl.controllerbuddy.input.Input;
import java.awt.EventQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public abstract class RunMode implements Runnable {

    public static final int DEFAULT_POLL_INTERVAL = 1;
    private static final Logger log = Logger.getLogger(RunMode.class.getName());
    final Main main;
    final Input input;
    long pollInterval = DEFAULT_POLL_INTERVAL;
    int minAxisValue;
    int maxAxisValue;
    int nButtons;
    private boolean stopping;

    RunMode(final Main main, final Input input) {
        this.main = main;
        this.input = input;
        input.setRunMode(this);
    }

    final void controllerDisconnected() {
        if (stopping) {
            return;
        }

        Thread.startVirtualThread(() -> main.stopAll(true, true, true));

        log.log(Level.WARNING, Main.assembleControllerLoggingMessage("Could not read from", input.getController()));
        EventQueue.invokeLater(() -> GuiUtils.showMessageDialog(
                main,
                main.getFrame(),
                Main.strings.getString("COULD_NOT_READ_FROM_CONTROLLER_DIALOG_TEXT"),
                Main.strings.getString("ERROR_DIALOG_TITLE"),
                JOptionPane.ERROR_MESSAGE));

        stopping = true;
    }

    abstract Logger getLogger();

    public final int getMaxAxisValue() {
        return maxAxisValue;
    }

    public final int getMinAxisValue() {
        return minAxisValue;
    }

    public final long getPollInterval() {
        return pollInterval;
    }

    public final int getnButtons() {
        return nButtons;
    }

    final void logStart() {
        getLogger().log(Level.INFO, "Starting output");
    }

    final void logStop() {
        getLogger().log(Level.INFO, "Stopped output");
    }

    public final void setPollInterval(final long pollInterval) {
        this.pollInterval = pollInterval;
    }

    void setnButtons(final int nButtons) {
        this.nButtons = nButtons;
        input.initButtons();
    }
}
