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
 * @author Kayla
 * @since 5/8/2015
 */

var api;

var DuelController = Java.type('org.virtue.model.content.minigame.impl.DuelController');

var WidgetListener = Java.extend(Java.type('org.virtue.script.listeners.WidgetListener'), {

	/* The interfaces to bind to */
	getIDs: function() {
		return [ 1366, 1367 , 1446, 1365, 1369, 1446];
	},
	
	open : function (player, parentID, parentComponent, interfaceID) {
		if (interfaceID == 1367) {
			var targetPlayer = api.getInteractionTarget(player);
			if (targetPlayer == null) {
				return;
			}
			api.setWidgetText(player, 1367, 67, api.getName(targetPlayer) + " - " + targetPlayer.getSkills().getCombatLevel());	//Username / level
			api.setWidgetText(targetPlayer, 1367, 67, api.getName(player) + " - " + player.getSkills().getCombatLevel());	//Username / level
		} else if (interfaceID == 1366) {
			var targetPlayer = api.getInteractionTarget(player);
			if (targetPlayer == null) {
				return;
			}
			api.setWidgetText(player, 1366, 33, api.getName(targetPlayer));//Name
			api.setWidgetText(targetPlayer, 1366, 33, api.getName(player));//Name
		} 
		else if (interfaceID = 1365) {
			var targetPlayer = api.getInteractionTarget(player);
			if (targetPlayer == null) {
				return;
			}
			api.setWidgetText(player, 1365, 8, " " + api.getName(player));//Name
			//api.setWidgetText(targetPlayer, 1365, 8, " " + ntapi.getName(player));//Name
		}
	},

	/* Pressed a button on the interface */
	handleInteraction: function(player, interfaceID, component, slot, itemID, option) {
		if (interfaceID == 1367) {
			switch (component) {
			case 57:
				Challenge.acceptDuel(player);
				return true;
			case 68:
				api.closeCentralWidgets(player);
				return true;
			}
		} else if (interfaceID == 1366) {
				switch (component) {
				case 24:
					//api.setWidgetText(player, 1366, 18, "jajaja");
					var targetPlayer = api.getInteractionTarget(player);
					Challenge.processDuel(player, targetPlayer);
					return true;
				case 34:
					api.closeCentralWidgets(player);
					return true;
				
				}
		}

	},
	
	close : function (player, parentID, parentComponent, interfaceID) {
		if (interfaceID == 1367 || interfaceID == 1366) {
			Challenge.cancelDuel(player);
			api.clearInteractionTarget(player);
		}
	},
	
	drag : function (player, interface1, component1, slot1, item1, interface2, component2, slot2, item2) {
		return false;
	}
});

/* Listen to the interface ids specified */
var listen = function(scriptManager) {
	api = scriptManager.getApi();
	var widgetListener = new WidgetListener();
	//var varListener = new VarListener();
	scriptManager.registerWidgetListener(widgetListener, widgetListener.getIDs());
};

var Challenge = {

		cancelDuel : function (player) {
			var targetPlayer = api.getInteractionTarget(player);
			if (targetPlayer != null) {
				api.closeOverlaySub(targetPlayer, 1007, false);
				api.closeOverlaySub(targetPlayer, 1008, false);
				api.clearInteractionTarget(targetPlayer);
				api.sendMessage(targetPlayer, "Other player declined the challenge.");
			}
		},
		
		acceptDuel : function (player, isConfirm) {
			var targetPlayer = api.getInteractionTarget(player);
			if (targetPlayer == null) {
				return;
			}
			api.setWidgetText(targetPlayer, 1367, 56, "Other player has accepted the challenge.");
			api.setWidgetText(targetPlayer, 1366, 23, "Other player has accepted the challenge.");
			if (!api.tradeAccepted(targetPlayer)) {
				api.setTradeAccepted(player, true);
				api.setWidgetText(player, 1367, 56, "Waiting for opponent...");
				api.setWidgetText(player, 1366, 23, "Waiting for opponent...");
			} else if (isConfirm) {
				Challenge.processDuel(player, targetPlayer);	
			} else {
				api.closeOverlaySub(targetPlayer, 1007, false);
				api.openCentralWidget(targetPlayer, 1366, false);
				api.closeOverlaySub(player, 1007, false);
				api.openCentralWidget(player, 1366, false);
			}
		},
		
		processDuel : function (player1, player2) {
			//player1.test2(player1, player2);
			minigame = Java.type('org.virtue.Virtue').getInstance().getController().createDuel(2, 2);
			minigame.getPlayers().add(player1);
			minigame.getPlayers().add(player2);
			
			api.closeOverlaySub(player1, 1007, false);
			api.closeOverlaySub(player2, 1007, false);
			api.closeCentralWidgets(player1);
			api.closeCentralWidgets(player2);
		},
}
