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
 * @since 9/02/2015
 */
var api;

var LocationListener = Java.extend(Java.type('org.virtue.script.listeners.LocationListener'), {

	/* The location ids to bind to */
	getIDs: function() {
		return [ 36768, 36769, 36770, 36984, 36985 ];
	},

	/* The first option on an object */
	handleInteraction: function(player, loc, option) {
		switch (loc.getID()) {
		case 36768://General ladder bottom
			if (option == 1) {
				api.runAnimation(player, 828);
				api.teleportEntityBy(player, 0, 0, 1);
				return true;
			} else {
				return false;
			}
		case 36769://General ladder middle
			if (option == 2) {
				api.runAnimation(player, 828);
				api.teleportEntityBy(player, 0, 0, 1);
				return true;
			} else if (option == 3) {
				api.runAnimation(player, 828);
				api.teleportEntityBy(player, 0, 0, -1);
				return true;
			} else {
				return false;
			}
		case 36770://General ladder top
			if (option == 1) {
				api.runAnimation(player, 828);
				api.teleportEntityBy(player, 0, 0, -1);
				return true;
			} else {
				return false;
			}
		case 36984://Church west ladder bottom
			if (option == 1) {
				var tile = api.getTile(loc);
				api.runAnimation(player, 828);
				api.teleportEntity(player, tile.getX()-1, tile.getY(), tile.getPlane()+1);
				api.faceDirection(player, tile);
				return true;
			} else {
				return false;
			}
		case 36985://Church west ladder top
			if (option == 1) {
				var tile = api.getTile(loc);
				api.runAnimation(player, 828);
				api.teleportEntity(player, tile.getX(), tile.getY(), tile.getPlane()-1);
				api.faceDirection(player, tile);
				return true;
			} else {
				return false;
			}
		default:
			return false;		
		}
	},
	
	/* The range that a player must be within to interact */
	getInteractRange : function (object, option) {
		return 1;
	},
	
	/* A backpack item used on the location */
	handleItemOnLoc : function (player, location, item, invSlot) {
		return false;
	}

});

/* Listen to the interface ids specified */
var listen = function(scriptManager) {
	api = scriptManager.getApi();
	var locationListener = new LocationListener();
	scriptManager.registerLocationListener(locationListener, locationListener.getIDs());
};