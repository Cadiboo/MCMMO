package com.landofminecraft.mcmmo.material;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.lang3.StringUtils;

import com.landofminecraft.mcmmo.EventSubscriber;
import com.landofminecraft.mcmmo.MinecraftMMO;
import com.landofminecraft.mcmmo.block.BlockModOre;
import com.landofminecraft.mcmmo.block.BlockResource;
import com.landofminecraft.mcmmo.block.IBlockModMaterial;
import com.landofminecraft.mcmmo.client.render.entity.EntityThrownDaggerRenderer;
import com.landofminecraft.mcmmo.entity.EntityThrownDagger;
import com.landofminecraft.mcmmo.init.ModItems;
import com.landofminecraft.mcmmo.item.IItemModMaterial;
import com.landofminecraft.mcmmo.item.ItemCurvedSword;
import com.landofminecraft.mcmmo.item.ItemDagger;
import com.landofminecraft.mcmmo.item.ItemHammer;
import com.landofminecraft.mcmmo.item.ItemMace;
import com.landofminecraft.mcmmo.item.ItemModArmor;
import com.landofminecraft.mcmmo.item.ItemModAxe;
import com.landofminecraft.mcmmo.item.ItemModHoe;
import com.landofminecraft.mcmmo.item.ItemModHorseArmor;
import com.landofminecraft.mcmmo.item.ItemModPickaxe;
import com.landofminecraft.mcmmo.item.ItemModShovel;
import com.landofminecraft.mcmmo.item.ItemModSword;
import com.landofminecraft.mcmmo.item.ItemResource;
import com.landofminecraft.mcmmo.item.ItemResourcePiece;
import com.landofminecraft.mcmmo.item.ItemWarAxe;
import com.landofminecraft.mcmmo.item.ModItemBlock;
import com.landofminecraft.mcmmo.util.ModEnums.IEnumNameFormattable;
import com.landofminecraft.mcmmo.util.ModReference;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.HorseArmorType;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

/**
 * MOHS Hardness from <a href= "https://en.wikipedia.org/wiki/Mohs_scale_of_mineral_hardness">Wikipedia</a> and <a href= "http://periodictable.com/Properties/A/MohsHardness.v.html">Periodictable</a>
 *
 * @author Cadiboo
 */
public enum ModMaterial implements IEnumNameFormattable {

	// TODO @Cadiboo weight

	BRONZE(0, new MetalProperties(false, 3.00f)),

	STEEL(1, new MetalProperties(false, 4.50f)),

	SILVER(2, new MetalProperties(true, 2.50f)),

	DARK_IRON(3, new MetalProperties(true, 5.00f)),

	LIGHT_STEEL(4, new MetalProperties(false, 5.00f)),

	QUICK_SILVER(5, new MetalProperties(false, 5.50f)),

	TITANIUM(6, new MetalProperties(true, 6.00f)),

	/* technically diamond is the highest the scale goes at 10, but "-Alverium: A unique Alloy that’s stronger than Diamonds" */
	ALVERIUM(7, new MetalProperties(false, 11.00f)),

	MIXED_CHUNK(8, new ModMaterialProperties(false, true, true, null, false, null, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 3.00f, null, null, null)),

	GOLD(9, new MetalProperties(false, 2.50f)), // hasOre = false to let vanilla ore gen do the work. note that this results in getOre() always returning null

	IRON(10, new MetalProperties(false, 4.00f)), // hasOre = false to let vanilla ore gen do the work. note that this results in getOre() always returning null

	DIAMOND(11, new GemProperties(false, true, true, 10.00f, null, null)), // hasOre = false to let vanilla ore gen do the work. note that this results in getOre() always returning null

	EMERALD(12, new GemProperties(false, false, false, 8.00f, null, null)), // hasOre = false to let vanilla ore gen do the work. note that this results in getOre() always returning null

	RUBY(13, new GemProperties(true, false, false, 9.00f, () -> ModItems.RUBY, (final Integer fortune, final Random rand) -> {
		return (rand.nextInt(4) + 1) * (fortune + 1);
	})),

