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

/**
 * @author Im Frizzy <skype:kfriz1998>
 * @author Frosty Teh Snowman <skype:travis.mccorkle>
 * @author Arthur <skype:arthur.behesnilian>
 * @author Kayla <skype:ashbysmith1996>
 * @author Sundays211
 * @since 24/01/2015
 */
var ItemListener = Java.extend(Java.type('org.virtue.script.listeners.ItemListener'), {
	
	/* The item ids to bind to */
	getItemIDs: function() {
		return [20709];
	},//13634 = Clan vex NPC id

	/* The first option on an object */
	handleInteraction: function(player, item, slot, option) {
		switch (option) {
		case 3://Teleport
		case 7://Interact
		return true;
		case 22://place vex
			placeClanVex(player, item, slot);
		return true;
		default:
			return false;
			//api.sendMessage(player, "Unhandled clan vex option: item="+item.getID()+", option="+option);
		}
	},
	
	/* Returns the examine text for the item, or "null" to use the default */
	getExamine : function (player, item) {
		return null;
	}

});

var NpcListener = Java.extend(Java.type('org.virtue.script.listeners.NpcListener'), {

	/* The npc ids to bind to */
	getIDs: function() {
		return [13634];
	},

	/* The first option on an npc */
	handleInteraction: function(player, npc, option) {
		switch (option) {
			case 1:
				readClanVex(player, npc);
				return true;			
			case 3:
				checkVexOwnership(player, npc);
				return true;
			default:
				api.sendMessage(player, "Unhandled NPC Interaction action: npcId="+npc.getID()+", option="+option);
				break;
		}
		return true;
	},
	
	getInteractRange : function (npc, option) {
		return 1;
	}

});

/* Listen to the item ids specified */
var listen = function(scriptManager) {
	api = scriptManager.getApi();
	var listener = new NpcListener();
	var itemListener = new ItemListener();
	scriptManager.registerItemListener(itemListener, itemListener.getItemIDs());
	scriptManager.registerNpcListener(listener, listener.getIDs());
}

function checkVexOwnership(player, npc) {
	if(!npc.isOwner(player)) {
		api.sendMessage(player, "You are not the owner of this Clan Vex.");
		return true;
	}
	npc.destroy();
	player.setPet(null);
	
}

function readClanVex(player, npc) {
	api.sendMessage(player, "There's no information about this clan.");
}

function placeClanVex (player, item, slot) {
	var npc = NPC.create(13634, new Tile(player.getCurrentTile()));
	if(npc.getOwner() != null) {
		api.sendMessage(player, "You already have a clan vex out.");
	} else {
	   npc.setOwner(player);
	   npc.setCanRespawn(false);
	   Java.type('org.virtue.model.World').getInstance().addNPC(npc);
	   api.runAnimation(player, 827);
	   player.getMovement().moveAdjacent();
	}
}
