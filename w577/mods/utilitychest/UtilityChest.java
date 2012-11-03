package w577.mods.utilitychest;

import java.util.logging.Level;


import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntity;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;

@Mod(modid = "UtilityChest", name = "Utility Chest Mod", version = "2.0.3")
@NetworkMod(channels = { "Utility", "UtilityServ" }, clientSideRequired = true, serverSideRequired = false, packetHandler = PacketHandler.class)
public class UtilityChest {

	public static BlockChestNetwork networkChestBlock;
	public static BlockChestTools toolsChestBlock;
	public static BlockChestOres oresChestBlock;
	public static BlockChestGrabber grabberChestBlock;
	public static BlockChestSmelter smelterChestBlock;
	//public static BlockChestDeenchant deenchantChestBlock;

	public static int utilityChestRender;

	@SidedProxy(clientSide = "w577.mods.utilitychest.client.ClientProxy", serverSide = "w577.mods.utilitychest.CommonProxy")
	public static CommonProxy proxy;
	
	@Instance("UtilityChest")
	public static UtilityChest instance;

	public static ChestNetworkHandler cnhInstance;

	@PreInit
	public void preload(FMLPreInitializationEvent evt) {
		Configuration cfg = new Configuration(
				evt.getSuggestedConfigurationFile());
		int ncbi = 3005;
		int tcbi = 3006;
		int ocbi = 3007;
		int gcbi = 3008;
		int scbi = 3009;
		int dcbi = 3010;

		try {
			cfg.load();
			ncbi = cfg.getOrCreateBlockIdProperty("networkID", 3005).getInt(
					3005);
			tcbi = cfg.getOrCreateBlockIdProperty("toolsID", 3006).getInt(3006);
			ocbi = cfg.getOrCreateBlockIdProperty("oresID", 3007).getInt(3007);
			gcbi = cfg.getOrCreateBlockIdProperty("grabberID", 3008).getInt(
					3008);
			scbi = cfg.getOrCreateBlockIdProperty("smelterID", 3009).getInt(
					3009);
			dcbi = cfg.getOrCreateBlockIdProperty("deenchantID", 3010).getInt(
					3010);
		} catch (Exception e) {
			FMLLog.log(Level.WARNING, e,
					"Utility Chest Mod could not load config, using defaults");
		} finally {
			cfg.save();
		}

		// Create Blocks
		networkChestBlock = new BlockChestNetwork(ncbi);
		toolsChestBlock = new BlockChestTools(tcbi);
		oresChestBlock = new BlockChestOres(ocbi);
		grabberChestBlock = new BlockChestGrabber(gcbi);
		smelterChestBlock = new BlockChestSmelter(scbi);
		//deenchantChestBlock = new BlockChestDeenchant(dcbi);

		// Register Blocks
		GameRegistry.registerBlock(networkChestBlock);
		GameRegistry.registerBlock(toolsChestBlock);
		GameRegistry.registerBlock(oresChestBlock);
		GameRegistry.registerBlock(grabberChestBlock);
		GameRegistry.registerBlock(smelterChestBlock);
		//GameRegistry.registerBlock(deenchantChestBlock);

		// Register TEs
		GameRegistry.registerTileEntity(TileEntityChestNetwork.class,
				"NetworkedChest");
		GameRegistry.registerTileEntity(TileEntityChestTools.class,
				"ToolsChest");
		GameRegistry.registerTileEntity(TileEntityChestOres.class, "OresChest");
		GameRegistry.registerTileEntity(TileEntityChestGrabber.class,
				"GrabberChest");
		GameRegistry.registerTileEntity(TileEntityChestSmelter.class,
				"SmelterChest");
		//GameRegistry.registerTileEntity(TileEntityChestDeenchant.class, "DeenchantChest");

		// Create recipes
		GameRegistry.addRecipe(new ItemStack(networkChestBlock), new Object[] {
				"wxw", "y#y", "zzz", 'w', Block.torchRedstoneActive, 'x',
				Item.sign, 'z', Item.redstoneRepeater, '#', Block.chest, 'y',
				Item.redstone });

		GameRegistry.addRecipe(new ItemStack(toolsChestBlock), new Object[] {
				" x ", "x#x", "yxy", 'x', Block.stone, '#', Block.chest, 'y',
				Block.blockDiamond });

		GameRegistry.addRecipe(new ItemStack(oresChestBlock), new Object[] {
				"IwI", "x#y", "IzI", 'w', Block.oreCoal, 'x', Block.oreLapis,
				'y', Block.oreRedstone, 'z', Block.oreDiamond, '#',
				Block.chest, 'I', Block.blockSteel });

		GameRegistry.addRecipe(new ItemStack(oresChestBlock), new Object[] {
				"IzI", "x#y", "IwI", 'w', Block.oreCoal, 'x', Block.oreLapis,
				'y', Block.oreRedstone, 'z', Block.oreDiamond, '#',
				Block.chest, 'I', Block.blockSteel });

		GameRegistry.addRecipe(new ItemStack(oresChestBlock), new Object[] {
				"IxI", "z#w", "IyI", 'w', Block.oreCoal, 'x', Block.oreLapis,
				'y', Block.oreRedstone, 'z', Block.oreDiamond, '#',
				Block.chest, 'I', Block.blockSteel });

		GameRegistry.addRecipe(new ItemStack(oresChestBlock), new Object[] {
				"IyI", "z#w", "IxI", 'w', Block.oreCoal, 'x', Block.oreLapis,
				'y', Block.oreRedstone, 'z', Block.oreDiamond, '#',
				Block.chest, 'I', Block.blockSteel });

		GameRegistry.addRecipe(new ItemStack(grabberChestBlock), new Object[] {
				"xxx", "y#y", "zyz", 'x', Item.stick, 'y', Item.slimeBall, '#',
				Block.chest, 'z', Block.pistonStickyBase });

		GameRegistry.addRecipe(new ItemStack(smelterChestBlock), new Object[] {
				"xxx", "z#z", "FLF", 'x', Item.fireballCharge, 'z',
				Item.blazeRod, '#', Block.chest, 'F', Block.stoneOvenIdle, 'L',
				Item.bucketLava });

	}

	@Init
	public void load(FMLInitializationEvent evt) {
		// Register names
		LanguageRegistry.instance().addStringLocalization(
				"tile.NetworkedChest.name", "en_US", "Networked Chest");
		LanguageRegistry.instance().addStringLocalization(
				"tile.ToolsChest.name", "en_US", "Tools Chest");
		LanguageRegistry.instance().addStringLocalization(
				"tile.OresChest.name", "en_US", "Ores Chest");
		LanguageRegistry.instance().addStringLocalization(
				"tile.GrabberChest.name", "en_US", "Grabber Chest");
		LanguageRegistry.instance().addStringLocalization(
				"tile.SmelterChest.name", "en_US", "Smelter Chest");
		//LanguageRegistry.instance().addStringLocalization(
		//		"tile.DeenchantChest.name", "en_US", "De-enchanting Chest");

		// Register GUI
		NetworkRegistry.instance().registerGuiHandler(this, proxy);

		// Register ticking
		TickRegistry.registerTickHandler(new UtilityTickHandler(), Side.SERVER);
		cnhInstance = new ChestNetworkHandler();

		// Register rendering
		proxy.registerTileEntitySpecialRenderers();
	}

}
