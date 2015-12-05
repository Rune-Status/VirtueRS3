/**
 * Copyright (c) 2014 Virtue Studios
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.virtue.model.entity.player.widget.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.virtue.model.entity.player.Player;
import org.virtue.model.entity.player.widget.Widget;
import org.virtue.model.entity.player.widget.WidgetState;
import org.virtue.network.event.context.impl.in.OptionButton;

/**
 * @author Im Frizzy <skype:kfriz1998>
 * @author Frosty Teh Snowman <skype:travis.mccorkle>
 * @author Arthur <skype:arthur.behesnilian>
 * @author Sundays211
 * @author Sam Bartlett
 * @since Jan 29, 2015
 */
public class CutsceneWidget extends Widget {

	/**
	 * The {@link Logger} instance
	 */
	private static Logger logger = LoggerFactory
			.getLogger(CutsceneWidget.class);

	/**
	 * The {@link CutsceneWidget} instance
	 */
	public static CutsceneWidget instance;

	/**
	 * The {@link CutsceneWidget} constructor
	 */
	public CutsceneWidget() {
		logger.info("Creating new CutsceneWidget instance");
	}

	/**
	 * Returns the {@link CutsceneWidget} instance
	 */
	public static CutsceneWidget getInstance() {
		if (instance == null) {
			try {
				instance = new CutsceneWidget();
			} catch (Exception e) {
				logger.error("Error creating new CutsceneWidget instance", e);
			}
		}
		return instance;
	}

	/* (non-Javadoc)
	 * @see org.virtue.model.entity.player.widget.Widget#click(int, int, int, int, org.virtue.model.entity.player.Player, org.virtue.network.event.context.impl.in.OptionButton)
	 */
	@Override
	public boolean click(int widgetId, int componentId, int slotId, int itemId, Player player, OptionButton option) {
			player.getWidgets().closeWidget(548, 4);
		    return true;
	}

	/* (non-Javadoc)
	 * @see org.virtue.model.entity.player.widget.Widget#getStates()
	 */
	@Override
	public int[] getStates() {
		return new int[] { WidgetState.CUTSCENE_WIDGET.getID() };
	}
}
