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
package org.virtue.script;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Im Frizzy <skype:kfriz1998>
 * @author Frosty Teh Snowman <skype:travis.mccorkle>
 * @author Arthur <skype:arthur.behesnilian>
 * @author Kayla <skype:ashbysmith1996>
 * @author Sundays211
 * @since 27/11/2015
 */
public enum ScriptEventType {
	/**
	 * The first option on a location
	 */
	LOC_OP1(1),
	
	/**
	 * The second option on a location
	 */
	LOC_OP2(2),
	
	/**
	 * The third option on a location
	 */
	LOC_OP3(3),
	
	/**
	 * The fourth option on a location
	 */
	LOC_OP4(4),
	
	/**
	 * The fifth option on a location
	 */
	LOC_OP5(5),
	
	/**
	 * Called when an interface component is used on a location
	 */
	LOC_OPUSE(6),
	
	/**
	 * Called when a backpack item is used on a location
	 */
	LOC_OP_ITEMUSE(7),
	
	/**
	 * The first option on an NPC
	 */
	NPC_OP1(21),
	
	/**
	 * The second option on an NPC
	 */
	NPC_OP2(22),
	
	/**
	 * The third option on an NPC
	 */
	NPC_OP3(23),
	
	/**
	 * The fourth option on an NPC
	 */
	NPC_OP4(24),
	
	/**
	 * The fifth option on an NPC
	 */
	NPC_OP5(25),
	
	/**
	 * Called when an interface component is used on an NPC
	 */
	NPC_OPUSE(26),
	
	/**
	 * The first option on an inventory item
	 */
	ITEM_IOP1(41),
	
	/**
	 * The second option on an inventory item
	 */
	ITEM_IOP2(42),
	
	/**
	 * The third option on an inventory item
	 */
	ITEM_IOP3(43),
	
	/**
	 * The fourth option on an inventory item
	 */
	ITEM_IOP4(44),
	
	/**
	 * The fifth option on an inventory item
	 */
	ITEM_IOP5(45),
	
	/**
	 * Called when an interface component is used on an inventory item
	 */
	ITEM_IOPUSE(46),
	
	/**
	 * The first option on a ground item
	 */
	ITEM_OP1(51),
	
	/**
	 * The second option on a ground item
	 */
	ITEM_OP2(52),
	
	/**
	 * The third option on a ground item
	 */
	ITEM_OP3(53),
	
	/**
	 * The fourth option on a ground item
	 */
	ITEM_OP4(54),
	
	/**
	 * The fifth option on a ground item
	 */
	ITEM_OP5(55),
	
	/**
	 * Called when an interface component is used on a ground item
	 */
	ITEM_OPUSE(56),
	
	/**
	 * The first option on an interface component
	 */
	IF_BUTTON1(81),
	
	/**
	 * The second option on an interface component
	 */
	IF_BUTTON2(82),
	
	/**
	 * The third option on an interface component
	 */
	IF_BUTTON3(83),
	
	/**
	 * The fourth option on an interface component
	 */
	IF_BUTTON4(84),
	
	/**
	 * The fifth option on an interface component
	 */
	IF_BUTTON5(85),
	
	/**
	 * The sixth option on an interface component
	 */
	IF_BUTTON6(86),
	
	/**
	 * The seventh option on an interface component
	 */
	IF_BUTTON7(87),
	
	/**
	 * The eight option on an interface component
	 */
	IF_BUTTON8(88),
	
	/**
	 * The nineth option on an interface component
	 */
	IF_BUTTON9(89),
	
	/**
	 * The tenth option on an interface component
	 */
	IF_BUTTON10(90),
	
	/**
	 * Called once every server cycle (600ms)
	 */
	WORLD_CYCLE(100),
	
	/**
	 * Called when the specified command is entered
	 */
	COMMAND(101),
	
	/**
	 * Called when the specified administrator-only command is entered
	 */
	COMMAND_ADMIN(102),
	
	/**
	 * Called when the specified moderator-only command is entered
	 */
	COMMAND_MOD(103);
	
	private int id;
	
	private ScriptEventType (int id) {
		this.id = id;
	}
	
	public int getId () {
		return id;
	}
	
	private static Map<Integer, ScriptEventType> lookupMap;
	
	/**
	 * Gets the ScriptEventType which matches the given ID
	 * @param id The ID to lookup
	 * @return The ScriptEventType for the specified ID, or null if none exists.
	 */
	public static ScriptEventType getById (int id) {
		if (lookupMap == null) {
			lookupMap = new HashMap<>();
			for (ScriptEventType type : values()) {
				lookupMap.put(type.getId(), type);
			}
		}
		return lookupMap.get(id);
	}
}
