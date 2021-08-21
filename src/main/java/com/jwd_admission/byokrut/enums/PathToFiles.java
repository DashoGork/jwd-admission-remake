package com.jwd_admission.byokrut.enums;

public enum PathToFiles {
    MMFPassed("output/passedMMF.ser"),
    RFIKTPassed("output/passedRFIKT.ser"),
    BIOPassed("output/passedBio.ser"),
    FMOPassed("output/passedFMO.ser");

    private final String path;

    PathToFiles(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
