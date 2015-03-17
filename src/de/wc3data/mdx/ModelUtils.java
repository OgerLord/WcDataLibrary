package de.wc3data.mdx;

import de.wc3data.mdx.GeosetChunk;
import de.wc3data.mdx.GeosetChunk.Geoset;
import de.wc3data.mdx.MdxModel;
import de.wc3data.mdx.ModelChunk;
import java.util.ArrayList;
import java.util.List;


/**
 *   This class contains some useful functions to create/edit mdx models
 * @author Oger-Lord
 */
public class ModelUtils {

    /**
     * Converts a list of integer values into a short array.
     * @param list
     * @return 
     */
    public static short[] convert2ShortArrayI(List<Integer> list) {
        short[] array = new short[list.size()];

        for (int i = 0; i < list.size(); i++) {
            array[i] = (short)((int)list.get(i));
        }

        return array;
    }
    
    /**
     * Converts a list of short values into a short array.
     * @param list
     * @return 
     */
    public static short[] convert2ShortArray(List<Short> list) {
        short[] array = new short[list.size()];

        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }

        return array;
    }

    /**
     * Converts a list of float values into a float array.
     * @param list
     * @return 
     */
    public static float[] convert2FloatArray(List<Float> list) {
        float[] array = new float[list.size()];

        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }

        return array;
    }

    /**
     * Generate the ModelChunk for a given model.
     * The ModelChunk contains the extents, blendtime and the name of the model.
     * Make sure to add the geosets befor using this function.
     * @param list
     * @return 
     */
    public static void generateModelChunk(MdxModel model, String name){
        ModelChunk chunk = new ModelChunk();
        
        float[] max = new float[3];
        float[] min = new float[3];
        
        if(model.geosetChunk != null && model.geosetChunk.geoset != null){

            for(Geoset geoset : model.geosetChunk.geoset){
                for(int a=0;a<3;a++){

                    if(geoset.maximumExtent[a] > max[a])
                        max[a] = geoset.maximumExtent[a];
                    if(geoset.minimumExtent[a] < min[a])
                        min[a] = geoset.minimumExtent[a];
                }
            }

            float bounds = 0;
            for(int a=0;a<3;a++){
                bounds += ((max[a]-min[a])*(max[a]-min[a]))/4;
            }
            bounds = (float)Math.sqrt(bounds);

            chunk.minimumExtent = min;
            chunk.maximumExtent = max;
            chunk.boundsRadius = bounds;
        }
        
        chunk.blendTime=150;
        chunk.name=name;
        
        model.modelChunk = chunk;
    }
    
    /**
    * Calculates the normals of a geoset. 
    * Make sure you added the vertices befor calling this function!
    * @param geoset 
    */
    public static void calculateExtent(GeosetChunk.Geoset geoset){
       if(geoset.vertexPositions == null || geoset.vertexPositions.length == 0){
           throw new IllegalArgumentException("Error, the geoset does not have vertices.");
       }
        
        
        float[] max = new float[3];
        float[] min = new float[3];
        
        for(int i=0;i<geoset.vertexPositions.length;i+=3){
            for(int a=0;a<3;a++){
                float val = geoset.vertexPositions[i+a];
                if(val > max[a])
                    max[a] = val;
                if(val < min[a])
                    min[a] = val;
            }
        }
        
        float bounds = 0;
        for(int a=0;a<3;a++){
            bounds += ((max[a]-min[a])*(max[a]-min[a]))/4;
        }
        bounds = (float)Math.sqrt(bounds);
        
        geoset.minimumExtent = min;
        geoset.maximumExtent = max;
        geoset.boundsRadius = bounds;
    }
    
    
    
   /**
    * Calculates the vertex groups, face groups and facetypegroups of a geoset. 
    * Make sure you added the vertices and faces befor calling this function!
    * @param geoset 
    */
    public static void calculateVertexGroup(GeosetChunk.Geoset geoset){
        
        geoset.nrOfTextureVertexGroups=1;
                
        geoset.faceTypeGroups = new int[1];
        geoset.faceTypeGroups[0] = 4;
        
        geoset.vertexGroups = new byte[geoset.vertexPositions.length/3];
        geoset.faceGroups = new int[1];
        geoset.faceGroups[0] = geoset.faces.length;
    }

   /**
    * Calculates the normals of a geoset. 
    * Make sure you added the vertices and faces befor calling this function!
    * @param geoset 
    */
   public static void calculateNormals(GeosetChunk.Geoset geoset){
       if(geoset.vertexPositions == null || geoset.vertexPositions.length == 0){
           throw new IllegalArgumentException("Error, the geoset does not have vertices.");
       }
       
       geoset.vertexNormals = calculateNormals(geoset.vertexPositions, geoset.faces);
   }
    
    private static float[] calculateNormals(float[] vertices, short[] faces) {
        ArrayList<Float> vectorList[] = new ArrayList[vertices.length / 3];

        for (int i = 0; i < vectorList.length; i++) {
            vectorList[i] = new ArrayList();
        }

        for (int i = 0; i < faces.length; i += 3) {
            float[] n = calculateNormalFace(vertices, faces, i);

            for (int a = 0; a < 3; a++) {
                vectorList[faces[i + a]].add(n[0]);
                vectorList[faces[i + a]].add(n[1]);
                vectorList[faces[i + a]].add(n[2]);
            }
        }

        //Durchschnitts normal errechnen
        float[] normals = new float[vertices.length];

        for (int i = 0; i < vectorList.length; i++) {

            int vertPos = i * 3;

            float x = 0;
            float y = 0;
            float z = 0;

            for (int a = 0; a < vectorList[i].size(); a += 3) {
                x += vectorList[i].get(a);
                y += vectorList[i].get(a + 1);
                z += vectorList[i].get(a + 2);
            }

            float dist = (float) Math.sqrt(x * x + y * y + z * z);
            x /= dist;
            y /= dist;
            z /= dist;

            normals[vertPos] = x;
            normals[vertPos + 1] = y;
            normals[vertPos + 2] = z;
        }

        return normals;
    }
    
    private static float[] calculateNormalFace(float[] vertices, short[] faces, int i) {

        if (i % 3 != 0) {
            throw new IllegalArgumentException("i%3 must be 0");
        }

        float ax = vertices[faces[i] * 3];
        float ay = vertices[faces[i] * 3 + 1];
        float az = vertices[faces[i] * 3 + 2];

        float bx = vertices[faces[i + 1] * 3];
        float by = vertices[faces[i + 1] * 3 + 1];
        float bz = vertices[faces[i + 1] * 3 + 2];

        float cx = vertices[faces[i + 2] * 3];
        float cy = vertices[faces[i + 2] * 3 + 1];
        float cz = vertices[faces[i + 2] * 3 + 2];

        float d1x = bx - ax;
        float d1y = by - ay;
        float d1z = bz - az;

        float d2x = cx - ax;
        float d2y = cy - ay;
        float d2z = cz - az;

        float nx = d1y * d2z - d1z * d2y;
        float ny = d1z * d2x - d1x * d2z;
        float nz = d1x * d2y - d1y * d2x;

        float dist = (float) Math.sqrt(nx * nx + ny * ny + nz * nz);
        nx = nx / dist;
        ny = ny / dist;
        nz = nz / dist;

        float n[] = {nx, ny, nz};
        return n;
    }
}
