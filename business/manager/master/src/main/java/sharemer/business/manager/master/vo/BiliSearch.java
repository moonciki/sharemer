package sharemer.business.manager.master.vo;

import java.util.List;

/**
 * Created by 18073 on 2017/6/8.
 */
public class BiliSearch implements java.io.Serializable {

    private static final long serialVersionUID = 4154416530112384081L;

    private List<SearchResult> result;

    public List<SearchResult> getResult() {
        return result;
    }

    public void setResult(List<SearchResult> result) {
        this.result = result;
    }
}
