package w577.mods.utilitychest;

import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class TileEntityChestDeenchanter extends TileEntityChestSmelter {
	
	@Override
	public String getInvName() {
		return "tile.DeenchanterChest.name";
	}

	@Override
	public int getGuiXPos() {
		return 3;
	}
	
	@Override
	public boolean burnFromTop() {
		return false;
	}
	
	@Override
	public int getScaledPercentBurned(int scaler) {
		return cookTime*scaler/timeToSmelt;
	}
	
	@Override
	public ItemStack getSmeltingResult(ItemStack is) {
		if (is == null) {
			return null;
		}
		if (is.hasTagCompound() && is.getTagCompound().hasKey("ench")) {
			
			NBTTagList tags = is.getTagCompound().getTagList("ench");
			int totallevels = 0;
			Random rand = new Random();
			for (int i = 0; i < tags.tagCount(); i++) {
				NBTTagCompound curTag = (NBTTagCompound) tags.tagAt(i);
				int curLvl = curTag.getShort("lvl");
				totallevels += (rand.nextInt(3)+1)*(curLvl-1) + 1;
			}
			System.out.println(totallevels);
		}
		return null;
	}
	
	@Override
	public boolean doesCook() {
		return false;
	}

}
