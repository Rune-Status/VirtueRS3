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
 * @since 19/11/2014
 */

var InterfaceSettingsListener = Java.extend(Java.type('org.virtue.engine.script.listeners.EventListener'), {
	invoke : function (event, interfaceID, args) {
		var player = args.player;
		if (event == EventType.IF_OPEN) {
			api.setWidgetEvents(player, 1442, 132, 0, api.getEnumSize(9616), 2);
			api.setWidgetEvents(player, 1442, 133, 0, api.getEnumSize(9616), 2);
			return;
		}
		
		switch (args.component) {
		case 9://Toggle slim headers
			var slim = api.getVarBit(player, 19924) == 1;
			api.setVarBit(player, 19924, slim ? 0 : 1);
			return;
		case 15://Click-through chat box
			var enabled = api.getVarBit(player, 20188) == 1;
			api.setVarBit(player, 20188, enabled ? 0 : 1);
			return;
		case 21://Hide title bars when locked
			var hide = api.getVarBit(player, 19928) == 1;
			api.setVarBit(player, 19928, hide ? 0 : 1);
			return;
		case 27://Lock interfaces
			var locked = api.getVarBit(player, 19925) == 1;
			api.setVarBit(player, 19925, locked ? 0 : 1);
			return;
		case 67://Show target information
			var enabled = api.getVarBit(player, 19927) == 1;
			api.setVarBit(player, 19927, enabled ? 0 : 1);
			return;
		case 84://Task complete pop-ups
			//possibly varc 1429?
			api.sendMessage(player, "Unhandled task complete pop-ups toggle");
			return;
		case 86://Task information windows
			var enabled = api.getVarBit(player, 3568) == 1;
			api.setVarBit(player, 3568, enabled ? 0 : 1);
			return;
		case 91://Xp popups
			var enabled = api.getVarBit(player, 228) == 1;
			api.setVarBit(player, 228, enabled ? 0 : 1);
			return;
		case 97://Make-x progress window
			var enabled = api.getVarBit(player, 3034) == 1;
			api.setVarBit(player, 3034, enabled ? 0 : 1);
			return;
		case 73://Show target reticules
			var enabled = api.getVarBit(player, 19929) == 1;
			api.setVarBit(player, 19929, enabled ? 0 : 1);
			return;
		case 103://Split private chat
			var enabled = api.getVarBit(player, 20187) == 1;
			api.setVarBit(player, 20187, enabled ? 0 : 1);
			return;
		case 109://Player login/out notifications notifications
			var enabled = api.getVarBit(player, 24940) == 1;
			api.setVarBit(player, 24940, enabled ? 0 : 1);
			return;
		case 116://Ability cooldown timer
		case 129://Skill target based xp pop-ups
		case 133://Utc clock settings
		case 139://Reset timers on game clock
		case 150://Open edit mode
		case 163://Favourites clock alarm
		case 168://Timestamps in chatbox
		case 177://Slayer counter
			
		default:
			api.sendMessage(player, "Unhandled Interface Settings button: comp="+args.component+", button="+args.button+", slot="+args.slot);
			return;
		}
	}
});

/* Listen to the interface ids specified */
var listen = function(scriptManager) {
	var listener = new InterfaceSettingsListener();
	scriptManager.registerListener(EventType.IF_OPEN, 1442, listener);
	scriptManager.registerListener(EventType.IF_BUTTON, 1442, listener);
};