package w577.mods.utilitychest;

import net.minecraft.inventory.InventoryBasic;

public class InventoryChestNetwork extends InventoryBasic {
	
	String network;

	public InventoryChestNetwork(String network) {
		super("Networked Chest", 27);
		this.network = network;
	}
	
	@Override
	public String getInvName() {
		StringBuilder net = new StringBuilder();
		net.append("\"");
		if (network.indexOf('+') != -1) {
			net.append(network.substring(0, network.indexOf('+')));
			net.append("\"*");
		} else {
			net.append(network);
			net.append("\"");
		}
		
		System.out.println(network);

		return "Networked Chest: " + net.toString();
	}

}
