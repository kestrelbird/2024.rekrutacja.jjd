import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FileCabinet implements Cabinet {
    private List<Folder> folders;

    @Override
    public Optional<Folder> findFolderByName(String name) {
        return Optional.empty();
    }

    @Override
    public List<Folder> findFoldersBySize(String size) {
        return List.of();
    }

    @Override
    public int count() {
        return getAllFolders().size();
    }

    private List<Folder> getAllFolders() {
        List<Folder> allFolders = new ArrayList<>();
        for (Folder folder : folders) {
            if (folder instanceof MultiFolder) {
                List<Folder> subFolders = ((MultiFolder) folder).getFolders();
                allFolders.addAll(subFolders);
            } else {
                allFolders.add(folder);
            }
        }
        return allFolders;
    }
}
