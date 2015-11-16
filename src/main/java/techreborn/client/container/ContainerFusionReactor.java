package techreborn.client.container;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import reborncore.client.gui.SlotOutput;
import reborncore.common.container.RebornContainer;
import techreborn.tiles.fusionReactor.TileEntityFusionController;


public class ContainerFusionReactor extends RebornContainer {

    public int coilStatus;
    public int energy;

    TileEntityFusionController fusionController;

    public ContainerFusionReactor(TileEntityFusionController tileEntityFusionController,
                                  EntityPlayer player) {
        super();
        this.fusionController = tileEntityFusionController;

        addSlotToContainer(new Slot(tileEntityFusionController, 0, 88, 17));
        addSlotToContainer(new Slot(tileEntityFusionController, 1, 88, 53));
        addSlotToContainer(new SlotOutput(tileEntityFusionController, 2, 148, 35));

        int i;

        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(player.inventory, j + i * 9
                        + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18,
                    142));
        }

    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (int i = 0; i < this.crafters.size(); i++) {
            ICrafting icrafting = (ICrafting) this.crafters.get(i);
            if (this.coilStatus != fusionController.coilStatus) {
                icrafting.sendProgressBarUpdate(this, 0, fusionController.coilStatus);
            }
            if (this.energy != (int) fusionController.getEnergy()) {
                icrafting.sendProgressBarUpdate(this, 1, (int) fusionController.getEnergy());
            }
        }
    }

    @Override
    public void addCraftingToCrafters(ICrafting crafting) {
        super.addCraftingToCrafters(crafting);
        crafting.sendProgressBarUpdate(this, 0, fusionController.coilStatus);
        crafting.sendProgressBarUpdate(this, 1, (int) fusionController.getEnergy());
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int id, int value) {
        if (id == 0) {
            this.coilStatus = value;
        }  else if (id == 1) {
            this.energy = value;
        }
    }
}
