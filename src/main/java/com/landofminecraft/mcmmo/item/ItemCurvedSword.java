package com.landofminecraft.mcmmo.item;

import com.landofminecraft.mcmmo.material.ModMaterial;
import com.landofminecraft.mcmmo.util.ModUtil;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemSword;

public class ItemCurvedSword extends ItemSword implements IItemModMaterial {

	protected final ModMaterial material;

	public ItemCurvedSword(final ModMaterial material) {
		super(material.getToolMaterial());
		ModUtil.setRegistryNames(this, material, "curved_sword");
		this.material = material;
	}

	@Override
	public final ModMaterial getModMaterial() {
		return this.material;
	}

	@Override
	public CreativeTabs[] getCreativeTabs() {
		return ModUtil.getCreativeTabs(this);
	}

}
