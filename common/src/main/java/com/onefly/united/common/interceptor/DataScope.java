
package com.onefly.united.common.interceptor;

import com.onefly.united.common.annotation.DataFilter;
import com.onefly.united.common.user.UserDetail;
import lombok.Data;

/**
 * 数据范围
 *
 * @author Mark Rundon
 * @since 1.0.0
 */
@Data
public class DataScope {

    private DataFilter dataFilter;

    private UserDetail user;

    public DataScope(UserDetail user, DataFilter dataFilter) {
        this.user = user;
        this.dataFilter = dataFilter;
    }

    @Override
    public String toString() {
        return "[table=" + this.dataFilter.tableAlias() + ",userId=" + this.dataFilter.userId() + ",dept_id=" + this.dataFilter.deptId() + "]";
    }
}