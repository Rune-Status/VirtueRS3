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
 * @author Sundays211
 * @since 29/12/2014
 */
var api;

var WidgetListener = Java.extend(Java.type('org.virtue.engine.script.listeners.WidgetListener'), {

	/* The object ids to bind to */
	getIDs: function() {
		return [573];
	},
	
	open : function (player, parentID, parentComponent, interfaceID) {
		api.setWidgetEvents(player, 573, 59, 0, 126, 2);//Rank select dropdown option
		api.setWidgetEvents(player, 573, 5, 0, 30, 2);//Selected rank dropdown
		api.setWidgetEvents(player, 573, 20, 0, 30, 2);//Enable specific
		api.setWidgetEvents(player, 573, 19, 0, 6, 2);//Enable category
		load(player);
	},

	/* A button pressed on the interface */
	handleInteraction: function(player, interfaceID, component, slot, itemID, option) {
		switch (component) {//Slot=308
		case 71://Enable all
			return setAll(player, 1);
		case 65://Disable all
			return setAll(player, 0);
		case 19://Enable category
			slot = api.getEnumType(8661).getValueInt(slot);
			return enableCategory(player, slot);
		case 20://Enable specific
			slot = api.getEnumType(8659).getValueInt(slot);
			if (slot != -1) {
				var enabled = isEnabled(player, slot);
				return setEnabled(player, slot, enabled ? 0 : 1);
			} else {
				return false;
			}
		case 5://Select rank dropdown
			slot = api.getEnumType(8659).getValueInt(slot);
			api.setVarp(player, 4285, slot);
			return true;
		case 59://Select rank
			return setRank(player, api.getVarp(player, 4285), slot);
		case 47://Confirm save
			save(player);
			api.closeWidget(player, 1477, 326);
			api.openWidget(player, 1477, 326, 573, false);
			return true;
		default://See script 3736
			print("Clicked interface 573, comp "+component+", slot "+slot+"\n");
			return false;
		}		
	},
	
	close : function (player, parentID, parentComponent, interfaceID) {
		
	},
	
	drag : function (player, interface1, component1, slot1, item1, interface2, component2, slot2, item2) {
		return false;
	}

});

/* Listen to the interface ids specified */
var listen = function(scriptManager) {
	api = scriptManager.getApi();
	var widgetListener = new WidgetListener();
	scriptManager.registerWidgetListener(widgetListener, widgetListener.getIDs());
};

function load (player) {
	api.setVarp(player, 4276, getValue(api.getVarClanSetting(player, 5), 0));
	api.setVarp(player, 4277, getValue(api.getVarClanSetting(player, 6), 0));
	api.setVarp(player, 4278, getValue(api.getVarClanSetting(player, 7), 0));
	api.setVarp(player, 4279, getValue(api.getVarClanSetting(player, 8), 0));
	api.setVarp(player, 4280, getValue(api.getVarClanSetting(player, 9), 0));
	api.setVarp(player, 4281, getValue(api.getVarClanSetting(player, 10), 0));
	api.setVarp(player, 4282, getValue(api.getVarClanSetting(player, 11), 0));
	api.setVarp(player, 4283, getValue(api.getVarClanSetting(player, 12), 0));
	api.setVarp(player, 4284, getValue(api.getVarClanSetting(player, 14), 0));
}

function getValue (value, def) {
	return value == null ? def : value;
} 

function save (player) {
	if (canChangeBroadcasts(player, clanApi.getRank(api.getClanHash(player), player.getUserHash()))) {
		api.setVarClanSetting(player, 5, api.getVarp(player, 4276));
		api.setVarClanSetting(player, 6, api.getVarp(player, 4277));
		api.setVarClanSetting(player, 7, api.getVarp(player, 4278));
		api.setVarClanSetting(player, 8, api.getVarp(player, 4279));
		api.setVarClanSetting(player, 9, api.getVarp(player, 4280));
		api.setVarClanSetting(player, 10, api.getVarp(player, 4281));
		api.setVarClanSetting(player, 11, api.getVarp(player, 4282));
		api.setVarClanSetting(player, 12, api.getVarp(player, 4283));
		api.setVarClanSetting(player, 14, api.getVarp(player, 4284));
		api.setVarp(player, 4286, -1);
		clanApi.sendBroadcast(api.getClanHash(player), 28, ["[Player A]"], [api.getName(player)]);
	}
}

function canChangeBroadcasts (player, rank) {
	switch (rank) {
	case 100:
		return api.getVarBit(player, 21735);
	case 101:
		return api.getVarBit(player, 21736);
	case 102:
		return api.getVarBit(player, 21737);
	case 103:
		return api.getVarBit(player, 21738);
	case 125:
		return api.getVarBit(player, 21739);
	case 126:
	case 127:
		return 1;
	default:
		return 0;
	}	
}

function isEnabled (player, slot) {
	switch (slot) {
	case 0:
		return api.getVarBit(player, 21957);
	case 1:
		return api.getVarBit(player, 21958);
	case 2:
		return api.getVarBit(player, 21959);
	case 3:
		return api.getVarBit(player, 21960);
	case 4:
		return api.getVarBit(player, 21961);
	case 5:
		return api.getVarBit(player, 21962);
	case 6:
		return api.getVarBit(player, 21963);
	case 7:
		return api.getVarBit(player, 21964);
	case 8:
		return api.getVarBit(player, 21965);
	case 9:
		return api.getVarBit(player, 21966);
	case 10:
		return api.getVarBit(player, 21967);
	case 11:
		return api.getVarBit(player, 21968);
	case 12:
		return api.getVarBit(player, 21969);
	case 13:
		return api.getVarBit(player, 21970);
	case 14:
		return api.getVarBit(player, 21971);
	case 15:
		return api.getVarBit(player, 21972);
	case 16:
		return api.getVarBit(player, 21973);
	case 17:
		return api.getVarBit(player, 21974);
	case 18:
		return api.getVarBit(player, 21975);
	case 19:
		return api.getVarBit(player, 21976);
	case 20:
		return api.getVarBit(player, 21977);
	case 21:
		return api.getVarBit(player, 21978);
	case 22:
		return api.getVarBit(player, 21979);
	case 23:
		return api.getVarBit(player, 21980);
	case 24:
		return api.getVarBit(player, 21981);
	case 25:
		return api.getVarBit(player, 21982);
	case 26:
		return api.getVarBit(player, 21983);
	case 27:
		return api.getVarBit(player, 21984);
	case 28:
		return api.getVarBit(player, 21985);
	case 29:
		return api.getVarBit(player, 21986);
	default:
		return 0;
	}
}

