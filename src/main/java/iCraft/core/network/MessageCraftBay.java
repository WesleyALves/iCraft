package iCraft.core.network;

import iCraft.core.entity.EntityPackingCase;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MessageCraftBay extends MessageBase<MessageCraftBay>
{
	private int itemId;
	private int meta;

	public MessageCraftBay() {}

	public MessageCraftBay(int itemId)
	{
		this(itemId, 1);
	}

	public MessageCraftBay(int itemId, int meta)
	{
		this.itemId = itemId;
		this.meta = meta;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		itemId = buf.readInt();
		meta = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(itemId);
		buf.writeInt(meta);
	}

	@Override
	public void handleClientSide(MessageCraftBay message, EntityPlayer player) {}

	@Override
	public void handleServerSide(MessageCraftBay message, EntityPlayer player)
	{
		World world = player.getEntityWorld();
		ItemStack[] stack = new ItemStack[1];
		stack[0] = new ItemStack(Item.getItemById(message.itemId), 1, message.meta);
		Entity packingCase = new EntityPackingCase(world, stack);
		packingCase.setPosition(player.posX, (player.posY + 75 <= 256 ? player.posY + 75 : 75), player.posZ);
		world.spawnEntityInWorld(packingCase);
	}
}