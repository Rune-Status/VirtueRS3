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
package org.virtue;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.virtue.engine.GameEngine;
import org.virtue.engine.cycle.ticks.SystemUpdateTick;
import org.virtue.model.Lobby;
import org.virtue.model.World;
import org.virtue.model.content.dialogue.DialogueHandler;
import org.virtue.model.content.exchange.GrandExchange;
import org.virtue.model.content.minigame.MinigameController;
import org.virtue.model.content.social.clan.ClanManager;
import org.virtue.model.content.social.clan.ClanSettings;
import org.virtue.model.entity.combat.impl.SpecialAttackHandler;
import org.virtue.model.entity.combat.impl.ability.ActionBar;
import org.virtue.model.entity.npc.AbstractNPC;
import org.virtue.model.entity.npc.NpcTypeList;
import org.virtue.model.entity.player.Player;
import org.virtue.model.entity.player.inv.InvRepository;
import org.virtue.model.entity.player.inv.ItemTypeList;
import org.virtue.model.entity.player.var.VarBitTypeList;
import org.virtue.model.entity.player.var.VarRepository;
import org.virtue.model.entity.player.widget.WidgetRepository;
import org.virtue.model.entity.region.LocTypeList;
import org.virtue.model.entity.region.RegionManager;
import org.virtue.network.Network;
import org.virtue.network.event.GameEventRepository;
import org.virtue.openrs.Archive;
import org.virtue.openrs.Cache;
import org.virtue.openrs.ChecksumTable;
import org.virtue.openrs.Container;
import org.virtue.openrs.FileStore;
import org.virtue.openrs.ReferenceTable;
import org.virtue.openrs.def.impl.Js5Archive;
import org.virtue.openrs.def.impl.Js5ConfigGroup;
import org.virtue.parser.ParserRepository;
import org.virtue.parser.impl.NewsDataParser;
import org.virtue.parser.impl.NpcDataParser;
import org.virtue.parser.impl.NpcSpawnParser;
import org.virtue.script.JSListeners;
import org.virtue.utility.EnumTypeList;
import org.virtue.utility.QuestTypeList;
import org.virtue.utility.RenderTypeList;
import org.virtue.utility.StructTypeList;
import org.virtue.utility.text.Huffman;
import org.virtue.utility.text.QuickChatTypeList;

import com.google.common.collect.Maps;


/**
 * @author Im Frizzy <skype:kfriz1998>
 * @since Aug 8, 2014
 */
public class Virtue {//

	/**
	 * The {@link Logger} Instance
	 */
	private static Logger logger = LoggerFactory.getLogger(Virtue.class);

	/**
	 * The {@link Virtue} Instance
	 */
	private static Virtue instance;
	
	/**
	 * The {@link GameEngine} Instance
	 */
	private GameEngine engine;
	
	/**
	 * The {@link ExecutorService} instance for the game engine
	 */
	private ExecutorService engineService;
	
	/**
	 * The {@link Cache} Instance
	 */
	private Cache cache;
	
	/**
	 * The {@link Container} Instance
	 */
	private Container container;
	
	/**
	 * The {@link ByteBuffer} Instance containing the {@link ChecksumTable}
	 */
	private ByteBuffer checksum;
	
	/**
	 * The {@link Network} Instance
	 */
	private Network network;
	
	/**
	 * The {@link AccountIndex} Instance
	 */
	private AccountIndex accountIndex;
	
	/**
	 * The {@link PacketRepository} Instance
	 */
	private GameEventRepository event;
	
	/**
	 * The {@link ParserRepository} instance
	 */
	private ParserRepository parser;
	
	/**
	 * The {@link WidgetRepository} instance
	 */
	private WidgetRepository widget;
	
	/**
	 * The {@link JavaScriptManager} instance
	 */
	private JSListeners scripts;
	
	/**
	 * The {@link ClanManager} instance
	 */
	private ClanManager clans;
	
	/**
	 * The {@link GrandExchange} instance
	 */
	private GrandExchange exchange;
	
	/**
	 * The {@link MinigameController} instance
	 */
	private MinigameController controller;
	
	/**
	 * The current number of days since SERVER_DAY_0
	 */
	private long serverDay0;
	
	private int updateTimer = -1;
	
	private boolean systemUpdate = false;
	
	private boolean live = true;
	
