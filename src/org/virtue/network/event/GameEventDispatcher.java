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
package org.virtue.network.event;

import java.util.Timer;
import java.util.TimerTask;

import org.virtue.Messages;
import org.virtue.Virtue;
import org.virtue.model.content.social.OnlineStatus;
import org.virtue.model.content.social.ignore.Ignore;
import org.virtue.model.entity.Entity;
import org.virtue.model.entity.player.GameState;
import org.virtue.model.entity.player.Player;
import org.virtue.model.entity.player.inv.Item;
import org.virtue.model.entity.player.skill.SkillData;
import org.virtue.model.entity.player.skill.SkillType;
import org.virtue.model.entity.player.var.LoginDispatcher;
import org.virtue.model.entity.player.var.VarKey;
import org.virtue.model.entity.region.MapSize;
import org.virtue.model.entity.region.Tile;
import org.virtue.network.event.buffer.OutboundBuffer;
import org.virtue.network.event.context.GameEventContext;
import org.virtue.network.event.context.impl.EmptyEventContext;
import org.virtue.network.event.context.impl.in.OptionButton;
import org.virtue.network.event.context.impl.out.ClientScriptEventContext;
import org.virtue.network.event.context.impl.out.CutsceneEventContext;
import org.virtue.network.event.context.impl.out.EnumEventContext;
import org.virtue.network.event.context.impl.out.FriendListEventContext;
import org.virtue.network.event.context.impl.out.HideWidgetEventContext;
import org.virtue.network.event.context.impl.out.IgnoreListEventContext;
import org.virtue.network.event.context.impl.out.InvEventContext;
import org.virtue.network.event.context.impl.out.LogoutEventContext;
import org.virtue.network.event.context.impl.out.MessageEventContext;
import org.virtue.network.event.context.impl.out.MessageEventContext.ChannelType;
import org.virtue.network.event.context.impl.out.MusicEventContext;
import org.virtue.network.event.context.impl.out.PlayerOptionEventContext;
import org.virtue.network.event.context.impl.out.RootWidgetEventContext;
import org.virtue.network.event.context.impl.out.RunEnergyEventContext;
import org.virtue.network.event.context.impl.out.RunWeightEventContext;
import org.virtue.network.event.context.impl.out.SceneGraphEventContext;
import org.virtue.network.event.context.impl.out.SystemUpdateEventContext;
import org.virtue.network.event.context.impl.out.TargetEventContext;
import org.virtue.network.event.context.impl.out.VarcEventContext;
import org.virtue.network.event.context.impl.out.VarcStringEventContext;
import org.virtue.network.event.context.impl.out.WidgetExternalSpriteEventContext;
import org.virtue.network.event.context.impl.out.WidgetModelEventContext;
import org.virtue.network.event.context.impl.out.WidgetModelEventContext.ModelType;
import org.virtue.network.event.context.impl.out.WidgetSettingsEventContext;
import org.virtue.network.event.context.impl.out.WidgetSubEventContext;
import org.virtue.network.event.context.impl.out.WidgetTextEventContext;
import org.virtue.network.event.context.impl.out.WorldListEventContext;
import org.virtue.network.event.encoder.GameEventEncoder;
import org.virtue.network.event.encoder.impl.ClientScriptEventEncoder;
import org.virtue.network.event.encoder.impl.CutsceneEventEncoder;
import org.virtue.network.event.encoder.impl.EnumEventEncoder;
import org.virtue.network.event.encoder.impl.FriendListEventEncoder;
import org.virtue.network.event.encoder.impl.HideWidgetEventEncoder;
import org.virtue.network.event.encoder.impl.IgnoreListEventEncoder;
import org.virtue.network.event.encoder.impl.InvEventEncoder;
import org.virtue.network.event.encoder.impl.KeepAliveEventEncoder;
import org.virtue.network.event.encoder.impl.LogoutEventEncoder;
import org.virtue.network.event.encoder.impl.MessageEventEncoder;
import org.virtue.network.event.encoder.impl.MusicEventEncoder;
import org.virtue.network.event.encoder.impl.NpcUpdateEventEncoder;
import org.virtue.network.event.encoder.impl.PlayerOptionEventEncoder;
import org.virtue.network.event.encoder.impl.PlayerUpdateEventEncoder;
import org.virtue.network.event.encoder.impl.ResetVarEventEncoder;
import org.virtue.network.event.encoder.impl.RootWidgetEventEncoder;
import org.virtue.network.event.encoder.impl.RunEnergyEventEncoder;
import org.virtue.network.event.encoder.impl.RunWeightEventEncoder;
import org.virtue.network.event.encoder.impl.SceneGraphEventEncoder;
import org.virtue.network.event.encoder.impl.SkillEventEncoder;
import org.virtue.network.event.encoder.impl.SystemUpdateEventEncoder;
import org.virtue.network.event.encoder.impl.TargetEventEncoder;
import org.virtue.network.event.encoder.impl.UnlockFriendsEventEncoder;
import org.virtue.network.event.encoder.impl.VarcEventEncoder;
import org.virtue.network.event.encoder.impl.VarcStringEventEncoder;
import org.virtue.network.event.encoder.impl.WidgetExternalSpriteEventEncoder;
import org.virtue.network.event.encoder.impl.WidgetModelEventEncoder;
import org.virtue.network.event.encoder.impl.WidgetSettingsEventEncoder;
import org.virtue.network.event.encoder.impl.WidgetSubEventEncoder;
import org.virtue.network.event.encoder.impl.WidgetTextEventEncoder;
import org.virtue.network.event.encoder.impl.WorldListEventEncoder;
import org.virtue.network.protocol.message.login.LoginTypeMessage;
import org.virtue.openrs.utility.crypto.BKDR;
import org.virtue.utility.SerialisableEnum;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;

