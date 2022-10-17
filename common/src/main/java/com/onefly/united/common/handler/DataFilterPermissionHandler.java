package com.onefly.united.common.handler;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.handler.DataPermissionHandler;
import com.onefly.united.common.annotation.DataFilter;
import com.onefly.united.common.interceptor.DataScope;
import com.onefly.united.common.user.UserDetail;
import lombok.RequiredArgsConstructor;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.PlainSelect;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class DataFilterPermissionHandler implements DataPermissionHandler {

    @Override
    public Expression getSqlSegment(Expression where, String mappedStatementId) {
        return null;
    }

    public void processDataPermission(PlainSelect plainSelect, DataScope scope) {
        Expression expression = plainSelect.getWhere();
        DataFilter dataFilter = scope.getDataFilter();
        UserDetail user = scope.getUser();
        //获取表的别名
        String tableAlias = dataFilter.tableAlias();

        //当前用户
        EqualsTo equalsTo = new EqualsTo();
        equalsTo.setLeftExpression(buildColumn(tableAlias, dataFilter.userId()));
        equalsTo.setRightExpression(new LongValue(user.getId()));

        //部门ID列表
        List<Long> deptIdList = user.getDeptIdList();
        if (CollUtil.isNotEmpty(deptIdList)) {

            InExpression inExpression = new InExpression();
            inExpression.setLeftExpression(buildColumn(tableAlias, dataFilter.deptId()));
            ExpressionList expressionList = new ExpressionList();
            List<Expression> expressions = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(deptIdList)) {
                for (Long organizationId : deptIdList) {
                    expressions.add(new LongValue(organizationId));
                }
            }
            expressionList.setExpressions(expressions);
            inExpression.setRightItemsList(expressionList);

            OrExpression orExpression = new OrExpression(equalsTo, new Parenthesis(inExpression));

            expression = ObjectUtils.isNotEmpty(expression) ? new AndExpression(expression, new Parenthesis(orExpression)) : orExpression;
        } else {
            expression = ObjectUtils.isNotEmpty(expression) ? new AndExpression(expression, new Parenthesis(equalsTo)) : equalsTo;
        }

        plainSelect.setWhere(expression);
    }

    /**
     * 构建Column
     *
     * @param dataTableAlias 表别名
     * @param columnName     字段名称
     * @return 带表别名字段
     */
    public static Column buildColumn(String dataTableAlias, String columnName) {
        if (StringUtils.isNotEmpty(dataTableAlias)) {
            columnName = dataTableAlias + StringPool.DOT + columnName;
        }
        return new Column(columnName);
    }
}
