package iCraft.core;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

public class BuyableItems
{
	public static final List<ItemStack> items = new ArrayList();

	public static void addItems(ItemStack itemStack)
	{
		if (itemStack != null && !items.contains(itemStack))
		{
			items.add(itemStack);
		}
	}

	public static boolean hasItemStack(ItemStack itemStack)
	{
		for (ItemStack stack : items)
		{
			if (stack.isItemEqual(itemStack))
			{
				return true;
			}
		}
		return false;
	}
}