/**
 * @author Im Frizzy <skype:kfriz1998>
 * @since Oct 9, 2014
 */
public class GameEventDispatcher {

	/**
	 * The player to dispatch game events
	 */
	private Player player;

	/**
	 * The {@link GameEventDispatcher} constructor
	 */
	public GameEventDispatcher(Player player) {
		this.player = player;
	}

	/**
	 * Dispatches the login type
	 * 
	 * @param type
	 *            - type of login
	 */
	public void dispatchLogin(LoginTypeMessage type) {
		switch (type) {
		case LOGIN_LOBBY:
			player.setGameState(GameState.LOBBY);
			if (Virtue.getInstance().hasUpdate()) {
				sendSystemUpdate(Virtue.getInstance().getUpdateTime() * 12);
			}
			sendGameMessage("Welcome to " + Messages.ServerName + ".");
			LoginDispatcher.onLobbyLogin(player);
			break;
		case LOGIN_WORLD:
			player.setGameState(GameState.WORLD);
			sendSceneGraph(player.getCurrentTile(), MapSize.DEFAULT, true,
					false);
			if (Virtue.getInstance().hasUpdate()) {
				sendSystemUpdate(Virtue.getInstance().getUpdateTime() - 2);
				// Subtract a couple of ticks to account for delay
			}
			player.setGameState(GameState.WORLD_READY);
			player.getViewport().onMapLoaded();
			sendGameMessage("Welcome to " + Messages.ServerName + ".");
			sendGameMessage(
					"<col=#333333>Commands: ::item, ::godwars, ::vorago, ::edge, ::tele, ::home, ::yell ::players");
			sendGameMessage("<col=#3399FF>Visit our site at www.zrs3.com ");
			player.getEquipment().refresh(true);
			player.getAppearance().refresh();
			player.updateWeight();
			player.getSkills().sendAllSkills();
			player.updateWeight();
			sendRunEnergy(player.getRunEnergy());
			if (player.getClanHash() != 0L) {
				Virtue.getInstance().getClans().getSettings()
						.registerPlayer(player.getChat(), false);
			}
			switch (player.getMode()) {
			case EOC:
			case REVOLUTION:
				LoginDispatcher.onEoCLogin(player);
				player.getCombatSchedule().getActionBar().refresh();
				break;
			case LEGACY:
				LoginDispatcher.onLegacyLogin(player);
				break;
			}
			player.getInteractions().initialise();
			player.getExchangeOffers().init();
			player.getCombat().setAdrenaline(0);
			player.getCombatSchedule().setRetaliating(
					player.getVars().getVarValueInt(
							VarKey.Player.AUTO_RETALIATE_DISABLED) != 1);
			player.getImpactHandler().setMaximumLifepoints(player.getSkills().getBaseLevel(SkillType.CONSTITUTION) * 100);
			player.getImpactHandler().restoreLifepoints();
			player.getCombat()
					.getPrayer()
					.setPrayerPoints(
							player.getSkills().getBaseLevel(SkillType.PRAYER) * 10);
			player.getMoneyPouch().refresh(false);
			player.getVars().processLogin(player.getLastLogin());
			sendMusic(36067, 100);
			player.wildernessCheck();
			break;
		}
		sendOnlineStatus(player.getChat().getFriendsList().getOnlineStatus());
		sendUnlockFriends();
		player.getChat().getFriendsList().sendFriendsMyStatus(false);
		player.getChat().getIgnoreList().sendIgnores();
		player.getChat().getFriendsList().sendFriendsList();
		if (player.getClanHash() != 0L) {
			Virtue.getInstance().getClans().getChannels()
					.joinMyChannel(player.getChat());
		}
	}

