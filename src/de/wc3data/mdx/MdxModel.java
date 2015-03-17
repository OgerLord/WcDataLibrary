package de.wc3data.mdx;

import de.wc3data.stream.StreamUtils;
import java.io.IOException;
import java.util.*;

public class MdxModel {
	public VersionChunk versionChunk;
	public ModelChunk modelChunk;
	public SequenceChunk sequenceChunk;
	public GlobalSequenceChunk globalSequenceChunk;
	public MaterialChunk materialChunk;
	public TextureChunk textureChunk;
	public TextureAnimationChunk textureAnimationChunk;
	public GeosetChunk geosetChunk;
	public GeosetAnimationChunk geosetAnimationChunk;
	public BoneChunk boneChunk;
	public LightChunk lightChunk;
	public HelperChunk helperChunk;
	public AttachmentChunk attachmentChunk;
	public PivotPointChunk pivotPointChunk;
	public ParticleEmitterChunk particleEmitterChunk;
	public ParticleEmitter2Chunk particleEmitter2Chunk;
	public RibbonEmitterChunk ribbonEmitterChunk;
	public EventObjectChunk eventObjectChunk;
	public CameraChunk cameraChunk;
	public CollisionShapeChunk collisionShapeChunk;

	public static final String key = "MDLX";

	public void load(BlizzardDataInputStream in) throws IOException {
		StreamUtils.checkId(in, "MDLX");
		for (int i = 0; i < 20; i++) {
			if (StreamUtils.checkOptionalId(in, versionChunk.key)) {
				versionChunk = new VersionChunk();
				versionChunk.load(in);
			} else if (StreamUtils.checkOptionalId(in, modelChunk.key)) {
				modelChunk = new ModelChunk();
				modelChunk.load(in);
			} else if (StreamUtils.checkOptionalId(in, sequenceChunk.key)) {
				sequenceChunk = new SequenceChunk();
				sequenceChunk.load(in);
			} else if (StreamUtils.checkOptionalId(in, globalSequenceChunk.key)) {
				globalSequenceChunk = new GlobalSequenceChunk();
				globalSequenceChunk.load(in);
			} else if (StreamUtils.checkOptionalId(in, materialChunk.key)) {
				materialChunk = new MaterialChunk();
				materialChunk.load(in);
			} else if (StreamUtils.checkOptionalId(in, textureChunk.key)) {
				textureChunk = new TextureChunk();
				textureChunk.load(in);
			} else if (StreamUtils.checkOptionalId(in, textureAnimationChunk.key)) {
				textureAnimationChunk = new TextureAnimationChunk();
				textureAnimationChunk.load(in);
			} else if (StreamUtils.checkOptionalId(in, geosetChunk.key)) {
				geosetChunk = new GeosetChunk();
				geosetChunk.load(in);
			} else if (StreamUtils.checkOptionalId(in, geosetAnimationChunk.key)) {
				geosetAnimationChunk = new GeosetAnimationChunk();
				geosetAnimationChunk.load(in);
			} else if (StreamUtils.checkOptionalId(in, boneChunk.key)) {
				boneChunk = new BoneChunk();
				boneChunk.load(in);
			} else if (StreamUtils.checkOptionalId(in, lightChunk.key)) {
				lightChunk = new LightChunk();
				lightChunk.load(in);
			} else if (StreamUtils.checkOptionalId(in, helperChunk.key)) {
				helperChunk = new HelperChunk();
				helperChunk.load(in);
			} else if (StreamUtils.checkOptionalId(in, attachmentChunk.key)) {
				attachmentChunk = new AttachmentChunk();
				attachmentChunk.load(in);
			} else if (StreamUtils.checkOptionalId(in, pivotPointChunk.key)) {
				pivotPointChunk = new PivotPointChunk();
				pivotPointChunk.load(in);
			} else if (StreamUtils.checkOptionalId(in, particleEmitterChunk.key)) {
				particleEmitterChunk = new ParticleEmitterChunk();
				particleEmitterChunk.load(in);
			} else if (StreamUtils.checkOptionalId(in, particleEmitter2Chunk.key)) {
				particleEmitter2Chunk = new ParticleEmitter2Chunk();
				particleEmitter2Chunk.load(in);
			} else if (StreamUtils.checkOptionalId(in, ribbonEmitterChunk.key)) {
				ribbonEmitterChunk = new RibbonEmitterChunk();
				ribbonEmitterChunk.load(in);
			} else if (StreamUtils.checkOptionalId(in, eventObjectChunk.key)) {
				eventObjectChunk = new EventObjectChunk();
				eventObjectChunk.load(in);
			} else if (StreamUtils.checkOptionalId(in, cameraChunk.key)) {
				cameraChunk = new CameraChunk();
				cameraChunk.load(in);
			} else if (StreamUtils.checkOptionalId(in, collisionShapeChunk.key)) {
				collisionShapeChunk = new CollisionShapeChunk();
				collisionShapeChunk.load(in);
			}

		}
	}

