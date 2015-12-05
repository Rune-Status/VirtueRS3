package org.virtue.model.entity.combat.impl.spec;

import org.virtue.model.entity.combat.CombatStyle;
import org.virtue.model.entity.combat.impl.SpecialAttackHandler;
import org.virtue.model.entity.update.block.AnimationBlock;
import org.virtue.model.entity.update.block.GraphicsBlock;

/**
 * Handles the armadyl godsword special attack.
 * @author Emperor
 *
 */
public final class JudgementSpecial extends SpecialAttackHandler {

	/**
	 * Constructs a new {@code JudgementSpecial} {@code Object}.
	 */
	public JudgementSpecial() {
		super(CombatStyle.MELEE, 11694);
		super.animation = new AnimationBlock(11989);
		super.graphics = new GraphicsBlock(1, 2113);
		super.accuracyModifier = 50;
		super.damageModifier = 2.50;
	}

}