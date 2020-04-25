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

package techreborn.client.render.entitys;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import techreborn.blockentity.storage.item.StorageUnitBaseBlockEntity;

/**
 * Created by drcrazy on 07-Jan-20 for TechReborn-1.15.
 */
public class StorageUnitRenderer extends BlockEntityRenderer<StorageUnitBaseBlockEntity> {
	public StorageUnitRenderer(BlockEntityRenderDispatcher dispatcher) {
		super(dispatcher);
	}

	@Override
	public void render(StorageUnitBaseBlockEntity storage, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		if (storage.getWorld() == null) {
			return;
		}
		ItemStack stack = storage.getStoredStack();
		if (stack.isEmpty()) {
			return;
		}
		matrices.push();
		Direction direction = storage.getFacing();
		matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion((direction.getHorizontal() - 2) * 90F));
		matrices.scale(0.5F, 0.5F, 0.5F);
		switch (direction) {
			case NORTH:
			case WEST:
				matrices.translate(1, 1, 0);
				break;
			case SOUTH:
				matrices.translate(-1, 1, -2);
				break;
			case EAST:
				matrices.translate(-1, 1, 2);
				break;
		}
		int lightAbove = WorldRenderer.getLightmapCoordinates(storage.getWorld(), storage.getPos().offset(storage.getFacing()));
		MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformation.Mode.FIXED, lightAbove, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers);
		matrices.pop();
	}
}
