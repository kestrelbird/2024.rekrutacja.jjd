import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FileCabinet implements Cabinet {
    private final List<Folder> folders;
    private final List<Folder> allFolders;
    private final List<Folder> helpList = new ArrayList<>();

    public FileCabinet(List<Folder> folders) {
        this.folders = folders;
        this.allFolders = getAllFolders();
    }

    @Override
    public Optional<Folder> findFolderByName(String name) {
        for (Folder folder : allFolders) {
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
            if (folder.getSize().equalsIgnoreCase(size)) {
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
        if (folders != null) {
            return getAll(folders);
        } else {
            return new ArrayList<>();
        }
    }

    private boolean isMultiFolder(Folder folder) {
        return folder instanceof MultiFolder;
    }

    private List<Folder> getAll(List<Folder> list) {
        for (Folder f : list) {
            if (isMultiFolder(f)) {
                helpList.add(f);
                List<Folder> subFolders = ((MultiFolder) f).getFolders();
                getAll(subFolders);
            }
            if (!helpList.contains(f)) {
                helpList.add(f);
            }
        }
        return helpList;
    }
}
