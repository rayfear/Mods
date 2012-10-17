package w577.mods.utilitychest;

//public class TileEntityChestOres extends TileEntityChestAdvancedUtility {
public class TileEntityChestOres extends TileEntityChestUtility {
	
	public int nextTick = 0;

	@Override
	public String getInvName() {
		return "Ores Chest";
	}
	
	//@Override
	//public int getGuiXPos() {
	//	return 2;
	//}
	
	public int getTimeUntilNextTick() {
		return nextTick;
	}

}