	public void save(BlizzardDataOutputStream out) throws IOException {
		out.writeNByteString("MDLX", 4);
		if (versionChunk != null) {
			versionChunk.save(out);
		}
		if (modelChunk != null) {
			modelChunk.save(out);
		}
		if (sequenceChunk != null) {
			sequenceChunk.save(out);
		}
		if (globalSequenceChunk != null) {
			globalSequenceChunk.save(out);
		}
		if (materialChunk != null) {
			materialChunk.save(out);
		}
		if (textureChunk != null) {
			textureChunk.save(out);
		}
		if (textureAnimationChunk != null) {
			textureAnimationChunk.save(out);
		}
		if (geosetChunk != null) {
			geosetChunk.save(out);
		}
		if (geosetAnimationChunk != null) {
			geosetAnimationChunk.save(out);
		}
		if (boneChunk != null) {
			boneChunk.save(out);
		}
		if (lightChunk != null) {
			lightChunk.save(out);
		}
		if (helperChunk != null) {
			helperChunk.save(out);
		}
		if (attachmentChunk != null) {
			attachmentChunk.save(out);
		}
		if (pivotPointChunk != null) {
			pivotPointChunk.save(out);
		}
		if (particleEmitterChunk != null) {
			particleEmitterChunk.save(out);
		}
		if (particleEmitter2Chunk != null) {
			particleEmitter2Chunk.save(out);
		}
		if (ribbonEmitterChunk != null) {
			ribbonEmitterChunk.save(out);
		}
		if (eventObjectChunk != null) {
			eventObjectChunk.save(out);
		}
		if (cameraChunk != null) {
			cameraChunk.save(out);
		}
		if (collisionShapeChunk != null) {
			collisionShapeChunk.save(out);
		}

	}

	public int getSize() {
		int a = 0;
		a += 4;
		if (versionChunk != null) {
			a += versionChunk.getSize();
		}
		if (modelChunk != null) {
			a += modelChunk.getSize();
		}
		if (sequenceChunk != null) {
			a += sequenceChunk.getSize();
		}
		if (globalSequenceChunk != null) {
			a += globalSequenceChunk.getSize();
		}
		if (materialChunk != null) {
			a += materialChunk.getSize();
		}
		if (textureChunk != null) {
			a += textureChunk.getSize();
		}
		if (textureAnimationChunk != null) {
			a += textureAnimationChunk.getSize();
		}
		if (geosetChunk != null) {
			a += geosetChunk.getSize();
		}
		if (geosetAnimationChunk != null) {
			a += geosetAnimationChunk.getSize();
		}
		if (boneChunk != null) {
			a += boneChunk.getSize();
		}
		if (lightChunk != null) {
			a += lightChunk.getSize();
		}
		if (helperChunk != null) {
			a += helperChunk.getSize();
		}
		if (attachmentChunk != null) {
			a += attachmentChunk.getSize();
		}
		if (pivotPointChunk != null) {
			a += pivotPointChunk.getSize();
		}
		if (particleEmitterChunk != null) {
			a += particleEmitterChunk.getSize();
		}
		if (particleEmitter2Chunk != null) {
			a += particleEmitter2Chunk.getSize();
		}
		if (ribbonEmitterChunk != null) {
			a += ribbonEmitterChunk.getSize();
		}
		if (eventObjectChunk != null) {
			a += eventObjectChunk.getSize();
		}
		if (cameraChunk != null) {
			a += cameraChunk.getSize();
		}
		if (collisionShapeChunk != null) {
			a += collisionShapeChunk.getSize();
		}

		return a;
	}
}
