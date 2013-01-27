package w577.mods.utilitychest;

import net.minecraft.inventory.InventoryBasic;

public class InventoryChestNetwork extends InventoryBasic {
	
	String network;
	String name;

	/*public InventoryChestNetwork(String network) {
		super("Networked Chest", 27);
		this.network = network;
	}*/
	
	public InventoryChestNetwork(String network, String name) {
		super("Networked Chest", 27);
		this.network = network;
		this.name = name;
	}
	
	@Override
	public String getInvName() {
		StringBuilder net = new StringBuilder();
		net.append("\"");
		if (name.indexOf('+') != -1) {
			net.append(name.substring(0, name.indexOf('+')));
			net.append("\"*");
		} else {
			net.append(name);
			net.append("\"");
		}

		return "Networked Chest: " + net.toString();
	}
	
	public void setName(String name) {
		this.name = name;
	}

}
