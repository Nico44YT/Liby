package nazario.liby.internal.assetgen.v1.client.model.obj;

import nazario.liby.api.client.renderer.LibyFace;
import nazario.liby.api.client.renderer.LibyTriangleData;
import net.minecraft.resource.Resource;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;

@ApiStatus.Internal
public class LibyObjDeserializer {
    public static List<LibyFace> objToFaceList(Resource resource) {
        try {
            List<Vector3f> vertices = new ArrayList<>();
            List<Vector3f> normals = new ArrayList<>();
            List<Vector2f> texCoords = new ArrayList<>();
            List<LibyFace> faces = new ArrayList<>();

            List<String> lines = resource.getReader().lines().toList();

            lines.forEach(line -> {
                String[] parts = line.split(" ");
                if(line.startsWith("v ")) {
                    vertices.add(new Vector3f(
                            Float.parseFloat(parts[1]),
                            Float.parseFloat(parts[2]),
                            Float.parseFloat(parts[3])
                    ));
                }

                if(line.startsWith("vn ")) {
                    normals.add(new Vector3f(
                            Float.parseFloat(parts[1]),
                            Float.parseFloat(parts[2]),
                            Float.parseFloat(parts[3])
                    ));
                }

                if(line.startsWith("vt ")) {
                    texCoords.add(new Vector2f(Float.parseFloat(parts[1]), Float.parseFloat(parts[2])));
                }


                if(line.startsWith("f ")) {
                    try{
                        faces.add(new LibyFace(convertFace(new String[]{parts[1],parts[2],parts[3]}, vertices, normals, texCoords)));

                        if(parts.length >= 5) {
                            faces.add(new LibyFace(convertFace(new String[]{parts[1],parts[3],parts[4]}, vertices, normals, texCoords)));

                        }
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            });

            return faces;
        }catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public static LibyTriangleData[] convertFace(String[] parts, List<Vector3f> vertices, List<Vector3f> normals, List<Vector2f> texCoords) {
        //vertex/texCoord/normal

        LibyTriangleData[] triangleData = new LibyTriangleData[4];

        for(int i = 0;i<3;i++) {
            String[] faceDataPoints = parts[i].split("/");

            int vertexIndex = Integer.parseInt(faceDataPoints[0]) - 1;
            int textureIndex = Integer.parseInt(faceDataPoints[1]) - 1;
            int normalIndex = Integer.parseInt(faceDataPoints[2]) - 1;

            triangleData[i] = new LibyTriangleData(
                    vertices.get(vertexIndex),
                    normals.get(normalIndex).normalize(),
                    texCoords.get(textureIndex)
            );
        }

        triangleData[3] = triangleData[0];

        return triangleData;
    }
}