	SAPHIRE(14, new GemProperties(true, false, false, 9.00f, () -> ModItems.SAPHIRE, (final Integer fortune, final Random rand) -> {
		return (rand.nextInt(4) + 1) * (fortune + 1);
	})),

	AMETHYST(15, new GemProperties(true, false, false, 7.00f, () -> ModItems.AMETHYST, (final Integer fortune, final Random rand) -> {
		return (rand.nextInt(6) + 1) * (fortune + 1);
	})),

	TOPAZ(16, new GemProperties(true, false, false, 8.00f, () -> ModItems.TOPAZ, (final Integer fortune, final Random rand) -> {
		return (rand.nextInt(5) + 1) * (fortune + 1);
	})),

	WOOD(17, new ModMaterialProperties(false, false, false, null, false, null, false, true, 1.00f, null, null, null)),

	STONE(18, new ModMaterialProperties(false, false, false, null, false, null, false, true, 2.00f, null, null, null)),

	CLOTH(18, new ModMaterialProperties(false, true, true, "", false, null, true, false, 0.50f, null, new BlockRenderLayer[] { BlockRenderLayer.CUTOUT }, null)),

	;

	private final int					id;
	private final ModMaterialProperties	properties;
	private final ArmorMaterial			armorMaterial;
	private final ToolMaterial			toolMaterial;
	private final HorseArmorType		horseArmorType;
	private final String				modId;

	private BlockModOre		ore;
	private BlockResource	block;

	private ModItemBlock	itemBlockOre;
	private ModItemBlock	itemBlockBlock;

	private ItemResource		resource;
	private ItemResourcePiece	resourcePiece;
	private ItemModArmor		helmet;
	private ItemModArmor		chestplate;
	private ItemModArmor		leggings;
	private ItemModArmor		boots;
	private ItemModHorseArmor	horseArmor;
	private ItemModPickaxe		pickaxe;
	private ItemModAxe			axe;
	private ItemModSword		sword;
	private ItemModShovel		shovel;
	private ItemModHoe			hoe;
	private ItemMace			mace;
	private ItemHammer			hammer;
	private ItemWarAxe			warAxe;
	private ItemCurvedSword		curvedSword;
	private ItemDagger			dagger;

	// something interesting to note is that "because of how minecraft entities work" i.e. "they're based on class" theres actually only 1 dagger entity thats used, despite one being registered for each material
	private EntityEntry thrownDagger;

	private ModMaterial(final int id, final ModMaterialProperties properties) {
		this(id, properties, ModReference.MOD_ID);
	}

	private ModMaterial(final int id, final ModMaterialProperties properties, final String modId) {
		this.id = id;
		this.properties = properties;
		this.modId = modId;
		this.armorMaterial = this.generateArmorMaterial();
		this.toolMaterial = this.generateToolMaterial();
		this.horseArmorType = this.generateHorseArmorType();
	}

	public int getId() {
		return this.id;
	}

	public ModMaterialProperties getProperties() {
		return this.properties;
	}

	public String getModId() {
		return this.modId;
	}

	public ArmorMaterial getArmorMaterial() {
		return this.armorMaterial;
	}

	public ToolMaterial getToolMaterial() {
		return this.toolMaterial;
	}

	public HorseArmorType getHorseArmorType() {
		return this.horseArmorType;
	}

	private ToolMaterial generateToolMaterial() {
		boolean hasTools = false;
		hasTools |= this.getProperties().hasPickaxe();
		hasTools |= this.getProperties().hasAxe();
		hasTools |= this.getProperties().hasSword();
		hasTools |= this.getProperties().hasShovel();
		hasTools |= this.getProperties().hasHoe();

		if (!hasTools) {
			return null;
		} else {
			final String name = this.getNameUppercase();
			final int harvestLevel = Math.min(3, Math.round(this.getProperties().getHardness() / 3f));
			final int maxUses = (int) Math.ceil(this.getProperties().getHardness() * 150f);
			final float efficiency = this.getProperties().getHardness();
			final float damageVsEntity = this.getProperties().getHardness();
			final int enchantability = (int) this.getProperties().getHardness();

			final ToolMaterial toolMaterial = EnumHelper.addToolMaterial(name, harvestLevel, maxUses, efficiency, damageVsEntity, enchantability);
			return toolMaterial;
		}
	}

