package w577.mods.utilitychest;

import java.util.EnumSet;

import net.minecraft.world.World;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.relauncher.Side;

public class UtilityTickHandler implements ITickHandler {
	
	private Side side;

	public UtilityTickHandler(Side side) {
		this.side = side;
	}

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		//System.out.println("Side: " + FMLCommonHandler.instance().getEffectiveSide());
		//System.out.println("Start: " + type + " - " + tickData.length);

		if (type.equals(EnumSet.of(TickType.WORLDLOAD))) {
			UtilityChest.getCNH().load((World)tickData[0]);
		}
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		//System.out.println("End: " + type + " - " + tickData);
		if (type.equals(EnumSet.of(TickType.SERVER))) {
			//System.out.println("Ticking");
			UtilityChest.getCNH().tick();
		}
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.WORLDLOAD, TickType.SERVER);
	}

	@Override
	public String getLabel() {
		return "UtilityTickHandler";
	}

}
