package iCraft.core.block;

import iCraft.core.tile.TilePackingCase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockPackingCase extends BlockContainer
{
	public BlockPackingCase()
	{
		super(Material.wood);
		setStepSound(Block.soundTypeWood);
		setCreativeTab(CreativeTabs.tabMisc);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityliving, ItemStack itemstack)
	{
		TilePackingCase te = (TilePackingCase)world.getTileEntity(x, y, z);
		int side = MathHelper.floor_double((entityliving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		int height = Math.round(entityliving.rotationPitch);
		int change = 3;

		if(te == null)
		{
			return;
		}

		if(te.canSetFacing(0) && te.canSetFacing(1))
		{
			if(height >= 65)
			{
				change = 1;
			}
			else if(height <= -65)
			{
				change = 0;
			}
		}

		if(change != 0 && change != 1)
		{
			switch(side)
			{
				case 0: change = 2; break;
				case 1: change = 5; break;
				case 2: change = 3; break;
				case 3: change = 4; break;
			}
		}

		te.setFacing((short)change);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int facing, float hitX, float hitY, float hiZ)
	{
		if (!world.isRemote && !entityPlayer.isSneaking())
		{
			TilePackingCase te = (TilePackingCase)world.getTileEntity(x, y, z);
			if (te != null && te.inventory[0] != null)
			{
				world.playSoundEffect(x + 0.5F, y + 0.5D, z + 0.5F, "random.chestopen", 0.5F, (world.rand.nextFloat()*0.1F) + 0.9F);

				float motion = 0.7F;
	            double motionX = (double)(world.rand.nextFloat() * motion) + (double)(1.0F - motion) * 0.5D;
	            double motionY = (double)(world.rand.nextFloat() * motion) + (double)(1.0F - motion) * 0.5D;
	            double motionZ = (double)(world.rand.nextFloat() * motion) + (double)(1.0F - motion) * 0.5D;
				EntityItem e = new EntityItem(world, x + motionX, y + motionY, z + motionZ, te.inventory[0]);
				world.setBlockToAir(x, y, z);
				world.spawnEntityInWorld(e);
			}
		}
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TilePackingCase();
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public int getRenderType()
	{
		return -1;
	}
}