	private ArmorMaterial generateArmorMaterial() {
		boolean hasArmor = false;
		hasArmor |= this.getProperties().hasHelmet();
		hasArmor |= this.getProperties().hasChestplate();
		hasArmor |= this.getProperties().hasLeggings();
		hasArmor |= this.getProperties().hasBoots();

		if (!hasArmor) {
			return null;
		} else {
			final String name = this.getNameUppercase();

			String nameSuffix = null;
			if ((nameSuffix == null) && this.getProperties().hasHelmet()) {
				nameSuffix = "helmet";
			}
			if ((nameSuffix == null) && this.getProperties().hasChestplate()) {
				nameSuffix = "chestplate";
			}
			if ((nameSuffix == null) && this.getProperties().hasLeggings()) {
				nameSuffix = "leggings";
			}
			if ((nameSuffix == null) && this.getProperties().hasBoots()) {
				nameSuffix = "boots";
			}

			final String textureName = new ResourceLocation(this.getResouceLocationDomainWithOverrides(nameSuffix, ForgeRegistries.ITEMS), this.getNameLowercase()).toString();

			final int durability = (int) Math.ceil(this.getProperties().getHardness() * 5);

			final int[] reductionAmounts = new int[4];
			Arrays.fill(reductionAmounts, (int) Math.ceil(this.getProperties().getHardness() / 2f));

			final int enchantability = (int) Math.ceil(this.getProperties().getHardness());

			final SoundEvent soundOnEquip = SoundEvents.ITEM_ARMOR_EQUIP_IRON;

			final float toughness = (int) Math.ceil(this.getProperties().getHardness() / 5f);

			final ArmorMaterial armorMaterial = EnumHelper.addArmorMaterial(name, textureName, durability, reductionAmounts, enchantability, soundOnEquip, toughness);
			// TODO TEST THIS!!
			armorMaterial.setRepairItem(new ItemStack(this.getResource()));
			return armorMaterial;

		}
	}

	private HorseArmorType generateHorseArmorType() {
		boolean hasArmor = false;
		hasArmor |= this.getProperties().hasHelmet();
		hasArmor |= this.getProperties().hasChestplate();
		hasArmor |= this.getProperties().hasLeggings();
		hasArmor |= this.getProperties().hasBoots();

		if (!hasArmor) {
			return HorseArmorType.NONE;
		} else {
			final String name = this.getNameUppercase();

			final String textureLocation = new ResourceLocation(this.getResouceLocationDomainWithOverrides("horse_armor", ForgeRegistries.ITEMS), "textures/entity/horse/armor/horse_armor_" + this.getNameLowercase()).toString() + ".png";

			final int armorStrength = (int) Math.ceil(this.getProperties().getHardness());

			return EnumHelper.addHorseArmor(name, textureLocation, armorStrength);
		}
	}

	public String getResouceLocationDomainWithOverrides(final String nameSuffix, final IForgeRegistry registry) {
		for (final ModContainer mod : Loader.instance().getActiveModList()) {
			if (!mod.getModId().equals(ModReference.MOD_ID)) {
				if (registry.containsKey(new ResourceLocation(mod.getModId(), this.getVanillaNameLowercase(nameSuffix) + (nameSuffix.length() > 0 ? "_" + nameSuffix : "")))) {
					return mod.getModId();
				}
			}
		}
		return ModReference.MOD_ID;
	}

	public String getVanillaNameLowercase(final String suffix) {

		final String vanillaNameLowercase;

		if (this.getNameLowercase().equals("gold")) {
			switch (suffix.toLowerCase()) {
			case "sword":
			case "shovel":
			case "pickaxe":
			case "axe":
			case "hoe":
			case "helmet":
			case "chestplate":
			case "leggings":
			case "boots":
			case "apple":
			case "carrot":
			case "horse_armor":
				vanillaNameLowercase = this.getNameLowercase() + "en";
				break;
			default:
				vanillaNameLowercase = this.getNameLowercase();
			}
		} else if (this.getNameLowercase().equals("wood")) {

			switch (suffix.toLowerCase()) {
			case "sword":
			case "shovel":
			case "pickaxe":
			case "axe":
			case "hoe":
				vanillaNameLowercase = this.getNameLowercase() + "en";
				break;
			default:
				vanillaNameLowercase = this.getNameLowercase();
			}
		} else {
			vanillaNameLowercase = this.getNameLowercase();
		}
		return vanillaNameLowercase;

	}

