package iCraft.core.network;

import iCraft.core.ICraft;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class MessageConfigSync extends MessageBase<MessageConfigSync>
{
	public MessageConfigSync() {}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		ICraft.isVoiceEnabled = buf.readBoolean();
		ICraft.VOICE_PORT = buf.readInt();
		int size = buf.readInt();
		ICraft.buyableItems = new int[size];
		for (int i = 0; i < size; i++)
		{
			ICraft.buyableItems[i] = buf.readInt();
		}
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeBoolean(ICraft.isVoiceEnabled);
		buf.writeInt(ICraft.VOICE_PORT);
		buf.writeInt(ICraft.buyableItems.length);
		for (int i : ICraft.buyableItems)
		{
			buf.writeInt(i);
		}
	}

	@Override
	public void handleClientSide(MessageConfigSync message, EntityPlayer player)
	{
		ICraft.proxy.onConfigSync();
	}

	@Override
	public void handleServerSide(MessageConfigSync message, EntityPlayer player) {}
}