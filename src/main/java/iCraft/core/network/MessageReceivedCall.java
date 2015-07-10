package iCraft.core.network;

import cpw.mods.fml.common.FMLCommonHandler;
import iCraft.core.ICraft;
import iCraft.core.utils.ICraftUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class MessageReceivedCall extends MessageBase<MessageReceivedCall>
{
	private int status;
	private boolean isCalling;

	public MessageReceivedCall() {}

	public MessageReceivedCall(int status, boolean isCalling)
	{
		this.status = status;
		this.isCalling = isCalling;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		status = buf.readInt();
		isCalling = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(status);
		buf.writeBoolean(isCalling);
	}

	@Override
	public void handleClientSide(MessageReceivedCall message, EntityPlayer player)
	{
		player.closeScreen();
		player.openGui(ICraft.instance, status, player.worldObj, 0, 0, 0);
	}

	@Override
	public void handleServerSide(MessageReceivedCall message, EntityPlayer player)
	{
		ItemStack itemStack = player.getCurrentEquippedItem();
		switch (message.status)
		{
			case 0:
				updateGui(itemStack, message.isCalling);
				ICraftUtils.changeCalledStatus(itemStack, 0, 0, message.isCalling);
				break;
			case 7:
				ICraftUtils.changeCalledStatus(itemStack, 2, 2, message.isCalling);
				updateGui(itemStack, message.isCalling);
				break;
			default:
				ICraft.logger.error("Fatal Error while handling Received Call Packet at " + player.posX + ";" + player.posY + ";" + player.posZ + "\n" + "Culpa do Ratao");
				break;
		}
	}

	private void updateGui(ItemStack itemStack, boolean isCalling)
	{
		NetworkHandler.sendTo(this, FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().func_152612_a(isCalling ? itemStack.stackTagCompound.getString("calledPlayer") : itemStack.stackTagCompound.getString("callingPlayer")));
	}
}