	public String getVanillaNameUppercase(final String suffix) {
		return this.getVanillaNameLowercase(suffix).toUpperCase();
	}

	public String getVanillaNameFormatted(final String suffix) {
		return StringUtils.capitalize(this.getVanillaNameLowercase(suffix));
	}

	/** tungsten_carbite used to turn into Tungsten_carbite. Now it turns into Tungsten Carbite */
	@Override
	public String getNameFormatted() {
		return String.join(" ", Arrays.asList(this.getNameLowercase().split("_")).stream().map(String::toUpperCase).collect(Collectors.toList()));
	}

	public BlockModOre getOre() {
		return this.ore;
	}

	public BlockResource getBlock() {
		return this.block;
	}

	public ItemResource getResource() {
		return this.resource;
	}

	public ItemResourcePiece getResourcePiece() {
		return this.resourcePiece;
	}

	public ModItemBlock getItemBlockOre() {
		return this.itemBlockOre;
	}

	public ModItemBlock getItemBlockBlock() {
		return this.itemBlockBlock;
	}

	public ItemModArmor getHelmet() {
		return this.helmet;
	}

	public ItemModArmor getChestplate() {
		return this.chestplate;
	}

	public ItemModArmor getLeggings() {
		return this.leggings;
	}

	public ItemModArmor getBoots() {
		return this.boots;
	}

	public ItemModHorseArmor getHorseArmor() {
		return this.horseArmor;
	}

	public ItemModPickaxe getPickaxe() {
		return this.pickaxe;
	}

	public ItemModAxe getAxe() {
		return this.axe;
	}

	public ItemModSword getSword() {
		return this.sword;
	}

	public ItemModShovel getShovel() {
		return this.shovel;
	}

	public ItemModHoe getHoe() {
		return this.hoe;
	}

	public ItemMace getMace() {
		return this.mace;
	}

	public ItemHammer getHammer() {
		return this.hammer;
	}

	public ItemWarAxe getWarAxe() {
		return this.warAxe;
	}

	public ItemCurvedSword getCurvedSword() {
		return this.curvedSword;
	}

	public ItemDagger getDagger() {
		return this.dagger;
	}

	public static float getHighestHardness() {
		float ret = 0;
		for (final ModMaterial material : ModMaterial.values()) {
			if (material.getProperties().getHardness() > ret) {
				ret = material.getProperties().getHardness();
			}
		}
		return ret;
	}

	public static ModMaterial byId(final int id) {
		return values()[Math.min(Math.abs(id), values().length)];
	}

	public class MaterialEventSubscriber {

		@SubscribeEvent
		public void onRegisterBlocksEvent(final RegistryEvent.Register<Block> event) {
			final IForgeRegistry<Block> registry = event.getRegistry();

			if (ModMaterial.this.getProperties().hasOre()) {
				ModMaterial.this.ore = new BlockModOre(ModMaterial.this);
				registry.register(ModMaterial.this.ore);
			}

			if (ModMaterial.this.getProperties().hasBlock()) {
				ModMaterial.this.block = new BlockResource(ModMaterial.this);
				registry.register(ModMaterial.this.block);
			}

			MinecraftMMO.debug("Registered blocks for " + ModMaterial.this.getNameFormatted());
		}

