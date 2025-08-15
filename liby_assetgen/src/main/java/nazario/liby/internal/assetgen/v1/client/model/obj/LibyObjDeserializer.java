package nazario.liby.internal.assetgen.v1.client.model.obj;

import net.minecraft.resource.Resource;
import org.jetbrains.annotations.ApiStatus;
import org.joml.Vector2d;
import org.joml.Vector3d;

import java.util.ArrayList;
import java.util.List;

@ApiStatus.Internal
public class LibyObjDeserializer {
    public static List<Face> objToFaceList(Resource resource) {
        try {
            List<Vector3d> vertices = new ArrayList<>();
            List<Vector3d> normals = new ArrayList<>();
            List<Vector2d> texCoords = new ArrayList<>();
            List<Face> faces = new ArrayList<>();

            List<String> lines = resource.getReader().lines().toList();

            lines.forEach(line -> {
                String[] parts = line.split(" ");
                if(line.startsWith("v ")) {
                    vertices.add(new Vector3d(
                            Double.parseDouble(parts[1]),
                            Double.parseDouble(parts[2]),
                            Double.parseDouble(parts[3])
                    ));
                }

                if(line.startsWith("vn ")) {
                    normals.add(new Vector3d(
                            Double.parseDouble(parts[1]),
                            Double.parseDouble(parts[2]),
                            Double.parseDouble(parts[3])
                    ));
                }

                if(line.startsWith("vt ")) {
                    texCoords.add(new Vector2d(Double.parseDouble(parts[1]), Double.parseDouble(parts[2])));
                }


                if(line.startsWith("f ")) {
                    try{
                        faces.add(new Face(convertFace(new String[]{parts[1],parts[2],parts[3]}, vertices, normals, texCoords)));

                        if(parts.length >= 5) {
                            faces.add(new Face(convertFace(new String[]{parts[1],parts[3],parts[4]}, vertices, normals, texCoords)));

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

    public static TriangleData[] convertFace(String[] parts, List<Vector3d> vertices, List<Vector3d> normals, List<Vector2d> texCoords) {
        //vertex/texCoord/normal

        TriangleData[] triangleData = new TriangleData[4];

        for(int i = 0;i<3;i++) {
            String[] faceDataPoints = parts[i].split("/");

            int vertexIndex = Integer.parseInt(faceDataPoints[0]) - 1;
            int textureIndex = Integer.parseInt(faceDataPoints[1]) - 1;
            int normalIndex = Integer.parseInt(faceDataPoints[2]) - 1;

            triangleData[i] = new TriangleData(
                    vertices.get(vertexIndex),
                    normals.get(normalIndex).normalize(),
                    texCoords.get(textureIndex)
            );
        }

        triangleData[3] = triangleData[0];

        return triangleData;
    }
}
