package iCraft.core;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CreativeTabiCraft extends CreativeTabs
{
	public CreativeTabiCraft()
	{
		super("tabiCraft");
	}

	@Override
	public ItemStack getIconItemStack()
	{
		return new ItemStack(ICraft.iCraft);
	}

	@Override
	public Item getTabIconItem()
	{
		return ICraft.iCraft;
	}
}