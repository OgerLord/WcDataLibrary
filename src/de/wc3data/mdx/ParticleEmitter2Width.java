package de.wc3data.mdx;

import de.wc3data.stream.StreamUtils;
import java.io.IOException;
import java.util.*;

public class ParticleEmitter2Width {
	public int interpolationType;
	public int globalSequenceId;
	public ScalingTrack[] scalingTrack = new ScalingTrack[0];

	public static final String key = "KP2W";

	public void load(BlizzardDataInputStream in) throws IOException {
		StreamUtils.checkId(in, "KP2W");
		int nrOfTracks = in.readInt();
		interpolationType = in.readInt();
		globalSequenceId = in.readInt();
		scalingTrack = new ScalingTrack[nrOfTracks];
		for (int i = 0; i < nrOfTracks; i++) {
			scalingTrack[i] = new ScalingTrack();
			scalingTrack[i].load(in);
		}
	}

	public void save(BlizzardDataOutputStream out) throws IOException {
		int nrOfTracks = scalingTrack.length;
		out.writeNByteString("KP2W", 4);
		out.writeInt(nrOfTracks);
		out.writeInt(interpolationType);
		out.writeInt(globalSequenceId);
		for (int i = 0; i < scalingTrack.length; i++) {
			scalingTrack[i].save(out);
		}

	}

	public int getSize() {
		int a = 0;
		a += 4;
		a += 4;
		a += 4;
		a += 4;
		for (int i = 0; i < scalingTrack.length; i++) {
			a += scalingTrack[i].getSize();
		}

		return a;
	}

	public class ScalingTrack {
		public int time;
		public float width;
		public float inTan;
		public float outTan;

		public void load(BlizzardDataInputStream in) throws IOException {
			time = in.readInt();
			width = in.readFloat();
			if (interpolationType > 1) {
				inTan = in.readFloat();
				outTan = in.readFloat();
			}
		}

		public void save(BlizzardDataOutputStream out) throws IOException {
			out.writeInt(time);
			out.writeFloat(width);
			if (interpolationType > 1) {
				out.writeFloat(inTan);
				out.writeFloat(outTan);
			}

		}

		public int getSize() {
			int a = 0;
			a += 4;
			a += 4;
			if (interpolationType > 1) {
				a += 4;
				a += 4;
			}

			return a;
		}
	}
}
