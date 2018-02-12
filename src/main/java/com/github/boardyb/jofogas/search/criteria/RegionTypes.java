package com.github.boardyb.jofogas.search.criteria;

public enum RegionTypes {
    DEFAULT("magyarorszag"),
    BUDAPEST("budapest"),
    BACS_KISKUN("bacs-kiskun"),
    BARANYA("baranya"),
    BEKES("bekes"),
    BORSOD_ABAUJ_ZEMPLEN("borsod-abauj-zemplen"),
    CSONGRAD("csongrad"),
    FEJER("fejer"),
    GYOR_MOSON_SOPRON("gyor-moson-sopron"),
    HAJDU_BIHAR("hajdu-bihar"),
    HEVES("heves"),
    JASZ_NAGYKUN_SZOLNOK("jasz-nagykun-szolnok"),
    KOMAROM_ESZTERGOM("komarom-esztergom"),
    NOGRAD("nograd"),
    PEST("pest"),
    SOMOGY("somogy"),
    SZABOLCS_SZATMAR_BEREG("szabolcs-szatmar-bereg"),
    TOLNA("tolna"),
    VAS("vas"),
    VESZPREM("veszprem"),
    ZALA("zala");

    private final String value;

    RegionTypes(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
