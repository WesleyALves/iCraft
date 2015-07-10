package iCraft.client;

import iCraft.core.ICraft;
import iCraft.core.item.ItemiCraft;
import iCraft.core.utils.ICraftClientUtils;

import java.util.Arrays;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientTickHandler
{
	public static Minecraft mc = FMLClientHandler.instance().getClient();
	private boolean hasNotified = false;
	private int playTime = 140;

	@SubscribeEvent
	public void onTick(ClientTickEvent event)
	{
		if(event.phase == Phase.START)
		{
			tickStart();
		}
	}

	public void tickStart()
	{
		if(!hasNotified && mc.theWorld != null && ICraft.newestVersion != null)
		{
			ICraftClientUtils.checkUpdates(mc.thePlayer);
			hasNotified = true;
		}
		if (mc.theWorld != null)
		{
			List<ItemStack> itemStacks = Arrays.asList(mc.thePlayer.inventory.mainInventory);
			for (ItemStack itemStack : itemStacks)
			{
				if (itemStack != null && itemStack.getItem() instanceof ItemiCraft)
				{
					if (itemStack.stackTagCompound != null)
					{
						if (itemStack.stackTagCompound.hasKey("called") && itemStack.stackTagCompound.getInteger("called") == 1 && itemStack.stackTagCompound.hasKey("isCalling") && itemStack.stackTagCompound.getBoolean("isCalling") == false)
						{
							if (playTime == 140)
							{
								mc.theWorld.playSound(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, "iCraft:ringPhone", 0.5F, 1.0F, false);
							}
							playTime = (playTime - 1 >= 0 ? playTime - 1 : 140);
						}
					}
				}
			}
		}
	}
}