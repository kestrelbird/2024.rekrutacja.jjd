import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FileCabinet implements Cabinet {
    private List<Folder> folders;
    private final List<Folder> allFolders = getAllFolders();

    @Override
    public Optional<Folder> findFolderByName(String name) {
        for (Folder folder : folders) {
            if (folder.getName().equals(name)) {
                return Optional.of(folder);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Folder> findFoldersBySize(String size) {
        List<Folder> foldersBySize = new ArrayList<>();
        for (Folder folder : allFolders) {
            if (folder.getSize().equals(size)) {
                foldersBySize.add(folder);
            }
        }
        return foldersBySize;
    }

    @Override
    public int count() {
        return allFolders.size();
    }

    private List<Folder> getAllFolders() {
        List<Folder> allFolders = new ArrayList<>();
        if (folders != null) {
            for (Folder folder : folders) {
                if (folder instanceof MultiFolder) {
                    List<Folder> subFolders = ((MultiFolder) folder).getFolders();
                    allFolders.addAll(subFolders);
                } else {
                    allFolders.add(folder);
                }
            }
        }
        return allFolders;
    }
}
