package de.wc3data.mdx;

import de.wc3data.stream.StreamUtils;
import java.io.IOException;
import java.util.*;

public class CameraChunk {
	public Camera[] camera = new Camera[0];

	public static final String key = "CAMS";

	public void load(BlizzardDataInputStream in) throws IOException {
		StreamUtils.checkId(in, "CAMS");
		int chunkSize = in.readInt();
		List<Camera> cameraList = new ArrayList();
		int cameraCounter = chunkSize;
		while (cameraCounter > 0) {
			Camera tempcamera = new Camera();
			cameraList.add(tempcamera);
			tempcamera.load(in);
			cameraCounter -= tempcamera.getSize();
		}
		camera = cameraList.toArray(new Camera[cameraList.size()]);
	}

	public void save(BlizzardDataOutputStream out) throws IOException {
		int nrOfCameras = camera.length;
		out.writeNByteString("CAMS", 4);
		out.writeInt(getSize() - 8);// ChunkSize
		for (int i = 0; i < camera.length; i++) {
			camera[i].save(out);
		}

	}

	public int getSize() {
		int a = 0;
		a += 4;
		a += 4;
		for (int i = 0; i < camera.length; i++) {
			a += camera[i].getSize();
		}

		return a;
	}

	public class Camera {
		public String name = "";
		public float[] position = new float[3];
		public int fieldOfView;
		public int farClippingPlane;
		public int nearClippingPlane;
		public float[] targetPosition = new float[3];
		public CameraPositionTranslation cameraPositionTranslation;
                public CameraRotation cameraRotation;
		public CameraTargetTranslation cameraTargetTranslation;

		public void load(BlizzardDataInputStream in) throws IOException {
			int inclusiveSize = in.readInt();
			name = in.readCharsAsString(80);
			position = StreamUtils.loadFloatArray(in, 3);
			fieldOfView = in.readInt();
			farClippingPlane = in.readInt();
			nearClippingPlane = in.readInt();
			targetPosition = StreamUtils.loadFloatArray(in, 3);
			for (int i = 0; i < 3; i++) {
				if (StreamUtils.checkOptionalId(in, cameraPositionTranslation.key)) {
					cameraPositionTranslation = new CameraPositionTranslation();
					cameraPositionTranslation.load(in);
				} else if (StreamUtils.checkOptionalId(in,
						cameraRotation.key)) {
					cameraRotation = new CameraRotation();
					cameraRotation.load(in);
				} else if (StreamUtils.checkOptionalId(in,
						cameraTargetTranslation.key)) {
					cameraTargetTranslation = new CameraTargetTranslation();
					cameraTargetTranslation.load(in);
				}

			}
		}

		public void save(BlizzardDataOutputStream out) throws IOException {
			out.writeInt(getSize());// InclusiveSize
			out.writeNByteString(name, 80);
			if (position.length % 3 != 0) {
				throw new IllegalArgumentException(
						"The array position needs either the length 3 or a multiple of this number. (got "
								+ position.length + ")");
			}
			StreamUtils.saveFloatArray(out, position);
			out.writeInt(fieldOfView);
			out.writeInt(farClippingPlane);
			out.writeInt(nearClippingPlane);
			if (targetPosition.length % 3 != 0) {
				throw new IllegalArgumentException(
						"The array targetPosition needs either the length 3 or a multiple of this number. (got "
								+ targetPosition.length + ")");
			}
			StreamUtils.saveFloatArray(out, targetPosition);
			if (cameraPositionTranslation != null) {
				cameraPositionTranslation.save(out);
			}
                        if (cameraRotation != null) {
				cameraRotation.save(out);
			}
			if (cameraTargetTranslation != null) {
				cameraTargetTranslation.save(out);
			}

		}

		public int getSize() {
			int a = 0;
			a += 4;
			a += 80;
			a += 12;
			a += 4;
			a += 4;
			a += 4;
			a += 12;
			if (cameraPositionTranslation != null) {
				a += cameraPositionTranslation.getSize();
			}
                        if (cameraRotation != null) {
				a += cameraRotation.getSize();
			}
			if (cameraTargetTranslation != null) {
				a += cameraTargetTranslation.getSize();
			}

			return a;
		}
	}
}
