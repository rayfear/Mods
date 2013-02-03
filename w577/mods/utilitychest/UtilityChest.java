package w577.mods.utilitychest;

import java.util.logging.Level;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = "UtilityChest", name = "Utility Chest Mod", version = "2.1.1")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, channels = {"NetworkChest", "NetworkChestServ"}, packetHandler = PacketHandler.class)
public class UtilityChest {

	@Instance("UtilityChest")
	public static UtilityChest instance;
	
	@SidedProxy(clientSide = "w577.mods.utilitychest.client.ClientProxy", serverSide = "w577.mods.utilitychest.CommonProxy")
	public static CommonProxy proxy;
	
	public static BlockChestNetwork blockChestNetwork;
	public static BlockChestAdvancedUtility blockChestTools;
	public static BlockChestAdvancedUtility blockChestOres;
	public static BlockChestAdvancedUtility blockChestSmelter;	
	public static BlockChestGrabber blockChestGrabber;
	
	public static BlockChestAdvancedUtility blockChestDeenchanter;
	public static BlockChestStealth blockChestStealth;
	
	public static int renderId;

	public static int stealthRenderId;
	
	@PreInit
	public void preInit(FMLPreInitializationEvent evt) {
		Configuration conf = new Configuration(evt.getSuggestedConfigurationFile());
		int ncbi = 3005;
		int tcbi = 3006;
		int ocbi = 3007;
		int gcbi = 3008;
		int scbi = 3009;
		
		int dcbi = 3010;
		int stcbi = 3011;
		
		try {
			conf.load();
			ncbi = conf.getBlock("networkID", 3005).getInt();
			tcbi = conf.getBlock("toolsID", 3006).getInt();
			ocbi = conf.getBlock("oresID", 3007).getInt();
			gcbi = conf.getBlock("grabberID", 3008).getInt();
			scbi = conf.getBlock("smelterID", 3009).getInt();
			
			dcbi = conf.getBlock("deenchanterID", 3010).getInt();
			stcbi = conf.getBlock("stealthID", 3011).getInt();
		} catch (Exception e) {
			FMLLog.log(Level.WARNING, e,
					"Utility Chest Mod could not load config, using defaults");
		} finally {
			conf.save();
		}
		
		blockChestNetwork = new BlockChestNetwork(ncbi);
		blockChestTools = (BlockChestAdvancedUtility) new BlockChestAdvancedUtility(tcbi, TileEntityChestTools.class).setBlockName("ToolsChest");
		blockChestOres = (BlockChestAdvancedUtility) new BlockChestAdvancedUtility(ocbi, TileEntityChestOres.class).setBlockName("OresChest");
		blockChestGrabber = new BlockChestGrabber(gcbi);
		blockChestSmelter = (BlockChestAdvancedUtility) new BlockChestAdvancedUtility(
				scbi, TileEntityChestSmelter.class).setBlockName("SmelterChest");
		
		blockChestDeenchanter = (BlockChestAdvancedUtility) new BlockChestAdvancedUtility(dcbi, TileEntityChestDeenchanter.class).setBlockName("DeenchanterChest");
		blockChestStealth = new BlockChestStealth(stcbi);
		
		GameRegistry.registerBlock(blockChestNetwork, ItemBlock.class, "NetworkedChest", "UtilityChest");
		GameRegistry.registerBlock(blockChestTools, ItemBlock.class, "ToolsChest", "UtilityChest");
		GameRegistry.registerBlock(blockChestOres, ItemBlock.class, "OresChest", "UtilityChest");
		GameRegistry.registerBlock(blockChestGrabber, ItemBlock.class, "GrabberChest", "UtilityChest");
		GameRegistry.registerBlock(blockChestSmelter, ItemBlock.class, "SmelterChest", "UtilityChest");
		
		GameRegistry.registerBlock(blockChestDeenchanter, ItemBlock.class, "DeenchanterChest", "UtilityChest");
		GameRegistry.registerBlock(blockChestStealth, ItemBlock.class, "StealthChest", "UtilityChest");
		
		GameRegistry.registerTileEntity(TileEntityChestNetwork.class, "NetworkedChest");
		GameRegistry.registerTileEntity(TileEntityChestTools.class, "ToolsChest");
		GameRegistry.registerTileEntity(TileEntityChestOres.class, "OresChest");
		GameRegistry.registerTileEntity(TileEntityChestGrabber.class, "GrabberChest");
		GameRegistry.registerTileEntity(TileEntityChestSmelter.class, "SmelterChest");
		
		GameRegistry.registerTileEntity(TileEntityChestDeenchanter.class, "DeenchanterChest");
		GameRegistry.registerTileEntity(TileEntityChestStealth.class, "StealthChest");
		
		UtilityChestRecipeHandler.addRecipes();
		
		MinecraftForge.EVENT_BUS.register(new ChestNetworkSaveHandler.ChestNetworkWorldEventHandler());
	}
	
	@Init
	public void init(FMLInitializationEvent evt) {
		LanguageRegistry.instance().addStringLocalization("tile.NetworkedChest.name", "en_US", "Networked Chest");
		LanguageRegistry.instance().addStringLocalization("tile.ToolsChest.name", "en_US", "Tools Chest");
		LanguageRegistry.instance().addStringLocalization("tile.OresChest.name", "en_US", "Ores Chest");
		LanguageRegistry.instance().addStringLocalization("tile.GrabberChest.name", "en_US", "Grabber Chest");
		LanguageRegistry.instance().addStringLocalization("tile.SmelterChest.name", "en_US", "Smelter Chest");
		LanguageRegistry.instance().addStringLocalization("tile.DeenchanterChest.name", "en_US", "Deenchanter Chest");
		LanguageRegistry.instance().addStringLocalization("tile.StealthChest.name", "en_US", "Stealth Chest");
		
		NetworkRegistry.instance().registerGuiHandler(this, proxy);
		proxy.registerRenderers();
	}
	
	@PostInit
	public void postInit(FMLPostInitializationEvent evt) {
		
	}
}
