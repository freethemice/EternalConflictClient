package net.eternalconflict.www.holders;

public class DownloadHolder {
    private String url;
    private String version;
    private String key;

    public DownloadHolder(String url, String version, String key) {
        this.url = url;
        this.version = version;
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public String getVersion() {
        return version;
    }

    public String getKey() {
        return key;
    }
}
