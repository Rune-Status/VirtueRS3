/**
 * Handles Magic Short Spec
 * @author Kayla
 *
 */
var SpecialAttackHandler = Java.type('org.virtue.model.entity.combat.impl.SpecialAttackHandler');
var CombatStyle = Java.type('org.virtue.model.entity.combat.CombatStyle');
var AttackInfo = Java.type('org.virtue.model.entity.combat.impl.ImpactInfo');
var AnimationBlock = Java.type('org.virtue.model.entity.update.block.AnimationBlock');
var GraphicsBlock = Java.type('org.virtue.model.entity.update.block.GraphicsBlock');
var Projectile = Java.type('org.virtue.model.entity.region.packets.Projectile');

var SpecialAttack = Java.extend(SpecialAttackHandler);
var magicShortbow = new SpecialAttack(CombatStyle.RANGE, [861]) {
	getImpacts : function(entity, lock) {
		return [Java.super(magicShortbow).impact(entity, lock, CombatStyle.RANGE, null, new Projectile(249, 48, 72, 16, 34, 16)),
		        Java.super(magicShortbow).impact(entity, lock, CombatStyle.RANGE, null, new Projectile(249, 60, 90, 16, 34, 16))];
	},
};

var listen = function(scriptManager) {
	magicShortbow.animation = new AnimationBlock(426);
	magicShortbow.damageModifier = 1.12;
	SpecialAttackHandler.register(magicShortbow);
};