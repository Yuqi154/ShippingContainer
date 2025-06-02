package org.hiedacamellia.shippingcontainer.core.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemExchangeUtil {

    public record Data(
            Item want,int wantCount,
            Item give,int giveCount
    ) {
        public DataRaw toRaw() {
            return new DataRaw(
                    SCItemUtil.toString(want), wantCount,
                    SCItemUtil.toString(give), giveCount
            );
        }

        public Data(ItemStack want, int wantCount, ItemStack give, int giveCount) {
            this(want.getItem(), wantCount, give.getItem(), giveCount);
        }

    }

    public record DataRaw(
            String want,int wantCount,
            String give,int giveCount
    ) {
        public Data build() {
            return new Data(
                    SCItemUtil.fromString(want), wantCount,
                    SCItemUtil.fromString(give), giveCount
            );
        }

        public static final StreamCodec<ByteBuf, DataRaw> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.STRING_UTF8, DataRaw::want,
                ByteBufCodecs.INT, DataRaw::wantCount,
                ByteBufCodecs.STRING_UTF8, DataRaw::give,
                ByteBufCodecs.INT, DataRaw::giveCount,
                DataRaw::new
        );

        public static final Codec<DataRaw> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        Codec.STRING.fieldOf("want").forGetter(DataRaw::want),
                        Codec.INT.fieldOf("wantCount").forGetter(DataRaw::wantCount),
                        Codec.STRING.fieldOf("give").forGetter(DataRaw::give),
                        Codec.INT.fieldOf("giveCount").forGetter(DataRaw::giveCount)
                ).apply(instance, DataRaw::new)
        );

    }


}
