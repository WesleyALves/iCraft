package iCraft.core;

import java.io.File;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.FMLInjectionData;
import iCraft.core.entity.EntityPizzaDelivery;
import iCraft.core.inventory.container.ContainerPizzaDelivery;
import iCraft.core.tile.TilePackingCase;
import iCraft.core.utils.ICraftUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;

public class CommonProxy
{
	public void loadConfiguration()
	{
		ICraft.isVoiceEnabled = ICraft.configuration.get("Voice Settings", "IsVoiceEnabled", true).getBoolean(true);
		ICraft.VOICE_PORT = ICraft.configuration.get("Voice Settings", "VoicePort", 2550, null, 1, 65535).getInt();
		ICraft.buyableItems = ICraft.configuration.get(Configuration.CATEGORY_GENERAL, "Buyable Items", new int[] { Item.getIdFromItem(Items.iron_ingot), Item.getIdFromItem(Items.gold_ingot), Item.getIdFromItem(Items.diamond) }, "Put a list of block/item IDs to be buyable. Separate by commas, no space.").getIntList();

		for (int Id : ICraft.buyableItems)
		{
			ICraftUtils.addBuyableItems(new ItemStack(Item.getItemById(Id), 1, 0));
		}
		if (ICraft.configuration.hasChanged())
			ICraft.configuration.save();
	}

	public void onConfigSync()
	{
		ICraft.logger.info("Received config from server.");
	}

	public EntityPlayer getClientPlayer()
	{
		return null;
	}

	public void registerUtilities() {}

	public void registerNetHandler() {}

	public void registerRenders() {}

	public void registerTiles()
	{
		GameRegistry.registerTileEntity(TilePackingCase.class, "PackingCase");
	}

	/**
	 * Get the actual interface for a GUI. Client-only.
	 * 
	 * @param ID - gui ID
	 * @param player - player that opened the GUI
	 * @param world - world the GUI was opened in
	 * @param x - gui's x position
	 * @param y - gui's y position
	 * @param z - gui's z position
	 * @return the GuiScreen of the GUI
	 */
	public Object getClientGui(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		return null;
	}

	/**
	 * Get the container for a GUI. Common.
	 * 
	 * @param ID - gui ID
	 * @param player - player that opened the GUI
	 * @param world - world the GUI was opened in
	 * @param x - gui's x position
	 * @param y - gui's y position
	 * @param z - gui's z position
	 * @return the Container of the GUI
	 */
	public Container getServerGui(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		switch (ID)
		{
			case 11:
				EntityPizzaDelivery pizza = (EntityPizzaDelivery)world.getEntityByID(x);
				if (pizza != null)
					return new ContainerPizzaDelivery(player.inventory, pizza);
		}
		return null;
	}

	/**
	 * Gets the Minecraft base directory.
	 * @return base directory
	 */
	public File getMinecraftDir()
	{
		return (File)FMLInjectionData.data()[6];
	}

	public void stopPhoneRingSound() {}
}