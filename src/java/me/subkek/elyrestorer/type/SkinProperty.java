package me.subkek.elyrestorer.type;

public class SkinProperty {
    private final String value;
    private final String signature;
    public static String TEXTURES_NAME = "textures";

    public SkinProperty(String value, String signature) {
        this.value = value;
        this.signature = signature;
    }

    public String getValue() {
        return value;
    }

    public String getSignature() {
        return signature;
    }
}