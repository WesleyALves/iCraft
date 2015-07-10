package iCraft.core.network;

import java.util.Arrays;
import java.util.List;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import iCraft.core.ICraft;
import iCraft.core.item.ItemiCraft;
import io.netty.buffer.ByteBuf;

public class MessageIncomeCalling extends MessageBase<MessageIncomeCalling>
{
	private int number;
	private int calledNumber;

	public MessageIncomeCalling() {}

	public MessageIncomeCalling(int number, int calledNumber)
	{
		this.number = number;
		this.calledNumber = calledNumber;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		number = buf.readInt();
		calledNumber = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(number);
		buf.writeInt(calledNumber);
	}

	@Override
	public void handleClientSide(MessageIncomeCalling message, EntityPlayer player)
	{
		player.closeScreen();
		player.openGui(ICraft.instance, 6, player.worldObj, 0, 0, 0);
	}

	@Override
	public void handleServerSide(MessageIncomeCalling message, EntityPlayer player)
	{
		List<EntityPlayerMP> playersOnline = FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().playerEntityList;

		search:
		for (EntityPlayerMP players : playersOnline)
		{
			if (players != player)
			{
				List<ItemStack> itemStacks = Arrays.asList(players.inventory.mainInventory);
				for (ItemStack itemStack : itemStacks)
				{
					if (itemStack != null && itemStack.getItem() instanceof ItemiCraft && itemStack.stackTagCompound.hasKey("number") && itemStack.stackTagCompound.getInteger("number") == message.calledNumber)
					{
						if (!itemStack.stackTagCompound.hasKey("called") || itemStack.stackTagCompound.getInteger("called") == 0)
						{
							itemStack.stackTagCompound.setInteger("called", 1);
							itemStack.stackTagCompound.setInteger("callingNumber", message.number);
							itemStack.stackTagCompound.setString("callingPlayer", player.getCommandSenderName());
							itemStack.stackTagCompound.setBoolean("isCalling", false);

							List<ItemStack> stacks = Arrays.asList(player.inventory.mainInventory);
							for (ItemStack stack : stacks)
							{
								if (stack != null && stack.getItem() instanceof ItemiCraft && stack.stackTagCompound.hasKey("number") && stack.stackTagCompound.getInteger("number") == message.number)
								{
									stack.stackTagCompound.setInteger("called", 1);
									stack.stackTagCompound.setInteger("calledNumber", itemStack.stackTagCompound.getInteger("number"));
									stack.stackTagCompound.setString("calledPlayer", players.getCommandSenderName());
									stack.stackTagCompound.setBoolean("isCalling", true);
									NetworkHandler.sendTo(this, (EntityPlayerMP) player);
									break search;
								}
							}
						}
					}
				}
			}
		}
	}
}