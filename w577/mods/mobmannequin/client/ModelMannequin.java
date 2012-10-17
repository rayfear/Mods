package w577.mods.mobmannequin.client;

import net.minecraft.src.Entity;
import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelBlaze;

public class ModelMannequin extends ModelBase {

	public ModelBase creature;
	private boolean set;
	
	public ModelMannequin() {
		set = false;
	}
	
	@Override
	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
		creature.render(par1Entity, par2, par3, par4, par5, par6, par7);
	}

	public void setCreature(ModelBase modelbase) {
		creature = modelbase;
		creature.isChild = false;
		set = true;
	}

	public boolean isSet() {
		return set;
	}
}
