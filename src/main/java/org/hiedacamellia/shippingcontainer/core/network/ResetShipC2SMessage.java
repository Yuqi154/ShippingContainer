package org.hiedacamellia.shippingcontainer.core.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.hiedacamellia.shippingcontainer.ShippingContainer;
import org.hiedacamellia.shippingcontainer.common.blockentity.ContainerBlockEntity;

public record ResetShipC2SMessage(BlockPos pos) implements CustomPacketPayload {


    public static final Type<ResetShipC2SMessage> TYPE = new Type<>(ShippingContainer.rl( "reset_ship_c2s" ));

    public static final StreamCodec<ByteBuf, ResetShipC2SMessage> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            ResetShipC2SMessage::pos,
            ResetShipC2SMessage::new
    );

    @Override
    public Type<ResetShipC2SMessage> type() {
        return TYPE;
    }


    public static void handleServer(final ResetShipC2SMessage data, final IPayloadContext context) {
        context.enqueueWork(() -> {
            BlockPos pos1 = data.pos();
            Level level = context.player().level();
            if(level.isLoaded(pos1)){
                BlockEntity blockEntity = level.getBlockEntity(pos1);
                if(blockEntity instanceof ContainerBlockEntity entity){
                    entity.resetShipped();;
                }

            }
        });
    }
}
