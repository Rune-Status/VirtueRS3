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
package org.virtue.network.event.context.impl.in;

import org.virtue.network.event.IncomingEventType;
import org.virtue.network.event.context.GameEventContext;

/**
 * @author Im Frizzy <skype:kfriz1998>
 * @since Oct 5, 2014
 */
public class ButtonClickEventContext implements GameEventContext {
	
	private int hash;
	private int slot;
	private int itemID;
	private OptionButton button;
	
	public ButtonClickEventContext(int hash, int slot, int itemID, int opcode) {
		this.hash = hash;
		this.slot = slot;
		this.itemID = itemID;
		this.button = forOpcode(opcode);
	}

	public int getHash() {
		return hash;
	}

	public int getSlot() {
		return slot;
	}

	public int getItemID() {
		return itemID;
	}
	
	public OptionButton getButton () {
		return button;
	}

	public int getInterfaceId() {
		return hash >> 16;
	}

	public int getComponentId() {
		return hash & 0xffff;
	}
	
	public static OptionButton forOpcode (int opcode) {
		switch (IncomingEventType.forOpcode(opcode)) {
		case IF_OPTION_1:
			return OptionButton.ONE;
		case IF_OPTION_2:
			return OptionButton.TWO;
		case IF_OPTION_3:
			return OptionButton.THREE;
		case IF_OPTION_4:
			return OptionButton.FOUR;
		case IF_OPTION_5:
			return OptionButton.FIVE;
		case IF_OPTION_6:
			return OptionButton.SIX;
		case IF_OPTION_7:
			return OptionButton.SEVEN;
		case IF_OPTION_8:
			return OptionButton.EIGHT;
		case IF_OPTION_9:
			return OptionButton.NINE;
		case IF_OPTION_10:
			return OptionButton.TEN;
		default:
			return null;
		}
	}
	
}