function setAll (player, enabled) {
	var entries = api.getEnumType(8660);
	var entry;
	for (var i=0; i<entries.getSize();i++) {
		entry = api.getStructType(entries.getValueInt(i));
		setEnabled(player, entry.getParam(4186, -1), enabled);
	}
	return true;
}

function enableCategory (player, category) {
	var entries = api.getEnumType(8660);
	var entry;
	for (var i=0; i<entries.getSize();i++) {
		entry = api.getStructType(entries.getValueInt(i));
		if (entry.getParam(4187, -1) == category) {
			setEnabled(player, entry.getParam(4186, -1), 1);
		}
	}
	return true;
}

function setEnabled (player, slot, enabled) {
	switch (slot) {
	case 0:
		return api.setVarBit(player, 21957, enabled);
	case 1:
		return api.setVarBit(player, 21958, enabled);
	case 2:
		return api.setVarBit(player, 21959, enabled);
	case 3:
		return api.setVarBit(player, 21960, enabled);
	case 4:
		return api.setVarBit(player, 21961, enabled);
	case 5:
		return api.setVarBit(player, 21962, enabled);
	case 6:
		return api.setVarBit(player, 21963, enabled);
	case 7:
		return api.setVarBit(player, 21964, enabled);
	case 8:
		return api.setVarBit(player, 21965, enabled);
	case 9:
		return api.setVarBit(player, 21966, enabled);
	case 10:
		return api.setVarBit(player, 21967, enabled);
	case 11:
		return api.setVarBit(player, 21968, enabled);
	case 12:
		return api.setVarBit(player, 21969, enabled);
	case 13:
		return api.setVarBit(player, 21970, enabled);
	case 14:
		return api.setVarBit(player, 21971, enabled);
	case 15:
		return api.setVarBit(player, 21972, enabled);
	case 16:
		return api.setVarBit(player, 21973, enabled);
	case 17:
		return api.setVarBit(player, 21974, enabled);
	case 18:
		return api.setVarBit(player, 21975, enabled);
	case 19:
		return api.setVarBit(player, 21976, enabled);
	case 20:
		return api.setVarBit(player, 21977, enabled);
	case 21:
		return api.setVarBit(player, 21978, enabled);
	case 22:
		return api.setVarBit(player, 21979, enabled);
	case 23:
		return api.setVarBit(player, 21980, enabled);
	case 24:
		return api.setVarBit(player, 21981, enabled);
	case 25:
		return api.setVarBit(player, 21982, enabled);
	case 26:
		return api.setVarBit(player, 21983, enabled);
	case 27:
		return api.setVarBit(player, 21984, enabled);
	case 28:
		return api.setVarBit(player, 21985, enabled);
	case 29:
		return api.setVarBit(player, 21986, enabled);
	default:
		return false;
	}
}

function setRank (player, slot, rank) {
	switch (slot) {
	case 0:
		return api.setVarBit(player, 21993, rank);
	case 1:
		return api.setVarBit(player, 21994, rank);
	case 2:
		return api.setVarBit(player, 21995, rank);
	case 3:
		return api.setVarBit(player, 21996, rank);
	case 4:
		return api.setVarBit(player, 21997, rank);
	case 5:
		return api.setVarBit(player, 21998, rank);
	case 6:
		return api.setVarBit(player, 21999, rank);
	case 7:
		return api.setVarBit(player, 22000, rank);	
	case 8:
		return api.setVarBit(player, 22001, rank);	
	case 9:
		return api.setVarBit(player, 22002, rank);	
	case 10:
		return api.setVarBit(player, 22003, rank);	
	case 11:
		return api.setVarBit(player, 22004, rank);	
	case 12:
		return api.setVarBit(player, 22005, rank);	
	case 13:
		return api.setVarBit(player, 22006, rank);	
	case 14:
		return api.setVarBit(player, 22007, rank);	
	case 15:
		return api.setVarBit(player, 22008, rank);	
	case 16:
		return api.setVarBit(player, 22009, rank);	
	case 17:
		return api.setVarBit(player, 22010, rank);
	case 18:
		return api.setVarBit(player, 22011, rank);
	case 19:
		return api.setVarBit(player, 22012, rank);
	case 20:
		return api.setVarBit(player, 22013, rank);
	case 21:
		return api.setVarBit(player, 22014, rank);
	case 22:
		return api.setVarBit(player, 22015, rank);
	case 23:
		return api.setVarBit(player, 22016, rank);
	case 24:
		return api.setVarBit(player, 22017, rank);
	case 25:
		return api.setVarBit(player, 22018, rank);
	case 26:
		return api.setVarBit(player, 22019, rank);
	case 27:
		return api.setVarBit(player, 22020, rank);
	case 28:
		return api.setVarBit(player, 22021, rank);
	case 29:
		return api.setVarBit(player, 22022, rank);
	default:
		return false;
	}
}