		@SubscribeEvent
		public void onRegisterItemsEvent(final RegistryEvent.Register<Item> event) {
			final IForgeRegistry<Item> registry = event.getRegistry();

			if (ModMaterial.this.getProperties().hasOre()) {
				ModMaterial.this.itemBlockOre = new ModItemBlock(ModMaterial.this.getOre(), new ResourceLocation(ModMaterial.this.getResouceLocationDomainWithOverrides("ore", ForgeRegistries.BLOCKS), ModMaterial.this.getNameLowercase() + "_ore"));
				registry.register(ModMaterial.this.itemBlockOre);
			}

			if (ModMaterial.this.getProperties().hasBlock()) {
				ModMaterial.this.itemBlockBlock = new ModItemBlock(ModMaterial.this.getBlock(), new ResourceLocation(ModMaterial.this.getResouceLocationDomainWithOverrides("block", ForgeRegistries.BLOCKS), ModMaterial.this.getNameLowercase() + "_block"));
				registry.register(ModMaterial.this.itemBlockBlock);
			}

			//

			if (ModMaterial.this.getProperties().hasResource()) {
				ModMaterial.this.resource = new ItemResource(ModMaterial.this);
				registry.register(ModMaterial.this.resource);
			}
			if (ModMaterial.this.getProperties().hasResourcePiece()) {
				ModMaterial.this.resourcePiece = new ItemResourcePiece(ModMaterial.this);
				registry.register(ModMaterial.this.resourcePiece);
			}

			if (ModMaterial.this.getProperties().hasHelmet()) {
				ModMaterial.this.helmet = new ItemModArmor(ModMaterial.this, EntityEquipmentSlot.HEAD);
				registry.register(ModMaterial.this.helmet);
			}
			if (ModMaterial.this.getProperties().hasChestplate()) {
				ModMaterial.this.chestplate = new ItemModArmor(ModMaterial.this, EntityEquipmentSlot.CHEST);
				registry.register(ModMaterial.this.chestplate);
			}
			if (ModMaterial.this.getProperties().hasLeggings()) {
				ModMaterial.this.leggings = new ItemModArmor(ModMaterial.this, EntityEquipmentSlot.LEGS);
				registry.register(ModMaterial.this.leggings);
			}
			if (ModMaterial.this.getProperties().hasBoots()) {
				ModMaterial.this.boots = new ItemModArmor(ModMaterial.this, EntityEquipmentSlot.FEET);
				registry.register(ModMaterial.this.boots);
			}
			if (ModMaterial.this.getProperties().hasHorseArmor()) {
				ModMaterial.this.horseArmor = new ItemModHorseArmor(ModMaterial.this);
				registry.register(ModMaterial.this.horseArmor);
			}

			if (ModMaterial.this.getProperties().hasPickaxe()) {
				ModMaterial.this.pickaxe = new ItemModPickaxe(ModMaterial.this);
				registry.register(ModMaterial.this.pickaxe);
			}
			if (ModMaterial.this.getProperties().hasAxe()) {
				ModMaterial.this.axe = new ItemModAxe(ModMaterial.this);
				registry.register(ModMaterial.this.axe);
			}
			if (ModMaterial.this.getProperties().hasSword()) {
				ModMaterial.this.sword = new ItemModSword(ModMaterial.this);
				registry.register(ModMaterial.this.sword);
			}
			if (ModMaterial.this.getProperties().hasShovel()) {
				ModMaterial.this.shovel = new ItemModShovel(ModMaterial.this);
				registry.register(ModMaterial.this.shovel);
			}
			if (ModMaterial.this.getProperties().hasHoe()) {
				ModMaterial.this.hoe = new ItemModHoe(ModMaterial.this);
				registry.register(ModMaterial.this.hoe);
			}
			if (ModMaterial.this.getProperties().hasMace()) {
				ModMaterial.this.mace = new ItemMace(ModMaterial.this);
				registry.register(ModMaterial.this.mace);
			}
			if (ModMaterial.this.getProperties().hasHammer()) {
				ModMaterial.this.hammer = new ItemHammer(ModMaterial.this);
				registry.register(ModMaterial.this.hammer);
			}
			if (ModMaterial.this.getProperties().hasWarAxe()) {
				ModMaterial.this.warAxe = new ItemWarAxe(ModMaterial.this);
				registry.register(ModMaterial.this.warAxe);
			}
			if (ModMaterial.this.getProperties().hasCurvedSword()) {
				ModMaterial.this.curvedSword = new ItemCurvedSword(ModMaterial.this);
				registry.register(ModMaterial.this.curvedSword);
			}
			if (ModMaterial.this.getProperties().hasDagger()) {
				ModMaterial.this.dagger = new ItemDagger(ModMaterial.this);
				registry.register(ModMaterial.this.dagger);
			}

			MinecraftMMO.debug("Registered items for " + ModMaterial.this.getNameFormatted());

		}

