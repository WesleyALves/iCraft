package iCraft.core.tile;

import java.util.HashSet;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public abstract class TileBlock extends TileBase
{
	/** The direction this block is facing. */
	public int facing;

	public int clientFacing;

	public HashSet<EntityPlayer> openedThisTick = new HashSet<EntityPlayer>();

	/** The players currently using this block. */
	public HashSet<EntityPlayer> playersUsing = new HashSet<EntityPlayer>();
	
	public void updateEntity()
	{
		if(!worldObj.isRemote)
		{
			openedThisTick.clear();
		}
	}

	public void open(EntityPlayer player)
	{
		playersUsing.add(player);
	}

	public void close(EntityPlayer player)
	{
		playersUsing.remove(player);
	}

	public void writeToPacket(ByteBuf buf)
	{	
		buf.writeInt(facing);
	}

	public void readFromPacket(ByteBuf buf)
	{
		facing = buf.readInt();
		if(clientFacing != facing)
		{
			worldObj.markBlockRangeForRenderUpdate(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
			worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord,
			worldObj.getBlock(xCoord, yCoord, zCoord));
			clientFacing = facing;
		}
	}
	
	@Override
	public void validate()
	{
		super.validate();
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtTags)
	{
		super.readFromNBT(nbtTags);

		facing = nbtTags.getInteger("facing");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbtTags)
	{
		super.writeToNBT(nbtTags);

		nbtTags.setInteger("facing", facing);
	}

	public boolean canSetFacing(int facing)
	{
		return facing != 0 && facing != 1;
	}

	public void setFacing(short direction)
	{
		if (canSetFacing(direction))
			facing = direction;

		if(facing != clientFacing || !worldObj.isRemote)
		{
			worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, worldObj.getBlock(xCoord, yCoord, zCoord));
			clientFacing = facing;
		}
	}
}