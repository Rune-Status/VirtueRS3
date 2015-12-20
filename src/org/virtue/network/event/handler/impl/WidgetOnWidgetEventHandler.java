/**
 * Copyright (c) 2014 Virtue Studios
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions\:
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
package org.virtue.network.event.handler.impl;

import org.virtue.Virtue;
import org.virtue.game.entity.player.Player;
import org.virtue.network.event.context.impl.in.WidgetOnWidgetEventContext;
import org.virtue.network.event.handler.GameEventHandler;

/**
 * @author Im Frizzy <skype:kfriz1998>
 * @author Frosty Teh Snowman <skype:travis.mccorkle>
 * @author Arthur <skype:arthur.behesnilian>
 * @author Sundays211
 * @since 6/11/2014
 */
public class WidgetOnWidgetEventHandler implements GameEventHandler<WidgetOnWidgetEventContext> {

	/* (non-Javadoc)
	 * @see org.virtue.network.event.handler.GameEventHandler#handle(org.virtue.game.entity.player.Player, org.virtue.network.event.context.GameEventContext)
	 */
	@Override
	public void handle(Player player, WidgetOnWidgetEventContext context) {
		if (!Virtue.getInstance().getWidgetRepository().handleUse(
				context.getIf1Interface(), context.getIf1Component(), context.getIf1Slot(), context.getIf1Item(), 
				context.getIf2Interface(), context.getIf2Component(), context.getIf2Slot(), context.getIf2Item(), player)) {
			defaultHandler(player, context);
		}
	}
	
	private void defaultHandler(Player player, WidgetOnWidgetEventContext context) {
		String message = "Nothing interesting happens.";
		if (player.getPrivilegeLevel().getRights() >= 2) {
			message = "Unhanded interface-on-interface: Interface1: id="+context.getIf1Interface()+", comp="+context.getIf1Component()
					+", slot="+context.getIf1Slot()+", itemID="+context.getIf1Item()
					+" Interface2: id="+context.getIf2Interface()+", comp="+context.getIf2Component()
					+", slot="+context.getIf2Slot()+", itemID="+context.getIf2Item();
		}		
		player.getDispatcher().sendGameMessage(message);
	}
}
