package de.wc3data.mdx;

import de.wc3data.stream.StreamUtils;
import java.io.IOException;
import java.util.*;

public class RibbonEmitterChunk {
	public RibbonEmitter[] ribbonEmitter = new RibbonEmitter[0];

	public static final String key = "RIBB";

	public void load(BlizzardDataInputStream in) throws IOException {
		StreamUtils.checkId(in, "RIBB");
		int chunkSize = in.readInt();
		List<RibbonEmitter> ribbonEmitterList = new ArrayList();
		int ribbonEmitterCounter = chunkSize;
		while (ribbonEmitterCounter > 0) {
			RibbonEmitter tempribbonEmitter = new RibbonEmitter();
			ribbonEmitterList.add(tempribbonEmitter);
			tempribbonEmitter.load(in);
			ribbonEmitterCounter -= tempribbonEmitter.getSize();
		}
		ribbonEmitter = ribbonEmitterList
				.toArray(new RibbonEmitter[ribbonEmitterList.size()]);
	}

	public void save(BlizzardDataOutputStream out) throws IOException {
		int nrOfRibbonEmitters = ribbonEmitter.length;
		out.writeNByteString("RIBB", 4);
		out.writeInt(getSize() - 8);// ChunkSize
		for (int i = 0; i < ribbonEmitter.length; i++) {
			ribbonEmitter[i].save(out);
		}

	}

	public int getSize() {
		int a = 0;
		a += 4;
		a += 4;
		for (int i = 0; i < ribbonEmitter.length; i++) {
			a += ribbonEmitter[i].getSize();
		}

		return a;
	}

	public class RibbonEmitter {
		public Node node = new Node();
		public float heightAbove;
		public float heightBelow;
		public float alpha;
		public float[] color = new float[3];
		public float lifeSpan;
		public int unknownNull;
		public int emissionRate;
		public int rows;
		public int columns;
		public int materialId;
		public float gravity;
		public RibbonEmitterVisibility ribbonEmitterVisibility;
		public RibbonEmitterHeightAbove ribbonEmitterHeightAbove;
		public RibbonEmitterHeightBelow ribbonEmitterHeightBelow;

		public void load(BlizzardDataInputStream in) throws IOException {
			int inclusiveSize = in.readInt();
			node = new Node();
			node.load(in);
			heightAbove = in.readFloat();
			heightBelow = in.readFloat();
			alpha = in.readFloat();
			color = StreamUtils.loadFloatArray(in, 3);
			lifeSpan = in.readFloat();
			unknownNull = in.readInt();
			emissionRate = in.readInt();
			rows = in.readInt();
			columns = in.readInt();
			materialId = in.readInt();
			gravity = in.readFloat();
			for (int i = 0; i < 3; i++) {
				if (StreamUtils.checkOptionalId(in, ribbonEmitterVisibility.key)) {
					ribbonEmitterVisibility = new RibbonEmitterVisibility();
					ribbonEmitterVisibility.load(in);
				} else if (StreamUtils.checkOptionalId(in,
						ribbonEmitterHeightAbove.key)) {
					ribbonEmitterHeightAbove = new RibbonEmitterHeightAbove();
					ribbonEmitterHeightAbove.load(in);
				} else if (StreamUtils.checkOptionalId(in,
						ribbonEmitterHeightBelow.key)) {
					ribbonEmitterHeightBelow = new RibbonEmitterHeightBelow();
					ribbonEmitterHeightBelow.load(in);
				}

			}
		}

		public void save(BlizzardDataOutputStream out) throws IOException {
			out.writeInt(getSize());// InclusiveSize
			node.save(out);
			out.writeFloat(heightAbove);
			out.writeFloat(heightBelow);
			out.writeFloat(alpha);
			if (color.length % 3 != 0) {
				throw new IllegalArgumentException(
						"The array color needs either the length 3 or a multiple of this number. (got "
								+ color.length + ")");
			}
			StreamUtils.saveFloatArray(out, color);
			out.writeFloat(lifeSpan);
			out.writeInt(unknownNull);
			out.writeInt(emissionRate);
			out.writeInt(rows);
			out.writeInt(columns);
			out.writeInt(materialId);
			out.writeFloat(gravity);
			if (ribbonEmitterVisibility != null) {
				ribbonEmitterVisibility.save(out);
			}
			if (ribbonEmitterHeightAbove != null) {
				ribbonEmitterHeightAbove.save(out);
			}
			if (ribbonEmitterHeightBelow != null) {
				ribbonEmitterHeightBelow.save(out);
			}

		}

		public int getSize() {
			int a = 0;
			a += 4;
			a += node.getSize();
			a += 4;
			a += 4;
			a += 4;
			a += 12;
			a += 4;
			a += 4;
			a += 4;
			a += 4;
			a += 4;
			a += 4;
			a += 4;
			if (ribbonEmitterVisibility != null) {
				a += ribbonEmitterVisibility.getSize();
			}
			if (ribbonEmitterHeightAbove != null) {
				a += ribbonEmitterHeightAbove.getSize();
			}
			if (ribbonEmitterHeightBelow != null) {
				a += ribbonEmitterHeightBelow.getSize();
			}

			return a;
		}
	}
}
