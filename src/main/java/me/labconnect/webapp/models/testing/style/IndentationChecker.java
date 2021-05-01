package me.labconnect.webapp.models.testing.style;

import java.util.ArrayList;

/**
 * Check if the source code is indented correctly
 *
 * @author Berk Çakar
 * @author Vedat Eren Arıcan
 * @version 23.04.2021
 */
public class IndentationChecker extends StyleChecker {

    /**
     * Counts the leading spaces of a line
     *
     * @param line A line containing a statement
     * @return Number of leading spaces
     */
    private int countLeadingSpaces(String line) {
        int spaceCount = 0;

        if (line.length() > 0) {
            for (int leadingIndex = 0; (line.charAt(leadingIndex) == ' ')
                    && (leadingIndex < line.length() - 1); leadingIndex++) {
                spaceCount++;
            }
        } else {
            return 0;
        }

        return spaceCount;
    }

    /**
     * Checks every line in a file for indentation errors.
     *
     * @param codeFile The file. List of every line.
     * @return List of lines that indentation violation occured
     *
     * I tried my best but regex is insufficient for many use cases...
     */
    @Override
    protected ArrayList<String> checkFile(ArrayList<String> codeFile) {
            final int INDENTATION_SPACE = 4;
            ArrayList<String> errorList = new ArrayList<>();
            int braces = 0;
            boolean casePresent = false;
            boolean defaultPresent = false;
            boolean lineWrapped = false;
            boolean sameLineAdded = false;
            for (int i = 0; i < codeFile.size(); i++) {
                sameLineAdded = false;
                String line = codeFile.get(i);

                if (!line.isBlank() && line.trim().charAt(0) != '*') {
                    if (line.contains("}")) {
                        if (defaultPresent) {
                            braces--;
                            defaultPresent = false;
                        }
                        braces--;
                    }

                    if ((braces == 1 || RegexHelper.ifRegexMatcher( line ) || RegexHelper.elseifRegexMatcher( line ) || RegexHelper.elseRegexMatcher( line ) || RegexHelper.switchRegexMatcher( line ) ||
                        RegexHelper.forRegexMatcher( line ) || RegexHelper.whileRegexMatcher( line ) || line.contains("catch")) &&
                        line.contains("(") && !line.contains("{")) {
                        lineWrapped = true;
                    }

                    if (line.contains(" default:")) {
                        braces--;
                        casePresent = false;
                        defaultPresent = true;
                    }

                    if (line.contains(" case ")) {
                        if (casePresent) {
                            braces--;
                        }
                        casePresent = true;
                    }

                    if (!lineWrapped) {
                        for (int j = 0; j < INDENTATION_SPACE  * braces; j++) {
                            if (line.charAt(j) != ' ') {
                                errorList.add( codeFile.get(i) );
                                sameLineAdded = true;
                                break;
                            }
                        }

                        if (line.length() > 1 + INDENTATION_SPACE  * braces &&
                            line.charAt(INDENTATION_SPACE  * braces) == ' ' &&
                            line.charAt(1 + INDENTATION_SPACE  * braces) != '*' && sameLineAdded == false) {
                                // if ( errorList.indexOf( codeFile.get(i) ) == -1 ) {
                                    if ( i > 0 ) {
                                        //if ( !(codeFile.get( i - 1 ).length() > 80) && !(codeFile.get(i).trim().endsWith(";"))) {
                                            errorList.add( codeFile.get(i) );
                                        //}
                                    }
                                // }
                        }
                    }

                    if (lineWrapped && (line.contains("{") || line.contains(";"))) {
                        lineWrapped = false;
                    }

                    if (!RegexHelper.ifRegexMatcher( line ) && !RegexHelper.switchRegexMatcher( line ) && !RegexHelper.forRegexMatcher( line ) &&
                        !RegexHelper.whileRegexMatcher( line ) && !line.contains("catch") &&
                        !RegexHelper.classRegexMatcher( line ) && !RegexHelper.elseRegexMatcher( line ) &&
                        !line.contains("finally") && !line.contains("try") && RegexHelper.elseifRegexMatcher( line ) &&
                        !RegexHelper.doRegexMatcher( line ) && !line.contains("{}") &&
                            line.charAt(line.length() - 1) != ';') {
                        lineWrapped = true;
                    }

                    if (line.contains(" default:") || line.contains("{") || line.contains(" case ")) {
                        braces++;
                    }
                }
            }
            return errorList;
        }

    /**
     * Gets the name of the checker
     *
     * @return Name of the style checker
     */
    @Override
    public String getName() {
        return "Indentation checker";
    }
}
