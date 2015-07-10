package iCraft.core.network;

import java.util.Arrays;

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
		for (int i : Arrays.asList(buf.readInt()))
		{
			int index = 0;
			ICraft.buyableItems[index] = buf.readInt();
			index++;
		}
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeBoolean(ICraft.isVoiceEnabled);
		buf.writeInt(ICraft.VOICE_PORT);
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