package pl.rzan.backend;

import lombok.extern.slf4j.Slf4j;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.HashMap;

@Slf4j
public class DiskInfoCollector {
    private static DiskInfoCollector INSTANCE;
    private HashMap<String, DiskInfo> disks;
    private int numberOfDisks;

    private DiskInfoCollector() {
        aggregateDisksInfo();
    }

    public static DiskInfoCollector getInstance() {
        if(INSTANCE == null) {
            return new DiskInfoCollector();
        }
        return INSTANCE;
    }

    private void aggregateDisksInfo() {
        File[] paths = File.listRoots();
        FileSystemView fsv = FileSystemView.getFileSystemView();

        disks = new HashMap<>();
        final String diskId = "Disk_";
        int diskCount = 0;

        for(File path : paths) {
            disks.put(diskId + ++diskCount, new DiskInfo(path.getPath(), fsv.getSystemTypeDescription(path)));
        }

        this.numberOfDisks = diskCount;
    }

    public int getDiskQuantity() {
        return this.numberOfDisks;
    }

    public HashMap<String, DiskInfo> getDiskInfo() {
        return this.disks;
    }

    public static class DiskInfo {
        public String name;
        public String description;

        public DiskInfo(String name, String description) {
            this.name = name;
            this.description = description;
        }

        @Override
        public String toString() {
            return "DiskInfo{" +
                    "name='" + name + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }
    }
}
