package w577.mods.mobmannequin;

import java.util.Iterator;
import java.util.List;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityEggInfo;
import net.minecraft.src.EntityList;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Facing;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.StatCollector;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class ItemMannequinPlacer extends Item {

	public ItemMannequinPlacer(int par1)
    {
        super(par1);
        this.setHasSubtypes(true);
        this.setTabToDisplayOn(CreativeTabs.tabDeco);
        this.setItemName("Mannequin");
        this.setIconCoord(0, 0);
        this.setTextureFile("/mannequinModImages/items.png");
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public String getItemDisplayName(ItemStack par1ItemStack)
    {
        String var2 = "Mannequin";
        String var3 = EntityList.getStringFromID(par1ItemStack.getItemDamage());

        if (var3 != null)
        {
            var2 = StatCollector.translateToLocal("entity." + var3 + ".name") + " " + var2;
        }

        return var2;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public int getColorFromDamage(int par1, int par2)
    {
        EntityEggInfo var3 = (EntityEggInfo)EntityList.entityEggs.get(Integer.valueOf(par1));
        return var3 != null ? (par2 == 0 ? var3.primaryColor : var3.secondaryColor) : 16777215;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public int getIconFromDamageForRenderPass(int par1, int par2)
    {
        return par2 > 0 ? super.getIconFromDamageForRenderPass(par1, par2) + 1 : super.getIconFromDamageForRenderPass(par1, par2);
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        Iterator var4 = EntityList.entityEggs.values().iterator();

        while (var4.hasNext())
        {
            EntityEggInfo var5 = (EntityEggInfo)var4.next();
            par3List.add(new ItemStack(par1, 1, var5.spawnedID));
        }
    }
	
	@Override
	public boolean tryPlaceIntoWorld(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (par3World.isRemote)
        {
            return true;
        }
        else
        {
            int var11 = par3World.getBlockId(par4, par5, par6);
            par4 += Facing.offsetsXForSide[par7];
            par5 += Facing.offsetsYForSide[par7];
            par6 += Facing.offsetsZForSide[par7];

            if (placeMannequin(par3World, par1ItemStack.getItemDamage(), par4, par5, par6)) {
            	if (!par2EntityPlayer.capabilities.isCreativeMode) {
            		--par1ItemStack.stackSize;
            	}
            	Block var12 = MobMannequin.mobManneBlock;
            	par3World.playSoundEffect((double)((float)par4 + 0.5F), (double)((float)par5 + 0.5F), (double)((float)par6 + 0.5F), var12.stepSound.getStepSound(), (var12.stepSound.getVolume() + 1.0F) / 2.0F, var12.stepSound.getPitch() * 0.8F);
            }

            return true;
        }
    }

	private boolean placeMannequin(World world, int itemDamage, int x,
			int y, int z) {
		if (world.getBlockId(x, y, z) == 0) {
			if (MobMannequin.mobManneBlock.canPlaceBlockAt(world, x, y, z)) {
				world.setBlockWithNotify(x, y, z, MobMannequin.mobManneBlock.blockID);
				MobMannequin.proxy.deleteEntityAt(x, y, z);
				TileEntity te = world.getBlockTileEntity(x, y, z);
				if (te instanceof TileEntityMobMannequin) {
					TileEntityMobMannequin temm = (TileEntityMobMannequin) te;
					temm.mobid = itemDamage;
					Minecraft.getMinecraft().getSendQueue().getNetManager().addToSendQueue(temm.getAuxillaryInfoPacket());
					world.markBlockNeedsUpdate(x,y,z);
				}
				return true;
			}
		}
		return false;
	}
}
