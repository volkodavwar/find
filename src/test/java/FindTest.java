import me.volkov.find.Find;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FindTest {

    private void assertContains(String expectedAbsolutePath, List<File> files) {
        boolean found = false;
        for (File file : files) {
            if (expectedAbsolutePath.equals(file.getAbsolutePath())) {
                found = true;
                break;
            }
        }
        assertTrue(found, "expected file: " + expectedAbsolutePath + " was not found!");
    }

    private void assertFiles(List<File> actual, String... expectedAbsolutePaths) {
        assertEquals(expectedAbsolutePaths.length, actual.size(), "Expected lengths: " + expectedAbsolutePaths.length + ", but was: " + actual.size());

        for (String expectedPath : expectedAbsolutePaths) {
            assertContains(expectedPath, actual);
        }
    }

    @Test
    public void testNonexistentDirs() {
        File root = new File("nonexistentdir");
        Find find = new Find(root, false);
        assertTrue(find.find("nonexistentFile").isEmpty());

        find = new Find(root, true);
        assertTrue(find.find("nonexistentFileItIsTrue.ThisFile.DoesNotExist").isEmpty());

        root.delete();
    }


    @Test
    public void testDirs() throws IOException {
        File root = new File("").getAbsoluteFile();
        File file = new File(root.getAbsolutePath(), "hello");
        file.createNewFile();

        assertFiles(new Find(root, false).find(file.getName()), file.getAbsolutePath());
        assertFiles(new Find(root, true).find(file.getName()), file.getAbsolutePath());
        assertFiles(new Find(root, false).find(file.getName() + "nonexistent") /* no files */);
        assertFiles(new Find(root, true).find(file.getName() + "nonexistent") /* no files */);

        File subdir = new File(root.getAbsolutePath(), "subdir");
        subdir.mkdir();

        // поиск файла с именем директории
        assertFiles(new Find(root, false).find(subdir.getName()) /* no files */);
        assertFiles(new Find(root, true).find(subdir.getName()) /* no files */);

        File innerFile = new File(subdir.getAbsolutePath(), "inner");
        innerFile.createNewFile();

        assertFiles(new Find(root, false).find(innerFile.getName()) /* no files */);
        assertFiles(new Find(root, true).find(innerFile.getName()), innerFile.getAbsolutePath());
        assertFiles(new Find(subdir, true).find(innerFile.getName()), innerFile.getAbsolutePath());
        assertFiles(new Find(subdir, false).find(innerFile.getName()), innerFile.getAbsolutePath());


        innerFile.delete();
        subdir.delete();
        file.delete();
        root.delete();
    }
}
