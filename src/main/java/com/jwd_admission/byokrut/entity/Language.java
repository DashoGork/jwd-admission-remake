package com.jwd_admission.byokrut.entity;

/**
 * Thus enum contains languages
 */

public enum Language {
    RU("ru"),
    EN("en"),
    DEFAULT(RU.languageName);

    private final String languageName;

    Language(String languageName) {
        this.languageName = languageName;
    }

    /**
     * This method defines which language should be chosen
     * @param language
     * @return
     */
    public static String getLanguage(String language) {
        for (Language lang : Language.values()) {
            if (lang.languageName.equals(language)) {
                return language;
            }
        }
        return DEFAULT.languageName;
    }
}
