package com.landofminecraft.mcmmo.block;

import java.util.Random;

import com.landofminecraft.mcmmo.material.ModMaterial;
import com.landofminecraft.mcmmo.util.ModUtil;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;

/**
 * Custom ore block for materials
 * 
 * @author Cadiboo
 */
public class BlockModOre extends Block implements IBlockModMaterial {

	protected final ModMaterial material;

	public BlockModOre(final ModMaterial material) {
		super(Material.ROCK);
		ModUtil.setRegistryNames(this, material, "ore");
		this.material = material;
	}

	@Override
	public final ModMaterial getModMaterial() {
		return this.material;
	}

	@Override
	public BlockRenderLayer getRenderLayer() {
		return this.material.getProperties().getBlockRenderLayers().get(0);
	}

	@Override
	public boolean canRenderInLayer(final IBlockState state, final BlockRenderLayer layer) {
		return this.material.getProperties().getBlockRenderLayers().contains(layer);
	}

	// TODO remove this
	@Override
	public boolean isFullCube(final IBlockState state) {
		return false;
	}

	// TODO remove this
	@Override
	public boolean isOpaqueCube(final IBlockState state) {
		return false;
	}

	@Override
	public Item getItemDropped(final IBlockState state, final Random rand, final int fortune) {
		final Item item = this.material.getProperties().getOreDrop();
		if (item == null) {
			return Item.getItemFromBlock(this);
		}
		return item;
	}

	@Override
	public int quantityDroppedWithBonus(final int fortune, final Random random) {
		return this.material.getProperties().getQuantityDroppedWithBonus(fortune, random);
	}

}
