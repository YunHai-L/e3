package cn.e3mall.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: YunHai
 * @Date: 2018/12/14 10:32
 * @Description:
 */
public class SearchResult implements Serializable {
    private long recordCount;
    private int totalCount;
    private List<SearchItem> itenList;


    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(long recordCount) {
        this.recordCount = recordCount;
    }



    public List<SearchItem> getItenList() {
        return itenList;
    }

    public void setItenList(List<SearchItem> itenList) {
        this.itenList = itenList;
    }
}
