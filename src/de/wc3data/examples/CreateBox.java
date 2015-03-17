package de.wc3data.examples;

import de.wc3data.mdx.ModelUtils;
import de.wc3data.mdx.BlizzardDataOutputStream;
import de.wc3data.mdx.GeosetChunk;
import de.wc3data.mdx.GeosetChunk.Geoset;
import de.wc3data.mdx.LayerChunk;
import de.wc3data.mdx.LayerChunk.Layer;
import de.wc3data.mdx.MaterialChunk;
import de.wc3data.mdx.MaterialChunk.Material;
import de.wc3data.mdx.MdxModel;
import de.wc3data.mdx.TextureChunk;
import de.wc3data.mdx.TextureChunk.Texture;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Oger-Lord
 */
public class CreateBox {
    
    
    public static void main(String args[]) throws FileNotFoundException, IOException{
        
        BlizzardDataOutputStream out = new BlizzardDataOutputStream(new File("Box.mdx"));
        MdxModel model = createBoxModel();
        model.save(out);
        out.close();
    }
    
    public static MdxModel createBoxModel()
    {
        MdxModel model = new MdxModel();
        
        //Create the Geoset
        model.geosetChunk = new GeosetChunk();
        createBoxGeoset(model.geosetChunk);
        
        //Create the Material
        model.materialChunk = new MaterialChunk();
        createMaterial(model.materialChunk);
        
        //Create the Texture
        model.textureChunk = new TextureChunk();
        createTexture(model.textureChunk);
        
       //This step is important so that the model can be displayed correctly
       //Generates the modelChunk, containing:
       // BlendTime, MinimumExtent, MaximumExtent, BoundRadius
       //Make sure that you set the geoset befor using this function
        ModelUtils.generateModelChunk(model, "Box");
        
        return model;
    }
    
    private static void createMaterial(MaterialChunk materialChunk){
        MaterialChunk.Material material = materialChunk.new Material();
        
        material.layerChunk = new LayerChunk();
        LayerChunk.Layer layer = material.layerChunk.new Layer();
        
        layer.textureId = 0; //id of the texture
        layer.filterMode = 1; //constant color
        layer.alpha = 1; //no Transparency
        
        layer.materialAlpha = null;
        layer.textureAnimationId = -1; //No Texture Animation
        
        material.layerChunk.layer = new LayerChunk.Layer[1];
        material.layerChunk.layer[0] = layer;
        
        materialChunk.material = new MaterialChunk.Material[1];
        materialChunk.material[0] = material;
    }
    
    private static void createTexture(TextureChunk textureChunk){
        TextureChunk.Texture texture = textureChunk.new Texture();
        
        texture.fileName = "environment\\Sky\\LordaeronSummerSky\\LordaeronSummerSky.blp";
        
        textureChunk.texture = new TextureChunk.Texture[1];
        textureChunk.texture[0] = texture;
    }
    
    private static void createBoxGeoset(GeosetChunk geosetChunk){
       
        GeosetChunk.Geoset geoset = geosetChunk.new Geoset();
        
        //Create all 8 vertex points of the Box
        geoset.vertexPositions = new float[]{   -50f,-50f,0f, 
                                                 50f,-50f,0f, 
                                                 50f, 50f,0f, 
                                                -50f, 50f,0f, 
                                                -50f,-50f,100f, 
                                                 50f,-50f,100f, 
                                                 50f, 50f,100f, 
                                                -50f, 50f,100f };
        
        //Position of the vertices on the texture
        geoset.vertexTexturePositions = new float[]{
            0f,0f,
            1f,0f,
            1f,1f,
            0f,1f,
            0f,0f,
            1f,0f,
            1f,1f,
            0f,1f
        };
        
        //Create all 6 Sides
        List<Integer> faces = new ArrayList();
        
        createRectangle(faces,0,4,7,3); //Front
        createRectangle(faces,1,2,6,5); //Back
        
        createRectangle(faces,4,5,6,7); //Top
        createRectangle(faces,3,2,1,0); //Bottom
        
        createRectangle(faces,0,1,5,4); //Left
        createRectangle(faces,3,7,6,2); //Right
        
        geoset.faces = ModelUtils.convert2ShortArrayI(faces);

        //Set MaterialId for texture
        geoset.materialId = 0;
        
        //Calculate the extents/bounds
        ModelUtils.calculateExtent(geoset);
        
        //Calculate the normals
        ModelUtils.calculateNormals(geoset);
        
        //generate face and vertex groups
        ModelUtils.calculateVertexGroup(geoset);
        
        geosetChunk.geoset = new GeosetChunk.Geoset[1];
        geosetChunk.geoset[0] = geoset;
    }
    
    
    private static void createRectangle(List<Integer> faces, int iA, int iB, int iC, int iD){
        //Add first triangle
        faces.add(iA);
        faces.add(iB);
        faces.add(iD);
        
        //Add second triangle
        faces.add(iB);
        faces.add(iC);
        faces.add(iD);

    }    
}
