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
package org.virtue.model.entity.region.packets;

import org.virtue.model.entity.player.Player;
import org.virtue.model.entity.region.SceneLocation;
import org.virtue.model.entity.region.Tile;
import org.virtue.network.event.buffer.OutboundBuffer;

/**
 * @author Im Frizzy <skype:kfriz1998>
 * @author Frosty Teh Snowman <skype:travis.mccorkle>
 * @author Arthur <skype:arthur.behesnilian>
 * @author Sundays211
 * @since 4/11/2014
 */
public class RemoveLocation implements SceneUpdatePacket {
	
	private int settings;
	private Tile tile;
	
	public RemoveLocation (SceneLocation object) {
		this.settings = (object.getRotation() & 0x3) | (object.getNodeType() << 2);
		this.tile = object.getTile();
	}

	/* (non-Javadoc)
	 * @see org.virtue.model.entity.region.packets.SceneUpdatePacket#getType()
	 */
	@Override
	public SceneUpdateType getType() {
		return SceneUpdateType.REMOVE_LOC;
	}

	/* (non-Javadoc)
	 * @see org.virtue.model.entity.region.packets.SceneUpdatePacket#encode(org.virtue.network.event.buffer.OutboundBuffer, org.virtue.model.entity.player.Player)
	 */
	@Override
	public void encode(OutboundBuffer buffer, Player player) {
		buffer.putByte(((tile.getX() % 8) & 0x7) << 4 | (tile.getY() % 8) & 0x7);
		buffer.putS(settings);
	}

	/* (non-Javadoc)
	 * @see org.virtue.model.entity.region.packets.SceneUpdatePacket#getTile()
	 */
	@Override
	public Tile getTile() {
		return tile;
	}

}
