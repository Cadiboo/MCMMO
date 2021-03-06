package com.landofminecraft.mcmmo.item;

import com.landofminecraft.mcmmo.material.ModMaterial;
import com.landofminecraft.mcmmo.util.ModUtil;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemPickaxe;

public class ItemModPickaxe extends ItemPickaxe implements IItemModMaterial {

	protected final ModMaterial material;

	public ItemModPickaxe(final ModMaterial material) {
		super(material.getToolMaterial());
		ModUtil.setRegistryNames(this, material, "pickaxe");
		this.material = material;
	}

	@Override
	public ModMaterial getModMaterial() {
		return this.material;
	}

	@Override
	public CreativeTabs[] getCreativeTabs() {
		return ModUtil.getCreativeTabs(this);
	}

}
