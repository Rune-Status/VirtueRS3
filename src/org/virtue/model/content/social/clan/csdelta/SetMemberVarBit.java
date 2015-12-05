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
package org.virtue.model.content.social.clan.csdelta;

import org.virtue.network.event.buffer.OutboundBuffer;

/**
 * @author Im Frizzy <skype:kfriz1998>
 * @author Frosty Teh Snowman <skype:travis.mccorkle>
 * @author Arthur <skype:arthur.behesnilian>
 * @author Sundays211
 * @since 28/12/2014
 */
public class SetMemberVarBit implements ClanSettingsDelta {
	
	private final int slot;
	private final int value;
	private final int start;
	private final int end;
	
	public SetMemberVarBit (int slot, int value, int start, int end) {
		this.slot = slot;
		this.value	= value;
		this.start = start;
		this.end = end;
	}

	/* (non-Javadoc)
	 * @see org.virtue.model.content.social.clan.csdelta.ClanSettingsDelta#packDelta(org.virtue.network.event.buffer.OutboundBuffer)
	 */
	@Override
	public void packDelta(OutboundBuffer buffer) {
		buffer.putShort(slot);
		buffer.putInt(value);
		buffer.putByte(start);
		buffer.putByte(end);
	}

	/* (non-Javadoc)
	 * @see org.virtue.model.content.social.clan.csdelta.ClanSettingsDelta#getTypeID()
	 */
	@Override
	public int getTypeID() {
		return 7;
	}

}
