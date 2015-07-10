package iCraft.core.entity;

import iCraft.core.ICraft;
import iCraft.core.tile.TilePackingCase;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityPackingCase extends Entity
{
	public ItemStack[] storedItems;
	private boolean hasLanded;

	public EntityPackingCase(World world, ItemStack[] storedItems)
	{
		this(world);
		this.storedItems = storedItems;
		hasLanded = false;
	}

	public EntityPackingCase(World world)
	{
		super(world);
	}

	@Override
	protected void entityInit() {}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbtTags)
	{
		final NBTTagList var2 = nbtTags.getTagList("Items", 10);
		storedItems = new ItemStack[1];

		for (int var3 = 0; var3 < var2.tagCount(); ++var3)
		{
			final NBTTagCompound var4 = var2.getCompoundTagAt(var3);
			final int var5 = var4.getByte("Slot") & 255;

			if (var5 < storedItems.length)
			{
				storedItems[var5] = ItemStack.loadItemStackFromNBT(var4);
			}
		}

		hasLanded = nbtTags.getBoolean("hasLanded");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbtTags)
	{
		final NBTTagList var2 = new NBTTagList();

        for (int var3 = 0; var3 < storedItems.length; ++var3)
        {
            if (storedItems[var3] != null)
            {
                final NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte) var3);
                storedItems[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        }

        nbtTags.setTag("Items", var2);
        nbtTags.setBoolean("hasLanded", hasLanded);
	}

	@Override
    public void onUpdate()
    {
		if (!hasLanded)
        {
            if (onGround && !worldObj.isRemote)
            {
                for (int i = 0; i < 100; i++)
                {
                    final int x = MathHelper.floor_double(this.posX);
                    final int y = MathHelper.floor_double(this.posY);
                    final int z = MathHelper.floor_double(this.posZ);

                    Block block = worldObj.getBlock(x, y + i, z);

                    if (block.getMaterial().isReplaceable())
                    {
                        if (placeCase(x, y + i, z))
                        {
                            setDead();
                            return;
                        }
                        else if (storedItems != null)
                        {
                            for (final ItemStack stack : storedItems)
                            {
                                final EntityItem e = new EntityItem(worldObj, posX, posY, posZ, stack);
                                worldObj.spawnEntityInWorld(e);
                            }

                            return;
                        }
                    }
                }

                if (storedItems != null)
                {
                    for (final ItemStack stack : storedItems)
                    {
                        final EntityItem e = new EntityItem(worldObj, posX, posY, posZ, stack);
                        worldObj.spawnEntityInWorld(e);
                    }
                }
            }
            else
            {
                motionY = -0.25;
            }

            moveEntity(0, motionY, 0);
        }
    }

	private boolean placeCase(int x, int y, int z)
	{
		worldObj.setBlock(x, y, z, ICraft.CaseBlock, 0, 3);
		final TileEntity te = worldObj.getTileEntity(x, y, z);

		if (te instanceof TilePackingCase && storedItems != null)
		{
			final TilePackingCase packingCase = (TilePackingCase)te;

			packingCase.inventory = new ItemStack[1];
			packingCase.inventory[0] = storedItems[0];

			return true;
		}
		hasLanded = true;

		return true;
	}
}