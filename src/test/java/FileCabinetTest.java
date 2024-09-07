import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class FileCabinetTest {

    @Test
    void testAll() {
        class DataFolder implements Folder {
            private final String name;
            private final String size;

            public DataFolder(String name, String size) {
                this.name = name;
                this.size = size;
            }

            @Override
            public String getName() {
                return name;
            }

            @Override
            public String getSize() {
                return size;
            }
        }

        class DataMultiFolder implements MultiFolder {
            private final String name;
            private final String size;
            private final List<Folder> folders;

            public DataMultiFolder(String name, String size, List<Folder> folders) {
                this.name = name;
                this.size = size;
                this.folders = folders;
            }

            @Override
            public List<Folder> getFolders() {
                return folders;
            }

            @Override
            public String getName() {
                return name;
            }

            @Override
            public String getSize() {
                return size;
            }
        }

        DataFolder dataFolder1 = new DataFolder("D", "LARGE");
        DataFolder dataFolder2a = new DataFolder("a", "MEDIUM");
        DataFolder dataFolder2b = new DataFolder("b", "LARGE");
        List<Folder> subSubFolderList = new ArrayList<>();
        subSubFolderList.add(dataFolder2a);
        subSubFolderList.add(dataFolder2b);
        DataMultiFolder dataFolder2 = new DataMultiFolder("Program Files", "SMALL", subSubFolderList);
        DataFolder dataFolder3 = new DataFolder("ProgramData", "SMALL");
        List<Folder> subFoldersList = new ArrayList<>();
        subFoldersList.add(dataFolder2);
        subFoldersList.add(dataFolder3);
        DataMultiFolder dataFolder4 = new DataMultiFolder("C", "MEDIUM", subFoldersList);

        List<Folder> testList = new ArrayList<>();
        testList.add(dataFolder1);
        testList.add(dataFolder4);
        FileCabinet fc = new FileCabinet(testList);

        assertAll(
                () -> assertEquals(Optional.of(dataFolder2), fc.findFolderByName("Program Files")),
                () -> assertEquals(Optional.of(dataFolder2b), fc.findFolderByName("b")),
                () -> assertEquals(List.of(dataFolder2, dataFolder3), fc.findFoldersBySize("SMALL")),
                () -> assertEquals(List.of(dataFolder1, dataFolder2b), fc.findFoldersBySize("lArGE")),
                () -> assertEquals(6, fc.count())

        );

        List<Folder> emptyList = new ArrayList<>();
        FileCabinet emptyFC = new FileCabinet(emptyList);

        assertAll(
                () -> assertEquals(Optional.empty(), emptyFC.findFolderByName("C")),
                () -> assertEquals(0, emptyFC.count())
        );

    }
}