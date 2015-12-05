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
package org.virtue.network.event.handler.impl;

import org.virtue.model.entity.player.Player;
import org.virtue.model.entity.region.Tile;
import org.virtue.network.event.context.impl.in.WalkEventContext;
import org.virtue.network.event.handler.GameEventHandler;

/**
 * @author Im Frizzy <skype:kfriz1998>
 * @since Oct 18, 2014
 */
public class WalkEventHandler implements GameEventHandler<WalkEventContext> {

	@Override
	public void handle(Player player, WalkEventContext context) {
		//logger.warn("Unhandled walk event: targetX=" + context.getBaseX() + ", targetY=" + context.getBaseY() + ", forceRun=" + context.forceRun());
		/*if (player.getCombat().inCombat()) {
			player.getCombat().destroy();
			player.getMovement().stop();
		}*/
		boolean success = player.getMovement().moveTo(context.getBaseX(), context.getBaseY());
		if (context.forceRun()) {
			player.getMovement().forceRunToggle();
		}
		if (success) {
			long currentTime = System.currentTimeMillis();
			if (player.getLockDelay() > currentTime) {
				return;
			}
			Tile target = player.getMovement().getDestination();
			if (target != null) {//TODO: Fix the minimap flag
				//System.out.println("Target: "+target);
				//System.out.println("BaseX="+(8 * (target.getChunkX() - (Tile.REGION_SIZES[0] >> 4)))
				//		+", BaseY="+(8 * (target.getChunkY() - (Tile.REGION_SIZES[0] >> 4))));
				//System.out.println("Flag: x="+target.getXInRegion()+", y="+target.getYInRegion());
				player.getDispatcher().sendMapFlag(target.getLocalX(player.getViewport().getBaseTile()), target.getLocalY(player.getViewport().getBaseTile()));
			}
		} else {
			player.getDispatcher().sendResetMapFlag();
		}
	}

}
