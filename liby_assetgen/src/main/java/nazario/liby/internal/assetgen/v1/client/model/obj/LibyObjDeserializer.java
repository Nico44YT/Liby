package nazario.liby.internal.assetgen.v1.client.model.obj;

import nazario.liby.api.client.renderer.LibyFace;
import nazario.liby.api.client.renderer.LibyTriangleData;
import net.minecraft.resource.Resource;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;

@ApiStatus.Internal
public class LibyObjDeserializer {
    public static List<LibyFace> objToFaceList(Resource resource) {
        try {
            List<Vec3d> vertices = new ArrayList<>();
            List<Vec3d> normals = new ArrayList<>();
            List<Vec2f> texCoords = new ArrayList<>();
            List<LibyFace> faces = new ArrayList<>();

            List<String> lines = resource.getReader().lines().toList();

            lines.forEach(line -> {
                String[] parts = line.split(" ");
                if(line.startsWith("v ")) {
                    vertices.add(new Vec3d(
                            Double.parseDouble(parts[1]),
                            Double.parseDouble(parts[2]),
                            Double.parseDouble(parts[3])
                    ));
                }

                if(line.startsWith("vn ")) {
                    normals.add(new Vec3d(
                            Double.parseDouble(parts[1]),
                            Double.parseDouble(parts[2]),
                            Double.parseDouble(parts[3])
                    ));
                }

                if(line.startsWith("vt ")) {
                    texCoords.add(new Vec2f(Float.parseFloat(parts[1]), Float.parseFloat(parts[2])));
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

    public static LibyTriangleData[] convertFace(String[] parts, List<Vec3d> vertices, List<Vec3d> normals, List<Vec2f> texCoords) {
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
