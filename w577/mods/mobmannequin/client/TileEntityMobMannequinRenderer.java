package w577.mods.mobmannequin.client;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import w577.mods.mobmannequin.MobMannequin;
import w577.mods.mobmannequin.TileEntityMobMannequin;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityBlaze;
import net.minecraft.src.EntityCreeper;
import net.minecraft.src.EntityList;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ModelBase;
import net.minecraft.src.OpenGlHelper;
import net.minecraft.src.RenderLiving;
import net.minecraft.src.RenderManager;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntitySpecialRenderer;
import net.minecraft.src.World;

public class TileEntityMobMannequinRenderer extends TileEntitySpecialRenderer {
	
	private ModelMannequin mm = new ModelMannequin();
	
	private static Map<List<Integer>, Entity> entityList = new HashMap<List<Integer>, Entity>();
	
	private Entity getEntityFromList(List<Integer> i) {
		return entityList.get(i);
	}
	
	private void putEntityToList(List<Integer> i, Entity e) {
		entityList.put(i, e);
	}
	
	public static void deleteEntityInPosition(int i, int j, int k) {
		entityList.put(Arrays.asList(i, j, k), null);
	}
	
	public void renderTileEntityMobMannequinAt(TileEntityMobMannequin te, double x, double y,
			double z, float f) {
		if (MobMannequin.proxy.getClientWorld() == null || MobMannequin.disallowedMobIds.contains(te.mobid)) {
			return;
		}
		if (getEntityFromList(Arrays.asList(te.xCoord, te.yCoord, te.zCoord)) == null) {
			Entity entTemp = EntityList.createEntityByID(te.mobid, MobMannequin.proxy.getClientWorld());
			putEntityToList(Arrays.asList(te.xCoord, te.yCoord, te.zCoord), entTemp);
		}
		
		Entity ent = getEntityFromList(Arrays.asList(te.xCoord, te.yCoord, te.zCoord));
		if (ent == null) {
			return;
		}
		
		ent.setPositionAndRotation(te.xCoord+0.5D, te.yCoord+0.125D, te.zCoord+0.5D, 0.0F, 0.0F);
		System.out.println(ent.getLookVec());
		
		//MobMannequin.proxy.getServer().worldServerForDimension(MobMannequin.proxy.getClientWorld().getWorldInfo().getDimension());
		
		//.getEntityByID(ent.entityId).setPositionAndRotation(te.xCoord+0.5D, te.yCoord+0.125D, te.zCoord+0.5D, 0.0F, 0.0F);
		
		//System.out.println(ent.rotationYaw);
		
		//RenderManager.instance.renderEntityWithPosYaw(ent, x, y, z, 0.5F, 0.0F);
		//GL11.glRotatef(90, 0.0F, 1.0F, 0.0F);
		RenderManager.instance.renderEntity(ent, 1.0F);
		//GL11.glRotatef(0, 0, 1, 0);
		
	}
	
	
	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y,
			double z, float var8) {
		renderTileEntityMobMannequinAt((TileEntityMobMannequin) te, x, y, z, var8);
	}

}
