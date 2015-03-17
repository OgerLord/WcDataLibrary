package de.wc3data.mdx;

import de.wc3data.stream.StreamUtils;
import java.io.IOException;
import java.util.*;

public class GlobalSequenceChunk {
	public int[] globalSequences = new int[0];

	public static final String key = "GLBS";

	public void load(BlizzardDataInputStream in) throws IOException {
		StreamUtils.checkId(in, "GLBS");
		int chunkSize = in.readInt();
		globalSequences = StreamUtils.loadIntArray(in, chunkSize / 4);
	}

	public void save(BlizzardDataOutputStream out) throws IOException {
		int nrOfGlobalSequences = globalSequences.length;
		out.writeNByteString("GLBS", 4);
		out.writeInt(getSize() - 8);// ChunkSize
		StreamUtils.saveIntArray(out, globalSequences);

	}

	public int getSize() {
		int a = 0;
		a += 4;
		a += 4;
		a += 4 * globalSequences.length;

		return a;
	}
}
