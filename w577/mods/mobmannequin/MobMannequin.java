package w577.mods.mobmannequin;

import java.util.Arrays;
import java.util.List;

import w577.mods.mobmannequin.PacketHandler;
import w577.mods.mobmannequin.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "MobMannequin", name = "Mob Mannequin Mod", version = "0.1")
@NetworkMod(channels = { "MobManne"}, clientSideRequired = true, serverSideRequired = false, packetHandler = PacketHandler.class)
public class MobMannequin {
	
	public static BlockMobMannequin mobManneBlock;
	public static ItemMannequinPlacer mannePlacerItem;
	
	public static List<Integer> disallowedMobIds = Arrays.asList(0, 1, 9, 20, 21, 40, 41, 48, 49, 200);
	
	public static int mobManneRender;
	
	@SidedProxy(clientSide = "w577.mods.mobmannequin.client.ClientProxy", serverSide = "w577.mods.mobmannequin.CommonProxy")
	public static CommonProxy proxy;
	
	@Instance("MobMannequin")
	public static MobMannequin instance;
	
	@PreInit
	public void preload(FMLPreInitializationEvent evt) {
		mobManneBlock = new BlockMobMannequin(3012);
		mannePlacerItem = new ItemMannequinPlacer(5010);
		
		GameRegistry.registerBlock(mobManneBlock);
		
		GameRegistry.registerTileEntity(TileEntityMobMannequin.class, "MobManne");
	}
	
	@Init
	public void load(FMLInitializationEvent evt) {
		LanguageRegistry.instance().addStringLocalization(
				"tile.MannequinBlock.name", "en_US", "Mannequin Block");
		
		proxy.registerRendererHandler();
	}
}
