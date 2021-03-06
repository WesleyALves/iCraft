package iCraft.client;

import java.io.File;

import com.google.common.io.Files;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import iCraft.client.gui.GuiPizzaDelivery;
import iCraft.client.gui.GuiiCraft;
import iCraft.client.gui.GuiiCraftBlacklist;
import iCraft.client.gui.GuiiCraftCalc;
import iCraft.client.gui.GuiiCraftClock;
import iCraft.client.gui.GuiiCraftDelivery;
import iCraft.client.gui.GuiiCraftInCall;
import iCraft.client.gui.GuiiCraftIncomingCall;
import iCraft.client.gui.GuiiCraftMP3Player;
import iCraft.client.gui.GuiiCraftNumPad;
import iCraft.client.gui.GuiiCraftSettings;
import iCraft.client.gui.GuiiCraftShopping;
import iCraft.client.mp3.MP3Player;
import iCraft.client.render.ItemRenderHandler;
import iCraft.client.render.RenderFallingCase;
import iCraft.client.render.RenderPackingCase;
import iCraft.client.render.RenderPizzaDelivery;
import iCraft.core.CommonProxy;
import iCraft.core.ICraft;
import iCraft.core.entity.EntityPackingCase;
import iCraft.core.entity.EntityPizzaDelivery;
import iCraft.core.tile.TilePackingCase;
import iCraft.core.utils.ICraftClientUtils;

public class ClientProxy extends CommonProxy
{
	@Override
	public void loadConfiguration()
	{
		super.loadConfiguration();
		ICraftClientUtils.homePage = ICraft.configuration.get("general", "Web Home Page", "mod://mcef/home.html").getString();
		if(ICraft.configuration.hasChanged())
			ICraft.configuration.save();
	}

	@Override
	public void onConfigSync()
	{
		super.onConfigSync();

		if (ICraft.isVoiceEnabled && ICraft.voiceClient != null)
		{
			ICraft.voiceClient.start();
		}
		if (ICraft.mp3Folder.listFiles().length != 0)
		{
			for (File file : ICraft.mp3Folder.listFiles())
			{
				if (!file.isDirectory() && Files.getFileExtension(file.getAbsolutePath()).equals("mp3"))
				{
					ICraft.musics.add(file);
					String patchedName = file.getName().replaceAll(".mp3", "");
					ICraft.musicNames.add(patchedName);
				}
			}
		}
		ICraft.mp3Player = new MP3Player();
	}

	@Override
	public EntityPlayer getClientPlayer()
	{
		return Minecraft.getMinecraft().thePlayer;
	}

	@Override
	public void registerUtilities()
	{
		FMLCommonHandler.instance().bus().register(new ICraftClientEventHandler());
		MinecraftForge.EVENT_BUS.register(new ICraftClientEventHandler());
		FMLCommonHandler.instance().bus().register(new ClientTickHandler());
		new ICraftKeyHandler();
	}

	@Override
	public void registerNetHandler()
	{
		new InternetHandler();
	}

	@Override
	public void registerRenders()
	{
		// Blocks/Items
		ItemRenderHandler handler = new ItemRenderHandler();
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ICraft.CaseBlock), handler);
		// Entities
		RenderingRegistry.registerEntityRenderingHandler(EntityPackingCase.class, new RenderFallingCase());
		RenderingRegistry.registerEntityRenderingHandler(EntityPizzaDelivery.class, new RenderPizzaDelivery());

		ICraft.logger.info("Render registrations complete.");
	}

	@Override
	public void registerTiles()
	{
		ClientRegistry.registerTileEntity(TilePackingCase.class, "PackingCase", new RenderPackingCase());
	}

	@Override
	public GuiScreen getClientGui(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		switch (ID)
		{
			case 0:
				return new GuiiCraft("GuiiCraft");
			case 1:
				return new GuiiCraftCalc("GuiiCraftCalc");
			case 2://GPS
				break;
			case 3:
				return new GuiiCraftClock("GuiiCraftClock");
			case 4:
				return new GuiiCraftSettings("GuiiCraftSettings");
			case 5:
				return new GuiiCraftNumPad("GuiiCraftNumPad");
			case 6:
				return new GuiiCraftIncomingCall("whatever");
			case 7:
				return new GuiiCraftInCall("GuiiCraftInCall");
			case 8://SMS
				break;
			case 9:
				return new GuiiCraftShopping("GuiiCraftShopping");
			case 10:
				return new GuiiCraftMP3Player("GuiiCraftMP3Player");
			case 11:
				EntityPizzaDelivery pizza = (EntityPizzaDelivery)world.getEntityByID(x);
				if (pizza != null)
					return new GuiPizzaDelivery(player.inventory, pizza);
			case 12:
				return new GuiiCraftDelivery("GuiiCraftDelivery");
			case 13:
				return new GuiiCraftBlacklist("GuiiCraftClock");
		}
		return null;
	}

	@Override
	public File getMinecraftDir()
	{
		return Minecraft.getMinecraft().mcDataDir;
	}

	@Override
	public void stopPhoneRingSound()
	{
		Minecraft.getMinecraft().getSoundHandler().stopSound(ClientTickHandler.phoneRing);
	}
}