		@SubscribeEvent
		public void onRegisterEntitiesEvent(final RegistryEvent.Register<EntityEntry> event) {
			final IForgeRegistry<EntityEntry> registry = event.getRegistry();

			if (ModMaterial.this.getProperties().hasDagger()) {
				final ResourceLocation registryName = new ResourceLocation(ModReference.MOD_ID, ModMaterial.this.getNameLowercase() + "_thrown_dagger");
				final boolean hasEgg = false;
				final int range = 128;
				final int updateFrequency = 2;
				final boolean sendVelocityUpdates = true;

				EntityEntryBuilder<Entity> builder = EntityEntryBuilder.create();
				builder = builder.entity(EntityThrownDagger.class);
				builder = builder.id(registryName, EventSubscriber.entityId++);
				builder = builder.name(registryName.getPath());
				builder = builder.tracker(range, updateFrequency, sendVelocityUpdates);

				if (hasEgg) {
					builder = builder.egg(0xFFFFFF, 0xAAAAAA);
				}

				ModMaterial.this.thrownDagger = builder.build();
				registry.register(ModMaterial.this.thrownDagger);
			}

			MinecraftMMO.debug("Registered entities for " + ModMaterial.this.getNameFormatted());
		}

		/** CLIENT ONLY */
		@SideOnly(Side.CLIENT)
		@SubscribeEvent
		public void onRegisterModelsEvent(final ModelRegistryEvent event) {

			RenderingRegistry.registerEntityRenderingHandler(EntityThrownDagger.class, EntityThrownDaggerRenderer::new);

			if (ModMaterial.this.getProperties().hasOre()) {
				this.registerBlockModMaterialItemBlockModel(ModMaterial.this.getOre());
			}

			if (ModMaterial.this.getProperties().hasBlock()) {
				this.registerBlockModMaterialItemBlockModel(ModMaterial.this.getBlock());
			}

			//

			if (ModMaterial.this.getProperties().hasResource()) {
				if (ModMaterial.this.getResouceLocationDomainWithOverrides(ModMaterial.this.getProperties().getResourceSuffix().toLowerCase(), ForgeRegistries.ITEMS).equals(ModMaterial.this.getModId())) {
					this.registerItemModMaterialModel(ModMaterial.this.getResource());
				}
			}
			if (ModMaterial.this.getProperties().hasResourcePiece()) {
				if (ModMaterial.this.getResouceLocationDomainWithOverrides(ModMaterial.this.getProperties().getResourcePieceSuffix().toLowerCase(), ForgeRegistries.ITEMS).equals(ModMaterial.this.getModId())) {
					this.registerItemModMaterialModel(ModMaterial.this.getResourcePiece());
				}
			}

			if (ModMaterial.this.getProperties().hasHelmet()) {
				this.registerItemModMaterialModel(ModMaterial.this.getHelmet());
			}
			if (ModMaterial.this.getProperties().hasChestplate()) {
				this.registerItemModMaterialModel(ModMaterial.this.getChestplate());
			}
			if (ModMaterial.this.getProperties().hasLeggings()) {
				this.registerItemModMaterialModel(ModMaterial.this.getLeggings());
			}
			if (ModMaterial.this.getProperties().hasBoots()) {
				this.registerItemModMaterialModel(ModMaterial.this.getBoots());
			}
			if (ModMaterial.this.getProperties().hasHorseArmor()) {
				this.registerItemModMaterialModel(ModMaterial.this.getHorseArmor());
			}

			if (ModMaterial.this.getProperties().hasPickaxe()) {
				this.registerItemModMaterialModel(ModMaterial.this.getPickaxe());
			}
			if (ModMaterial.this.getProperties().hasAxe()) {
				this.registerItemModMaterialModel(ModMaterial.this.getAxe());
			}
			if (ModMaterial.this.getProperties().hasSword()) {
				this.registerItemModMaterialModel(ModMaterial.this.getSword());
			}
			if (ModMaterial.this.getProperties().hasShovel()) {
				this.registerItemModMaterialModel(ModMaterial.this.getShovel());
			}
			if (ModMaterial.this.getProperties().hasHoe()) {
				this.registerItemModMaterialModel(ModMaterial.this.getHoe());
			}
			if (ModMaterial.this.getProperties().hasMace()) {
				this.registerItemModMaterialModel(ModMaterial.this.getMace());
			}
			if (ModMaterial.this.getProperties().hasHammer()) {
				this.registerItemModMaterialModel(ModMaterial.this.getHammer());
			}
			if (ModMaterial.this.getProperties().hasWarAxe()) {
				this.registerItemModMaterialModel(ModMaterial.this.getWarAxe());
			}
			if (ModMaterial.this.getProperties().hasCurvedSword()) {
				this.registerItemModMaterialModel(ModMaterial.this.getCurvedSword());
			}
			if (ModMaterial.this.getProperties().hasDagger()) {
				this.registerItemModMaterialModel(ModMaterial.this.getDagger());
			}

			MinecraftMMO.debug("Registered models for materials");
		}

