package eg.edu.alexu.csd.oop.Utilities;

import java.io.File;

public class FileUtilities {
    public static boolean deleteDirectory(File dir){
        if(!dir.isDirectory()){
            return dir.delete();
        }
        if(dir.listFiles().length == 0){
            return dir.delete();
        }
        boolean deleteAll = true;
        for(File subDir : dir.listFiles()){
            deleteAll = deleteAll && deleteDirectory(subDir);
        }
        return deleteAll;
    }
}
