/**
 * @Author Kayla
 */
var SpecialAttackHandler = Java.type('org.virtue.model.entity.combat.impl.SpecialAttackHandler');
var CombatStyle = Java.type('org.virtue.model.entity.combat.CombatStyle');
var AttackInfo = Java.type('org.virtue.model.entity.combat.impl.ImpactInfo');
var AnimationBlock = Java.type('org.virtue.model.entity.update.block.AnimationBlock');
var GraphicsBlock = Java.type('org.virtue.model.entity.update.block.GraphicsBlock');

var SpecialAttack = Java.extend(SpecialAttackHandler);
var dragonLongsword = new SpecialAttack(CombatStyle.MELEE, [1305]) {
	getImpacts : function(entity, lock) {
		return [Java.super(dragonLongsword).impact(entity, lock, CombatStyle.MELEE, null, null)];
	},
};

var listen = function(scriptManager) {
	dragonLongsword.animation = new AnimationBlock(12033);
	dragonLongsword.graphics = new GraphicsBlock(1, 2117);
	dragonLongsword.damageModifier = 1.25;
	SpecialAttackHandler.register(dragonLongsword);
};