package com.mobingc.personal.config.hibernate;

import org.hibernate.boot.Metadata;
import org.hibernate.dialect.Database;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.dialect.spi.DialectResolutionInfo;
import org.hibernate.engine.jdbc.dialect.spi.DialectResolver;
import org.hibernate.mapping.ForeignKey;
import org.hibernate.tool.schema.internal.StandardForeignKeyExporter;
import org.hibernate.tool.schema.spi.Exporter;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

/**
 * 外键取消方言解析器
 **/
public class ForeignKeyCancelDialectResolver implements DialectResolver {

    @Override
    public Dialect resolveDialect(DialectResolutionInfo info) {
        for (Database database : Database.values()) {
            Dialect dialect = database.resolveDialect(info);
            if (dialect != null) {
                Enhancer enhancer = new Enhancer();
                // 设置超类，cglib是通过继承来实现的
                enhancer.setSuperclass(dialect.getClass());
                // 设置回调函数，不生成外键
                enhancer.setCallback((MethodInterceptor) (object, method, objects, methodProxy) -> {
                    // 直接返回Exporter.NO_COMMANDS
                    if (method.getName().equals("getForeignKeyExporter")) {
                        return new StandardForeignKeyExporter((Dialect) object) {
                            @Override
                            public String[] getSqlCreateStrings(ForeignKey foreignKey, Metadata metadata) {
                                return Exporter.NO_COMMANDS;
                            }

                            @Override
                            public String[] getSqlDropStrings(ForeignKey foreignKey, Metadata metadata) {
                                return Exporter.NO_COMMANDS;
                            }
                        };
                    }
                    return methodProxy.invokeSuper(object, objects);
                });
                return (Dialect) enhancer.create();
            }
        }
        return null;
    }

}
