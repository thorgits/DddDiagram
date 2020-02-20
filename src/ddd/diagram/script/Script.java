package ddd.diagram.script;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author liwenjun
 * @ClassName Script
 * @Date 2019-12-17 17:25
 */
public class Script {
    Style style;
    Footer footer;
    Header header;
    Set<String> relations = new HashSet();
    Set<String> items = new HashSet();
    Set<String> sequences = new HashSet();

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public Footer getFooter() {
        return footer;
    }

    public void setFooter(Footer footer) {
        this.footer = footer;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Set<String> getRelations() {
        return relations;
    }

    public void setRelations(Set<String> relations) {
        this.relations = relations;
    }

    public Set<String> getItems() {
        return items;
    }

    public void setItems(Set<String> items) {
        this.items = items;
    }

    public Set<String> getSequences() {
        return sequences;
    }

    public void setSequences(Set<String> sequences) {
        this.sequences = sequences;
    }
}
