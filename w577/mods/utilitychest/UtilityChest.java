package w577.mods.utilitychest;

import net.minecraft.item.ItemBlock;
import cpw.mods.fml.common.FMLCommonHandler;
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

@Mod(modid = "UtilityChest", name = "Utility Chest Mod", version = "2.1")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, channels = {"NetworkChest", "NetworkChestServ", "NetworkSet"}, packetHandler = PacketHandler.class)
public class UtilityChest {

	@Instance("UtilityChest")
	public static UtilityChest instance;
	
	@SidedProxy(clientSide = "w577.mods.utilitychest.client.ClientProxy", serverSide = "w577.mods.utilitychest.CommonProxy")
	public static CommonProxy proxy;
	
	public static BlockChestNetwork blockChestNetwork;
	
	public static int renderId;
	
	private static ChestNetworkHandler cnh;
	
	@PreInit
	public void preInit(FMLPreInitializationEvent evt) {
		int ncbi = 3005;
		
		blockChestNetwork = new BlockChestNetwork(ncbi);
		
		GameRegistry.registerBlock(blockChestNetwork, ItemBlock.class, "NetworkedChest", "UtilityChest");
		
		GameRegistry.registerTileEntity(TileEntityChestNetwork.class, "NetworkedChest");
	}
	
	@Init
	public void init(FMLInitializationEvent evt) {
		LanguageRegistry.instance().addStringLocalization("tile.NetworkedChest.name", "en_US", "Networked Chest");
		
		NetworkRegistry.instance().registerGuiHandler(this, proxy);
		
		TickRegistry.registerTickHandler(new UtilityTickHandler(Side.SERVER), Side.SERVER);
		cnh = new ChestNetworkHandler();
		proxy.registerRenderers();
	}
	
	@PostInit
	public void postInit(FMLPostInitializationEvent evt) {
		
	}

	public static ChestNetworkHandler getCNH() {
		return cnh;
	}
}
