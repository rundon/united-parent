/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.onefly.united.common.interceptor;

import com.baomidou.mybatisplus.core.plugins.InterceptorIgnoreHelper;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.plugins.inner.DataPermissionInterceptor;
import com.onefly.united.common.handler.DataFilterPermissionHandler;
import lombok.*;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SetOperationList;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 数据过滤
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DataFilterInterceptor extends DataPermissionInterceptor {

    private DataFilterPermissionHandler dataPermissionHandler;

    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
        if (!InterceptorIgnoreHelper.willIgnoreDataPermission(ms.getId())) {
            PluginUtils.MPBoundSql mpBs = PluginUtils.mpBoundSql(boundSql);
            mpBs.sql(this.parserSingle(mpBs.sql(), boundSql.getParameterObject()));
        }
    }

    @Override
    protected void processSelect(Select select, int index, String sql, Object obj) {
        SelectBody selectBody = select.getSelectBody();
        if (selectBody instanceof PlainSelect) {
            PlainSelect plainSelect = (PlainSelect) selectBody;
            this.processDataPermission(plainSelect, obj);
        } else if (selectBody instanceof SetOperationList) {
            SetOperationList setOperationList = (SetOperationList) selectBody;
            List<SelectBody> selectBodyList = setOperationList.getSelects();
            selectBodyList.forEach((s) -> {
                PlainSelect plainSelect = (PlainSelect) s;
                this.processDataPermission(plainSelect, obj);
            });
        }
    }

    protected void processDataPermission(PlainSelect plainSelect, Object paramObj) {
        // 判断参数里是否有DataScope对象
        DataScope scope = null;
        if (paramObj instanceof DataScope) {
            scope = (DataScope) paramObj;
        } else if (paramObj instanceof Map) {
            for (Object arg : ((Map) paramObj).values()) {
                if (arg instanceof DataScope) {
                    scope = (DataScope) arg;
                    break;
                }
            }
        }
        if (scope != null) {
            this.dataPermissionHandler.processDataPermission(plainSelect, scope);
        }
    }
}