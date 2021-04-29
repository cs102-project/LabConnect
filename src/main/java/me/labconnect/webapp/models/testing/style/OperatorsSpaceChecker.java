package me.labconnect.webapp.models.testing.style;

import java.util.ArrayList;

/**
 * This class is for checking whether every operator character have space right
 * before and after them or not.
 * 
 * @author Berk Çakar
 * @version 28.04.2021
 */
public class OperatorsSpaceChecker extends StyleChecker {

    /**
     * Gets the name of the checker
     *
     * @return Name of the style checker
     */
    @Override
    public String getName() {
        return "Space between operators checker";
    }

    /**
     * Checks whether every operator character have space right before and after
     * them or not.
     *
     * @param codeFile is the file. List of every line.
     * @return The lines that are failed the check.
     */
    @Override
    protected ArrayList<String> checkFile(ArrayList<String> codeFile) {
        ArrayList<String> errorList = new ArrayList<>();

        for (int i = 0; i < codeFile.size(); i++) {
            for (int j = 1; j < codeFile.get(i).length() - 1; j++) {
                if (codeFile.get(i).charAt(j) == '+' && !(codeFile.get(i).charAt(j - 1) == ' ')
                        || !(codeFile.get(i).charAt(j + 1) == ' ')) {
                    errorList.add(codeFile.get(i));
                } else if (codeFile.get(i).charAt(j) == '-' && !(codeFile.get(i).charAt(j - 1) == ' ')
                        || !(codeFile.get(i).charAt(j + 1) == ' ')) {
                    errorList.add(codeFile.get(i));
                } else if (codeFile.get(i).charAt(j) == '*' && !(codeFile.get(i).charAt(j - 1) == ' ')
                        || !(codeFile.get(i).charAt(j + 1) == ' ')) {
                    errorList.add(codeFile.get(i));
                } else if (codeFile.get(i).charAt(j) == '/' && !(codeFile.get(i).charAt(j - 1) == ' ')
                        || !(codeFile.get(i).charAt(j + 1) == ' ')) {
                    errorList.add(codeFile.get(i));
                } else if (codeFile.get(i).charAt(j) == '%' && !(codeFile.get(i).charAt(j - 1) == ' ')
                        || !(codeFile.get(i).charAt(j + 1) == ' ')) {
                    errorList.add(codeFile.get(i));
                }
            }
        }
        return errorList;
    }

}