var GraphicsBlock = Java.type('org.virtue.model.entity.update.block.GraphicsBlock');
var AnimationBlock = Java.type('org.virtue.model.entity.update.block.AnimationBlock');
/**
 * @Author Kayla
 * @Date 11/14/2015
 */

var CommandListener = Java.extend(Java.type('org.virtue.script.listeners.CommandListener'), {

	/* The object ids to bind to */
	getPossibleSyntaxes: function() {
		return [ "freeze", "unfreeze"];
	},

	/* The first option on an object */
	handle: function(player, syntax, args, clientCommand) {
		
		var Handler = Java.extend(Java.type('org.virtue.model.entity.player.dialog.InputEnteredHandler'), {
			handle : function (value) {
				if (syntax.toLowerCase() == "freeze") {
				if (value.length > 0) {
					var hash = api.getUserHash(value);
					if (hash != null) {
						var targetPlayer = api.getWorldPlayerByHash(hash);
						if (targetPlayer != null) {
							api.sendMessage(player, "You have frozen the player named: " + targetPlayer)
							player.queueUpdateBlock(new AnimationBlock(1979));
							player.queueUpdateBlock(new GraphicsBlock(1, 366));
							targetPlayer.queueUpdateBlock(new GraphicsBlock(1, 369));
							targetPlayer.lock();
							api.sendMessage(targetPlayer, "You have been frozen.")
						} else {
							api.sendMessage(player, value+" is not currently in the game world.")
						}
					} else {
						api.sendMessage(player, value+" is not registered on this server.")
					}
				}
			}  else if (syntax.toLowerCase() == "unfreeze") {
				if (value.length > 0) {
					var hash = api.getUserHash(value);
					if (hash != null) {
						var targetPlayer = api.getWorldPlayerByHash(hash);
						if (targetPlayer != null) {
							api.sendMessage(player, "You have unfrozen the player. ")
							targetPlayer.unlock();
							api.sendMessage(targetPlayer, "You can now move again!")
						} else {
							api.sendMessage(player, value+" is not currently in the game world.")
						}
					} else {
						api.sendMessage(player, value+" is not registered on this server.")
					}
				}
			}
			}
		});
		
		player.getDialogs().requestString("Please enter the display name of the player you wish to unfreeze:", new Handler());
		return true;
	},
	
		
	adminCommand : function () {
		return true;
	}

});

/* Listen to the object ids specified */
var listen = function(scriptManager) {
	var listener = new CommandListener();
	scriptManager.registerCommandListener(listener, listener.getPossibleSyntaxes());
};

