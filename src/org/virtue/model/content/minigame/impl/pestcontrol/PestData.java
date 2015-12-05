package org.virtue.model.content.minigame.impl.pestcontrol;

import org.virtue.model.entity.npc.NPC;
import org.virtue.model.entity.npc.script.pestcontrol.PestPortal;
import org.virtue.model.entity.region.Tile;

public enum PestData {

	NOVICE(new PestPortal[]{
			new PestPortal(6146, new Tile(2628, 2591, 0), new Tile(2627, 2592, 0)), //purple portal
			new PestPortal(6147, new Tile(2680, 2588, 0), new Tile(2677, 2588, 0)), //blue portal
			new PestPortal(6148, new Tile(2669, 2570, 0), new Tile(2666, 2570, 0)), //yellow portal
			new PestPortal(6149, new Tile(2645, 2569, 0), new Tile(2642, 2569, 0)) //red portal
			}, new int[] { 
			/* Shifters */ 3732, 3733, 3734, 3735, 
			/* Ravagers */ 3742, 3743, 3744, 
			/* Brawler */ 3772, 3773, 
			/* Splatter */ 3727, 3728, 3729, 
			/* Spinner */ 3747, 3748, 3749,
			/* Torcher */ 3752, 3753, 3754, 3755, 
			/* Defiler */ 3762, 3763, 3764, 3765 },
			new int[] { 3732, 3733, 3734, 3735 }, 3),

	INTERMEDIATE(null, new int[] { 
			/* Shifters */3734, 3735, 3736, 3737, 3738, 3739
			/* Ravagers */, 3744, 3743, 3745,
			/* Brawler */3773, 3775, 3776, 
			/* Splatter */3728, 3729, 3730, 
			/* Spinner */3748, 3749, 3750, 3751,
			/* Torcher */3754, 3755, 3756, 3757, 3758, 3759, 
			/* Defiler */
			3764, 3765, 3766, 3768, 3769 }, new int[] { 3734, 3735, 3736, 3737, 3738, 3739 }, 5),

	VETERAN(null, new int[] { 
			/* Shifters */ 3736, 3737, 3738, 3739, 3740, 3741 
			/* Ravagers */, 3744, 3745, 3746,
			/* Brawler */ 3776, 3774, 
			/* Splatter */ 3729, 3730, 3731, 
			/* Spinner */ 3749, 3750, 3751, 
			/* Torcher */ 3758, 3759, 3760, 3761, 
			/* Defiler */ 3770, 3771 }, new int[] { 3736, 3737, 3738, 3739, 3740, 3741 }, 7);

	private int[] pests, shifters;
	private PestPortal[] portals;
	private int reward;
	private NPC knight;

	private PestData(PestPortal[] portals, int[] pests, int[] shifters, int reward) {
		this.portals = portals;
		this.pests = pests;
		this.shifters = shifters;
		this.reward = reward;
	}

	public int[] getShifters() {
		return shifters;
	}

	public int[] getPests() {
		return pests;
	}

	public int getReward() {
		return reward;
	}

	public NPC getKnight() {
		return knight;
	}
	
	public PestPortal[] getPortals() {
		return portals;
	}
}