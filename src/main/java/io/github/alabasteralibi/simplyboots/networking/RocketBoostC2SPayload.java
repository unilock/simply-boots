package io.github.alabasteralibi.simplyboots.networking;

import io.github.alabasteralibi.simplyboots.SimplyBootsHelpers;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public class RocketBoostC2SPayload implements CustomPayload {
	public static final Id<RocketBoostC2SPayload> ID = new Id<>(SimplyBootsHelpers.id("rocket_boost"));

	public static final RocketBoostC2SPayload INSTANCE = new RocketBoostC2SPayload();
	public static final PacketCodec<RegistryByteBuf, RocketBoostC2SPayload> CODEC = PacketCodec.unit(INSTANCE);

	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}
