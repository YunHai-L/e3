package cn.e3mall.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: YunHai
 * @Date: 2018/11/10 00:41
 * @Description:
 */
public class EasyUIDataGroupResult implements Serializable{
    private long total;

    private List rows;

    public List getRows() {
        return rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }
}
