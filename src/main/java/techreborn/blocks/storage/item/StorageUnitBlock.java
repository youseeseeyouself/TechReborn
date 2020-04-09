/*
 * This file is part of TechReborn, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2020 TechReborn
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package techreborn.blocks.storage.item;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import reborncore.api.blockentity.IMachineGuiHandler;
import reborncore.common.blocks.BlockMachineBase;
import techreborn.blockentity.storage.item.StorageUnitBaseBlockEntity;
import techreborn.client.GuiType;
import techreborn.init.TRContent;

public class StorageUnitBlock extends BlockMachineBase {

	public final TRContent.StorageUnit unitType;

	public StorageUnitBlock(TRContent.StorageUnit unitType) {
		super();
		this.unitType = unitType;
	}

	@Override
	public BlockEntity createBlockEntity(BlockView worldIn) {
		return new StorageUnitBaseBlockEntity(unitType);
	}

	@Override
	public ActionResult onUse(BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn, Hand hand, BlockHitResult hitResult) {
		if (unitType == TRContent.StorageUnit.CREATIVE) {
			return super.onUse(state, worldIn, pos, playerIn, hand, hitResult);
		}

		final StorageUnitBaseBlockEntity storageEntity = (StorageUnitBaseBlockEntity) worldIn.getBlockEntity(pos);
		ItemStack stackInHand = playerIn.getStackInHand(Hand.MAIN_HAND);

		if (storageEntity != null && storageEntity.isSameType(stackInHand)) {

			// Add item which is the same type (in users inventory) into storage
			for (int i = 0; i < playerIn.inventory.size() && !storageEntity.isFull(); i++) {
				ItemStack curStack = playerIn.inventory.getStack(i);
				if (storageEntity.isSameType(curStack)) {
					playerIn.inventory.setStack(i, storageEntity.processInput(curStack));
				}
			}

			return ActionResult.SUCCESS;
		}
		return super.onUse(state, worldIn, pos, playerIn, hand, hitResult);
	}

	@Override
	public IMachineGuiHandler getGui() {
		return GuiType.STORAGE_UNIT;
	}
}