	/**
	 * Sends the cutscene.
	 */
	public void sendCutscene() {
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				player.getWidgets().openWidget(1477, 19, 548, true);// 834
																	// parent
																	// slot,
																	// would
																	// need to
																	// be found
																	// for 823
			}
		}, 2000);
	}

	public void sendVarReset() {
		sendEvent(ResetVarEventEncoder.class, new EmptyEventContext());
	}

	public void sendVarc(int key, int value) {
		sendEvent(VarcEventEncoder.class, new VarcEventContext(key, value));
	}

	public void sendVarcBit(int key, int value) {
		sendEvent(VarcEventEncoder.class,
				new VarcEventContext(key, value, true));
	}

	public void sendVarcString(int key, String value) {
		sendEvent(VarcStringEventEncoder.class, new VarcStringEventContext(key,
				value));
	}

	public void sendRootWidget(int widget) {
		sendEvent(RootWidgetEventEncoder.class, new RootWidgetEventContext(
				widget));
	}

	public void sendWidget(int window, int component, int widgetId,
			boolean alwaysOpen) {
		sendEvent(WidgetSubEventEncoder.class, new WidgetSubEventContext(
				window, component, widgetId, alwaysOpen));
	}

	public void openWidget(int window, int component, int widgetId,
			boolean alwaysOpen, Entity parent) {
		sendEvent(WidgetSubEventEncoder.class, new WidgetSubEventContext(
				window, component, widgetId, alwaysOpen, parent));
	}

	public void sendHideWidget(int widget, int component, boolean hidden) {
		sendEvent(HideWidgetEventEncoder.class, new HideWidgetEventContext(
				widget, component, hidden));
	}

	public void sendWidgetText(int widget, int component, String text) {
		sendEvent(WidgetTextEventEncoder.class, new WidgetTextEventContext(
				widget, component, text));
	}

	public void sendWidgetModel(ModelType type, int widget, int component) {
		sendEvent(WidgetModelEventEncoder.class, new WidgetModelEventContext(
				type, widget, component));
	}

	public void sendOtherPlayerWidgetModel(int widget, int component,
			Player otherPlayer) {
		int namehash = BKDR.hash(otherPlayer.getName());
		sendEvent(WidgetModelEventEncoder.class, new WidgetModelEventContext(
				ModelType.PLAYER_MODEL_OTHER, widget, component, otherPlayer.getIndex(), namehash));
	}

	public void sendWidgetModel(ModelType type, int widget, int component,
			int modelID) {
		sendEvent(WidgetModelEventEncoder.class, new WidgetModelEventContext(
				type, widget, component, modelID));
	}

	public void sendWidgetSettings(int root, int component, int from, int to,
			int settings) {
		sendEvent(WidgetSettingsEventEncoder.class,
				new WidgetSettingsEventContext(root, component, from, to,
						settings));
	}

	public void sendWidgetExtarnalSprite(int widget, int component, int i) {
		sendEvent(WidgetExternalSpriteEventEncoder.class,
				new WidgetExternalSpriteEventContext(widget, component, i));
	}

	public void sendKeepAlive(EmptyEventContext context) {
		sendEvent(KeepAliveEventEncoder.class, context);
	}

	public void sendWorldList(WorldListEventContext context) {
		sendEvent(WorldListEventEncoder.class, context);
	}

	public void sendSceneGraph(Tile tile, MapSize mapSize, boolean init,
			boolean isStatic) {
		sendEvent(SceneGraphEventEncoder.class, new SceneGraphEventContext(
				tile, mapSize, init, isStatic));
	}

	public void sendLogout(boolean toLobby) {
		sendEvent(LogoutEventEncoder.class, new LogoutEventContext(toLobby));
	}

	public void sendMessage(String message, ChannelType type) {
		sendEvent(MessageEventEncoder.class, new MessageEventContext(type,
				message));
	}

	public void sendMessage(MessageEventContext message) {
		sendEvent(MessageEventEncoder.class, message);
	}

	public void sendGameMessage(String message) {
		sendEvent(MessageEventEncoder.class, new MessageEventContext(
				ChannelType.GAME, message));
	}

	public void sendConsoleMessage(String message) {
		sendEvent(MessageEventEncoder.class, new MessageEventContext(
				ChannelType.CONSOLE, message));
	}

	public void sendTrayMessage(String message) {
		// sendEvent(TrayMessageEventEncoder.class, new
		// TrayMessageEventContext(message));
	}

	/**
	 * Sends the online status of the player
	 */
	public void sendOnlineStatus(OnlineStatus status) {
		sendEnum(OutgoingEventType.ONLINE_STATUS, status);
	}

	/**
	 * Unlocks the friends/ignores list
	 */
	public void sendUnlockFriends() {
		sendEvent(UnlockFriendsEventEncoder.class, new EmptyEventContext());
	}

	/**
	 * Generates the friends list if the play has no friends
	 */
	public void generateFriendsBlock(boolean empty) {
		sendEvent(FriendListEventEncoder.class, new FriendListEventContext(
				empty));
	}

	/**
	 * Sends the ignores list
	 */
	public void sendIgnoresList(Ignore ignore, boolean warned) {
		sendEvent(IgnoreListEventEncoder.class, new IgnoreListEventContext(
				ignore, warned));
	}

	/**
	 * Sends a system update timer
	 * 
	 * @param delay
	 *            - the delay in ticks
	 */
	public void sendSystemUpdate(int delay) {
		sendEvent(SystemUpdateEventEncoder.class, new SystemUpdateEventContext(
				delay));
	}

	/**
	 * Sends the player's current run energy level
	 * 
	 * @param energy
	 *            The energy level
	 */
	public void sendRunEnergy(int energy) {
		sendEvent(RunEnergyEventEncoder.class,
				new RunEnergyEventContext(energy));
	}

	/**
	 * Sends the player's current weight
	 * 
	 * @param weight
	 *            The weight of the player
	 */
	public void sendRunWeight(int weight) {
		sendEvent(RunWeightEventEncoder.class,
				new RunWeightEventContext(weight));
	}

	/**
	 * Sends an update for the specified skill
	 * 
	 * @param skill
	 *            The skill to update
	 */
	public void sendSkill(SkillData skill) {
		sendEvent(SkillEventEncoder.class, skill);
	}

	/**
	 * Sends the prayer points varp
	 * 
	 * @param pray
	 */
	public void sendPrayer(int pray) {
		player.getVars().setVarValueInt(3274, pray);
	}

	/**
	 * Sends the player update event
	 */
	public void sendPlayerUpdate() {
		sendEvent(PlayerUpdateEventEncoder.class, player.getViewport());
	}

	/**
	 * Sends the npc update event
	 */
	public void sendNPCUpdate() {
		sendEvent(NpcUpdateEventEncoder.class, player.getViewport());
	}

	/**
	 * Resets the position of the minimap flag (to the player's current
	 * position)
	 */
	public void sendResetMapFlag() {
		sendMapFlag(-1, -1);
	}

	/**
	 * Sends the location of the client minimap flag.
	 * 
	 * @param posX
	 *            The local x-coordinate of the map flag
	 * @param posY
	 *            The local y-coordinate of the map flag
	 */
	public void sendMapFlag(int posX, int posY) {
		sendEvent(TargetEventEncoder.class, new TargetEventContext(posX, posY));
	}

	/**
	 * Sends an inventory items
	 * 
	 * @param id
	 * @param items
	 */
	public void sendItems(int id, Item[] items) {
		sendEvent(InvEventEncoder.class, new InvEventContext(id, items));
	}

	/**
	 * Sends iventory items
	 * 
	 * @param id
	 * @param items
	 * @param slots
	 */
	public void sendItems(int id, Item[] items, int... slots) {
		sendEvent(InvEventEncoder.class, new InvEventContext(id, items, slots));
	}

	/**
	 * Sends a cs2 scripts (client script)
	 * 
	 * @param id
	 * @param params
	 */
	public void sendCS2Script(int id, Object... params) {
		sendEvent(ClientScriptEventEncoder.class, new ClientScriptEventContext(
				id, params));
	}

	/**
	 * Sends a serialisable enumeration value
	 */
	public void sendEnum(OutgoingEventType type, SerialisableEnum value) {
		sendEvent(EnumEventEncoder.class, new EnumEventContext(type, value));
	}

	/**
	 * Sets a client-side right-click option for all other players
	 * 
	 * @param option
	 *            The option to set
	 * @param text
	 *            The text for the option
	 * @param cursor
	 *            The cursor ID
	 * @param isTop
	 *            Whether the option sits at the top of the menu or not
	 */
	public void sendPlayerOption(OptionButton option, String text, int cursor,
			boolean isTop) {
		sendEvent(PlayerOptionEventEncoder.class, new PlayerOptionEventContext(
				option, text, cursor, isTop));
	}

	/**
	 * Plays the cutscene in the player's client
	 * 
	 * @param id
	 *            The id of the cutscene to play
	 */
	public void sendCutscene(int id) {
		sendEvent(CutsceneEventEncoder.class, new CutsceneEventContext(id,
				player.getAppearance().getData()));
	}

	public void sendMusic(int id, int vol) {
		sendEvent(MusicEventEncoder.class, new MusicEventContext(id, vol));
	}

	/**
	 * Sends a GameEventEncoder over the network
	 * 
	 * @param clazz
	 * @param context
	 */
	public <T extends GameEventEncoder<?>> ChannelFuture sendEvent(
			Class<T> clazz, GameEventContext context) {
		if (player.getChannel().isActive()) {
			OutboundBuffer packet = Virtue.getInstance().getEventRepository()
					.encode(player, clazz, context);
			ByteBuf buffer = Unpooled.copiedBuffer(packet.buffer(), 0,
					packet.offset());
			synchronized (player.getChannel()) {
				return player.getChannel().writeAndFlush(buffer);
			}
		}
		return null;
	}

	/**
	 * Sens a OutBuffer over the network
	 * 
	 * @param buffer
	 */
	public ChannelFuture sendBuffer(OutboundBuffer packet) {
		if (player.getChannel().isActive()) {
			ByteBuf buffer = Unpooled.copiedBuffer(packet.buffer(), 0,
					packet.offset());
			synchronized (player.getChannel()) {
				return player.getChannel().writeAndFlush(buffer);
			}
		}
		return null;
	}

	/**
	 * Send a ByteBuf over the network
	 * 
	 * @param buffer
	 */
	public ChannelFuture sendByteBuf(ByteBuf buffer) {
		if (player.getChannel().isActive()) {
			synchronized (player.getChannel()) {
				return player.getChannel().writeAndFlush(
						Unpooled.copiedBuffer(buffer));
			}
		}
		return null;
	}

	public void sendMusic(int song) {
		sendEvent(MusicEventEncoder.class, new MusicEventContext(song, 100));
		
	}

}
