import FFM.FileMaster;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class AssassinSetup extends FileMaster {

    public void loadProfiles(){
        // set up the pathname for input data
        String pathname = "res/PlayerData";

        // scan in the data
        File file = new File(pathname);
        if (file.exists()) {
            Scanner s;
            try {
                s = new Scanner(file);
            } catch (FileNotFoundException var5) {
                throw new RuntimeException(var5);
            }

            int count = 0;

            ArrayList list;
            for(list = new ArrayList(); s.hasNextLine(); ++count) {
                list.add(s.nextLine());
            }

            s.close();
            ArrayList<String> scannedData = list;
        }

    }

    public static void main(String[] args) {
        new AssassinSetup().loadProfiles();
    }

}
