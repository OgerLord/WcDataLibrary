package de.wc3data.mdx;

import de.wc3data.stream.StreamUtils;
import java.io.IOException;
import java.util.*;

public class TextureTranslation {
	public int interpolationType;
	public int globalSequenceId;
	public TranslationTrack[] translationTrack = new TranslationTrack[0];

	public static final String key = "KTAT";

	public void load(BlizzardDataInputStream in) throws IOException {
		StreamUtils.checkId(in, "KTAT");
		int nrOfTracks = in.readInt();
		interpolationType = in.readInt();
		globalSequenceId = in.readInt();
		translationTrack = new TranslationTrack[nrOfTracks];
		for (int i = 0; i < nrOfTracks; i++) {
			translationTrack[i] = new TranslationTrack();
			translationTrack[i].load(in);
		}
	}

	public void save(BlizzardDataOutputStream out) throws IOException {
		int nrOfTracks = translationTrack.length;
		out.writeNByteString("KTAT", 4);
		out.writeInt(nrOfTracks);
		out.writeInt(interpolationType);
		out.writeInt(globalSequenceId);
		for (int i = 0; i < translationTrack.length; i++) {
			translationTrack[i].save(out);
		}

	}

	public int getSize() {
		int a = 0;
		a += 4;
		a += 4;
		a += 4;
		a += 4;
		for (int i = 0; i < translationTrack.length; i++) {
			a += translationTrack[i].getSize();
		}

		return a;
	}

	public class TranslationTrack {
		public int time;
		public float[] translation = new float[3];
		public float[] inTan = new float[3];
		public float[] outTan = new float[3];

		public void load(BlizzardDataInputStream in) throws IOException {
			time = in.readInt();
			translation = StreamUtils.loadFloatArray(in, 3);
			if (interpolationType > 1) {
				inTan = StreamUtils.loadFloatArray(in, 3);
				outTan = StreamUtils.loadFloatArray(in, 3);
			}
		}

		public void save(BlizzardDataOutputStream out) throws IOException {
			out.writeInt(time);
			if (translation.length % 3 != 0) {
				throw new IllegalArgumentException(
						"The array translation needs either the length 3 or a multiple of this number. (got "
								+ translation.length + ")");
			}
			StreamUtils.saveFloatArray(out, translation);
			if (interpolationType > 1) {
				if (inTan.length % 3 != 0) {
					throw new IllegalArgumentException(
							"The array inTan needs either the length 3 or a multiple of this number. (got "
									+ inTan.length + ")");
				}
				StreamUtils.saveFloatArray(out, inTan);
				if (outTan.length % 3 != 0) {
					throw new IllegalArgumentException(
							"The array outTan needs either the length 3 or a multiple of this number. (got "
									+ outTan.length + ")");
				}
				StreamUtils.saveFloatArray(out, outTan);
			}

		}

		public int getSize() {
			int a = 0;
			a += 4;
			a += 12;
			if (interpolationType > 1) {
				a += 12;
				a += 12;
			}

			return a;
		}
	}
}
