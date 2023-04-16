import java.util.concurrent.CopyOnWriteArraySet;

public class SiteMapNode {
    private String url;
    private volatile SiteMapNode parent;
    private volatile int depth;
    private volatile CopyOnWriteArraySet<SiteMapNode> sublinks;

    public SiteMapNode(String url) {
        this.url = url;
        sublinks = new CopyOnWriteArraySet<>();
        depth = 0;
        parent = null;

    }

    public void addSublinks(SiteMapNode sublink) {
        if (!sublinks.contains(sublink) && sublink.getUrl().startsWith(url)) {
            this.sublinks.add(sublink);
            sublink.setParent(this);
        }
    }

    private void setParent(SiteMapNode siteMapNode) {
        synchronized (this) {
            this.parent = siteMapNode;
            this.depth = setDepth();
        }
    }

    public int getDepth() {
        return depth;
    }

    private int setDepth() {
        if (parent == null) {
            return 0;
        }
        return 1 + parent.getDepth();
    }


    public CopyOnWriteArraySet<SiteMapNode> getSublinks() {
        return sublinks;
    }

    public String getUrl() {
        return url;
    }
}
