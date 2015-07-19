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

        String chunkId; ;
        while ((chunkId= StreamUtils.readOptionalId(in)) != null) {

            switch (chunkId) {
                case VersionChunk.key:
                    versionChunk = new VersionChunk();
                    versionChunk.load(in);
                    break;
                case ModelChunk.key:
                    modelChunk = new ModelChunk();
                    modelChunk.load(in);
                    break;
                case SequenceChunk.key:
                    sequenceChunk = new SequenceChunk();
                    sequenceChunk.load(in);
                    break;
                case GlobalSequenceChunk.key:
                    globalSequenceChunk = new GlobalSequenceChunk();
                    globalSequenceChunk.load(in);
                    break;
                case MaterialChunk.key:
                    materialChunk = new MaterialChunk();
                    materialChunk.load(in);
                    break;
                case TextureChunk.key:
                    textureChunk = new TextureChunk();
                    textureChunk.load(in);
                    break;
                case TextureAnimationChunk.key:
                    textureAnimationChunk = new TextureAnimationChunk();
                    textureAnimationChunk.load(in);
                    break;
                case GeosetChunk.key:
                    geosetChunk = new GeosetChunk();
                    geosetChunk.load(in);
                    break;
                case GeosetAnimationChunk.key:
                    geosetAnimationChunk = new GeosetAnimationChunk();
                    geosetAnimationChunk.load(in);
                    break;
                case BoneChunk.key:
                    boneChunk = new BoneChunk();
                    boneChunk.load(in);
                    break;
                case LightChunk.key:
                    lightChunk = new LightChunk();
                    lightChunk.load(in);
                    break;
                case HelperChunk.key:
                    helperChunk = new HelperChunk();
                    helperChunk.load(in);
                    break;
                case AttachmentChunk.key:
                    attachmentChunk = new AttachmentChunk();
                    attachmentChunk.load(in);
                    break;
                case PivotPointChunk.key:
                    pivotPointChunk = new PivotPointChunk();
                    pivotPointChunk.load(in);
                    break;
                case ParticleEmitterChunk.key:
                    particleEmitterChunk = new ParticleEmitterChunk();
                    particleEmitterChunk.load(in);
                    break;
                case ParticleEmitter2Chunk.key:
                    particleEmitter2Chunk = new ParticleEmitter2Chunk();
                    particleEmitter2Chunk.load(in);
                    break;
                case RibbonEmitterChunk.key:
                    ribbonEmitterChunk = new RibbonEmitterChunk();
                    ribbonEmitterChunk.load(in);
                    break;
                case EventObjectChunk.key:
                    eventObjectChunk = new EventObjectChunk();
                    eventObjectChunk.load(in);
                    break;
                case CameraChunk.key:
                    cameraChunk = new CameraChunk();
                    cameraChunk.load(in);
                    break;
                case CollisionShapeChunk.key:
                    collisionShapeChunk = new CollisionShapeChunk();
                    collisionShapeChunk.load(in);
                    break;
                default:
                    StreamUtils.checkId(in, chunkId);
                    int chunkSize = in.readInt();
                    in.read(new byte[chunkSize]);
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
