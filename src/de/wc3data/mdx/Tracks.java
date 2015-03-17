package de.wc3data.mdx;

import de.wc3data.stream.StreamUtils;
import java.io.IOException;
import java.util.*;

public class Tracks {
	public int globalSequenceId;
	public int[] tracks = new int[0];

	public static final String key = "KEVT";

	public void load(BlizzardDataInputStream in) throws IOException {
		StreamUtils.checkId(in, "KEVT");
		int nrOfTracks = in.readInt();
		globalSequenceId = in.readInt();
		tracks = StreamUtils.loadIntArray(in, nrOfTracks);
	}

	public void save(BlizzardDataOutputStream out) throws IOException {
		int nrOfTracks = tracks.length;
		out.writeNByteString("KEVT", 4);
		out.writeInt(nrOfTracks);
		out.writeInt(globalSequenceId);
		StreamUtils.saveIntArray(out, tracks);

	}

	public int getSize() {
		int a = 0;
		a += 4;
		a += 4;
		a += 4;
		a += 4 * tracks.length;

		return a;
	}
}