	/**
	 * Main entry point of Virtue
	 * @param args - command line arguments
	 */
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		instance = getInstance();
		if (args.length >= 1) {
			instance.live = Boolean.parseBoolean(args[0]);
		}
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		cal.set(Constants.SERVER_YEAR_0, Constants.SERVER_MONTH_0, Constants.SERVER_DAY_0);
		instance.serverDay0 = cal.getTimeInMillis();
		//System.out.println(instance.getTickInDay());
		try {
			instance.initLogging();
			instance.loadEngine();
			instance.loadCache();
			instance.loadConfig();
			instance.loadGame();
			instance.loadNetwork();
			//new GameTick().start();
			//new MaintananceThread().start();
			logger.info("Virtue Loaded in " + TimeUnit.MILLISECONDS.toSeconds((System.currentTimeMillis() - start)) + " seconds.");
			logger.info("Virtue is currently running a "+(instance.live ? "live" : "testing")+" instance on " + System.getProperty("os.name") + " on a(n) " + System.getProperty("os.arch") + " architecture.");
		} catch (Exception e) {
			logger.error("Error launching server.", e);
		}
	}
	
	private void initLogging () {
		String loggerPath = System.getenv("APPDATA") + "//Z835/log/game//"+getServerDay()+".log";
		
		org.apache.log4j.Logger.getRootLogger().setLevel(Level.INFO);
		
		org.apache.log4j.Logger.getRootLogger().addAppender(new ConsoleAppender(new PatternLayout(PatternLayout.TTCC_CONVERSION_PATTERN)));
		
		try {
			FileAppender appender = new FileAppender(new PatternLayout(PatternLayout.TTCC_CONVERSION_PATTERN), loggerPath);
			appender.setThreshold(Level.WARN);
			org.apache.log4j.Logger.getRootLogger().addAppender(appender);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void loadEngine() {
		engineService  = Executors.newSingleThreadExecutor();
		engine = new GameEngine();
		engine.load(live);
		engineService.execute(engine);
	}
	
	/**
	 * Loads the cache and stores it
	 * @throws IOException 
	 */
	private void loadCache() throws IOException {
		File cachePath = new File(Constants.CACHE_REPOSITORY);
		if (!cachePath.exists()) {
			cachePath = new File("repository/cache/");
		}
		cache = new Cache(FileStore.open(cachePath));
		container = new Container(Container.COMPRESSION_NONE, cache.createChecksumTable().encode(true, Constants.ONDEMAND_MODULUS, Constants.ONDEMAND_EXPONENT));
		checksum = container.encode();
	}
	
	/**
	 * Loads the network and binds it to a port
	 */
	private void loadNetwork() {
		network = new Network();
		network.bindNetwork();
	}
	
	/**
	 * Loads the packets and stores them in {@link Maps}
	 * @throws Exception 
	 */
	private void loadGame() throws Exception {
		accountIndex = new AccountIndex();
		accountIndex.load();
		event = new GameEventRepository();
		event.load();
		parser = new ParserRepository();
		parser.load();
		widget = new WidgetRepository();
		widget.load();
		scripts = new JSListeners();
		scripts.load();
		clans = new ClanManager();
		clans.load();
		exchange = new GrandExchange();
		exchange.load();
		controller = new MinigameController();
		controller.start();
		NewsDataParser.loadJsonNewsData();
		NpcDataParser.loadJsonNpcData();
		SpecialAttackHandler.init();
		ActionBar.init();
		AbstractNPC.init();
		NpcSpawnParser.loadNpcs();
		DialogueHandler.handle();
	}
	
	/**
	 * Loads the cache configuration decoders
	 * @throws IOException If the decoders could not be loaded due to a cache read error
	 */
	private void loadConfig () throws IOException {
		Container container = Container.decode(cache.getStore().read(255, Js5Archive.CONFIG.getArchiveId()));
		ReferenceTable configTable = ReferenceTable.decode(container.getData());
		InvRepository.init(Archive.decode(cache.read(2, Js5ConfigGroup.INVTYPE.id).getData(), 
				configTable.getEntry(Js5ConfigGroup.INVTYPE.id).size()));
		Archive varbits = Archive.decode(cache.read(2, Js5ConfigGroup.VAR_BIT.id).getData(), 
				configTable.getEntry(Js5ConfigGroup.VAR_BIT.id).size());
		Archive varps = Archive.decode(cache.read(2, Js5ConfigGroup.VAR_PLAYER.id).getData(), 
				configTable.getEntry(Js5ConfigGroup.VAR_PLAYER.id).size());
		Archive varclans = Archive.decode(cache.read(2, Js5ConfigGroup.VAR_CLAN_SETTING.id).getData(), 
				configTable.getEntry(Js5ConfigGroup.VAR_CLAN_SETTING.id).size());
		VarRepository.init(varps);
		VarBitTypeList.init(varbits, configTable.getEntry(Js5ConfigGroup.VAR_BIT.id));
		ClanSettings.init(varclans, configTable.getEntry(Js5ConfigGroup.VAR_CLAN_SETTING.id));
		ItemTypeList.init(cache, Constants.ITEM_DATA);
		LocTypeList.init(cache);
		NpcTypeList.init(cache, Constants.NPC_DATA);
		RegionManager.init(cache);
		EnumTypeList.init(cache);
		StructTypeList.init(cache);
		RenderTypeList.init(Archive.decode(cache.read(2, Js5ConfigGroup.RENDERTYPE.id).getData(), 
				configTable.getEntry(Js5ConfigGroup.RENDERTYPE.id).size()));
		QuickChatTypeList.init(cache);
		Archive quests = Archive.decode(cache.read(2, Js5ConfigGroup.QUESTTYPE.id).getData(), 
				configTable.getEntry(Js5ConfigGroup.QUESTTYPE.id).size());
		QuestTypeList.init(configTable, quests);
		//Appearance.init(cache.read(Js5Archive.DEFAULTS.getArchiveId(), DefaultsGroup.APPEARANCE.js5Id).getData());
		Huffman.setHuffman(new Huffman(cache.read(10, cache.getFileId(Js5Archive.BINARY.getArchiveId(), "huffman")).getData()));
	}
	
	/**
	 * Returns true if the server is running in a "live" environment
	 * @return True if the server is running live, false if running in development
	 */
	public boolean isLive () {
		return live;
	}
	
	/**
	 * Return the engine
	 */
	public GameEngine getEngine() {
		return engine;
	}
	
	/**
	 * Return the cache for the game build
	 */
	public Cache getCache() {
		return cache;
	}
	
	/**
	 * Return the container of the cache
	 */
	public Container getContainer() {
		return container;
	}
	
	/**
	 * Return the {@link ChecksumTable} data in the form of a {@link ByteBuffer}
	 */
	public ByteBuffer getChecksumTable() {
		return checksum;
	}
	
	/**
	 * Return the repo of accounts
	 */
	public AccountIndex getAccountIndex() {
		return accountIndex;
	}
	
	/**
	 * Return the repo of encoders/decoders
	 */
	public GameEventRepository getEventRepository() {
		return event;
	}
	
	/**
	 * Return the repo of parsers
	 */
	public ParserRepository getParserRepository() {
		return parser;
	}
	
	/**
	 * Return the repo of widgets
	 */
	public WidgetRepository getWidgetRepository() {
		return widget;
	}
	
	/**
	 * Returns the script manager
	 * @return
	 */
	public JSListeners getScripts() {
		return scripts;
	}
	
	/**
	 * Returns the clan manager
	 * @return
	 */
	public ClanManager getClans () {
		return clans;
	}
	
	/**
	 * Returns the grand exchange
	 * @return
	 */
	public GrandExchange getExchange () {
		return exchange;
	}
	
	/**
	 * Returns the grand exchange
	 * @return
	 */
	public MinigameController getController () {
		return controller;
	}
	
	/**
	 * Gets the current number of days since SERVER_DAY_0
	 * @return The number of days
	 */
	public int getServerDay () {
		return (int) ((System.currentTimeMillis() - serverDay0) / 86400000);
	}
	
	public int getTickInDay () {
		return (int) (System.currentTimeMillis() % 86400000) / 600;
	}
	
	/**
	 * Returns whether a system update is queued
	 * @return True if an update is queued, false otherwise
	 */
	public boolean hasUpdate () {
		return systemUpdate;
	}
	
	public int getUpdateTime () {
		return updateTimer;
	}
	
	public void runUpdate (int time) {
		System.out.println("Running update in "+time+" ticks...");
		this.systemUpdate = true;
		this.updateTimer = time;
		engine.invoke(new SystemUpdateTick());
		for (Player p : Lobby.getInstance().getPlayers()) {
			p.getDispatcher().sendSystemUpdate(time * 12);
		}
		for (Player p : World.getInstance().getPlayers()) {
			p.getDispatcher().sendSystemUpdate(time);
		}
	}
	
	public void decreaseUpdateTimer () {
		updateTimer--;
		if (updateTimer <= 0) {
			//systemUpdate = false;
		}
	}
	
	public void saveAll () {
		//Saves the account index, if it needs saving
		if (accountIndex != null) {
			if (accountIndex.needsSave()) {
				accountIndex.save();
			}
		}
		
		//Saves the clan data				
		if (clans != null) {
			clans.runSaveTasks();
		}
		
		//Saves the Grand Exchange data
		if (exchange != null && exchange.needsSave()) {
			exchange.saveOffers();
		}
	}
	
	public void restart() {
		System.gc();
		try {
			Runtime.getRuntime().exec("java -Xmx1024M -cp binary;library/binary/gson-2.3.jar;library/binary/slf4j-api-1.7.7.jar;library/binary/mysql-connector-java-5.1.34-bin.jar;library/binary/netty-all-4.0.26.Final.jar;library/binary/slf4j-log4j12-1.7.7.jar;library/binary/log4j-1.2.17.jar;library/binary/guava-18.0.jar org.virtue.Virtue");
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Returns The {@link Virtue} Instance
	 */
	public static Virtue getInstance() {
		if (instance == null) {
			try {
				instance = new Virtue();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return instance;
	}
}