		/** CLIENT ONLY */
		@SideOnly(Side.CLIENT)
		private <T extends Item & IItemModMaterial> void registerItemModMaterialModel(final T item) {
			if (item == null) {
				MinecraftMMO.debug("sad");
			}
			final boolean isVanilla = item.getRegistryName().getNamespace().equals("minecraft");
			final String registryNameResourceDomain = isVanilla ? "minecraft" : item.getModMaterial().getModId().toString();
			final String registryNameResourcePath = item.getRegistryName().getPath();

			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(new ResourceLocation(registryNameResourceDomain, registryNameResourcePath), "normal"));
		}

		/** CLIENT ONLY */
		@SideOnly(Side.CLIENT)
		private <T extends Block & IBlockModMaterial> void registerBlockModMaterialItemBlockModel(final T block) {
			final boolean isVanilla = block.getRegistryName().getNamespace().equals("minecraft");
			final String registryNameResourceDomain = isVanilla ? "minecraft" : block.getModMaterial().getModId().toString();
			final String registryNameResourcePath = block.getRegistryName().getPath();

			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(new ResourceLocation(registryNameResourceDomain, registryNameResourcePath), "normal"));
		}

		@SubscribeEvent(priority = EventPriority.LOWEST)
		public void onRegistryEventRegister(final RegistryEvent.Register event) {
			final IForgeRegistry registry = event.getRegistry();

			if (registry == ForgeRegistries.BLOCKS) {
				final IForgeRegistry<Block> castRegistry = registry;

				if (ModMaterial.this.getProperties().hasOre()) {
					ModMaterial.this.ore = (BlockModOre) this.getRegistryValue(castRegistry, "ore");
				}
				if (ModMaterial.this.getProperties().hasBlock()) {
					ModMaterial.this.block = (BlockResource) this.getRegistryValue(castRegistry, "block");
				}
			}

			if (registry == ForgeRegistries.ITEMS) {
				final IForgeRegistry<Item> castRegistry = registry;

				if (ModMaterial.this.getProperties().hasOre()) {
					ModMaterial.this.itemBlockOre = (ModItemBlock) this.getRegistryValue(castRegistry, "ore");
				}
				if (ModMaterial.this.getProperties().hasBlock()) {
					ModMaterial.this.itemBlockBlock = (ModItemBlock) this.getRegistryValue(castRegistry, "block");
				}

				//

				if (ModMaterial.this.getProperties().hasResource()) {
					ModMaterial.this.resource = (ItemResource) this.getRegistryValue(castRegistry, ModMaterial.this.getProperties().getResourceSuffix());
				}
				if (ModMaterial.this.getProperties().hasResourcePiece()) {
					ModMaterial.this.resourcePiece = (ItemResourcePiece) this.getRegistryValue(castRegistry, ModMaterial.this.getProperties().getResourcePieceSuffix());
				}

				if (ModMaterial.this.getProperties().hasHelmet()) {
					ModMaterial.this.helmet = (ItemModArmor) this.getRegistryValue(castRegistry, "helmet");
				}
				if (ModMaterial.this.getProperties().hasChestplate()) {
					ModMaterial.this.chestplate = (ItemModArmor) this.getRegistryValue(castRegistry, "chestplate");
				}
				if (ModMaterial.this.getProperties().hasLeggings()) {
					ModMaterial.this.leggings = (ItemModArmor) this.getRegistryValue(castRegistry, "leggings");
				}
				if (ModMaterial.this.getProperties().hasBoots()) {
					ModMaterial.this.boots = (ItemModArmor) this.getRegistryValue(castRegistry, "boots");
				}
				if (ModMaterial.this.getProperties().hasHorseArmor()) {
					ModMaterial.this.horseArmor = (ItemModHorseArmor) this.getRegistryValue(castRegistry, "horse_armor");
				}
				if (ModMaterial.this.getProperties().hasPickaxe()) {
					ModMaterial.this.pickaxe = (ItemModPickaxe) this.getRegistryValue(castRegistry, "pickaxe");
				}
				if (ModMaterial.this.getProperties().hasAxe()) {
					ModMaterial.this.axe = (ItemModAxe) this.getRegistryValue(castRegistry, "axe");
				}
				if (ModMaterial.this.getProperties().hasSword()) {
					ModMaterial.this.sword = (ItemModSword) this.getRegistryValue(castRegistry, "sword");
				}
				if (ModMaterial.this.getProperties().hasShovel()) {
					ModMaterial.this.shovel = (ItemModShovel) this.getRegistryValue(castRegistry, "shovel");
				}
				if (ModMaterial.this.getProperties().hasHoe()) {
					ModMaterial.this.hoe = (ItemModHoe) this.getRegistryValue(castRegistry, "hoe");
				}
				if (ModMaterial.this.getProperties().hasMace()) {
					ModMaterial.this.mace = (ItemMace) this.getRegistryValue(castRegistry, "mace");
				}
				if (ModMaterial.this.getProperties().hasHammer()) {
					ModMaterial.this.hammer = (ItemHammer) this.getRegistryValue(castRegistry, "hammer");
				}
				if (ModMaterial.this.getProperties().hasWarAxe()) {
					ModMaterial.this.warAxe = (ItemWarAxe) this.getRegistryValue(castRegistry, "war_axe");
				}
				if (ModMaterial.this.getProperties().hasCurvedSword()) {
					ModMaterial.this.curvedSword = (ItemCurvedSword) this.getRegistryValue(castRegistry, "curved_sword");
				}
				if (ModMaterial.this.getProperties().hasDagger()) {
					ModMaterial.this.dagger = (ItemDagger) this.getRegistryValue(castRegistry, "dagger");
				}
			}

			if (registry == ForgeRegistries.ENTITIES) {
				final IForgeRegistry<EntityEntry> castRegistry = registry;

				if (ModMaterial.this.getProperties().hasDagger()) {
					ModMaterial.this.thrownDagger = this.getRegistryValue(castRegistry, "thrown_dagger");
				}
			}

		}

		@Nullable
		public <T> T getRegistryValue(@Nonnull final IForgeRegistry<? extends IForgeRegistryEntry<T>> registry, @Nonnull String nameSuffix) {
			nameSuffix = nameSuffix.toLowerCase();
			return (T) registry.getValue(new ResourceLocation(ModMaterial.this.getResouceLocationDomainWithOverrides(nameSuffix, registry), (ModMaterial.this.getVanillaNameLowercase(nameSuffix) + (nameSuffix.length() > 0 ? "_" + nameSuffix : ""))));
		}

	}

	private final MaterialEventSubscriber eventSubscriber = new MaterialEventSubscriber();

	public MaterialEventSubscriber getEventSubscriber() {
		return this.eventSubscriber;
	}

}
