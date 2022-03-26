package chaoziken.tfcloader.util;

import com.google.common.io.Resources;

import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;

public class FileHelper {

    @Nullable
    public static File createDir(@Nullable File oldDir, String newDir) {
        if (oldDir == null) {
            return null;
        }

        File dir = new File(oldDir, newDir);

        if (!dir.exists()) {
            try {
                if (!dir.mkdir()) {
                    TFCLog.logger.error("Could not create config directory " + newDir);
                    return null;
                }
            } catch (SecurityException e) {
                TFCLog.logger.error("Could not create config directory " + newDir, e);
                return null;
            }
        }
        return dir;
    }

    @Nullable
    public static File createFile(@Nullable File dir, String fileName) {
        if (dir == null) {
            return null;
        }

        File newFile = new File(dir, fileName);

        if (!newFile.exists()) {
            try {
                if (!newFile.createNewFile()) {
                    TFCLog.logger.error("Could not make file " + fileName);
                    return null;
                }
            } catch (IOException e) {
                TFCLog.logger.error("Could not make file " + fileName, e);
                return null;
            }
        }
        return newFile;
    }

    /**
     * Creates the given file.
     *
     * @return {@code false} if File#createNewFile returns false or if an IOException is thrown. {@code true} otherwise.
     */
    public static boolean createFile(@Nullable File file) {
        if (file == null) {
            return false;
        }
        try {
            if (!file.createNewFile()) {
                TFCLog.logger.error("Could not make file " + file.getName());
                return false;
            }
        } catch (IOException e) {
            TFCLog.logger.error("Could not make file " + file.getName(), e);
            return false;
        }
        return true;
    }


    public static void createBlankJSONFile(File dir, String fileName) {
        File newFile = new File(dir, fileName);

        if (!newFile.exists()) {
            newFile = createFile(dir, fileName);
            if (newFile != null) {
                try {
                    List<String> line = Collections.singletonList("{}");
                    Files.write(newFile.toPath(), line, StandardCharsets.UTF_8);
                } catch (IOException e) {
                    TFCLog.logger.error("Could not write to JSON file " + fileName, e);
                }
            }
        }
    }

    //Color is in RGB
    public static void copyImageAndManipulate(InputStream resourceStream, File destFile, int color, int alphaManipulation) {
        try {
            BufferedImage img = ImageIO.read(resourceStream);
            for(int x = 0; x < img.getWidth(); x++) {
                for (int y = 0; y < img.getHeight(); y++) {
                    int p = img.getRGB(x, y);
                    if (((p>>24)&0xff) == alphaManipulation) {
                        int p1 = tintPixelToColor(p, color);
                        img.setRGB(x, y, p1);
                    }
                }
            }
            ImageIO.write(img, "png", destFile);
            //Files.copy(resourceStream, destFile.toPath());
        } catch (IOException e) {
            TFCLog.logger.error("Could not process image file to " + destFile.toPath(), e);
        }
    }

    //Color is in RGB
    private static int tintPixelToColor(int pixel, int color) {
        float[] pixelHSB = Color.RGBtoHSB((pixel>>16)&0xff, (pixel>>8)&0xff, pixel&0xff, null);
        float[] colorHSB = Color.RGBtoHSB((color>>16)&0xff, (color>>8)&0xff, color&0xff, null);
        pixelHSB[0] = colorHSB[0]; //Set the pixel hue to the color's hue
        if (pixelHSB[1] == 0) {
            pixelHSB[1] = colorHSB[1]; //Set the pixel saturation to the color's saturation, only if the pixel's saturation is 0
        } else {
            pixelHSB[1] = (pixelHSB[1] + colorHSB[1])/2; //Average the saturation
        }
        return Color.HSBtoRGB(pixelHSB[0], pixelHSB[1], pixelHSB[2]); //Returns an alpha of 0xff already
    }

    //Resources is marked as beta
    @SuppressWarnings("UnstableApiUsage")
    public static void copyInputToFileAndReplace(URL inputFile, File destFile, String toReplace, String replaceWith) throws IOException {
        if (inputFile == null) {
            throw new IOException("Could not find asset");
        }

        PrintWriter writer = new PrintWriter(destFile);

        for (String line : Resources.readLines(inputFile, StandardCharsets.UTF_8)) {
            line = line.replace(toReplace, replaceWith);
            writer.write(line);
        }
        writer.close();
    }

}