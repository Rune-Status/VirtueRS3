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
package org.virtue.network.event.handler.impl;

import org.virtue.model.World;
import org.virtue.model.entity.combat.impl.ability.Ability;
import org.virtue.model.entity.combat.impl.ability.ActionBar;
import org.virtue.model.entity.npc.NPC;
import org.virtue.model.entity.player.Player;
import org.virtue.model.entity.player.inv.Item;
import org.virtue.model.entity.player.skill.magic.MagicSpell;
import org.virtue.model.entity.player.skill.magic.Spellbook;
import org.virtue.network.event.context.impl.in.WidgetOnNPCContext;
import org.virtue.network.event.handler.GameEventHandler;

/**
 * @author Im Frizzy <skype:kfriz1998>
 * @author Frosty Teh Snowman <skype:travis.mccorkle>
 * @author Arthur <skype:arthur.behesnilian>
 * @author Sundays211
 * @since Jan 23, 2015
 */
public class WidgetOnNPCHandler implements GameEventHandler<WidgetOnNPCContext> {

	@Override
	public void handle(Player player, WidgetOnNPCContext context) {
		System.out.println(context.getWidgetID() + ", " + context.getSlot() + ", " + context.getNpcIndex());
		NPC npc = World.getInstance().getNPCs().get(context.getNpcIndex());
		if (context.getWidgetID() == 1461) {
			Item mainHand = player.getEquipment().getWorn(3);
			MagicSpell spell = Spellbook.MODERN.get(context.getSlot());
			if (spell != null) {
				if (mainHand == null) {
					player.getDispatcher().sendGameMessage("This ability requires a magic weapon in your main hand.");
					return;
				}
				spell.cast(player, npc);
				return;
			}
		}
		Ability ability = ActionBar.getAbilities().get(context.getWidgetID() << 16 | context.getSlot());
		player.getDispatcher().sendGameMessage("Ability Button ID: " + context.getSlot());
		System.out.println("Ability: " + ability);
		if (ability != null) {
			player.getCombatSchedule().lock(npc);
			player.getCombatSchedule().run(ability);
		}
	}
}
